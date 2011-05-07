/**
 * 
 */
package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;

/**
 * @author kiet
 *  A class to represent a reservation made by a guest for future visit
 *  One reservation can contain many room , calculate by room capacity
 */
@Entity
public class Reservation extends Model {
	public Date startDate;
	public Date endDate;
	// Number of guess, adult only
	public int numberOfGuest;
	public String specialRequest;
	
	@ManyToMany
	public List<Room> roomList = new ArrayList<Room>();
	
	public Reservation addRoom(Room aRoom){
		roomList.add(aRoom);
		return this;
		
	}
	public List<Room> getRoomList() {
		return roomList;
	}


	public String toString(){
		return "From " +startDate + " to " + endDate + " Request:" + specialRequest + " Room " + roomList; 
	}
}
