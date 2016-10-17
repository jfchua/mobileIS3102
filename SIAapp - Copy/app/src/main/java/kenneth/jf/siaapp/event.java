package kenneth.jf.siaapp;

import android.graphics.drawable.Drawable;

/**
 * Created by User on 16/10/2016.
 */

public class Event
{
    public String title;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //public Drawable productImage;
    public String description;
    public double price;
    public boolean selected;

    public Event(String title, String description,
                   double price) {
        this.title = title;
        //this.productImage = productImage;
        this.description = description;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
