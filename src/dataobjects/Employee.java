package dataobjects;

import java.sql.Timestamp;
import java.util.UUID;

/***
 * 
 * @author elizabeth fischer
 * Container class for one Employees record
 *
 */
public class Employee {
	private UUID id;
	private UUID business_id;
	private String first_name,last_name;
	private Double pay_rate;
	private String updated_at,created_at;
	
	public Employee(UUID id, UUID business_id, String first_name, String last_name, Double pay_rate, String updated_at, String created_at) {
		this.id = id;
		this.business_id  = business_id;
		this.first_name = first_name;
		this.last_name = last_name;
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
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public Double getPay_rate() {
		return pay_rate;
	}
	public void setPay_rate(Double pay_rate) {
		this.pay_rate = pay_rate;
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
	
	

}
