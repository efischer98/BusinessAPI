package dataobjects;
/***
 * @author elizabeth fischer
 * Container class for a single Check.  Check represents one table waited on by an employee in a shift at
 * a business.  Shift is represented by LaborEntry object
 *
 */

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class Check {
	private UUID id,business_id,employee_id;
	private String name;
	private Boolean closed;
	private String updated_at,created_at;
	Timestamp closed_at;
	private List<OrderedItem> items;
	

	public Check (UUID id,UUID business_id,UUID employee_id,String name, Boolean closed, String updated_at,
				String created_at,String closed_at)
	{
		this.id = id;
		this.business_id = business_id;
		this.employee_id = employee_id;
		this.name = name;
		this.closed = closed;
		this.updated_at = updated_at;
		this.created_at = created_at;
		this.closed_at = Timestamp.valueOf(closed_at);
		this.items = null;
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
	public Boolean getClosed() {
		return closed;
	}
	public void setClosed(Boolean closed) {
		this.closed = closed;
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
	public Timestamp getClosedAt() {
		return closed_at;
	}
	public void setClosedAt(Timestamp closed_at) {
		this.closed_at = closed_at;
	}
	public List<OrderedItem> getItems() {
		return items;
	}

	public void setItems(List<OrderedItem> items) {
		this.items = items;
	}

}
