package com.games.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.games.entity.Game;
import com.games.enums.ChipColor;
import com.games.enums.GameMode;
import com.games.exception.ApplicationErrorCode;
import com.games.exception.ApplicationException;
import com.games.repo.GameRepo;
import com.games.service.IGameInitService;

@Service
public class GameInitServiceImpl implements IGameInitService {

	Logger Logger = LoggerFactory.getLogger(GameInitServiceImpl.class);

	@Autowired
	private GameRepo gameRepo;

	public Game createGame(ChipColor playerOneColor, ChipColor opponentColor, GameMode mode) {
		Game game = new Game();
		game.setGameMode(mode);
		game.setPlayerOneColor(playerOneColor);
		game.setOpponentColor(opponentColor);
		game.setGameBoard(new byte[6][7]);
		this.save(game);
		return game;
	}

	public void save(Game game) {
		gameRepo.createGame(game);
	}

	public Game getById(String id) {
		return this.gameRepo.getGameById(id);
	}

	public Game restartGame(String gameId) {
		Game game = this.getById(gameId);
		if (null == game) {
			throw new ApplicationException(ApplicationErrorCode.GAME_DOESNOT_EXIST);
		}
		game.setGameBoard(new byte[6][7]);
		gameRepo.updateGameBoard(game);
		return this.getById(gameId);
	}

	public Game delete(String gameId) {
		Game game = this.getById(gameId);
		if (null == game) {
			throw new ApplicationException(ApplicationErrorCode.GAME_DOESNOT_EXIST);
		} else {
			gameRepo.removeGameBoard(game);
		}
		return game;
	}

	public Game updateGame(Game game) {
		gameRepo.updateGameBoard(game);
		return this.getById(game.getId());
	}
}
