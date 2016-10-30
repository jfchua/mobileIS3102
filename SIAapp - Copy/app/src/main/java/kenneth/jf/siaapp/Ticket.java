package kenneth.jf.siaapp;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class Ticket implements Parcelable
{
    Long code; // represents id
    String name = null;
    double price = 0.0;
    int index = 0;
    boolean selected = false;

    public Ticket(){
        super();
    }


    public Ticket(String name, double price, boolean selected) {
        super();
        this.code = code;
        this.name = name;
        this.selected = selected;
        this.price = price;
        // this.index = index;
    }

    protected Ticket(Parcel in) {
        code = in.readLong();
        name = in.readString();
        price = in.readDouble();
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

    public static final Creator<Ticket> CREATOR = new Creator<Ticket>() {
        @Override
        public Ticket createFromParcel(Parcel in) {
            return new Ticket(in);
        }

        @Override
        public Ticket[] newArray(int size) {
            return new Ticket[size];
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

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
}
