package Tictactoe;

import java.util.Objects;

/**
 * Created by vitiv on 5/14/17.
 */
public class Game {
    private Integer[][] _board = new Integer[3][3];

    public Integer getWinner() {
        return _winner;
    }

    private Integer _winner = null;
    private String player1;
    private String player2;



    public void setPlayer1(String player1) {
        this.player1 = player1;
    }


    public void setPlayer2(String player2) {
        this.player2 = player2;
    }


    public Game() {
        player1 = null;
        player2 = null;
    }

    public Game(String player1) {
        this.player1 = player1;
        this.player2 = null;
    }

    public Game(String player1, String player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public Integer getCell(int x, int y) {
        return _board[x-1][y-1];
    }


    public void setCell(int x, int y, Integer val) {
        _board[x-1][y-1] = val;
        _winner = this._computeWinner();
    }

    public void setCell(int x, int y) {
        _board[x-1][y-1] = getCurrentPlayer();
        _winner = this._computeWinner();
    }

    private Integer _computeWinner() {
        // checks lines and columns
        for(int i = 0; i < 3 && this._winner == null; ++i) {
            if (_board[0][i] != null && Objects.equals(_board[0][i], _board[1][i]) && Objects.equals(_board[1][i], _board[2][i]))
                return _board[0][i];
            if (_board[i][0] != null && Objects.equals(_board[i][0], _board[i][1]) && Objects.equals(_board[i][1], _board[i][2]))
                return _board[i][0];
        }

        // check diagonals
        if (_board[0][0] != null && Objects.equals(_board[0][0], _board[1][1]) && Objects.equals(_board[1][1], _board[2][2]))
            return _board[0][0];
        if (_board[0][2] != null && Objects.equals(_board[0][2], _board[1][1]) && Objects.equals(_board[1][1], _board[2][0]))
            return _board[0][2];
        return null;
    }


    public Integer getCurrentPlayer() {
        int nr1=0, nr2=0;
        for(int i=0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (_board[i][j] != null && _board[i][j].equals(1)) {
                    nr1++;
                }
                if (_board[i][j] != null  && _board[i][j].equals(2)) {
                    nr2++;
                }
            }
        }
        if(nr1-nr2 == 0)
            return 1;
        return 2;
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }
}
