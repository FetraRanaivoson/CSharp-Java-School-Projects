package FinalExam.View;

import FinalExam.Model.BathModel;
import FinalExam.Model.BathObserver;
import FinalExam.Model.Exceptions.StopperNotPluggedException;
import FinalExam.Model.Exceptions.WaterAlreadyClosedException;
import FinalExam.Model.Exceptions.WaterAlreadyOpenException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BathControllerGUI extends JFrame implements BathObserver {

    private JPanel controllerMenuPanel;
    private JPanel controllerInfoPanel;
    private JTextArea bathStatusConsole;
    private JButton openWaterBtn;
    private JButton closeWaterBtn;
    private JButton plugStopperBtn;
    private JButton removeStopperBtn;
    private JLabel bathLevelLabel;
    private JLabel bathCapacityLabel;
    private JProgressBar currentBathLevelBar;
    private CreateBathGUI createBathGUI = new CreateBathGUI();
    private BathModel bathModel = new BathModel();

    public BathControllerGUI ()
    {

    }

    public BathControllerGUI (Float maxCapacity)
    {
        //createBathGUI.setControllerGui(bathModel,this);

        bathModel.setMaxCapacity(maxCapacity);
        bathModel.addObservers(this);

        setTitle("Bath Controller");
        setSize(295,300);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createBathControllerGUI();
        setVisible(true);
    }

    public void createBathControllerGUI ()
    {
        add (controllerMenuPanel = new JPanel(), BorderLayout.NORTH);
        controllerMenuPanel.setLayout(new GridLayout(2,2,10,10));
        controllerMenuPanel.setBorder(BorderFactory.createTitledBorder("Bath commands"));
        controllerMenuPanel.add (openWaterBtn = new JButton("Open Water"));
        controllerMenuPanel.add (closeWaterBtn = new JButton("Close water"));
        controllerMenuPanel.add (plugStopperBtn = new JButton("Plug stopper"));
        controllerMenuPanel.add (removeStopperBtn = new JButton("Remove Stopper"));

        add (controllerInfoPanel = new JPanel(), BorderLayout.CENTER);
        controllerInfoPanel.setBorder(BorderFactory.createTitledBorder("Current bath"));
        controllerInfoPanel.add (bathStatusConsole = new JTextArea(2,20));
        controllerInfoPanel.add (bathLevelLabel = new JLabel("Current Level: " + BathModel.getWaterLevel()));
        controllerInfoPanel.add (bathCapacityLabel = new JLabel("Max Capacity: " + BathModel.getMaxCapacity()));
        controllerInfoPanel.add (currentBathLevelBar = new JProgressBar(0, (int) BathModel.getMaxCapacity()));

        openWaterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //bathModel.increaseWaterLevel();
                try {
                    bathModel.openWater();
                    bathStatusConsole.setText(bathModel.bathInfo());

                } catch (StopperNotPluggedException | WaterAlreadyOpenException stopperNotPluggedException) {
                    stopperNotPluggedException.printStackTrace();
                }
            }
        });

        closeWaterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    bathModel.closeWater(); //This already contains the call to notify 'this' observer
                    bathStatusConsole.setText(bathModel.bathInfo());
                } catch (WaterAlreadyClosedException waterAlreadyClosedException) {
                    waterAlreadyClosedException.printStackTrace();
                }

            }
        });

        removeStopperBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bathModel.decreaseWaterLevel();
                bathStatusConsole.setText(bathModel.bathInfo());
            }
        });

        bathStatusConsole.setText(bathModel.bathInfo());
    }


    public void setBathCapacityFieldText (String text)
    {
        bathCapacityLabel.setText(text);
    }


    @Override
    public void update(double waterLevel) {
        currentBathLevelBar.setValue((int) waterLevel);
    }
}
