package netcracker;

/**
 * Created by Denis on 14.12.2015.
 */
public class Car {
    private String mark;
    private String name;
    private String owner;
    private int handling_time;
    private int type_of_record;

    public static final int BMW=0;
    public static final int MERCEDES=1;
    public static final int LADA=2;

    public static final int CONSOLETYPE=0;
    public static final int XMLTYPE=1;
    public static final int DBTYPE=2;

    public Car(String mark,String name, String owner,int handling_time,int type_of_record){
        this.mark=mark;
        this.name=name;
        this.owner=owner;
        this.handling_time=handling_time;
        this.type_of_record=type_of_record;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getHandling_time() {
        return handling_time;
    }

    public void setHandling_time(int handling_time) {
        this.handling_time = handling_time;
    }

    public int getType_of_record() {
        return type_of_record;
    }

    public void setType_of_record(int type_of_record) {
        this.type_of_record = type_of_record;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
