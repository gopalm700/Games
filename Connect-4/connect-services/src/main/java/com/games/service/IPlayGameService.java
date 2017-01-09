package com.games.service;

import com.games.dto.GameResponse;
import com.games.enums.Player;

public interface IPlayGameService {

	
	public GameResponse playGame(String gameId, Player player, int column);
}
