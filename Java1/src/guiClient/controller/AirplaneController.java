package guiClient.controller;

import Airplane.model.AirplaneServer;
import Airplane.model.Exception.*;
import guiClient.view.airplaneGUIv2Client;

public class AirplaneController {

    private AirplaneServer airplaneServer;
    private airplaneGUIv2Client airplaneGUIv2Client;


    public AirplaneController (airplaneGUIv2Client airplaneGUIv2Client, AirplaneServer airplaneServer)
    {
        this.airplaneGUIv2Client = airplaneGUIv2Client;
        this.airplaneServer = airplaneServer;
    }

    public void startAirplane() throws MotorHasAlreadyStartedException, AirplaneExplodedException
    {
        airplaneServer.startMotor();
    }

    public void setAltitude (double altitude)
    {

        airplaneServer.setAltitude(altitude);
    }
    public void setMotorOff() //Listener/Subscriber //Notifier setDefaultAirplaneState (CreateAirplaneGui) should call this method
    {
        airplaneServer.setMotorOff();

    }
    public void startMotor() throws MotorHasAlreadyStartedException, AirplaneExplodedException {
        airplaneServer.startMotor();
    }

    public void stopMotor() throws MotorHasAlreadyStoppedException, AirplaneExplodedException, CannotStopFlyingAirplaneException {
        airplaneServer.stopMotor();
    }

    public void takeOff() throws AirplaneExplodedException, AirplaneAlreadyTookOffException, MotorIsNotStartedException {
        airplaneServer.takeOff();
    }

    public void increaseAltitude() throws AirplaneNotInAirException, MotorIsNotStartedException, AirplaneExplodedException, AltitudeDangerException, AirplaneBoomException {
        airplaneServer.increaseAltitude();
    }

    public void decreaseAltitude() throws AirplaneBoomException, AirplaneExplodedException, AltitudeDangerException, AirplaneAlreadyGroundedException {
        airplaneServer.decreaseAltitude();
    }
}
