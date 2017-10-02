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
class MiniMaxDFSAlphaBetaPrunning extends MiniMax{
    public MiniMaxDFSAlphaBetaPrunning(int MAX){
        super(MAX);
    }
    
    /**
     *  works through valid game states to find the best move for the current game state
     *
     *  @param gameState
     *  @param depth
     *
     *  @return utilityValue;
     */
    public int findBestMove(GameState board, int depth, int alpha, int beta){
        int bestChildMove = -1;
        
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
            int bestChildUtilityValue = alpha;
            
            for(int move : this.getPossibleMoves(board)){
                GameState clone = board.clone();
                clone.makeMove(move);
                
                int boardUtility = this.findBestMove(clone,depth - 1, bestChildUtilityValue, beta);
                
                if(boardUtility > bestChildUtilityValue){
                    bestChildMove = move;
                    bestChildUtilityValue = boardUtility;
                }else if(bestChildUtilityValue > beta){
                    return beta;
                }
            }
            
            this.bestMove =  bestChildMove;
            return bestChildUtilityValue;
        }else{
            int bestChildUtilityValue = beta;
            
            for(int move : this.getPossibleMoves(board)){
                
                GameState clone = board.clone();
                clone.makeMove(move);
                
                int boardUtility = this.findBestMove(clone,depth - 1, alpha, bestChildUtilityValue);
                
                if(boardUtility < bestChildUtilityValue){
                    bestChildMove = move;
                    bestChildUtilityValue = boardUtility;
                }else if(bestChildUtilityValue < alpha){
                    return alpha;
                }
            }
            
            this.bestMove = bestChildMove;
            return bestChildUtilityValue;
        }
    }
}
