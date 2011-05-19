import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {
	Reservation test1;
	Reservation test2;
	Date startDate;
	Date endDate;
	@Before
	public void setUp(){
		Calendar cal = Calendar.getInstance();
    	// Start Date is 6 days from now
    	cal.add(Calendar.DATE, 6);
    	startDate = cal.getTime();
    	// End Date is 13 days, stay period is 7
    	cal.add(Calendar.DATE, 7);
    	endDate = cal.getTime();
    	
    	test1 = new Reservation();
    	test2 = new Reservation();
    	test1.startDate = startDate;
    	test1.endDate = endDate;
    	test2.startDate = startDate;
    	test2.endDate = endDate;
	}
	@Test
	public void testGetRoomCount(){
		long count = Room.count();
		assertEquals(count,6l);
	}
    @Test
    public void testCreateRservation(){
    	Calendar cal = Calendar.getInstance();
    	// Start Date is 6 days from now
    	cal.add(Calendar.DATE, 6);
    	Date startDate = cal.getTime();
    	// End Date is 13 days, stay period is 7
    	cal.add(Calendar.DATE, 7);
    	Date endDate = cal.getTime();
    	Reservation test = new Reservation();
    	
    	test.startDate = startDate;
    	test.endDate = endDate;
    	test.numberOfGuest = 2;
    	test.specialRequest = " None Smoking";
    	RoomType standardRoom = RoomType.find("byType","Standard").first();
    	Room room = Room.find("byRoomType",standardRoom).first();
    	// This is just to test some basic adding / removing
    	// Should have restriction on adding the reservation, so that no duplication
    	// of reservation occurs
    	room.addReservation(test);
    
    	test.save();
    	room.save();
    	// Let's get back the object
    	Reservation test2 = Reservation.findById(test.id);
    	assertEquals(test.startDate, test2.startDate);
    } 
    
    @Test
    public void testCheckAvailableRange(){
    	Calendar cal = Calendar.getInstance();
    	// Start Date is 6 days from now
    	cal.add(Calendar.DATE, 6);
    	Date startDate = cal.getTime();
    	// End Date is 13 days, stay period is 7
    	cal.add(Calendar.DATE, 7);
    	Date endDate = cal.getTime();
    	long roomCount = Room.count();
    	List<Room> availableRooms = Room.findAvailableRoom(startDate, endDate, null);
    	// One reservation is alread made, thus only 5 room available
    	// Should fix since this is dependent from the previous test run
    	assertEquals(roomCount - 1, availableRooms.size());
    }
    
    @Test
    public void testDoubleRersevationCheckAvailableFirst(){
    	
    	List<Room> targetRooms = Room.findAvailableRoom(startDate, endDate, "Suit");
    	System.out.println(" Room list"  +  targetRooms);
    	// First book those rooms
    	for( Room it : targetRooms){
    		it.addReservation(test1);
    	}
    	
    	// Try again getting the list again. No room should be available
    	List<Room> targetRooms2 = Room.findAvailableRoom(startDate, endDate, "Suit");
    	assertEquals(0, targetRooms2.size());
    }
    @Test
    public void testDoubleBookingWithSameReservation(){
    	List<Room> targetRooms = Room.findAvailableRoom(startDate, endDate, "Standard");
    	System.out.println(" Room List " + printRoomList(targetRooms));
    	Room pick = targetRooms.get(0);
    	// Should pass
    	//System.out.println(" Done making reservation " + 
    	Room firstResult = pick.addReservation(test1);
    	
    	pick.save();
    	System.out.println(" First Room " + firstResult.toString());
    	// Fail at here
    	Room result = pick.addReservation(test1);
    	assertNull(result);
    }
    
    @Test
    public void testInvalidBookingFailWithLateEndDate(){
    	List<Room> targetRooms = Room.findAvailableRoom(startDate, endDate, "Standard");
    	System.out.println(" Room List " + printRoomList(targetRooms));
    	Room pick = targetRooms.get(0);
    	// Should pass
    	//System.out.println(" Done making reservation " + 
    	Room firstResult = pick.addReservation(test1);
    	pick.save();
    	Room firstResult2 = pick.addReservation(test1);
    	System.out.println(" First Try " + firstResult.toString());
    	System.out.println(" Second Try " + firstResult2.toString());
    	// Create another Reservation with start date later than test1
    	Reservation test = new Reservation();
    	test.endDate = test1.endDate;
    	
    }
    @Test
    public void testGetRoomTypeList(){
    	List<String> roomTypes = RoomType.getRoomTypeList();
    	for(String it : roomTypes){
    		System.out.println(it);
    	}
    }
    private String printRoomList(List<Room> rooms){
    	StringBuilder builder = new StringBuilder();
    	for( Room it : rooms){
    		builder.append(it.roomNumber).append("-");
    	}
    	return builder.toString();
    }
}

