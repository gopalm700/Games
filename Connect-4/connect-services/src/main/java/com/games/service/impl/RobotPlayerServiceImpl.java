package com.games.service.impl;

import static com.games.service.impl.GameServiceUtils.createGameResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.games.dto.GameResponse;
import com.games.dto.Move;
import com.games.enums.ChipColor;
import com.games.enums.GameState;
import com.games.enums.Player;
import com.games.exception.ApplicationException;
import com.games.service.RobotPlayerService;

@Service
public class RobotPlayerServiceImpl implements RobotPlayerService {

	@Autowired
	private CheckWinService checkWinService;

	@Autowired
	private PlaceTheChipService placeTheChipService;

	public GameResponse play(byte[][] gameBoard, ChipColor ownColor, ChipColor opponentColor, String gameId) {

		Player nextTurn = Player.PLAYER_1;
		// check whether robot can win
		for (int i = 0; i < gameBoard[0].length; i++) {
			try {
				Move move = placeTheChipService.putChip(i, ownColor, gameBoard);
				if (checkWinService.checkWin(gameBoard, move.getRow(), move.getColumn(), gameBoard.length,
						gameBoard[0].length)) {
					return createGameResponse(gameBoard, GameState.ENDED, null, gameId, Player.COMPUTER);
				}
				gameBoard[move.getRow()][move.getColumn()] = 0;
			} catch (ApplicationException e) {

			}
		}
		// check opponent can win, if yes, block it.
		for (int i = 0; i < gameBoard[0].length; i++) {
			try {
				Move move = placeTheChipService.putChip(i, opponentColor, gameBoard);
				if (checkWinService.checkWin(gameBoard, move.getRow(), move.getColumn(), gameBoard.length,
						gameBoard[0].length)) {
					gameBoard[move.getRow()][move.getColumn()] = ownColor.getColorCode();
					return createGameResponse(gameBoard, GameState.IN_PROGRESS, nextTurn, gameId, null);
				}
				gameBoard[move.getRow()][move.getColumn()] = 0;
			} catch (ApplicationException e) {

			}
		}

		// take the center square if available
		if (gameBoard[0][gameBoard[0].length / 2] == 0) {
			placeTheChipService.putChip((gameBoard[0].length / 2), ownColor, gameBoard);
			return createGameResponse(gameBoard, GameState.IN_PROGRESS, nextTurn, gameId, null);
		}
		// take the left corner if available
		else if (gameBoard[0][0] == 0) {
			placeTheChipService.putChip(0, ownColor, gameBoard);
			return createGameResponse(gameBoard, GameState.IN_PROGRESS, nextTurn, gameId, null);
		}
		// take the right corner if available
		else if (gameBoard[0][gameBoard[0].length - 1] == 0) {
			placeTheChipService.putChip(gameBoard[0].length - 1, ownColor, gameBoard);
			return createGameResponse(gameBoard, GameState.IN_PROGRESS, nextTurn, gameId, null);
		}

		// place anywhere if a place is available
		for (int i = 0; i < gameBoard[0].length; i++) {
			try {
				placeTheChipService.putChip(i, ownColor, gameBoard);
				return createGameResponse(gameBoard, GameState.IN_PROGRESS, nextTurn, gameId, null);
			} catch (ApplicationException e) {
			}
		}
		// board is full
		return createGameResponse(gameBoard, GameState.DRAW, null, gameId, null);
	}

}
