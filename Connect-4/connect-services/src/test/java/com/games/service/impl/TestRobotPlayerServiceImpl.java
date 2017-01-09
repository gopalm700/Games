package com.games.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.games.dto.GameResponse;
import com.games.dto.Move;
import com.games.enums.ChipColor;
import com.games.enums.GameState;
import com.games.exception.ApplicationErrorCode;
import com.games.exception.ApplicationException;
import com.games.service.RobotPlayerService;

@ContextConfiguration(locations = { "classpath:TestRobotPlayerServiceImpl-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestRobotPlayerServiceImpl {

	@Autowired
	private RobotPlayerService robotPlayerService;

	@Autowired
	private CheckWinService checkWinService;

	@Autowired
	private PlaceTheChipService placeTheChipService;

	@Before
	public void startup() {
		Mockito.reset(checkWinService, placeTheChipService);
	}

	@Test
	public void testPlay() {
		byte[][] arr = new byte[6][7];
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				arr[i][j] = (byte) 1;
			}
		}
		Mockito.when(placeTheChipService.putChip(Mockito.anyInt(), Mockito.any(ChipColor.class),
				Mockito.any(byte[][].class))).thenThrow(new ApplicationException(ApplicationErrorCode.INVALID_COLUMN));
		GameResponse respone = robotPlayerService.play(arr, ChipColor.BLUE, ChipColor.RED,"");
		Assert.assertNotNull(respone);
		Assert.assertTrue(respone.getState() == GameState.DRAW);
	}

	@Test
	public void testPlayWin() {
		byte[][] arr = new byte[6][7];
		arr[5][0] = (byte) 2;
		arr[4][0] = (byte) 2;
		arr[3][0] = (byte) 2;
		Mockito.when(placeTheChipService.putChip(Mockito.anyInt(), Mockito.any(ChipColor.class),
				Mockito.any(byte[][].class))).thenReturn(new Move(2, 0));
		Mockito.when(checkWinService.checkWin(Mockito.any(byte[][].class), Mockito.anyInt(), Mockito.anyInt(),
				Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
		GameResponse respone = robotPlayerService.play(arr, ChipColor.BLUE, ChipColor.RED,"");
		Assert.assertNotNull(respone);
		Assert.assertTrue(respone.getState() == GameState.ENDED);
	}

}
