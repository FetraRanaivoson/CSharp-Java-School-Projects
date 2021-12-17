package Airplane.controller;


import Airplane.view.AirplaneGUIv2;

public class GuiAirplaneClient {
    public static void main(String[] args) {
        AirplaneGUIv2 client;
        client = new AirplaneGUIv2("127.0.0.1");
        client.startClient();
    }
}
