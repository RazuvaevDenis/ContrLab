package netcracker;

import org.apache.log4j.Level;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Denis on 14.12.2015.
 */
public class Service {

    private int ListLength;
    private int MastersLength;
    private Properties property=new Properties();
    private BlockingQueue<Car> blockingQueue;


    public Service(){
        init();
        this.blockingQueue=new ArrayBlockingQueue<Car>(ListLength-1);

    }

    public int getMastersLength(){
        return  MastersLength-1;
    }

    public void init(){
        try (FileInputStream fis = new FileInputStream("src/main/resources/init.properties")){
            property.load(fis);
        } catch (IOException e) {
            Test.log.log(Level.ERROR,"Error." + e.getMessage(), e);
        }
        ListLength=Integer.parseInt(property.getProperty("list"));
        MastersLength=Integer.parseInt(property.getProperty("masters"));
    }

    public void Push(Car car){
        try {
            this.blockingQueue.put(car);
        } catch (InterruptedException e) {
            Test.log.log(Level.ERROR,"Error." + e.getMessage(), e);
        }
    }

    public Car Pop(){
        try {
            return this.blockingQueue.take();
        } catch (InterruptedException e) {
            Test.log.log(Level.ERROR,"Error." + e.getMessage(), e);
            return null;
        }
    }
}
