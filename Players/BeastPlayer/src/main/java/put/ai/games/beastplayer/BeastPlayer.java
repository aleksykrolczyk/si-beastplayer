/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.games.beastplayer;

import java.util.List;
import java.util.Random;
import put.ai.games.game.Board;
import put.ai.games.game.Move;
import put.ai.games.game.Player;

public class BeastPlayer extends Player {

    private Integer max = 0;
    private int depth = 3;

    @Override
    public String getName() {
        return "IM THE BEAST";
    }

    public Move nextMoveRandom(Board board){
        List<Move> moves = board.getMovesFor(getColor());
        return moves.get(new Random().nextInt(moves.size()));
    }


    @Override
    public Move nextMove(Board board) {
        List<Move> moves = board.getMovesFor(getColor());
        return moves.get(new Random().nextInt(moves.size()));
    }

    public int intelligentMove(GameTree currentTree, boolean maximizingPlayer, Board board)
    {
        int bestVal = 0;

        if(depth == 0)
        {
            //System.out.println("Reached leaf node with utility of " + currentTree.getBoard().getUtility() + ". Board:");
            //currentTree.getBoard().printBoard();
            return getUtility(board);
        }

        if(maximizingPlayer){
            bestVal = Integer.MIN_VALUE;
            board.
            currentTree.populateNodeChildren();
            ArrayList<GameTree> childTrees = currentTree.getChildTrees();
            //for each child tree of current tree node
            for(int i = 0; i < childTrees.size(); i++) {
                //get max utility value
                int thisVal = intelligentMove(depth - 1, childTrees.get(i), false);
                if(bestVal < thisVal) bestVal = thisVal;
            }
            //if depth of one below parent is reached through recursion and the board has better utility than the previous best, make new board next move
            if(bestVal > max && depth == this.depth - 1) {
                max = bestVal;
                maxBoard = currentTree.getBoard();
            }
        }
        else{	//minimizing player
            bestVal = Integer.MAX_VALUE;
            currentTree.populateNodeChildren();
            ArrayList<GameTree> childTrees = currentTree.getChildTrees();
            for(int i = 0; i < childTrees.size(); i++)
            {
                int thisVal = intelligentMove(depth - 1, childTrees.get(i), true);
                if(bestVal > thisVal)
                    bestVal = thisVal;
            }
        }

        return bestVal;
    }

    public int getUtility(Board board) {
        Color color = this.getColor();
        Integer size = board.getSize();
        int utility = 0;
        int streak = 0;			//streak is used to add additional points for more than 2 in a row
        //2 in a row = 1 pt, 3 in a row = 2 pt, etc.

        // hotizontalle
        for(int i = 0; i < size; i++){	//go down row
            for(int j = 0; j < size-1; j++){	//count doubles
                if(board.getState(i,j) == color && board.getState(i,j+1) == color){
                    utility += streak + 1;
                    streak++;
                }
                else streak = 0;
            }
        }

        // verticalle
        for(int i = 0; i < size; i++){	//go down columns
            for(int j = 0; j < size-1; j++){	//count doubles
                if(board.getState(j, i) == color && board.getState(j+1, i) == color) {
                    utility += streak + 1;
                    streak++;
                }
                else streak = 0;
            }
        }

        // diagonalle (start top-left)
        for(int i = 0; i < size-1; i++) {
            if(board.getState(i,i) == color && board.getState(i+1, i+1) == color) {
                utility += streak + 1;
                streak++;
            }
            else streak = 0;
        }

        //count main diagonal up-right to down-left
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

//    public int getUtility(Board board) {
//        Color color = this.getColor();
//        Integer size = board.getSize();
//        int utility = 0;
//        int streak = 0;			//streak is used to add additional points for more than 2 in a row
//        //2 in a row = 1 pt, 3 in a row = 2 pt, etc.
//
//        // hotizontalle
//        for(int i = 0; i < 6; i++){	//go down row
//            for(int j = 0; j < 5; j++){	//count doubles
//                if(board.getState(i,j) == color && board.getState(i,j+1) == color){
//                    utility += streak + 1;
//                    streak++;
//                }
//                else streak = 0;
//            }
//        }
//
//        // verticalle
//        for(int i = 0; i < 6; i++){	//go down columns
//            for(int j = 0; j < 5; j++){	//count doubles
//                if(board.getState(j, i) == color && board.getState(j+1, i) == color) {
//                    utility += streak + 1;
//                    streak++;
//                }
//                else streak = 0;
//            }
//        }
//
//        // diagonalle (start top-left)
//        for(int i = 0; i < 5; i++) {
//            if(board.getState(i,i) == color && board.getState(i+1, i+1) == color) {
//                utility += streak + 1;
//                streak++;
//            }
//            else streak = 0;
//        }
//
//        //count main diagonal up-right to down-left
//        for(int i = 0; i < 5; i++) {
//            if(board.getState(i, 5-i) == color && board.getState(i+1,4-i) == color) {
//                utility += streak + 1;
//                streak++;
//            }
//            else
//                streak = 0;
//        }
//
//        return utility;
//    }
}
