package controllers;

import play.*;

import play.mvc.*;
import play.data.binding.*;
import play.data.validation.*;
import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
    	List<String> roomTypes = RoomType.getRoomTypeList();
        render("/Application/index2.html",roomTypes);
    }

    public static void searchRoom(@Required @As("MM/dd/yyyy") Date startDate,@Required @As("MM/dd/yyyy") Date endDate, String roomType){
    	System.out.println(" Search for " + "sDate " +":" + startDate
    			    + " eDate " +":" + endDate + "room type " + roomType); 
    	
    	List<Room> rooms = Room.findAvailableRoom(startDate,endDate,roomType);
    	System.out.println(" Available room " + rooms.size());
    	render("Application/test2.html",rooms);
    }
    public static void logOut(){
    	index();
    }
    public static void test(){
    	render("/Application/layout.html");
    }
    
    public static void test2(){
    	String post = " Hi " ;
    	List<String> allPost = new ArrayList<String>();
    	allPost.add("Post #1");
    	allPost.add("Post #2");
    	allPost.add("Post #3");
    	render(post, allPost);
    }
}