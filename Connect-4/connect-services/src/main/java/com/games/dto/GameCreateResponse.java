package com.games.dto;

import com.games.enums.Player;

public class GameCreateResponse extends BaseResponse {

	private String gameId;

	private Player turn =  Player.PLAYER_1;
	

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
}
