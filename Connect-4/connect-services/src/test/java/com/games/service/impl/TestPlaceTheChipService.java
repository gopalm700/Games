package com.games.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.games.enums.ChipColor;
import com.games.exception.ApplicationException;

@ContextConfiguration(locations = { "classpath:TestPlaceTheChipService-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestPlaceTheChipService {

	@Autowired
	private PlaceTheChipService placeTheChipService;

	@Test
	public void testPutChip() {
		byte[][] arr = new byte[6][7];
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				arr[i][j] = (byte) 1;
			}
		}

		for (int i = 0; i < 7; i++) {
			try {
				placeTheChipService.putChip(i, ChipColor.BLUE, arr);
				assert false;
			} catch (ApplicationException e) {

			}
		}
	}

	@Test
	public void testPutChipTest() {
		byte[][] arr = null;
		for (int i = 0; i < 7; i++) {
			arr = new byte[6][7];
			for (int j = 0; j < 7; j++) {
				arr[5][j] = 1;
			}
			placeTheChipService.putChip(i, ChipColor.BLUE, arr);
			Assert.assertTrue(arr[4][i] == Byte.valueOf("2"));
		}
	}
}
