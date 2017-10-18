/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package ai;

import java.util.HashMap;
import java.util.Map;
import kalaha.GameState;

/**
 * @author Optimistic
 *
 * Using minimax depth-first-search to find the best move for AI client
 */
class MiniMaxDFS extends MiniMax {
    
    public MiniMaxDFS(int MAX) {
        super(MAX);
    }

    public int findBestMove(GameState board, int depth) {
        if (board.gameEnded() || depth == DEPTH) {
            return getUtilityValue(board);
        }

        int boardUtility = (board.getNextPlayer() == MAX) ? -100 : 100;
        int bestBoardMove = -1;
        Map<Integer, Integer> utils = new HashMap<>();

        for (int move : this.getPossibleMoves(board)) {
            GameState clone = board.clone();
            clone.makeMove(move);

            int moveUtility = findBestMove(clone, depth + 1);
            utils.put(move, moveUtility);

            if (board.getNextPlayer() == MAX && boardUtility < moveUtility) {
                boardUtility = moveUtility;
                bestBoardMove = move;
            }

            if (board.getNextPlayer() == MIN && boardUtility > moveUtility) {
                boardUtility = moveUtility;
                bestBoardMove = move;
            }
        }

        System.out.println("DEPTH " + depth);
        System.out.println(utils);

        this.bestMove = bestBoardMove;
        return boardUtility;
    }
}
