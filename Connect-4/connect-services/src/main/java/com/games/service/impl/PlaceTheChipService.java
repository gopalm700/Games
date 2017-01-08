package com.games.service.impl;

import org.springframework.stereotype.Service;

import com.games.dto.Move;
import com.games.enums.ChipColor;
import com.games.exception.ApplicationErrorCode;
import com.games.exception.ApplicationException;

@Service
public class PlaceTheChipService {

	public Move putChip(int column, ChipColor color, byte gameBoard[][]) throws ApplicationException {

		if (gameBoard[0][column] == 0) {
			throw new ApplicationException(ApplicationErrorCode.INVALID_COLUMN);
		}

		for (int i = 1; i < gameBoard.length; i++) {
			if (gameBoard[i][column] != 0) {
				gameBoard[i - 1][column] = color.getColorCode();
				return new Move(i - 1, column);
			}
		}
		return null;

	}

}
