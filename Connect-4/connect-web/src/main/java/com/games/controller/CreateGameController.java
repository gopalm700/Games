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
import com.games.dto.BaseResponse;
import com.games.dto.GameCreateResponse;
import com.games.entity.Game;
import com.games.enums.ChipColor;
import com.games.enums.GameMode;
import com.games.service.IGameInitService;

@Controller
public class CreateGameController {

	@Autowired
	private IGameInitService gameInitService;

	Logger logger = LoggerFactory.getLogger(CreateGameController.class);

	@RequestMapping(value = "/colors", method = RequestMethod.GET)
	public ResponseEntity<ChipColor[]> chooseColor() {
		return new ResponseEntity<ChipColor[]>(ChipColor.values(), HttpStatus.OK);
	}

	@RequestMapping(value = "/playerOne/{playerOneColor}/start", method = RequestMethod.POST)
	public ResponseEntity<GameCreateResponse> createGame(
			@PathVariable(value = "playerOneColor") ChipColor playerOneColor, HttpSession session) {
		logger.info("Start the game");
		Game game = null;
		GameCreateResponse resp = (GameCreateResponse) session.getAttribute(ApplicationConstant.GAME_ID);
		if (resp != null) {
			game = gameInitService.getById(resp.getGameId());
		}
		if (game == null) {
			ChipColor opponentColor = ((playerOneColor == ChipColor.BLUE) ? ChipColor.RED : ChipColor.BLUE);
			game = gameInitService.createGame(playerOneColor, opponentColor, GameMode.SINGLE_PLAYER);
			logger.info("Game created with game id {} ", game.getId());
			resp = populateGameCreateResponse(game);
			session.setAttribute(ApplicationConstant.GAME_ID, resp);
			return new ResponseEntity<GameCreateResponse>(resp, HttpStatus.OK);
		} else {
			return restartGame(session);
		}

	}

	@RequestMapping(value = "/reStart", method = RequestMethod.PUT)
	public ResponseEntity<GameCreateResponse> restartGame(HttpSession session) {
		GameCreateResponse gameResponse = (GameCreateResponse) session.getAttribute(ApplicationConstant.GAME_ID);
		if (gameResponse != null) {
			Game game = gameInitService.restartGame(gameResponse.getGameId());
			logger.info("Restarted the game");
			return new ResponseEntity<GameCreateResponse>(populateGameCreateResponse(game), HttpStatus.OK);
		} else {
			GameCreateResponse gameCreateResponse = new GameCreateResponse();
			gameCreateResponse.setError(true);
			gameCreateResponse.setErrorMessage("Game not found");
			return new ResponseEntity<GameCreateResponse>(gameCreateResponse, HttpStatus.PRECONDITION_FAILED);
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public ResponseEntity<? extends BaseResponse> deleteGame(HttpSession session) {

		GameCreateResponse oldGameResponse = (GameCreateResponse) session.getAttribute(ApplicationConstant.GAME_ID);
		if (null != oldGameResponse) {
			gameInitService.delete(oldGameResponse.getGameId());
			session.invalidate();
			logger.info("Deleted the game");
			return new ResponseEntity<BaseResponse>(new BaseResponse(), HttpStatus.OK);
		} else {
			BaseResponse resp = new BaseResponse();
			resp.setError(true);
			resp.setErrorMessage("Game not found");
			return new ResponseEntity<BaseResponse>(resp, HttpStatus.PRECONDITION_FAILED);
		}

	}

	public GameCreateResponse populateGameCreateResponse(Game game) {
		if (game == null) {
			return null;
		}
		GameCreateResponse gameCreateResponse = new GameCreateResponse();
		gameCreateResponse.setGameId(game.getId());
		return gameCreateResponse;
	}
}
