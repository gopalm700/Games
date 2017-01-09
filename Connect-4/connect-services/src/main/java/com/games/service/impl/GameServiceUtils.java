package com.games.service.impl;

import com.games.dto.GameResponse;
import com.games.enums.GameState;
import com.games.enums.Player;

public class GameServiceUtils {
	public static GameResponse createGameResponse(byte[][] gameBoard, GameState state, Player turn, String gameId, Player winner) {
		GameResponse gameResponse = new GameResponse();
		gameResponse.setGameBoard(gameBoard);
		gameResponse.setState(state);
		gameResponse.setTurn(turn);
		gameResponse.setGameId(gameId);
		gameResponse.setWinner(winner);
		return gameResponse;

	}
}
