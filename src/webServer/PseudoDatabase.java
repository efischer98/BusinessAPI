package webServer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import apiCalls.Caller;
import dataobjects.Business;
import dataobjects.Check;
import dataobjects.Employee;
import dataobjects.LaborEntry;
import dataobjects.MenuItem;
import dataobjects.OrderedItem;

/**
 * Singleton class which takes the place of a database.  As calls are made to it, it calls the 
 * defined POS API to get information it lacks.  Once a particular "branch" (such as all employees
 * for a particular business) has been populated, it assumes it has everything it needs and does
 * not call the businessAPI again.
 * 
 * Note - no particular effort is being made here to make the calls super efficient.  Things are in
 * maps and lists and the program has to iterate through them.  Were this a real database, all would be 
 * done by doing targeted queries with, say, date ranges, instead of looking manually through all the
 * records.
 * @author elizabeth fischer
 *
 */
public class PseudoDatabase {
	private static PseudoDatabase instance_ = null;
	private static Boolean init_ = false;
	private Map<UUID,Business> businesses_;
	private Map<UUID,List<Employee>> employees_;
	private Map<UUID,List<MenuItem>> menuItems_;
	private Map<UUID,List<Check>> checks_;
	private Map<UUID,Map<UUID,List<OrderedItem>>> orderedItems_;
	private Map<UUID,Map<UUID,List<LaborEntry>>> laborEntries_;
	
	private Object sync = new Object();
	
	private PseudoDatabase() {
		businesses_ = null;
		employees_ = new HashMap<UUID,List<Employee>>();
		menuItems_ = new HashMap<UUID,List<MenuItem>>();
		laborEntries_ = new HashMap<UUID,Map<UUID,List<LaborEntry>>>();
		checks_ = new HashMap<UUID,List<Check>>();
		orderedItems_ = new HashMap<UUID,Map<UUID,List<OrderedItem>>>();
	}
	
	public static PseudoDatabase getInstance()	{
		if (instance_ == null)
		{
			synchronized (init_) {
				if (instance_ == null) {
					instance_ = new PseudoDatabase();
					init_ = true;					
				}
			}
		}
		return instance_;
	}
	
	/**
	 * Returns a single business identified by the businessId.  If business list has not
	 * yet been populated, does so.  If no such business exists in list (and list is already
	 * populated) just returns null
	 * @param businessId - id of desired business
	 * @return
	 */
	public Business getBusiness(UUID businessId) {
		Business business = null;
		checkBusinesses();
		if (businesses_.containsKey(businessId))
			business = businesses_.get(businessId);
		
		return business;
	}
	
	/**
	 * returns a list of all the businesses
	 * @return
	 */
	public List<Business> getBusinesses() {
		checkBusinesses();
		return new ArrayList<Business> (businesses_.values());
	}
	
	private synchronized void checkBusinesses()
	{
		if (businesses_ == null) {
			List<Business> bizList = Caller.getBusinesses();
			for (Business biz:bizList) {
				businesses_.put(biz.getId(), biz);
			}
		}
	}
	
	public List<MenuItem> getMenuItems (UUID businessID)
	{
		checkMenuItems(businessID);
		if (businesses_.containsKey(businessID)) {
			return menuItems_.get(businessID);
		}
		else
			return null;
	}
	private synchronized void checkMenuItems(UUID businessID)
	{
		checkBusinesses();
		if (businesses_.containsKey(businessID)) {
			if (!menuItems_.containsKey(businessID)) {
				// no error checking here.  We're assuming that, if a businessID is found that
				//		there will be a list of menu items for it
				List<MenuItem> items = Caller.getMenuItems(businessID);
				menuItems_.put(businessID,items);
			}
		}
		
	}
	
	public List<LaborEntry> getLaborEntries(UUID businessID, UUID employeeID) {
		List<LaborEntry> entries = new ArrayList<LaborEntry>();
		checkLabor(businessID, employeeID);
		if ((laborEntries_.containsKey(businessID)) && (laborEntries_.get(businessID).containsKey(employeeID)))
			return laborEntries_.get(businessID).get(employeeID);
		else
			return entries;
	}
	
