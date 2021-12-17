package FinalExam.View;

import FinalExam.Model.BathModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateBathGUI extends JFrame {

    private JPanel createBathGUIPanel;
    private JLabel createBathLabel;
    private JTextField bathCapacityField;
    private JButton createBathBtn;
    private BathModel bathmodel = new BathModel();
    private BathControllerGUI bathControllerGUI;

    public CreateBathGUI ()
    {
        setTitle("Create Bath");
        setSize(250,100);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        createBathGUIComponents();
        setVisible(true);
    }


    public void createBathGUIComponents () {
        //DESIGN
        add(createBathGUIPanel = new JPanel());
        createBathGUIPanel.setBorder(BorderFactory.createEtchedBorder());
        createBathGUIPanel.add(createBathLabel = new JLabel("Set max capacity"));
        createBathGUIPanel.add(bathCapacityField = new JTextField(6));
        createBathGUIPanel.add(createBathBtn = new JButton("Create"));

        //LISTENERS
        createBathBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                float getCapacity = Integer.parseInt(bathCapacityField.getText());
                if (getCapacity < 100 || getCapacity > 1000)
                {
                    JOptionPane.showMessageDialog(null,"Please enter a number between 100 and 1000");
                }
                else {
                    new BathControllerGUI(getCapacity);
                    setVisible(false);
                }
            }
        });

    }


    public void setControllerGui (BathModel bathModel, BathControllerGUI bathControllerGUI)
    {
        this.bathmodel = bathModel;
        this.bathControllerGUI = bathControllerGUI;
    }
}
