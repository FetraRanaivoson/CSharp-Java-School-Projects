package TicTacToe.View.GUI.GameGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class DoublePlayerBoardGUI extends JFrame {


    public String boardInstruction = "default";
    public String player1Mark = "X";
    public String player2Mark = "O";
    private String lastPlayer;
    private boolean playerRetry = false;
    public boolean isPlayer1Turn = true;
    public boolean isPlayer2Turn = false;
    public boolean isPlayer1Win = false;
    public boolean isPlayer2Win = false;
    public boolean retryGame = false;
    public boolean isDraw;
    private int player1Win = 0;
    private int player1Lose = 0;
    private int player1Draw = 0;
    private int player2Win = 0;
    private int player2Lose = 0;
    private int player2Draw = 0;

    public String [] [] playerMarks = {{"","",""},{"","",""},{"","",""}};

    //If player == 1, then "X" is the instruction

    //MENU BAR
    private JMenuItem exitGameItem;
    private JMenuItem loadGameItem;
    private JMenuItem newGameItem;

    //GAME GRID (CENTER)
    public JPanel boardCenterPanel;
    public JButton btn00;
    public JButton btn01;
    public JButton btn02;
    public JButton btn10;
    public JButton btn11;
    public JButton btn12;
    public JButton btn20;
    public JButton btn21;
    public JButton btn22;
    public ArrayList<JButton>gridBtn = new ArrayList<>();

    //PLAYER 1 SIDE PANEL (LEFT)
    public JPanel boardLeftPanel;
    private JLabel player1Lbl;
    private JTextArea player1Score;
    public static String player1Name = "Tom";

    //PLAYER 2 SIDE PANEL (RIGHT)
    private JPanel boardRightPanel;
    private JLabel player2Lbl;
    private JTextArea player2Score;
    public static String player2Name = "Becky";

    //GAME NETWORK PANEL (SOUTH)
    private JPanel networkPanel;
    public JTextArea networkStatusTextArea;

    public DoublePlayerBoardGUI() throws InterruptedException {
        setTitle("Tic Tac Toe Game");
        setSize(900, 700);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setJMenuBar(createMenuBar());
        createGameGridPanel ();
        createPlayerPanels ();
        createNetworkPanels();
        showGameScores();
        startGame();
        validate();
        setVisible(true);
    }

    public void startGame () throws InterruptedException {
        /**Choosing player Names**/
        choosePlayerName();

        /**Randomly choose the first Player**/
        randomizePlayer();

        /**Start the game**/
        checkBoardInstruction();

    }



    //GAME GRID CENTER PANEL
    public void createGameGridPanel()
    {
        //Grid Layout and components
        add (boardCenterPanel = new JPanel(),BorderLayout.CENTER);
        boardCenterPanel.setBorder(BorderFactory.createEtchedBorder());
        boardCenterPanel.setLayout(new GridLayout(3,3,10,10));
        boardCenterPanel.add (btn00 = new JButton("_"));
        boardCenterPanel.add (btn01 = new JButton("_"));
        boardCenterPanel.add (btn02 = new JButton("_"));
        boardCenterPanel.add (btn10 = new JButton("_"));
        boardCenterPanel.add (btn11 = new JButton("_"));
        boardCenterPanel.add (btn12 = new JButton("_"));
        boardCenterPanel.add (btn20 = new JButton("_"));
        boardCenterPanel.add (btn21 = new JButton("_"));
        boardCenterPanel.add (btn22 = new JButton("_"));

        //Buttons Font size
        btn00.setFont(new Font("Arial", Font.BOLD, 100));
        btn01.setFont(new Font("Arial", Font.BOLD, 100));
        btn02.setFont(new Font("Arial", Font.BOLD, 100));
        btn10.setFont(new Font("Arial", Font.BOLD, 100));
        btn11.setFont(new Font("Arial", Font.BOLD, 100));
        btn12.setFont(new Font("Arial", Font.BOLD, 100));
        btn20.setFont(new Font("Arial", Font.BOLD, 100));
        btn21.setFont(new Font("Arial", Font.BOLD, 100));
        btn22.setFont(new Font("Arial", Font.BOLD, 100));
        gridBtn.add (btn00);
        gridBtn.add (btn01);
        gridBtn.add (btn02);
        gridBtn.add (btn10);
        gridBtn.add (btn11);
        gridBtn.add (btn12);
        gridBtn.add (btn20);
        gridBtn.add (btn21);
        gridBtn.add (btn22);

        //Buttons listeners
        btn00.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBoardInstruction("00");
            }
        });
        btn01.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBoardInstruction("01");
            }
        });
        btn02.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBoardInstruction("02");
            }
        });
        btn10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBoardInstruction("10");
            }
        });
        btn11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBoardInstruction("11");
            }
        });
        btn12.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBoardInstruction("12");
            }
        });
        btn20.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBoardInstruction("20");
            }
        });
        btn21.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBoardInstruction("21");
            }
        });
        btn22.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBoardInstruction("22");
            }
        });
    }

    //PLAYER PANELS
    public void createPlayerPanels()
    {
        //FIRST PLAYER LEFT
        add (boardLeftPanel = new JPanel(), BorderLayout.WEST);
        Dimension leftDimension = boardLeftPanel.getPreferredSize();
        leftDimension.width = 200;
        boardLeftPanel.setPreferredSize(leftDimension);
        boardLeftPanel.setBorder(BorderFactory.createTitledBorder("Player 1"));
        boardLeftPanel.add (player1Lbl = new JLabel(getPlayer1Name())); //Todo set this to the player Name
        boardLeftPanel.add (player1Score = new JTextArea(3,5));
        player1Score.setFont(new Font("Arial", Font.BOLD, 25));
        player1Score.setEditable(false);

        //SECOND PLAYER RIGHT
        add (boardRightPanel = new JPanel(), BorderLayout.EAST);
        Dimension rightDimension = boardRightPanel.getPreferredSize();
        rightDimension.width = 200;
        boardRightPanel.setPreferredSize(rightDimension);
        boardRightPanel.setBorder(BorderFactory.createTitledBorder("Player 2"));
        boardRightPanel.add (player2Lbl = new JLabel(getPlayer2Name())); //Todo set this to the player Name
        boardRightPanel.add (player2Score = new JTextArea(3,5));
        player2Score.setFont(new Font("Arial", Font.BOLD, 25));
        player2Score.setEditable(false);
    }

    //SOUTH PANEL UTILITIES
    public void createNetworkPanels()
    {
        add (networkPanel = new JPanel(), BorderLayout.SOUTH);
        Dimension southDimension = networkPanel.getPreferredSize();
        southDimension.height = 100;
        southDimension.width = 750;
        networkPanel.setPreferredSize(southDimension);
        networkPanel.setBorder(BorderFactory.createTitledBorder("Network"));

        networkPanel.add (networkStatusTextArea = new JTextArea(4,55));


        JScrollPane scroll = new JScrollPane(networkStatusTextArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        networkPanel.add(scroll);
    }

    //MENU BAR
    public JMenuBar createMenuBar ()
    {
        //SETUP
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        menuBar.add (file);

        file.add (newGameItem = new JMenuItem("New Game"));
        file.add (loadGameItem = new JMenuItem("Load Game"));
        file.addSeparator();
        file.add (exitGameItem = new JMenuItem("Exit"));

        newGameItem.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    confirmNewGame();
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        exitGameItem.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                int userChoice = JOptionPane.showConfirmDialog(null, "Quit game?", "Tic Tac Toe", JOptionPane.YES_NO_OPTION);
                if (userChoice == JOptionPane.YES_OPTION)
                    System.exit(0);
                else {
                    //Continue Game
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        return menuBar;
    }

    //Check the input String given by setBoardInstruction (String instruction )
    //from the client/server
    // output.writeObject (instruction)  (FROM THE ONE WHO SEND)
    // instruction = (String).input.readObject // setBoardInstruction (instruction) (FROM THE ONE WHO RECEIVE)


    public void setBoardInstruction (String boardInstruction)
    {
        this.boardInstruction = boardInstruction;
        checkBoardInstruction();
    }

    //Checking instruction method
    public void checkBoardInstruction () {
        retryGame = false;
        if (isPlayer1Turn == true) {
            switch (boardInstruction) {
                case "00": {
                    if (player1WrongAtCell(0,0))
                        break;
                    btn00.setText(getPlayer1Mark());
                    playerMarks[0][0] = getPlayer1Mark();
                    validate();
                    break;
                }
                case "01": {
                    if (player1WrongAtCell(0,1))
                        break;
                    btn01.setText(getPlayer1Mark());
                    playerMarks[0][1] =getPlayer1Mark();
                    validate();
                    break;

                }
                case "02": {
                    if(player1WrongAtCell(0,2))
                        break;
                    btn02.setText(getPlayer1Mark());
                    playerMarks[0][2] = getPlayer1Mark();
                    validate();
                    break;

                }
                case "10": {
                    if(player1WrongAtCell(1,0))
                        break;
                    btn10.setText(getPlayer1Mark());
                    playerMarks[1][0] = getPlayer1Mark();
                    validate();
                    break;

                }
                case "11": {
                    if(player1WrongAtCell(1,1))
                        break;
                    btn11.setText(getPlayer1Mark());
                    playerMarks[1][1] = getPlayer1Mark();
                    validate();
                    break;

                }
                case "12": {
                    if(player1WrongAtCell(1,2))
                        break;
                    btn12.setText(getPlayer1Mark());
                    playerMarks[1][2] = getPlayer1Mark();
                    validate();
                    break;

                }
                case "20": {
                    if(player1WrongAtCell(2,0))
                        break;
                    btn20.setText(getPlayer1Mark());
                    playerMarks[2][0] = getPlayer1Mark();
                    validate();
                    break;

                }
                case "21": {
                    if(player1WrongAtCell(2,1))
                        break;
                    btn21.setText(getPlayer1Mark());
                    playerMarks[2][1] = getPlayer1Mark();
                    validate();
                    break;

                }
                case "22": {
                    if(player1WrongAtCell(2,2))
                        break;
                    btn22.setText(getPlayer1Mark());
                    playerMarks[2][2] = getPlayer1Mark();
                    validate();
                    break;

                }
                default:

            }

        }
        else if (isPlayer2Turn == true) {
            switch (boardInstruction) {
                case "00": {
                    if(player2WrongAtCell(0,0))
                        break;
                    btn00.setText(getPlayer2Mark());
                    playerMarks[0][0] = getPlayer2Mark();
                    validate();
                    break;

                }
                case "01": {
                    if(player2WrongAtCell(0,1))
                        break;
                    btn01.setText(getPlayer2Mark());
                    playerMarks[0][1] = getPlayer2Mark();
                    validate();
                    break;

                }
                case "02": {
                    if(player2WrongAtCell(0,2))
                        break;
                    btn02.setText(getPlayer2Mark());
                    playerMarks[0][2] = getPlayer2Mark();
                    validate();
                    break;

                }
                case "10": {
                    if(player2WrongAtCell(1,0))
                        break;
                    btn10.setText(getPlayer2Mark());
                    playerMarks[1][0] = getPlayer2Mark();
                    validate();
                    break;

                }
                case "11": {
                    if(player2WrongAtCell(1,1))
                        break;
                    btn11.setText(getPlayer2Mark());
                    playerMarks[1][1] = getPlayer2Mark();
                    validate();
                    break;

                }
                case "12": {
                    if(player2WrongAtCell(1,2))
                        break;
                    btn12.setText(getPlayer2Mark());
                    playerMarks[1][2] = getPlayer2Mark();
                    validate();
                    break;

                }
                case "20": {
                    if(player2WrongAtCell(2,0))
                        break;
                    btn20.setText(getPlayer2Mark());
                    playerMarks[2][0] = getPlayer2Mark();
                    validate();
                    break;

                }
                case "21": {
                    if(player2WrongAtCell(2,1))
                        break;
                    btn21.setText(getPlayer2Mark());
                    playerMarks[2][1] = getPlayer2Mark();
                    validate();
                    break;

                }
                case "22": {
                    if(player2WrongAtCell(2,2))
                        break;
                    btn22.setText(getPlayer2Mark());
                    playerMarks[2][2] = getPlayer2Mark();
                    validate();
                    break;

                }
            }

        }

        checkIfWin();
        if (isPlayer1Win == true || isPlayer2Win == true) {
            confirmRetry();
        }
        else {
            checkIfDraw();
            if (isDraw)
                confirmRetry();
        }

        //if (isPlayer1Win == true || isPlayer2Win == true ||isDraw == true) {
        //    confirmRetry();
        //}
    }


    public boolean player1WrongAtCell(int row, int column) {
        if (playerMarks [row][column] == getPlayer2Mark()) {
            JOptionPane.showMessageDialog(null,getPlayer1Name() + ", this cell is not yours!","Tic Tac Toe", JOptionPane.WARNING_MESSAGE);
            retryGame = true;
            return true;
        } else if (playerMarks [row] [column] == getPlayer1Mark()) {
            JOptionPane.showMessageDialog(null,getPlayer1Name() + ", this cell is yours!","Tic Tac Toe", JOptionPane.WARNING_MESSAGE);
            retryGame = true;
            return false;
        }
        return false;
    }

    public boolean player2WrongAtCell(int row, int column) {
        if (playerMarks [row][column] == getPlayer1Mark()) {
            JOptionPane.showMessageDialog(null,getPlayer2Name() + ", this cell is not yours!","Tic Tac Toe", JOptionPane.WARNING_MESSAGE);
            retryGame = true;
            return true;
        } else if (playerMarks [row] [column] == getPlayer2Mark()) {
            JOptionPane.showMessageDialog(null,getPlayer2Name() + ", this cell is yours!","Tic Tac Toe", JOptionPane.WARNING_MESSAGE);
            retryGame = true;
           return true;
        }
        return false;
    }
/*
    public boolean gameLoop () throws InterruptedException {
        while (true) {
            if (checkBoardInstruction()) {
                return true;
            }
            else {
                networkStatusTextArea.append("Waiting for player");
                Thread.sleep(20);
            }
        }
    }
*/


    //Winning method
    public boolean checkIfWin()
    {
        if (isPlayer1Turn == true) {
            int count = 0;
            for (int j = 0; j < 3; j++) {
                if (playerMarks[0][j].equals(player1Mark)) {
                    count += 1;
                }
                if (count == 3) {
                    JOptionPane.showMessageDialog(null, getPlayer1Name() + " wins!");
                    setPlayer1Win();
                    showGameScores();
                    return true;
                }
            }
            count = 0;
            for (int i = 0; i < 3; i++) {
                if (playerMarks[i][0].equals(player1Mark)) {
                    count += 1;
                }
                if (count == 3) {
                    JOptionPane.showMessageDialog(null, getPlayer1Name() + " wins!");
                    setPlayer1Win();
                    showGameScores();
                    return true;
                }
            }
            count = 0;
            for (int i = 0; i < 3; i++) {
                if (playerMarks[i][2].equals(player1Mark)) {
                    count += 1;
                }
                if (count == 3) {
                    JOptionPane.showMessageDialog(null, getPlayer1Name() + " wins!");
                    setPlayer1Win();
                    showGameScores();
                    return true;
                }
            }
            count = 0;
            for (int j = 0; j < 3; j++) {
                if (playerMarks[2][j].equals(player1Mark)) {
                    count += 1;
                }
                if (count == 3) {
                    JOptionPane.showMessageDialog(null, getPlayer1Name() + " wins!");
                    setPlayer1Win();
                    showGameScores();
                    return true;
                }
            }
            count = 0;
            for (int i = 0; i < 3; i++) {
                if (playerMarks[i][i].equals(player1Mark)) {
                    count += 1;
                }
                if (count == 3) {
                    JOptionPane.showMessageDialog(null, getPlayer1Name() + " wins!");
                    setPlayer1Win();
                    showGameScores();
                    return true;
                }
            }
            count = 0;
            for (int i = 0; i < 3; i++) {
                if (playerMarks[i][2 - i].equals(player1Mark)) {
                    count += 1;
                }
                if (count == 3) {
                    JOptionPane.showMessageDialog(null, getPlayer1Name() + " wins!");
                    setPlayer1Win();
                    showGameScores();
                    return true;
                }
            }
            count = 0;
            for (int i = 0; i < 3; i++) {
                if (playerMarks[i][1].equals(player1Mark)) {
                    count += 1;
                }
                if (count == 3) {
                    JOptionPane.showMessageDialog(null, getPlayer1Name() + " wins!");
                    setPlayer1Win();
                    showGameScores();
                    return true;
                }
            }
            count = 0;
            for (int j = 0; j < 3; j++) {
                if (playerMarks[1][j].equals(player1Mark)) {
                    count += 1;
                }
                if (count == 3) {
                    JOptionPane.showMessageDialog(null, getPlayer1Name() + " wins!");
                    setPlayer1Win();
                    showGameScores();
                    return true;
                }
            }
            if (retryGame == false)
                switchPlayers();
            else if (retryGame == true)
                doNotSwitchPlayers();
            return false;
        }
        else if (isPlayer2Turn == true) {
            int count = 0;
            for (int j = 0; j < 3; j++) {
                if (playerMarks[0][j].equals(player2Mark)) {
                    count += 1;
                }
                if (count == 3) {
                    JOptionPane.showMessageDialog(null, getPlayer2Name() + " wins!");
                    setPlayer2Win();
                    showGameScores();
                    return true;
                }
            }
            count = 0;
            for (int i = 0; i < 3; i++) {
                if (playerMarks[i][0].equals(player2Mark)) {
                    count += 1;
                }
                if (count == 3) {
                    JOptionPane.showMessageDialog(null, getPlayer2Name() + " wins!");
                    setPlayer2Win();
                    showGameScores();
                    return true;
                }
            }
            count = 0;
            for (int i = 0; i < 3; i++) {
                if (playerMarks[i][2].equals(player2Mark)) {
                    count += 1;
                }
                if (count == 3) {
                    JOptionPane.showMessageDialog(null, getPlayer2Name() + " wins!");
                    setPlayer2Win();
                    showGameScores();
                    return true;
                }
            }
            count = 0;
            for (int j = 0; j < 3; j++) {
                if (playerMarks[2][j].equals(player2Mark)) {
                    count += 1;
                }
                if (count == 3) {
                    JOptionPane.showMessageDialog(null, getPlayer2Name() + " wins!");
                    setPlayer2Win();
                    showGameScores();
                    return true;
                }
            }
            count = 0;
            for (int i = 0; i < 3; i++) {
                if (playerMarks[i][i].equals(player2Mark)) {
                    count += 1;
                }
                if (count == 3) {
                    JOptionPane.showMessageDialog(null, getPlayer2Name() + " wins!");
                    setPlayer2Win();
                    showGameScores();
                    return true;
                }
            }
            count = 0;
            for (int i = 0; i < 3; i++) {
                if (playerMarks[i][2 - i].equals(player2Mark)) {
                    count += 1;
                }
                if (count == 3) {
                    JOptionPane.showMessageDialog(null, getPlayer2Name() + " wins!");
                    setPlayer2Win();
                    showGameScores();
                    return true;
                }
            }
            count = 0;
            for (int i = 0; i < 3; i++) {
                if (playerMarks[i][1].equals(player2Mark)) {
                    count += 1;
                }
                if (count == 3) {
                    JOptionPane.showMessageDialog(null, getPlayer2Name() + " wins!");
                    setPlayer2Win();
                    showGameScores();
                    return true;
                }
            }
            count = 0;
            for (int j = 0; j < 3; j++) {
                if (playerMarks[1][j].equals(player2Mark)) {
                    count += 1;
                }
                if (count == 3) {
                    JOptionPane.showMessageDialog(null, getPlayer2Name() + " wins!");
                    setPlayer2Win();
                    showGameScores();
                    return true;
                }
            }
        }
        //isPlayer2Win = false;
        if (retryGame == false)
            switchPlayers();
        else if (retryGame == true)
            doNotSwitchPlayers();
        return false;
    }

    public boolean checkIfDraw() {
        int count = 0;
        for (int i=0; i < 3; i ++) {
            for (int j = 0; j < 3; j++) {
                if (playerMarks[i][j] != "") {
                    count ++;
                }
            }
        }
        if (count == 9) {
            JOptionPane.showMessageDialog(null, "Draw!");
            setPlayerDraw();
            showGameScores();
            return true;
        }
        return false;
    }

    public String getPlayer1Name () {
        return player1Name;
    }
    public String getPlayer2Name () {
        return player2Name;
    }

    public String getPlayer1Mark () {
        return player1Mark;
    }

    public String getPlayer2Mark () {
        return player2Mark;
    }

    public void setPlayer1Name (String player1Name) {
        this.player1Name = player1Name;
        player1Lbl.setText(getPlayer1Name());
    }
    public void setPlayer2Name (String player2Name) {
        this.player2Name = player2Name;
        player2Lbl.setText(getPlayer2Name());
    }

    public void setPlayer1Mark (String player1Mark) {
        this.player1Mark = player1Mark;
    }
    public void setPlayer2Mark (String player2Mark) {
        this.player2Mark = player2Mark;
    }

    public void setPlayer1Turn (boolean isPlayer1Turn) {
        this.isPlayer1Turn = isPlayer1Turn;
    }
    public void setPlayer2Turn (boolean isPlayer2Turn) {
        this.isPlayer2Turn = isPlayer2Turn;
    }

    public int getPlayer1Win (){
        return player1Win;
    }
    public int getPlayer1Lose () {
        return player1Lose;
    }
    public int getPlayer2Win () {
        return player2Win;
    }
    public int getPlayer2Lose () {
        return player2Lose;
    }

    public void setPlayer1Win () {
        player1Win ++;
        setPlayer2Lose();
        isPlayer1Win = true;
    }
    public void setPlayer1Lose ()
    {
        player1Lose ++;
        //isPlayer2Lose = true;
    }
    public void setPlayer2Win ()
    {
        player2Win ++;
        setPlayer1Lose();
        isPlayer2Win = true;
    }
    public void setPlayer2Lose () {
        player2Lose++;
        //isPlayer2Lose = true;
    }

    public int getPlayer1Draw ()
    {
        return player1Draw;
    }
    public int getPlayer2Draw ()
    {
        return  player2Draw;
    }

    public void setPlayerDraw () {
        player1Draw ++;
        player2Draw++;
        isDraw = true;
    }


    public void switchPlayers () {
        if (isPlayer1Turn == true) {
            isPlayer1Turn = false;
            boardLeftPanel.setBackground(Color.lightGray);
            isPlayer2Turn = true;
            boardRightPanel.setBackground(new Color( 0xFDCE5D));
        }
        else {
            isPlayer1Turn = true;
            boardLeftPanel.setBackground(new Color(0xFDCE5D));
            isPlayer2Turn= false;
            boardRightPanel.setBackground(Color.lightGray);
        }
    }

    public void doNotSwitchPlayers () {
        if (isPlayer1Turn == true) {
            isPlayer1Turn = true;
            boardLeftPanel.setBackground(new Color( 0xFDCE5D));
            isPlayer2Turn = false;
            boardRightPanel.setBackground(Color.lightGray);
        }
        else {
            isPlayer1Turn = false;
            boardLeftPanel.setBackground(Color.lightGray);
            isPlayer2Turn= true;
            boardRightPanel.setBackground(new Color( 0xFDCE5D));
        }
    }


    public void setLastPlayer (String lastPlayer) {
        this.lastPlayer = lastPlayer;
    }

    public void checkLastPlayerAndSetNextTurn () {
        if (lastPlayer == "player1") {
            setPlayer2Turn(true);
            boardRightPanel.setBackground(new Color( 0xFDCE5D));
            setPlayer1Turn(false);
            boardLeftPanel.setBackground(Color.lightGray);
        }
        else if (lastPlayer == "player2") {
            setPlayer1Turn(true);
            boardLeftPanel.setBackground(new Color( 0xFDCE5D));
            setPlayer2Turn(false);
            boardRightPanel.setBackground(Color.lightGray);
        }
    }

    public void setPlayerRetry (boolean playerRetry) {
        this.playerRetry = playerRetry;
    }

    public boolean isPlayer1Win () {
        return isPlayer1Win;
    }

    public boolean isPlayer2Win () {
        return isPlayer2Win;
    }

    public void confirmRetry () {
        int userChoice = JOptionPane.showConfirmDialog(null, "Retry the game?", "Tic Tac Toe", JOptionPane.YES_NO_OPTION);
        if (userChoice == JOptionPane.YES_OPTION) {
            //retryGame = true;
            resetBoard();
            randomizePlayer();
            isPlayer1Win = false;
            isPlayer2Win = false;
            isDraw = false;
        }
        else{
            //retryGame = false;
            System.exit(0);
        }
    }

    public void confirmNewGame () throws InterruptedException {
        int userChoice = JOptionPane.showConfirmDialog(null, "New game?", "Tic Tac Toe", JOptionPane.YES_NO_OPTION);
        if (userChoice == JOptionPane.YES_OPTION) {
            //retryGame = true;
            this.setVisible(false);
            new DoublePlayerBoardGUI();
        }
        else{
            //Continue game
        }
    }

    public void resetBoard () {
        for (int i = 0; i < 3; i ++) {
            for (int j = 0; j < 3; j ++) {
                playerMarks [i] [j] = "";
            }
        }
        for (JButton gridBtn: gridBtn) {
            Thread thread = new Thread(() ->{
                    gridBtn.setText("");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            });
            thread.start();
        }
    }

    public void randomizePlayer () {
        Random rand = new Random();
        if (rand.nextInt(2) == 0) {
            setPlayer1Turn(true);
            setLastPlayer("player2");
            setPlayer2Turn(false);
            boardLeftPanel.setBackground(new Color(0xFDCE5D));
            boardRightPanel.setBackground(Color.lightGray);
        } else {
            setPlayer1Turn(false);
            setPlayer2Turn(true);
            setLastPlayer("player1");
            boardLeftPanel.setBackground(Color.lightGray);
            boardRightPanel.setBackground(new Color(0xFDCE5D));

        }
    }

    public void choosePlayerName () {
        String chosenPlayer1Name = JOptionPane.showInputDialog("Enter first player Name","Tom");
        if (chosenPlayer1Name == null) {
            setPlayer1Name(player1Name);
        } else {
            setPlayer1Name(chosenPlayer1Name);
        }
        String chosenPlayer2Name = JOptionPane.showInputDialog("Enter second player Name", "Becky");
        if (chosenPlayer2Name == null) {
            setPlayer2Name(player2Name);
        } else {
            setPlayer2Name( chosenPlayer2Name);
        }
    }


    public void showGameScores ()
    {
        player1Score.setText("Win: " + getPlayer1Win() + "\n" + "Lose: " + getPlayer1Lose() + "\n" + "Draw: " + getPlayer1Draw());
        player2Score.setText("Win: " + getPlayer2Win() + "\n" + "Lose: " + getPlayer2Lose() + "\n" + "Draw: " + getPlayer2Draw());
    }


}
