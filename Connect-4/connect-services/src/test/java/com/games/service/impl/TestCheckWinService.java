package com.games.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { "classpath:TestCheckWinService-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestCheckWinService {

	@Autowired
	private CheckWinService checkWinService;

	@Test
	public void testCheckWinvertical() {
		byte arr[][] = new byte[6][7];
		for (int i = 0; i < 7; i++) {
			if (i < 3) {
				arr[i][i] = 1;
				arr[i + 1][i] = 1;
				arr[i + 2][i] = 1;
				arr[i + 3][i] = 1;
				boolean flag = checkWinService.checkWin(arr, i, i, 6, 7);
				Assert.assertTrue(flag);
			} else {
				arr[0][i] = 1;
				arr[1][i] = 1;
				arr[2][i] = 1;
				arr[3][i] = 1;
				boolean flag = checkWinService.checkWin(arr, 0, i, 6, 7);
				Assert.assertTrue(flag);

			}
		}
	}

	@Test
	public void testCheckWinHorizontal() {
		byte arr[][] = new byte[6][7];
		for (int i = 0; i < 6; i++) {
			if (i < 2) {
				arr[i][i] = 1;
				arr[i][i + 1] = 1;
				arr[i][i + 2] = 1;
				arr[i][i + 3] = 1;
				boolean flag = checkWinService.checkWin(arr, i, i, 6, 7);
				Assert.assertTrue(flag);
			} else {
				arr[i][1] = 1;
				arr[i][2] = 1;
				arr[i][3] = 1;
				arr[i][4] = 1;
				boolean flag = checkWinService.checkWin(arr, i, 0, 6, 7);
				Assert.assertTrue(flag);
			}
		}
	}

	@Test
	public void testCheckWinLowerDiagonal() {
		byte arr[][] = null;
		for (int i = 2; i >= 0; i--) {
			arr = new byte[6][7];
			for (int row = i, col = 0; row < 6 && col < 7; row++, col++) {
				arr[row][col] = 1;
			}
			boolean flag = checkWinService.checkWin(arr, i, 0, 6, 7);
			Assert.assertTrue(flag);
		}
	}

	@Test
	public void testCheckWinUpperDiagonal() {
		byte arr[][] = null;
		for (int i = 1; i < 4; i++) {
			arr = new byte[6][7];
			for (int row = 0, col = 1; row < 6 && col < (i + 4); row++, col++) {
				arr[row][col] = 1;
			}
			boolean flag = checkWinService.checkWin(arr, 0, i, 6, 7);
			Assert.assertTrue(flag);
		}
	}

	@Test
	public void testCheckReverseWinLowerDiagonal() {
		byte arr[][] = null;
		for (int i = 1; i < 4; i++) {
			arr = new byte[6][7];
			for (int row = 5, col = i; row >= 0 && col < 7; row--, col++) {
				arr[row][col] = 1;
			}
			boolean flag = checkWinService.checkWin(arr, 5, i, 6, 7);
			Assert.assertTrue(flag);
		}
	}

	@Test
	public void testCheckReverseWinUpperDiagonal() {
		byte arr[][] = null;
		for (int i = 5; i > 2; i--) {
			arr = new byte[6][7];
			for (int row = i, col = 0; row >= 0 && col < 7; row--, col++) {
				arr[row][col] = 1;
			}
			boolean flag = checkWinService.checkWin(arr, i, 0, 6, 7);
			Assert.assertTrue(flag);
		}
	}
}
