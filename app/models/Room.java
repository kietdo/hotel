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
	@OneToOne
	public RoomType roomType;
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
				if((roomType == null || "".equals(roomType)) || (it.roomType.type.equalsIgnoreCase(roomType))){
					availableRoomList.add(it);
				}
			}
		}
		return availableRoomList;
	}
	// Private method to check if the given date range is available for a single room
	private static boolean availableDateRange(List<Reservation> reservationList, Date startDate, Date endDate){
		// No reservation means all available
		if(reservationList.size() == 0 )
			return true;
		for( Reservation it : reservationList){
			if(it.startDate.before(startDate) && it.endDate.before(startDate)){
				return true;
			}
		}
		return false;
	}
	
	public List<Guest> getGuestList(){
		return currentGuestList;
	}
	/**
	 * 
	 * @param aReservation
	 * @return
	 */
	public Room addReservation(Reservation aReservation){
		// Need to check if this reservation would violate the existing booking list
		if(isAcceptable(aReservation,this.reservationList)){
			reservationList.add(aReservation);
			// Will need to add this room to the reservation as well
			// 	Need to manage the dependency
			aReservation.addRoom(this);
			// Save this to db as well
			
			aReservation.save();
			return this;
		}
		else 
			return null;
		
	}
	public List<Reservation> getReservations() {
		return reservationList;
	}

	public String toString(){
		return roomNumber +"-"+roomType+"-"+capacity + "-" +reservationList.toString();
	}
	
	// Private method to check if one Reservation period is acceptable from an existing list of 
	private boolean isAcceptable(Reservation reservation, List<Reservation> currentBooking){
		if (currentBooking.size() == 0) 
			return true;
		for(Reservation it : currentBooking) {
			if( it.equals(reservation)){
				return false;
			}
			// Start Date fail between the given range
	     	else if(it.startDate.after(reservation.startDate) && it.startDate.after(reservation.endDate)){
				return false;
			}
			// Same start and end dates
			else if(it.startDate.equals(reservation.startDate) && it.startDate.equals(reservation.endDate)){
				return false;
			}
			// Start earlier, end ealier than the end Date but still before the start Date
			else if(it.startDate.before(reservation.startDate) && it.endDate.before(reservation.endDate)
					&& it.endDate.after(reservation.startDate)){
				return false;
			}
		}
		// Should be OK to book
		return true;
	}
}
