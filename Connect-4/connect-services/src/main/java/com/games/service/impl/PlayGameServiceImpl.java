package com.games.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.games.dto.GameResponse;
import com.games.dto.Move;
import com.games.entity.Game;
import com.games.enums.ChipColor;
import com.games.enums.GameMode;
import com.games.enums.GameState;
import com.games.enums.Player;
import com.games.exception.ApplicationErrorCode;
import com.games.exception.ApplicationException;
import com.games.service.IGameInitService;
import com.games.service.IPlayGameService;
import com.games.service.IplayerService;

@Service
public class PlayGameServiceImpl implements IPlayGameService {

	@Autowired
	private IGameInitService gameInitService;

	@Autowired
	private PlaceTheChipService placeTheChipService;

	@Autowired
	private CheckWinService checkWinService;

	@Autowired
	private IplayerService robotPlayerService;

	public GameResponse playGame(String gameId, Player player, int column) {
		Game game = gameInitService.getById(gameId);
		if (game == null) {
			throw new ApplicationException(ApplicationErrorCode.GAME_DOESNOT_EXIST);
		}
		ChipColor chipColor = (player == Player.PLAYER_1 ? game.getPlayerOneColor() : game.getOpponentColor());
		Player nextTurn = getNextPlayerTurn(player, game);
		byte[][] gameBoard = game.getGameBoard();
		Move move = null;
		try {
			move = placeTheChipService.putChip(column, chipColor, gameBoard);
		} catch (ApplicationException e) {
			if (e.getErrorCode() == ApplicationErrorCode.GAME_BOARD_FULL) {
				return GameServiceUtils.createGameResponse(gameBoard, GameState.DRAW, null, gameId,null);
			} else {
				throw e;
			}
		}
		game.setGameBoard(gameBoard);
		game = gameInitService.updateGameBoard(game);
		boolean flag = checkWinService.checkWin(game.getGameBoard(), move.getRow(), move.getColumn(), gameBoard.length,
				gameBoard[0].length);
		if (flag) {
			return GameServiceUtils.createGameResponse(gameBoard, GameState.ENDED, null, gameId, player);
		}
		if (game.getGameMode() == GameMode.MULTIPLAYER) {
			return GameServiceUtils.createGameResponse(gameBoard, GameState.IN_PROGRESS, nextTurn, gameId,null);
		} else {
			// set opponnent color as own color
			GameResponse resp = robotPlayerService.play(gameBoard, game.getOpponentColor(), game.getPlayerOneColor(),
					gameId);
			game.setGameBoard(resp.getGameBoard());
			gameInitService.updateGameBoard(game);
			return resp;
		}
	}

	private Player getNextPlayerTurn(Player currentplayer, Game game) {
		Player nextPlayer = null;

		if (game.getGameMode() == GameMode.SINGLE_PLAYER) {
			if (currentplayer == Player.PLAYER_1) {
				nextPlayer = Player.COMPUTER;
			} else {
				nextPlayer = Player.PLAYER_1;
			}
		} else {
			if (currentplayer == Player.PLAYER_1) {
				nextPlayer = Player.PLAYER_2;
			} else {
				nextPlayer = Player.PLAYER_1;
			}
		}
		return nextPlayer;
	}

}
