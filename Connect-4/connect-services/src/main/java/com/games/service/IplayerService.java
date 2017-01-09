package com.games.service;

import com.games.dto.GameResponse;
import com.games.enums.ChipColor;

public interface IplayerService {
	public GameResponse play(byte[][] gameBoard, ChipColor ownColor, ChipColor opponentColor, String gameId);
}
