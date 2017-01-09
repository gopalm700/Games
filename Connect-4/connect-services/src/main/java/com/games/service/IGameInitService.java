package com.games.service;

import com.games.entity.Game;
import com.games.enums.ChipColor;
import com.games.enums.GameMode;

public interface IGameInitService {

	public Game createGame(ChipColor playerOneColor, ChipColor opponentColor, GameMode mode);

	public Game restartGame(String gameId);

	public Game delete(String gameId);

	public Game getById(String id);
	
	public Game updateGame (Game game);

}
