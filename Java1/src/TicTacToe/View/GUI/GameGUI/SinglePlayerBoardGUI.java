package TicTacToe.View.GUI.GameGUI;

import javax.swing.*;
import java.util.Random;

public class SinglePlayerBoardGUI extends DoublePlayerBoardGUI {

    private boolean cellFound = false;

    public SinglePlayerBoardGUI() throws InterruptedException {
        player2Name = "Bot";
    }

    @Override
    public void choosePlayerName() {
        String chosenPlayer1Name = JOptionPane.showInputDialog("Enter first player Name", "Tom");
        if (chosenPlayer1Name == null) {
            setPlayer1Name(player1Name);
        } else {
            setPlayer1Name(chosenPlayer1Name);
        }
    }


    @Override
    public void checkBoardInstruction() {
        retryGame = false;
        //Thread thread = new Thread(() -> {

            if (isPlayer1Turn == true) {
                switch (boardInstruction) {
                    case "00": {
                        if (player1WrongAtCell(0, 0))
                            break;
                        btn00.setText(getPlayer1Mark());
                        playerMarks[0][0] = getPlayer1Mark();
                        break;
                    }
                    case "01": {
                        if (player1WrongAtCell(0, 1))
                            break;
                        btn01.setText(getPlayer1Mark());
                        playerMarks[0][1] = getPlayer1Mark();
                        break;

                    }
                    case "02": {
                        if (player1WrongAtCell(0, 2))
                            break;
                        btn02.setText(getPlayer1Mark());
                        playerMarks[0][2] = getPlayer1Mark();
                        break;

                    }
                    case "10": {
                        if (player1WrongAtCell(1, 0))
                            break;
                        btn10.setText(getPlayer1Mark());
                        playerMarks[1][0] = getPlayer1Mark();
                        break;

                    }
                    case "11": {
                        if (player1WrongAtCell(1, 1))
                            break;
                        btn11.setText(getPlayer1Mark());
                        playerMarks[1][1] = getPlayer1Mark();
                        break;

                    }
                    case "12": {
                        if (player1WrongAtCell(1, 2))
                            break;
                        btn12.setText(getPlayer1Mark());
                        playerMarks[1][2] = getPlayer1Mark();
                        break;

                    }
                    case "20": {
                        if (player1WrongAtCell(2, 0))
                            break;
                        btn20.setText(getPlayer1Mark());
                        playerMarks[2][0] = getPlayer1Mark();
                        break;

                    }
                    case "21": {
                        if (player1WrongAtCell(2, 1))
                            break;
                        btn21.setText(getPlayer1Mark());
                        playerMarks[2][1] = getPlayer1Mark();
                        break;

                    }
                    case "22": {
                        if (player1WrongAtCell(2, 2))
                            break;
                        btn22.setText(getPlayer1Mark());
                        playerMarks[2][2] = getPlayer1Mark();
                        break;

                    }
                    default:

                }

            } else if (isPlayer2Turn == true) {

                cellFound = false;
                do {
                    int rndBtn = new Random().nextInt(gridBtn.size());
                    switch (rndBtn) {
                        case 0: {
                            if (playerMarks[0][0] == "") {
                                SwingWorker <Void, Void> worker = new SwingWorker<Void, Void>() {
                                    @Override
                                    protected Void doInBackground() throws Exception {
                                        return null;
                                    }

                                    @Override
                                    protected void done() {
                                        btn00.setText(player2Mark);
                                    }
                                };
                                btn00.setText(player2Mark);
                                playerMarks[0][0] = getPlayer2Mark();
                                cellFound = true;
                                worker.execute();
                                break;
                            }
                        }
                        case 1: {
                            if (playerMarks[0][1] == "") {
                                SwingWorker <Void, Void> worker1 = new SwingWorker<Void, Void>() {
                                    @Override
                                    protected Void doInBackground() throws Exception {
                                        return null;
                                    }

                                    @Override
                                    protected void done() {
                                        btn01.setText(player2Mark);
                                    }
                                };
                                btn01.setText(player2Mark);
                                playerMarks[0][1] = getPlayer2Mark();
                                cellFound = true;
                                worker1.execute();
                                break;
                            }
                        }
                        case 2: {
                            if (playerMarks[0][2] == "") {
                                SwingWorker <Void, Void> worker2 = new SwingWorker<Void, Void>() {
                                    @Override
                                    protected Void doInBackground() throws Exception {
                                        return null;
                                    }

                                    @Override
                                    protected void done() {
                                        btn02.setText(player2Mark);
                                    }
                                };
                                btn02.setText(player2Mark);
                                playerMarks[0][2] = getPlayer2Mark();
                                cellFound = true;
                                worker2.execute();
                                break;
                            }
                        }
                        case 3: {
                            if (playerMarks[1][0] == "") {
                                SwingWorker <Void, Void> worker3 = new SwingWorker<Void, Void>() {
                                    @Override
                                    protected Void doInBackground() throws Exception {
                                        return null;
                                    }

                                    @Override
                                    protected void done() {
                                        btn10.setText(player2Mark);
                                    }
                                };
                                btn10.setText(player2Mark);
                                playerMarks[1][0] = getPlayer2Mark();
                                cellFound = true;
                                worker3.execute();
                                break;
                            }
                        }
                        case 4: {
                            if (playerMarks[1][1] == "") {
                                SwingWorker <Void, Void> worker4 = new SwingWorker<Void, Void>() {
                                    @Override
                                    protected Void doInBackground() throws Exception {
                                        return null;
                                    }

                                    @Override
                                    protected void done() {
                                        btn11.setText(player2Mark);
                                    }
                                };
                                btn11.setText(player2Mark);
                                playerMarks[1][1] = getPlayer2Mark();
                                cellFound = true;
                                worker4.execute();
                                break;
                            }
                        }
                        case 5: {
                            if (playerMarks[1][2] == "") {
                                SwingWorker <Void, Void> worker5 = new SwingWorker<Void, Void>() {
                                    @Override
                                    protected Void doInBackground() throws Exception {
                                        return null;
                                    }

                                    @Override
                                    protected void done() {
                                        btn12.setText(player2Mark);
                                    }
                                };
                                btn12.setText(player2Mark);
                                playerMarks[1][2] = getPlayer2Mark();
                                cellFound = true;
                                worker5.execute();
                                break;
                            }
                        }
                        case 6: {
                            if (playerMarks[2][0] == "") {
                                SwingWorker <Void, Void> worker6 = new SwingWorker<Void, Void>() {
                                    @Override
                                    protected Void doInBackground() throws Exception {
                                        return null;
                                    }

                                    @Override
                                    protected void done() {
                                        btn20.setText(player2Mark);
                                    }
                                };
                                btn20.setText(player2Mark);
                                playerMarks[2][0] = getPlayer2Mark();
                                cellFound = true;
                                worker6.execute();
                                break;
                            }
                        }
                        case 7: {
                            if (playerMarks[2][1] == "") {
                                SwingWorker <Void, Void> worker7 = new SwingWorker<Void, Void>() {
                                    @Override
                                    protected Void doInBackground() throws Exception {
                                        return null;
                                    }

                                    @Override
                                    protected void done() {
                                        btn21.setText(player2Mark);
                                    }
                                };
                                btn21.setText(player2Mark);
                                playerMarks[2][1] = getPlayer2Mark();
                                cellFound = true;
                                worker7.execute();
                                break;
                            }
                        }
                        case 8: {
                            if (playerMarks[2][2] == "") {
                                SwingWorker <Void, Void> worker8 = new SwingWorker<Void, Void>() {
                                    @Override
                                    protected Void doInBackground() throws Exception {
                                        return null;
                                    }

                                    @Override
                                    protected void done() {
                                        btn22.setText(player2Mark);
                                    }
                                };
                                btn22.setText(player2Mark);
                                playerMarks[2][2] = getPlayer2Mark();
                                cellFound = true;
                                worker8.execute();
                                break;
                            }
                        }
                    }
                } while (!cellFound);


            }
            checkIfWin();
            if (isPlayer1Win == true || isPlayer2Win == true) {
                confirmRetry();
            } else {
                checkIfDraw();
                if (isDraw)
                    confirmRetry();
            }

        //});//////////////////////////////////
        //thread.start();//////////////////////////
    }

    @Override
    public boolean checkIfWin() {
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
        } else if (isPlayer2Turn == true) {

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


    @Override
    public void switchPlayers() {
        super.switchPlayers();
    }
}

