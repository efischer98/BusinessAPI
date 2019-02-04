package dataobjects;

import java.sql.Timestamp;
import java.util.UUID;

/***
 * 
 * @author elizabeth fischer
 * Container class for a single menu_items entry
 *
 */
public class MenuItem {
	private UUID id;
	private UUID business_id;
	private String name;
	private Long cost;
	private Long price;		// I guess menu items don't have any cents . . .
	private String updated_at,created_at;
	
	public MenuItem(UUID id,UUID business_id,String name, Long cost, Long price, String updated_at,String created_at) {
		this.id = id;
		this.business_id = business_id;
		this.name = name;
		this.cost = cost;
		this.price = price;
		this.updated_at = updated_at;
		this.created_at = created_at;
	}
	
	public UUID getId() {
		return business_id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public UUID getBusinessId() {
		return business_id;
	}
	public void setBusinessId(UUID business_id) {
		this.business_id = business_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getCost() {
		return cost;
	}
	public void setCost(Long cost) {
		this.cost = cost;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
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
	
	/***
	 * For debug
	 */
	public String toString() {
		return name + " id:" + id.toString() + ", biz:" + business_id.toString() + " cost/price" + cost.toString() + "/" + price.toString();
	}

}
