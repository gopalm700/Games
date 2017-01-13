package com.games.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.games.dto.ApplicationConstant;
import com.games.dto.GameResponse;
import com.games.enums.GameState;
import com.games.enums.Player;
import com.games.exception.ApplicationException;
import com.games.service.IGameInitService;
import com.games.service.IPlayGameService;
import com.google.gson.Gson;

@ContextConfiguration(locations = { "classpath:TestPlayController-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestPlayController {

	@Autowired
	private IGameInitService gameInitService;

	@Autowired
	private IPlayGameService playGameService;

	@Autowired
	private PlayController playController;
	
	private Gson gson = new Gson();

	@Test(expected = ApplicationException.class)
	public void testPlay() {
		ResponseEntity<String> resp = playController.play(1, new MockHttpSession());
		Assert.assertNotNull(resp);
	}

	@Test
	public void testPlayDraw() {
		GameResponse gameResponse = populateGameResponse();
		gameResponse.setWinner(null);
		Mockito.when(playGameService.playGame(Mockito.anyString(), Mockito.any(Player.class), Mockito.anyInt()))
				.thenReturn(gameResponse);
		Mockito.when(gameInitService.delete(Mockito.anyString())).thenReturn(null);
		MockHttpSession session = new MockHttpSession();
		session.setAttribute(ApplicationConstant.GAME_ID, gson.toJson(gameResponse));
		ResponseEntity<String>  resp = playController.play(1, session);
		Assert.assertNotNull(resp);
		String str = new String(resp.getBody());
		gameResponse = gson.fromJson(str, GameResponse.class);
		Assert.assertEquals(gameResponse.getState(), GameState.DRAW);
	}
	
	@Test
	public void testPlayNormal() {
		GameResponse gameResponse = populateGameResponse();
		gameResponse.setState(GameState.IN_PROGRESS);
		gameResponse.setTurn(Player.PLAYER_2);
		Mockito.when(playGameService.playGame(Mockito.anyString(), Mockito.any(Player.class), Mockito.anyInt()))
				.thenReturn(gameResponse);
		Mockito.when(gameInitService.delete(Mockito.anyString())).thenReturn(null);
		MockHttpSession session = new MockHttpSession();
		session.setAttribute(ApplicationConstant.GAME_ID, gson.toJson(gameResponse));
		ResponseEntity<String>  resp = playController.play(1, session);
		Assert.assertNotNull(resp);
		String str = new String(resp.getBody());
		gameResponse = gson.fromJson(str, GameResponse.class);
		Assert.assertEquals(gameResponse.getState(), GameState.IN_PROGRESS);
		Assert.assertEquals(gameResponse.getTurn(), Player.PLAYER_2);
	}

	private GameResponse populateGameResponse() {
		GameResponse gameResponse = new GameResponse();
		gameResponse.setState(GameState.DRAW);
		return gameResponse;
	}

}
