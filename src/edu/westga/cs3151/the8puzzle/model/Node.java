package edu.westga.cs3151.the8puzzle.model;

public class Node {
	private Board board;
	private Node parent;
	private Move move;
	
	public Node(Board board, Node parent, Move move) {
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
