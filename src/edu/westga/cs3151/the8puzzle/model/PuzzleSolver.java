package edu.westga.cs3151.the8puzzle.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Solves a puzzle based on the initial state input
 * @author CodyVollrath
 *
 */
public class PuzzleSolver {
	private Board board;
	private String currentState;
	private Queue<Move> moveTracker;
	private Queue<Move> minimumNumberOfMoves;
	private HashSet<String> states;
	private static final String GOAL_STATE = "123456780";
	
	/**
	 * Sets the initial state
	 * @param board the puzzle board
	 */
	public PuzzleSolver(Board board) {
		this.board = board;
		this.currentState = this.convertBoardToString();
		this.moveTracker = new LinkedList<Move>();
		this.minimumNumberOfMoves = new LinkedList<Move>();
		this.states = new HashSet<String>();
	}
	
	private String search() {
		this.goToNextGeneration();
		return null;
		
	}
	
	private void goToNextGeneration() {
		Position emptyPosition = this.board.getEmptyTilePosition();
		for (Position currPosition : emptyPosition.getNeighbors()) {
			Move move = new Move(currPosition, emptyPosition);
			this.moveTracker.add(move);
		}
	}
	
	private void applyMoves() {
		while (!this.moveTracker.isEmpty()) {
			Move move = this.moveTracker.remove();
			this.board.moveTile(move);
			this.currentState = this.convertBoardToString();
			if (this.currentState.equals(GOAL_STATE)) {
				break;
			}
		}
		
	}
	
	private String convertBoardToString() {
		String arrangement = "";
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				Position position = new Position(row, col);
				arrangement += this.board.getTile(position);
			}
		}
		return arrangement;
	}
}
