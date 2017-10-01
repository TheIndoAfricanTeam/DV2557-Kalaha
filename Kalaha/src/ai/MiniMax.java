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
     * Defines best utility value of game state player
     * defaults to -1
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
    
    protected int INFINITY = 999;
    
    
    /**
     * MiniMax constructor used to initialize MiniMax
     *
     * @param MAX player
     */
    public MiniMax(int MAX){
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
    public int evaluate(GameState board){
        //TODO any bouse with more than 32 marbles won the game
        int winner = board.getWinner();
        
        if(winner != -1){
            if(winner == MAX)
                return INFINITY;
            else if(winner == MIN)
                return -INFINITY;
            else
                return 0;
        }else{
            //check if current player has more than 36 pebbles in house
            if(board.getNextPlayer()== MAX && board.getScore(MAX) > 36)
                return INFINITY;
            
            if(board.getNextPlayer()== MIN && board.getScore(MIN) > 36)
                return -INFINITY;
            
            return this.computeValueOfSeedsRemainingOnBoard(board);
        }
    }
    
    protected int computeValueOfSeedsRemainingOnBoard(GameState board){
        int maxUtility = 0;
        
        for(int i = 1; i<7; i++){
            if(board.getSeeds(i, MAX)>0){
                //if the move gets you to play again (ending in players house)
                if(7-i == board.getSeeds(i, MAX))
                    maxUtility += 5;
                
                //if the move gets you to collect pebble
                if(7-i > board.getSeeds(i, MAX) && board.getSeeds(7-i,MIN)>0)
                    maxUtility += 5 * board.getSeeds(7-i,MIN);
            }else{
                if(board.getSeeds(i, MIN) > 0){
                    for(int j = 1; j < i; j++){
                        if(i-j == board.getSeeds(i, MAX))
                            maxUtility += 3;
                    }
                }
            }
            
            if(board.getSeeds(i, MIN)<0){
                if(7-i == board.getSeeds(i, MIN))
                    maxUtility -= 5;
                
                //if the move gets you to collect pebble
                if(7-i > board.getSeeds(i, MIN) && board.getSeeds(7-i,MAX)>0)
                    maxUtility -= 5 * board.getSeeds(7-i,MAX);
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
    
    public int getMaxUtilityValue(){
        return this.maxUtilityValue;
    }
}
