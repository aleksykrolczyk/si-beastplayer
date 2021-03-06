/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.games.beastplayer;

import java.util.*;

import put.ai.games.game.Board;
import put.ai.games.game.Move;
import put.ai.games.game.Player;

public class BeastPlayer extends Player {

    private Integer max = 0;
    private Move max_move;
    private int depth = 2;

    @Override
    public String getName() {
        return "Aleksy Królczyk 136746 Michał Cichy 132203";
    }

    public Move nextMoveRandom(Board board){
        List<Move> moves = board.getMovesFor(getColor());
        return moves.get(new Random().nextInt(moves.size()));
    }


    @Override
    public Move nextMove(Board board) {
        Board clone = board.clone();

        max = 0;
        minmax(this.depth, clone, false);
        System.out.println(max_move);
        clone.doMove(max_move);
        System.out.println(getUtility(clone));;
        return max_move;
    }

    public int minmax(int depth, Board parent_board, boolean maximizingPlayer)
    {
        int bestVal = 0;
        Move best_move = null;
        if(depth == 0) {
            return getUtility(parent_board);
        }

        List<Move> possible_moves = parent_board.getMovesFor(getColor());
        Board temp_board = parent_board.clone();

        for(Move move: possible_moves){
            temp_board.doMove(move);
            if (temp_board.getWinner(getColor()) == getColor()){
                max_move = move;
                return Integer.MAX_VALUE;
            }
            temp_board.undoMove(move);
        }


        if(maximizingPlayer){ // max
            bestVal = Integer.MIN_VALUE;
            for(Move move : possible_moves) {
                temp_board.doMove(move);
                int thisVal = minmax(depth - 1, temp_board, false);
                temp_board.undoMove(move);
                if(bestVal < thisVal) {
                    bestVal = thisVal;
                    best_move = move;
                    }
                }
            }
            if(bestVal > max && depth == this.depth - 1) {
                max = bestVal;
                max_move = best_move;
            }

        else{ // min
            bestVal = Integer.MAX_VALUE;
            for(Move move : possible_moves){
                temp_board.doMove(move);
                int thisVal = minmax(depth - 1, temp_board , true);
                temp_board.undoMove(move);
                if(bestVal > thisVal){
                    bestVal = thisVal;
                }
            }
        }

        return bestVal;
    }


    public int getUtility(Board board) {
        Color color = this.getColor();
        Integer size = board.getSize();
        int utility = 0;
        int streak = 0;

        if (board.getWinner(color) == color){
            return Integer.MAX_VALUE;
        }

        // vertical
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size-1; j++){
                if(board.getState(i,j) == color && board.getState(i,j+1) == color){
                    utility += streak + 1;
                    streak++;
                }
                else streak = 0;
            }
        }
        streak = 0;

        // horizontal
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size-1; j++){
                if(board.getState(j, i) == color && board.getState(j+1, i) == color) {
                    utility += streak + 1;
                    streak++;
                }
                else streak = 0;
            }
        }
        streak = 0;

        // diagonal 1
        for(int i = 0; i < size-1; i++) {
            if(board.getState(i,i) == color && board.getState(i+1, i+1) == color) {
                utility += streak + 1;
                streak++;
            }
            else streak = 0;
        }
        streak = 0;

        // diagonal 2
        for(int i = 0; i < size-1; i++) {
            if(board.getState(i, size-1-i) == color && board.getState(i+1,size-2-i) == color) {
                utility += streak + 1;
                streak++;
            }
            else
                streak = 0;
        }

        return utility;
    }
}
