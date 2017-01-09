package com.games.dto;

import com.games.enums.GameState;
import com.games.enums.Player;

public class GameResponse extends GameCreateResponse {

	private byte[][] gameBoard;

	private GameState state = GameState.IN_PROGRESS;

	private String gameId;

	private Player turn = Player.PLAYER_1;
	
	private Player winner;

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public Player getTurn() {
		return turn;
	}

	public void setTurn(Player turn) {
		this.turn = turn;
	}

	public byte[][] getGameBoard() {
		return gameBoard;
	}

	public void setGameBoard(byte[][] gameBoard) {
		this.gameBoard = gameBoard;
	}

	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public Player getWinner() {
		return winner;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}
}
