package guiClient.view;


import Airplane.model.AirplaneServer;

public class ChangeAltitudeThread implements Runnable
{

    private airplaneGUIv2Client airplaneGUIv2Client;
    private AirplaneServer airplaneServer;
    private int currentAltitude;
    private int targetAltitude;

    public ChangeAltitudeThread(AirplaneServer airplaneServer, airplaneGUIv2Client airplaneGUIv2Client) {
        this.airplaneServer = airplaneServer;
        this.airplaneGUIv2Client = airplaneGUIv2Client;
    }

    @Override
    public void run() {
        currentAltitude = (int) airplaneServer.getAltitude();

            targetAltitude = currentAltitude + 1000;
            while (currentAltitude != targetAltitude) {
                currentAltitude++;
                //airplaneGUIv2.changeAltitude(currentAltitude);
                airplaneServer.setAltitude(currentAltitude);
                airplaneGUIv2Client.appendTextToConsole(">>> : Altitude changed to : " + airplaneServer.getAltitude());
                airplaneGUIv2Client.updateProgressBar((int) airplaneServer.getAltitude());
                airplaneGUIv2Client.textArea.setText("                                                 " + airplaneServer.airplaneBoard());
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

}


