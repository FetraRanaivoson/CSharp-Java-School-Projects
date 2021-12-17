package FinalExam.Controller;

import FinalExam.Model.BathModel;
import FinalExam.View.BathMenuGUI;

public class BathApplication {
    public static void main(String[] args) {
        new BathMenuGUI(new BathModel());
        //new BathModel();

    }
}
