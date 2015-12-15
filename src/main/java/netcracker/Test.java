package netcracker;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by Denis on 14.12.2015.
 */
public class Test {
    public static final Logger log=Logger.getLogger(Test.class.getName());
    public static Service service=new Service();
    public static ArrayList<Master> masters;
    public static Evacuator evacuator;

    public static void InitMasters(ArrayList<Master> masters, Service service){
        for(int i=0;i<service.getMastersLength();i++){
            masters.add(new Master(service));
        }
    }

    public static void main(String[] args){
        evacuator=new Evacuator(service);
        masters=new ArrayList<Master>(service.getMastersLength());
        Thread evacuateThread=new Thread(evacuator);
        Thread[] mastersThread=new Thread[service.getMastersLength()];
        InitMasters(masters,service);
        for(int i=0;i<service.getMastersLength();i++){
            mastersThread[i]=new Thread(masters.get(i));
        }
        evacuateThread.start();
        for(int i=0;i<service.getMastersLength();i++){
            mastersThread[i].start();
        }
    }
}
