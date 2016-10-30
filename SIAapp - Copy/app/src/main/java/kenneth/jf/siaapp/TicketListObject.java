package kenneth.jf.siaapp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import static android.R.attr.name;
import static android.R.id.message;

/**
 * Created by Kenneth on 13/10/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketListObject {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("categoryName")
    private String TicketName;
    @JsonProperty("price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @JsonProperty("price")
    private double price;

    @JsonProperty("numOfTickets")
    private int numTix;



    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicketName() {
        return TicketName;
    }

    public void setTicketName(String TicketName) {
        this.TicketName = TicketName;
    }

    public String toString(){
        return "ID is " + id + " Name is " + TicketName;
    }

}
