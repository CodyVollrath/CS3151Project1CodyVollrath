package edu.westga.cs3151.the8puzzle.model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class PuzzleSolver {
	private Board board;
	private Queue<Node> vistedNodes;
	private static final int SOLUTION_NUMBER_OF_SORTED_TILES = 8;
	
	public PuzzleSolver(Board board) {
		this.board = board;
		this.vistedNodes = new LinkedList<Node>();
	}
	
	public Queue<Move> help(int tile) {
		return this.findSolution(tile);
	}
	
	public Queue<Move> solve() {
		return this.findSolution(SOLUTION_NUMBER_OF_SORTED_TILES);
	}
	
	public Board getBoard() {
		return this.board;
	}
	
	public void setBoard(Board board) {
		this.board = board;
	}
	
	private Queue<Move> findSolution(int tile) {
		this.vistedNodes = new LinkedList<Node>();
		Node initial = new Node(this.board, null, null);
		this.vistedNodes.add(initial);
		Queue<Move> moves = new LinkedList<Move>();
		while (!this.vistedNodes.isEmpty()) {
			Node currentPosition = this.vistedNodes.remove();
			Position emptyPosition = currentPosition.getBoard().getEmptyTilePosition();
			for (Position neighborPosition : emptyPosition.getNeighbors()) {
				Board newBoard = new Board(currentPosition.getBoard());
				Move move = new Move(neighborPosition, emptyPosition);
				newBoard.moveTile(move);
				Node newNode = new Node(newBoard, currentPosition, move);
				this.addToVisitedNodes(newNode);
				if (newBoard.getNumberSortedTiles() >= tile) {
					while (!(newNode.getParent() == null)) {
						Move solutionMove = newNode.getMove();
						moves.add(solutionMove);
						newNode = newNode.getParent();
					}
					return this.getMinimumNumberOfMoves(moves);
				}
			}
		}
		return null;
	}

	private void addToVisitedNodes(Node newNode) {
		if (!(this.vistedNodes.contains(newNode))) {
			this.vistedNodes.add(newNode);
		}
	}
	
	private Queue<Move> getMinimumNumberOfMoves(Queue<Move> movesList) {
		Stack<Move> reversal = new Stack<Move>();
		while (!movesList.isEmpty()) {
			reversal.add(movesList.remove());
		}
		while (!reversal.isEmpty()) {
			movesList.add(reversal.pop());
		}
		return movesList;
	}
}
