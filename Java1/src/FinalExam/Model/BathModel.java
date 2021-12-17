package FinalExam.Model;

import FinalExam.Model.Exceptions.StopperAlreadyRemoved;
import FinalExam.Model.Exceptions.StopperNotPluggedException;
import FinalExam.Model.Exceptions.WaterAlreadyClosedException;
import FinalExam.Model.Exceptions.WaterAlreadyOpenException;
import FinalExam.View.BathControllerGUI;
import FinalExam.View.BathMenuGUI;
import jdk.swing.interop.SwingInterOpUtils;

import java.util.ArrayList;

public class BathModel {

    private static double maxCapacity;
    private final double rateOfChange;
    private static double waterLevel;
    private boolean isWaterClosed;
    private boolean isStopperPlugged;
    private ArrayList<BathObserver> observers = new ArrayList();
    private BathMenuGUI bathMenuGUI;

    public void setBathMenuGui (BathMenuGUI bathMenuGUI)
    {
        this.bathMenuGUI = bathMenuGUI;

    }

    public void addObservers (BathObserver observer)
    {
        observers.add(observer);
    }

    public BathModel ()
    {
        isWaterClosed = true;
        isStopperPlugged = true;
        rateOfChange = maxCapacity * 0.1 * 100;
    }

    public void openWater () throws StopperNotPluggedException, WaterAlreadyOpenException
    {
        if (!isStopperPlugged)
            throw new StopperNotPluggedException("Cannot open water, stopper is not plugged");
        if (!isWaterClosed)
            throw new WaterAlreadyOpenException("Water is already open");
        else {
            setWaterClosed(false);
            increaseWaterLevel();
        }
    }

    public void closeWater () throws WaterAlreadyClosedException
    {
        if (isWaterClosed)
            throw new WaterAlreadyClosedException("Water is already closed");
        else {
            isWaterClosed = true;
            notifyObservers();
        }
    }

    public void removeStopper () throws StopperAlreadyRemoved, WaterAlreadyOpenException {
        if (!isStopperPlugged)
            throw new StopperAlreadyRemoved("Stopper is already removed");
        if (!isWaterClosed)
            throw new WaterAlreadyOpenException("Cannot remove stopper, water is open");
        else {
            setStopperPlugged(true);
            decreaseWaterLevel();
        }

    }

    public void setMaxCapacity (Float maxCapacity)
    {
        this.maxCapacity = maxCapacity;
    }

    public static double getMaxCapacity ()
    {
        return maxCapacity;
    }

    public static double getWaterLevel()
    {
        return waterLevel;
    }


    public void setWaterLevel(double waterLevel)
    {
        this.waterLevel = waterLevel;
    }

    public void increaseWaterLevel()
    {
        Thread increaseThread = new Thread( () -> {
            waterLevel = getWaterLevel();
            double targetLevel = getMaxCapacity();

            do {
                waterLevel++;
                setWaterLevel(waterLevel);
                notifyObservers();

                try {
                    Thread.sleep(400);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }

            } while (isWaterClosed == false || waterLevel != targetLevel);
        });
        increaseThread.start();
    }

    public void decreaseWaterLevel ()
    {
        Thread decreaseThread = new Thread( () -> {
            waterLevel = getWaterLevel();
            double targetLevel = 0;

            do {
                waterLevel --;
                setWaterLevel(waterLevel);
                notifyObservers();

                try {
                    Thread.sleep(400);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }

            } while (isWaterClosed == false || waterLevel != targetLevel);
        });
        decreaseThread.start();

    }

    public void setWaterClosed (boolean waterClosed )
    {
        isWaterClosed = waterClosed;
    }

    public void setStopperPlugged (boolean stopperPlugged)
    {
        isStopperPlugged = stopperPlugged;
    }



    public String isWaterClosed ()
    {
        if (isWaterClosed)
            return "Closed";
        else
            return "Open";
    }

    public String isStopperPlugged ()
    {
        if (isStopperPlugged)
            return "Plugged";
        else
            return "Removed";
    }

    private void notifyObservers ()
    {
        for (BathObserver observer: observers)
        {
            observer.update(BathModel.waterLevel); //Observer method //Its method
        }
    }

    public String bathInfo ()
    {
        return "Water: " + isWaterClosed() + "\nStopper: " + isStopperPlugged() + "\nCurrent Level: " + BathModel.waterLevel;
    }

}
