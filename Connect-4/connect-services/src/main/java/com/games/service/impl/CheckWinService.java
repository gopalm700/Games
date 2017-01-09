package com.games.service.impl;

import org.springframework.stereotype.Service;

@Service
public class CheckWinService {
	public boolean checkWin(byte[][] gridTable, int rowNum, int colNum, int maxRow, int maxCol) {

		int player = gridTable[rowNum][colNum]; // player ID
		int count = 0;

		// Horizontal check
		for (int i = 0; i < maxCol; i++) {
			if (gridTable[rowNum][i] == player)
				count++;
			else
				count = 0;

			if (count >= 4)
				return true;
		}
		count = 0;
		// Vertical check
		for (int i = 0; i < maxRow; i++) {
			if (gridTable[i][colNum] == player)
				count++;
			else
				count = 0;

			if (count >= 4)
				return true;
		}
		count = 0;
		// diagonaly
		for (int i = 0; i < maxRow - 3; i++) {
			count = 0;
			int row, col;
			for (row = i, col = 0; row < maxRow && col < maxCol; row++, col++) {
				if (gridTable[row][col] == player) {
					count++;
					if (count >= 4)
						return true;
				} else {
					count = 0;
				}
			}
		}
		count = 0;
		// diaginally
		for (int j = 1; j < maxCol - 3; j++) {
			count = 0;
			int row, col;
			for (row = 0, col = j; row < maxRow && col < maxCol; row++, col++) {
				if (gridTable[row][col] == player) {
					count++;
					if (count >= 4)
						return true;
				} else {
					count = 0;
				}
			}
		}
		count = 0;
		// diagonal
		for (int i = maxRow - 1; i > (maxRow - 4); i--) {
			count = 0;
			int row, col;
			for (row = i, col = 0; row >= 0 && col < maxCol; row--, col++) {
				if (gridTable[row][col] == player) {
					count++;
					if (count >= 4)
						return true;
				} else {
					count = 0;
				}
			}
		}

		count = 0;

		// diaginally
		for (int j = 1; j < maxCol; j++) {
			count = 0;
			int row, col;
			for (row = maxRow - 1, col = j; row > 0 && col < maxCol; row--, col++) {
				if (gridTable[row][col] == player) {
					count++;
					if (count >= 4)
						return true;
				} else {
					count = 0;
				}
			}
		}

		return false;
	}
}
