package dataobjects;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/***
 * 
 * @author elizabeth fischer
 * Container class for a single Business object
 *
 */
public class Business {

	private UUID id;
	private String name;
	private String updated_at,created_at;
	private List<Long> hours;
	

	public Business() {
		hours = new ArrayList<Long>();
	}
	
	public Business(UUID id, String name, String updated_at, String created_at, List<Long>hours) {
		this.id = id;
		this.name = name;
		this.updated_at = updated_at;
		this.created_at = created_at;
		this.hours = hours;
	}
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public List<Long> getHours() {
		return hours;
	}

	public void setHours(List<Long> hours) {
		this.hours = hours;
	}
	public void addHour(Long nxtHour) {
		hours.add(nxtHour);
	}

	public String toString()
	{
		return id.toString() + " " + name + " hours: " + hours.toString();
	}
}
