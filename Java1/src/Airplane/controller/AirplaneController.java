package Airplane.controller;

import Airplane.model.AirplaneServer;
import Airplane.model.Exception.*;
import Airplane.view.AirplaneGUIv2;

public class AirplaneController {

    private AirplaneServer airplaneServer;
    private AirplaneGUIv2 airplaneGUIv2;


    public AirplaneController (AirplaneGUIv2 airplaneGUIv2, AirplaneServer airplaneServer)
    {
        this.airplaneGUIv2 = airplaneGUIv2;
        this.airplaneServer = airplaneServer;
    }

    public void startAirplane() throws MotorHasAlreadyStartedException, AirplaneExplodedException
    {
        airplaneServer.startMotor();
    }

    public void setAltitude (double altitude) throws AirplaneAlreadyGroundedException
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
