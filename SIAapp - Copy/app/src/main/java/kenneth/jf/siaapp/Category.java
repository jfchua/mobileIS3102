package kenneth.jf.siaapp;

/**
 * Created by User on 25/10/2016.
 */

public class Category {
    String id = null;
    String name= null;
    double price=0.0;
    int numTickets = 0;
    Event event;
    //Set<Ticket> tixList = null;

    public Category(String id,String name, double price, int numTickets, Event event){
        this.id = id;
        this.name = name;
        this.price = price;
        this.numTickets = numTickets;
        this.event = event;
    }







}
