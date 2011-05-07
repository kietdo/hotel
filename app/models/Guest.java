/**
 * 
 */
package models;

import play.db.jpa.*;
import javax.persistence.*;
/**
 * @author kiet
 *
 */
@Entity
public class Guest extends Model {
	public String lastName;
	public String firstName;
	public String email;
	public String address;
	@ManyToOne
	public Room room;
	public String toString(){
		return firstName + " "+ lastName;
	}
}
