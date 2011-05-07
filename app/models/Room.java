/**
 * 
 */
package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;

/**
 * @author kiet
 *
 */
@Entity
public class Room extends Model{
	public String roomType;
	public String roomNumber;
	public int capacity;
	@OneToOne
	public Price price;
	@OneToMany
	public List<Guest> currentGuestList = new ArrayList<Guest>();	

	@ManyToMany
	public List<Reservation> reservationList = new ArrayList<Reservation>();
	
	public Room addGuest(Guest aGuest){
		currentGuestList.add(aGuest);
		return this;
	}
	/*
	 * 
	 */
	public static List<Room> findAvailableRoom(Date startDate, Date endDate, String roomType){
		List<Room> allRoom = Room.findAll();
		List<Room> availableRoomList = new ArrayList<Room>();
		// Walk through the available List
		for (Room it : allRoom){
			
			if(availableDateRange(it.getReservations(), startDate,endDate)){
				if((roomType == null || "".equals(roomType)) || (it.roomType.equalsIgnoreCase(roomType))){
					availableRoomList.add(it);
				}
			}
		}
		return availableRoomList;
	}
	// Private method to check if the given date range is available for a single room
	private static boolean availableDateRange(List<Reservation> reservationList, Date startDate, Date endDate){
		for( Reservation it : reservationList){
			if(it.startDate.before(startDate) && it.endDate.before(startDate)){
				return true;
			}
		}
		return false;
	}
	// Boiler plate getter and setter should go after this
	public List<Guest> getGuestList(){
		return currentGuestList;
	}
	public Room addReservation(Reservation aReservation){
		reservationList.add(aReservation);
		return this;
	}
	public List<Reservation> getReservations() {
		return reservationList;
	}

	public String toString(){
		return roomNumber +"-"+roomType+"-"+capacity;
	}
}
