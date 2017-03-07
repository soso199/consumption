package ge.idevelopers.consumption;

import java.text.DecimalFormat;

/**
 * Created by soso on 2/8/17.
 */

public class Calculate {
    private double fuel;
    private double distance;


    public void setFuel(double a)
    {
        this.fuel=a;
    }

    public double getFuel()
    {

        return fuel;
    }
    public void setDistance(double a)
    {
        this.distance=a;
    }

    public double getDistance()
    {
        return distance;
    }

    public double answar()
    {

        DecimalFormat twoDForm = new DecimalFormat("#.##");
        double number=(fuel*100/distance);
        number = Math.round(number * 100);
        number = number/100;
        return number;
    }

}
