package com.games.dto;

import com.games.enums.GameState;

public class GameResponse extends GameCreateResponse{

	private byte[][] gameBoard;
	
	private GameState state = GameState.IN_PROGRESS;

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

	
	
	
}
