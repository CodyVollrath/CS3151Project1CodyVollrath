package edu.westga.cs3151.the8puzzle.model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * This class is used to solve or help solve the 8 puzzle
 * @author Cody Vollrath
 *
 */
public class PuzzleSolver {
	private Board board;
	private Queue<Node> vistedNodes;
	private static final int SOLUTION_NUMBER_OF_SORTED_TILES = 8;
	private static final String BOARD_NULL = "Board can not be null";
	
	/**
	 * Initialize the Solver
	 * @param board the initial board
	 * @post getBoard() == board
	 */
	public PuzzleSolver(Board board) {
		if (board == null) {
			throw new IllegalArgumentException(BOARD_NULL);
		}
		this.board = board;
		this.vistedNodes = new LinkedList<Node>();
	}
	
	/**
	 * Solves the puzzle and outputs the minimum number of moves
	 * @post getBoard() is sorted to a specfic number.
	 * @param tile the number of tiles expected to be in order
	 * @return the minimum number of moves as a queue
	 */
	public Queue<Move> help(int tile) {
		return this.findSolution(tile);
	}
	
	/**
	 * Solves the puzzle and outputs the minimum number of moves
	 * @post getBoard() is changed to the solution state
	 * @return the minimum number of moves as a queue
	 */
	public Queue<Move> solve() {
		return this.findSolution(SOLUTION_NUMBER_OF_SORTED_TILES);
	}
	
	/**
	 * Gets the current board
	 * @return the board
	 */
	public Board getBoard() {
		return this.board;
	}
	
	/**
	 * Sets the board to be solved
	 * @pre board != null
	 * @post getBoard() == board
	 * @param board the board to be solved
	 */
	public void setBoard(Board board) {
		if (board == null) {
			throw new IllegalArgumentException(BOARD_NULL);
		}
		this.board = board;
	}
	
	private Queue<Move> findSolution(int tile) {
		this.vistedNodes.clear();
		Node initialNode = new Node(this.board, null, null);
		this.vistedNodes.add(initialNode);
		Queue<Move> moves = new LinkedList<Move>();
		while (!this.vistedNodes.isEmpty()) {
			
			Node currentNode = this.vistedNodes.remove();
			Position emptyPosition = currentNode.getBoard().getEmptyTilePosition();
			
			for (Position neighborPosition : emptyPosition.getNeighbors()) {
				
				Board nextBoard = new Board(currentNode.getBoard());
				Move move = new Move(neighborPosition, emptyPosition);
				nextBoard.moveTile(move);
				Node nextNode = new Node(nextBoard, currentNode, move);
				this.addToVisitedNodes(nextNode);
				
				if (nextBoard.getNumberSortedTiles() >= tile) {
					this.addFinalMove(moves, nextNode);
					System.out.println("Solved!");
					return this.getMinimumNumberOfMoves(moves);
				}
			}
		}
		System.out.println("The puzzle is not solvable!");
		return null;
	}
	
	private void addFinalMove(Queue<Move> moves, Node node) {
		while (!(node.getParent() == null)) {
			Move finalMove = node.getMove();
			moves.add(finalMove);
			node = node.getParent();
		}
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
	
	
	private class Node {
		private Board board;
		private Node parent;
		private Move move;
		
		Node(Board board, Node parent, Move move) {
			this.board = board;
			this.parent = parent;
			this.move = move;
		}

		public Board getBoard() {
			return this.board;
		}

		public Node getParent() {
			return this.parent;
		}

		public Move getMove() {
			return this.move;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Node)) {
				return false;
			}
			
			Node other = (Node) obj;
			return this.board.hashCode() == other.board.hashCode();
			
		}
		
		@Override
		public int hashCode() {
			int hashCode = this.board.hashCode() * 10;
			hashCode += this.move.hashCode();
			return hashCode;
		}
	}
}
