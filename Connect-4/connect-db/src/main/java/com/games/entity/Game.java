package com.games.entity;

import java.io.Serializable;
import java.util.Arrays;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.games.enums.ChipColor;
import com.games.enums.GameMode;

@Document(collection="connect4game")
public class Game implements Serializable {

	private static final long serialVersionUID = -6116683835672596675L;

	@Id
	private String id;

	private GameMode gameMode;

	private ChipColor playerOneColor;

	private ChipColor opponentColor;

	private byte[][] gameBoard;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public GameMode getGameMode() {
		return gameMode;
	}

	public void setGameMode(GameMode gameMode) {
		this.gameMode = gameMode;
	}

	public ChipColor getPlayerOneColor() {
		return playerOneColor;
	}

	public void setPlayerOneColor(ChipColor playerOneColor) {
		this.playerOneColor = playerOneColor;
	}

	public ChipColor getOpponentColor() {
		return opponentColor;
	}

	public void setOpponentColor(ChipColor opponentColor) {
		this.opponentColor = opponentColor;
	}

	public byte[][] getGameBoard() {
		return gameBoard;
	}

	public void setGameBoard(byte[][] gameBoard) {
		this.gameBoard = gameBoard;
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", gameMode=" + gameMode + ", playerOneColor=" + playerOneColor + ", opponentColor="
				+ opponentColor + ", gameBoard=" + Arrays.toString(gameBoard) + "]";
	}
}
