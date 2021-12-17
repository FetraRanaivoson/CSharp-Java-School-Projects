package Airplane.view;


import Airplane.model.AirplaneServer;

public class ChangeAltitudeThread implements Runnable
{

    private AirplaneGUIv2 airplaneGUIv2;
    private AirplaneServer airplaneServer;
    private int currentAltitude;
    private int targetAltitude;

    public ChangeAltitudeThread(AirplaneServer airplaneServer, AirplaneGUIv2 airplaneGUIv2) {
        this.airplaneServer = airplaneServer;
        this.airplaneGUIv2 = airplaneGUIv2;
    }

    @Override
    public void run() {
        currentAltitude = (int) airplaneServer.getAltitude();

            targetAltitude = currentAltitude + 1000;
            while (currentAltitude != targetAltitude) {
                currentAltitude++;
                //airplaneGUIv2.changeAltitude(currentAltitude);
                airplaneServer.setAltitude(currentAltitude);
                airplaneGUIv2.appendTextToConsole(">>> : Altitude changed to : " + airplaneServer.getAltitude());
                airplaneGUIv2.updateProgressBar((int) airplaneServer.getAltitude());
                airplaneGUIv2.textArea.setText("                                                 " + airplaneServer.airplaneBoard());
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }
}


