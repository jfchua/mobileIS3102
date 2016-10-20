package kenneth.jf.siaapp;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by User on 16/10/2016.
 */

public class Event implements Parcelable
{
    String code = null;
    String name = null;
    String price = null;
    boolean selected = false;

    public Event(String name, String price, boolean selected) {
        super();
        this.name = name;
        this.price = price;
        this.selected = selected;
    }

    protected Event(Parcel in) {
        code = in.readString();
        name = in.readString();
        price = in.readString();
        selected = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(name);
        dest.writeString(price);
        dest.writeByte((byte) (selected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
}
