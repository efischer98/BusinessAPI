package dataobjects;

import java.sql.Timestamp;
import java.util.UUID;

/***
 * 
 * @author elizabeth fischer
 * Container class for one item associated with a check
 *
 */
public class OrderedItem {
	private UUID id,businessID,employeeID,checkID,itemID;   // don't know what item_id references
	private String name;
	private Integer cost;
	private Integer price;
	private Boolean voided;
	private String updated_at,created_at;
	
	public OrderedItem (UUID id, UUID businessID,UUID employeeID,UUID checkID, UUID itemID,
			String name,Integer cost,Integer price,Boolean voided,String updated_at, String created_at) {
		this.id = id;
		this.businessID = businessID;
		this.employeeID = employeeID;
		this.checkID = checkID;
		this.itemID = itemID;
		this.name = name;
		this.cost = cost;
		this.price = price;
		this.voided = voided;
		this.updated_at = updated_at;
		this.created_at = created_at;
	}
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public UUID getBusinessID() {
		return businessID;
	}
	public void setBusiness_id(UUID businessID) {
		this.businessID = businessID;
	}
	public UUID getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(UUID employeeID) {
		this.employeeID = employeeID;
	}
	public UUID getCheckID() {
		return checkID;
	}
	public void setCheckID(UUID checkID) {
		this.checkID = checkID;
	}
	public UUID getItemID() {
		return itemID;
	}
	public void setItemID(UUID itemID) {
		this.itemID = itemID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCost() {
		return cost;
	}
	public void setCost(Integer cost) {
		this.cost = cost;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Boolean getVoided() {
		return voided;
	}
	public void setVoided(Boolean voided) {
		this.voided = voided;
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
