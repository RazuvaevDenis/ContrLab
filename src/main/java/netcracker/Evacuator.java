package netcracker;

import org.apache.log4j.Level;

import java.util.Random;

/**
 * Created by Denis on 14.12.2015.
 */
public class Evacuator implements Runnable {

    private Car car;
    private Service service;
    public Evacuator(Service service){
        this.service=service;
    }

    public Car getCat(){
        return car;
    }

    @Override
    public void run() {
        while(true){
            this.car=CarRandom.NewCarReturn();
            Random rnd1=new Random();
            Test.log.log(Level.INFO,"New car in service " + car.getMark()+ " " + car.getName() + " " + car.getOwner());
            try {
                service.Push(car);
                Thread.sleep(rnd1.nextInt(10)*1000);
            } catch (InterruptedException e) {
                Test.log.log(Level.ERROR,"Error"+e.getMessage(),e);
            }
        }
    }
}
