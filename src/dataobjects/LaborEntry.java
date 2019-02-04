package dataobjects;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;
/***
 * 
 * @author elizabeth fischer
 * Simple container class for a single labor_entries object.  This object reflectd one shift of 
 * a single employee at a particular business
 *
 */
public class LaborEntry {
	private UUID id,business_id,employee_id;
	private String name;
	private Timestamp clock_in,clock_out;
	private String updated_at,created_at;
	private Double pay_rate;
	
	public LaborEntry (UUID id, UUID business_id,UUID employee_id,String name,
			String clock_in,String clock_out,String updated_at,String created_at, Double pay_rate) {
		this.id = id;
		this.business_id = business_id;
		this.employee_id = employee_id;
		this.name = name;
		this.clock_in = Timestamp.valueOf(clock_in);
		this.clock_out = Timestamp.valueOf(clock_out);
		this.pay_rate = pay_rate;
		this.updated_at = updated_at;
		this.created_at = created_at;
	}
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public UUID getBusiness_id() {
		return business_id;
	}
	public void setBusiness_id(UUID business_id) {
		this.business_id = business_id;
	}
	public UUID getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(UUID employee_id) {
		this.employee_id = employee_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Timestamp getClockIn() {
		return clock_in;
	}
	public void setClockIn(Timestamp clock_in) {
		this.clock_in = clock_in;
	}
	public Timestamp getClockOut() {
		return clock_out;
	}
	public void setClockOut(Timestamp clock_out) {
		this.clock_out = clock_out;
	}
	public String getUpdatedAt() {
		return updated_at;
	}

	public void setUpdatedAt(String updated_at) {
		this.updated_at = updated_at;
	}

	public String getCreatedAt() {
		return created_at;
	}

	public void setCreatedAt(String created_at) {
		this.created_at = created_at;
	}
	public Double getPay_rate() {
		return pay_rate;
	}
	public void setPay_rate(Double pay_rate) {
		this.pay_rate = pay_rate;
	}
}
