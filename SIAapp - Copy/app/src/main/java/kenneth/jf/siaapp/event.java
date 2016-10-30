package kenneth.jf.siaapp;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by User on 16/10/2016.
 */

public class Event implements Parcelable
{
    Long code; // represents id
    String name = null;
    String price = null;
    int index = 0;
    boolean selected = false;

    public Event(){
        super();
    }


    public Event(String name, boolean selected) {
        super();
        this.code = code;
        this.name = name;
        this.selected = selected;
       // this.index = index;
    }

    protected Event(Parcel in) {
        code = in.readLong();
        name = in.readString();
        selected = in.readByte() != 0;
      //  index = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(code);
        dest.writeString(name);
     //   dest.writeInt(index);
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

    public Long getCode() {
        return code;
    }
    public void setCode(Long code) {
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

    public Integer getIndex(){
        index += 1;
        return index;
    }
}
