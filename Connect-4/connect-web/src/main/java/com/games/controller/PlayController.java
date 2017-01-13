package com.games.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.games.dto.ApplicationConstant;
import com.games.dto.GameResponse;
import com.games.enums.GameState;
import com.games.exception.ApplicationErrorCode;
import com.games.exception.ApplicationException;
import com.games.service.IGameInitService;
import com.games.service.IPlayGameService;
import com.google.gson.Gson;

@Controller
public class PlayController {

	Logger logger = LoggerFactory.getLogger(PlayController.class);

	@Autowired
	private IPlayGameService playGameService;

	@Autowired
	private IGameInitService gameService;

	private Gson gson = new Gson();

	@RequestMapping(value = "/coloumn/{columnNo}/play", method = RequestMethod.PUT)
	public ResponseEntity<String> play(@PathVariable(value = "columnNo") Integer column, HttpSession session) {

		String str = (String) session.getAttribute(ApplicationConstant.GAME_ID);
		GameResponse gameResponse = gson.fromJson(str, GameResponse.class);
		if (gameResponse == null) {
			throw new ApplicationException(ApplicationErrorCode.GAME_DOESNOT_EXIST);
		}
		GameResponse response = playGameService.playGame(gameResponse.getGameId(), gameResponse.getTurn(), column);
		if (response.getState() != GameState.ENDED && response.getState() != GameState.DRAW) {
			session.setAttribute(ApplicationConstant.GAME_ID, gson.toJson(response));
		} else {
			gameService.delete(gameResponse.getGameId());
			session.invalidate();
		}
		return new ResponseEntity<String>(gson.toJson(response), HttpStatus.OK);
	}

}
