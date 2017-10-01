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
public class MiniMaxDFS {
    /**
         * Defines best utility value of game state player
         * defaults to -1
         *
         */
    private int maxUtilityValue = 0;
        
    /**
     * Defines the maximum number for moves
     */
    private static final int MOVES_LENGTH = 7;
    
    /**
     * Defines the current board state
     */
    private GameState board;
    
    /**
     * Defines MAX player
     */
    private int MAX;
    
    /**
     * Defines MIN player
     */
    private int MIN;
    
    /**
     * Defines best move
     */
    private int bestMove = -1;
    
    
    /**
     * MiniMax constructor used to initialize MiniMax
     *
     * @param MAX player
     */
    public MiniMaxDFS(int MAX){
        if(MAX==1){
            this.MIN = 2;
            this.MAX = 1;
        }else{
            this.MIN = 1;
            this.MAX = 2;
        }
    }
    
    /**
     *  calculate the utility value of a node
     *
     *  @param gameState
     *
     *  @return utilityValue
     */
    private int evaluate(GameState board){
        int maxUtility = 0;
        
        int MAXScore = board.getScore(MAX);
        int MINScore = board.getScore(MIN);
        
        if(board.getWinner() == -1){
            if(MAXScore>MINScore){
                maxUtility += 1;
            }else{
                maxUtility -= 1;
            }
            
            return maxUtility = this.computeValueOfSeedsRemainingOnBoard(board, maxUtility);
        }else if(board.getWinner() == MAX){
            return 50;
        }else{
            return -50;
        }
    }
    
    private int computeValueOfSeedsRemainingOnBoard(GameState board, int maxUtility){
        for(int i = 1; i<7; i++){
            if(board.getSeeds(i, MAX)!=0){
                if(7-i == board.getSeeds(i, MAX))
                    maxUtility += 5;
            }else{
                if(board.getSeeds(i, MIN) > 0){
                    for(int j = 1; j < i; j++){
                        if(i-j == board.getSeeds(i, MAX))
                            maxUtility += 3;
                    }
                }
            }
            
            if(board.getSeeds(i, MIN)!=0){
                if(7-i == board.getSeeds(i, MIN))
                    maxUtility -= 5;
            }else{
                if(board.getSeeds(i, MAX) > 0){
                    for(int j = 1; j < i; j++){
                        if(i-j == board.getSeeds(i, MIN))
                            maxUtility -= 3;
                    }
                }
            }
        }
        
        return maxUtility;
    }
    
    /**
     *  works through valid game states to find the best move for the current game state
     *
     *  @param gameState
     *  @param depth
     *
     *  @return utilityValue;
     */
    public int findBestMove(GameState board, int depth){
               
        /**
         *   return the utility value of a board if game ends or depth is 0
         */
        if(depth<=0 || board.gameEnded()){
            return this.evaluate(board);
        }
        
        /**
         *   make moves for MAX and MIN till game state or final state base on depth
         */
        if(board.getNextPlayer()==MAX){            
            for(int move : this.getPossibleMoves(board)){
                GameState clone = board.clone();
                clone.makeMove(move);
                
                int boardUtility = this.findBestMove(clone,depth - 1);
                
                if(boardUtility < this.maxUtilityValue){
                    this.bestMove =  move;
                    this.maxUtilityValue = boardUtility;
                }
            }
        }else{
            for(int move : this.getPossibleMoves(board)){
                GameState clone = board.clone();
                clone.makeMove(move);
                
                int boardUtility = this.findBestMove(clone,depth - 1);
                
                if(boardUtility > this.maxUtilityValue){
                    this.bestMove =  move;
                    this.maxUtilityValue = boardUtility;
                }
            }
        }
        
        return this.maxUtilityValue;
    }
    
    public List<Integer> getPossibleMoves(GameState board){
        List<Integer> possibleMoves = new ArrayList<>();
        
        for(int move=1; move<MOVES_LENGTH; move++){
            if(board.moveIsPossible(move))
                possibleMoves.add(move);
        }
        
        return possibleMoves;
    }
    
    public int getBestMove(){
        return this.bestMove;
    }
}
