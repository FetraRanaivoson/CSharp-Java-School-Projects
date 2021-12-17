package FinalExam.View;

import FinalExam.Model.BathModel;
import FinalExam.Model.BathObserver;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BathMenuGUI extends JFrame implements BathObserver {

    private JPanel bathMenuTop;
    private JPanel bathMenuBottom;
    private JButton newBtn;
    private JButton loadBtn;
    private JButton logBtn;
    private JProgressBar bathProgressBar;
    private JLabel bathPercentageLabel;
    private BathControllerGUI bathControllerGUI;
    private BathModel bathModel = new BathModel();




    public BathMenuGUI (BathModel bathModel)
    {

        setTitle("Bath Menu");
        setSize(500,150);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        createBathMenuComponents();
        bathModel.setBathMenuGui(this);
        bathModel.addObservers(this);
        repaint();
        revalidate();
        setVisible(true);
    }

    public void createBathMenuComponents()
    {
        //MENU DESIGN
        add (bathMenuTop = new JPanel(), BorderLayout.NORTH);
        bathMenuTop.setBorder(BorderFactory.createTitledBorder("Menu"));
        bathMenuTop.add (newBtn = new JButton("New Bath"));
        bathMenuTop.add (loadBtn = new JButton("Load Bath"));
        bathMenuTop.add (logBtn = new JButton("Log Bath"));
        logBtn.setEnabled(false);
        logBtn.setToolTipText("Must create bath or create a new one.");

        add (bathMenuBottom = new JPanel(), BorderLayout.CENTER);
        bathMenuBottom.setBorder(BorderFactory.createTitledBorder("Current percentage"));


        bathMenuBottom.add (bathProgressBar = new JProgressBar(0,(int)BathModel.getMaxCapacity()));
        Dimension bathProgressBarDimension = bathProgressBar.getPreferredSize();
        bathProgressBarDimension.width = 300;


        Thread thread = new Thread(() -> {
            do {
                bathProgressBar.setValue((int) BathModel.getWaterLevel());
                bathPercentageLabel.setText(""+ (int) ( (BathModel.getWaterLevel()*100)/BathModel.getMaxCapacity() ) + "%");
                try {
                    Thread.sleep(2);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            } while (true);
        });
        thread.start();

        bathMenuBottom.add (bathPercentageLabel = new JLabel(""+ "%"));

        //LISTENERS
        newBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateBathGUI();
            }
        });

        loadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(BathModel.getWaterLevel());
            }
        });

        logBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo: before showing log you must first start or load bath
            }
        });

    }

    @Override
    public void update(double waterLevel) {
        System.out.println("Updated");
        bathProgressBar.setValue((int)waterLevel); //Only the method
    }
}
