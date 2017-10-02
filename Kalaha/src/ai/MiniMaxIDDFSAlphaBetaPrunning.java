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
class MiniMaxIDDFSAlphaBetaPrunning extends MiniMax {
    public MiniMaxIDDFSAlphaBetaPrunning(int MAX){
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
    public int findBestMove(GameState board, int depth, double timeElapsed,int alpha, int beta){
        long startTime = System.currentTimeMillis();
        int bestChildMove = -1;
        
        /**
         *   return the utility value of a board if game ends or depth is 0 or has been running for 4secs
         */
        if(depth<=0 || board.gameEnded() || timeElapsed>4){
            return this.evaluate(board);
        }
        
        /**
         *   make moves for MAX and MIN till game state or final state base on depth
         */
        if(board.getNextPlayer()==MAX){
            int bestChildUtilityValue = alpha;
            
            for(int _depth = 1; _depth<depth; _depth++){
                
                for(int move : this.getPossibleMoves(board)){
                    GameState clone = board.clone();
                    clone.makeMove(move);
                    
                    long endTime = System.currentTimeMillis()-startTime;
                    int boardUtility = this.findBestMove(clone,depth - 1, (endTime/1000), bestChildUtilityValue, beta);
                    
                    if(boardUtility > bestChildUtilityValue){
                        bestChildMove = move;
                        bestChildUtilityValue = boardUtility;
                    }else if(bestChildUtilityValue > beta){
                        return beta;
                    }
                }
            }
            
            this.maxUtilityValue = bestChildUtilityValue;
        }else{
            int bestChildUtilityValue = beta;
            
            for(int _depth = 1; _depth<depth; _depth++){
                
                for(int move : this.getPossibleMoves(board)){
                    
                    GameState clone = board.clone();
                    clone.makeMove(move);
                    
                    long endTime = System.currentTimeMillis()-startTime;
                    int boardUtility = this.findBestMove(clone,depth - 1, (endTime/1000), alpha, bestChildUtilityValue);
                    
                    if(boardUtility < bestChildUtilityValue){
                        bestChildMove = move;
                        bestChildUtilityValue = boardUtility;
                    }else if(bestChildUtilityValue < alpha){
                        return alpha;
                    }
                }
            }
            
            this.maxUtilityValue = bestChildUtilityValue;
        }
        
        
        
        this.bestMove = bestChildMove;
        return this.maxUtilityValue;
    }
}
