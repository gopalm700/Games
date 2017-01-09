package com.games.service.impl;

import org.springframework.stereotype.Service;

import com.games.dto.Move;
import com.games.enums.ChipColor;
import com.games.exception.ApplicationErrorCode;
import com.games.exception.ApplicationException;

@Service
public class PlaceTheChipService {

	public Move putChip(int column, ChipColor color, byte gameBoard[][]) throws ApplicationException {

		if (column < 0 || column >= gameBoard[0].length || gameBoard[0][column] != 0) {
			throw new ApplicationException(ApplicationErrorCode.INVALID_COLUMN);
		}

		for (int i = 0; i < gameBoard[0].length; i++) {
			int count =0;
			if(gameBoard[0][i] !=0 ){
				count++;
			}
			if(count ==gameBoard[0].length){
				throw new ApplicationException(ApplicationErrorCode.GAME_BOARD_FULL);
			}
		}
		for (int i = 1; i < gameBoard.length; i++) {
			// place the chip in the next available row or last row
			if (gameBoard[i][column] != 0) {
				gameBoard[i - 1][column] = color.getColorCode();
				return new Move(i - 1, column);
			} else if (i == gameBoard.length - 1) {
				gameBoard[i][column] = color.getColorCode();
				return new Move(i, column);
			}
		}
		return null;

	}

}
