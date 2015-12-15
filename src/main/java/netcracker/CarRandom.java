package netcracker;

import java.util.Random;

/**
 * Created by Denis on 14.12.2015.
 */
public class CarRandom {
    public static Car NewCarReturn(){
        Random rnd1=new Random();
        String s=null;
        switch(rnd1.nextInt(3)){
            case 0: s="BMW"; break;
            case 1: s="Mercedes";break;
            case 2: s="Lada";break;
        }
        return new Car(s,s+rnd1.nextInt(100),"Owner"+rnd1.nextInt(100),(rnd1.nextInt(10)+1)*2000,rnd1.nextInt(3));
    }
}
