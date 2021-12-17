package Airplane.model;

import java.util.function.Consumer;

public enum AirplaneState {

    ENGINE_OFF ("Off"), ENGINE_ON ("On"), FLYING("Good"), EXPLODED ("Exploded");

    private final String stateDescription;

    AirplaneState (String stateDescription)
    {
        this.stateDescription = stateDescription;
    }
    public String getDescription ()
    {
        return  stateDescription;
    }
}
