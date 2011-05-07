/**
 * 
 */
package models;
import javax.persistence.Entity;

import play.db.jpa.*;
/**
 * @author kiet
 * A class to represent the price point for a service/room
 */
@Entity
public class Price extends Model{
	public double amount;
	public String currency;
	
	public String toString(){
		return amount +" "+ currency;
	}
}
