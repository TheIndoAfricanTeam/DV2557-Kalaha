/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package ai;

import java.util.ArrayList;
import java.util.List;
import kalaha.GameState;

/**
 * @author Optimistic
 *
 * Using minimax depth-first-search to find the best move for AI client
 */
abstract class MiniMax {

    /**
     * Defines best utility value of game state player defaults to -1
     *
     */
    protected int maxUtilityValue = 0;

    /**
     * Defines the maximum number for moves
     */
    protected static final int MOVES_LENGTH = 7;

    /**
     * Defines the current board state
     */
    protected GameState board;

    /**
     * Defines MAX player
     */
    protected int MAX;

    /**
     * Defines MIN player
     */
    protected int MIN;

    /**
     * Defines best move
     */
    protected int bestMove = -1;

    /**
     * Defines a large infinity
     */
    protected static final int INFINITY = 9999;

    /**
     * Define the DEPTH constant for minimax
     */
    protected static final int DEPTH = 10;

    /**
     * MiniMax constructor used to initialise MiniMax
     *
     * @param MAX player
     */
    public MiniMax(int MAX) {
        if (MAX == 1) {
            this.MIN = 2;
            this.MAX = 1;
        } else {
            this.MIN = 1;
            this.MAX = 2;
        }
    }

    /**
     *  Gets the previous player of a game state
     * 
     *  @return previousPlayer
     * 
     */
    private int getPreviousPlayer(int player) {
        return player == 1 ? 2 : 1;
    }

    
    /**
     * calculate the utility value of a GameState. this is the score of the previous player
     *
     * @param gameState
     *
     * @return utilityValue
     */
    public int getUtilityValue(GameState board) {
        return board.getScore(this.getPreviousPlayer(board.getNextPlayer()));
    }
    
    /**
     *  Generate all possible moves for the board
     * 
     *  @param board
     * 
     *  @return possibleMoves[]
     * 
     */
    public List<Integer> getPossibleMoves(GameState board) {
        List<Integer> possibleMoves = new ArrayList<>();

        for (int move = 1; move < MOVES_LENGTH; move++) {
            if (board.moveIsPossible(move)) {
                possibleMoves.add(move);
            }
        }

        return possibleMoves;
    }

    
    /**
     *  Get the best move for the board
     * 
     *  @return bestMove
     * 
     */
    public int getBestMove() {
        return this.bestMove;
    }
}