	/**
	 * See if we've already retrieved info on the specified biz/employee pairing.  If so,
	 * go ahead and return.  If not, call the Avero reporting API and get this info
	 * @param businessID
	 * @param employeeID
	 */
	public synchronized void checkLabor (UUID businessID, UUID employeeID) {
		checkBusinesses();
		// don't bother do to this if the business ID passed in makes no sense
		if (businesses_.containsKey(businessID)) {
			// have we looked up labor times for this biz before?
			if (!laborEntries_.containsKey(businessID)) {
				Map<UUID,List<LaborEntry>> employeeMap = new HashMap<UUID,List<LaborEntry>>();
				laborEntries_.put(businessID,employeeMap);
			}
			// do we already have labor entries for this employee and this biz?
			if (!(laborEntries_.get(businessID).containsKey(employeeID)))
			{
				List<LaborEntry> allEntries = Caller.getLaborEntries(businessID,employeeID);
				laborEntries_.get(businessID).put(employeeID, allEntries);
			}
		}
	}
	
	public List<Employee> getEmployees(UUID businessID) {
		List<Employee> employees = new ArrayList<Employee>();
		checkEmployees(businessID);
		if (employees_.containsKey(businessID))
			return employees_.get(businessID);
			else
				return employees;
	}
	/**
	 * See if we've already retrieved the list of employees from the specified business.  If
	 * so, return without doing anything.  Otherwise, call the Avero businessAPI and get them
	 * @param businessID
	 */
	public synchronized void checkEmployees(UUID businessID) {
		checkBusinesses();
		if (businesses_.containsKey(businessID)) {
			if (!employees_.containsKey(businessID)) {
				List<Employee> employees = Caller.getEmployees(businessID);
				employees_.put(businessID, employees);
			}
		}
	}
	public List<Check> getChecks(UUID businessID,Timestamp startTime,Timestamp endTime) {
		List<Check> checks = new ArrayList<Check>();
		checkChecks(businessID);		// be sure we've already gotten checks for this biz from API
		if (checks_.containsKey(businessID)) {
			for (Check nextCheck: checks_.get(businessID)) {
				Timestamp time = nextCheck.getClosedAt();
				if (((time.after(startTime)) && (time.before(endTime))) ||(time.equals(startTime) || (time.equals(endTime)))) {
					checks.add(nextCheck);
				}
			}
		}
		
		return checks;
	}
	
	/**
	 * checks the map of businessID --> list of checks to see if we've yet downloaded
	 * all the checks for the specified business.  If not, calls to do so.  If business ID
	 * does not exist (not in list of businesses) call is ignored.
	 * @param businessID
	 */
	public synchronized void checkChecks(UUID businessID) {
		checkBusinesses();
		if (businesses_.containsKey(businessID)) {
			if (!checks_.containsKey(businessID)) {
				List<Check> allChecks = Caller.getChecks(businessID);
				checks_.put(businessID,  allChecks);
			}
		}
	}
	
	public List<OrderedItem> getOrderedItems(UUID businessID, UUID checkID) {
		checkOrderedItems(businessID);
		List<OrderedItem> items = new ArrayList<OrderedItem>();
		if ((orderedItems_.containsKey(businessID)) && (orderedItems_.get(businessID).containsKey(checkID)))
			return orderedItems_.get(businessID).get(checkID);
		else
			return items;   // nothing in list
		
	}
	
	/**
	 * As with other checkXXX calls, this one verifies whether or not the items for the specified
	 * check have already been retrieved.  If not, calls the Avero businessAPI to get the info
	 */
	public synchronized void checkOrderedItems(UUID businessID) {
		checkBusinesses();
		// is this a real business?  Cause I'm suspicious.
		if (businesses_.containsKey(businessID)) {
			// OK have we already populated items for this check?
			if (!orderedItems_.containsKey(businessID)) {
				Map<UUID,List<OrderedItem>> thisBizItems = new HashMap<UUID,List<OrderedItem>>();
				List<OrderedItem> items = Caller.getOrderedItems(businessID);
				for (OrderedItem item: items) {
					if (!thisBizItems.containsKey(item.getCheckID())) {
						List<OrderedItem> newList = new ArrayList<OrderedItem>();
						thisBizItems.put(item.getCheckID(), newList);
					}
					thisBizItems.get(item.getCheckID()).add(item);
				}
				orderedItems_.put(businessID, thisBizItems);
			}
		}
	}
}
