import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

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
    	
    	Room room = Room.find("byRoomType", "Standard").first();
    	// This is just to test some basic adding / removing
    	// Should have restriction on adding the reservation, so that no duplication
    	// of reservation occurs
    	test.addRoom(room);
    	System.out.println( " Rerservation ready to be save " + test.toString());
    	test.save();
    	// Let's get back the object
    	Reservation test2 = Reservation.findById(test.id);
    	assertEquals(test.startDate, test2.startDate);
    } 

}

