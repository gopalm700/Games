package com.games.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
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
import com.games.dto.GameResponse;
import com.games.entity.Game;
import com.games.enums.ChipColor;
import com.games.enums.GameMode;
import com.games.service.IGameInitService;
import com.google.gson.Gson;

@Controller
public class CreateGameController {

	@Autowired
	private IGameInitService gameInitService;

	Logger logger = LoggerFactory.getLogger(CreateGameController.class);

	@RequestMapping(value = "/colors", method = RequestMethod.GET)
	public ResponseEntity<Map<ChipColor, Byte>> chooseColor() {
		Map<ChipColor, Byte> map = new HashMap<>();
		for (ChipColor color : ChipColor.values()) {
			map.put(color, color.getColorCode());
		}
		return new ResponseEntity<Map<ChipColor, Byte>>(map, HttpStatus.OK);
	}

	private Gson gson = new Gson();

	@RequestMapping(value = "/playerOne/{playerOneColor}/start", method = RequestMethod.POST)
	public ResponseEntity<? extends BaseResponse> createGame(
			@PathVariable(value = "playerOneColor") ChipColor playerOneColor, HttpSession session) {
		logger.info("Start the game");
		Game game = null;
		String str = (String) session.getAttribute(ApplicationConstant.GAME_ID);
		GameResponse resp = null;
		if (StringUtils.isNotBlank(str)) {
			resp = gson.fromJson(str, GameResponse.class);
			if (null != resp) {
				game = gameInitService.getById(resp.getGameId());
			}
		}
		ChipColor opponentColor = ((playerOneColor == ChipColor.BLUE) ? ChipColor.RED : ChipColor.BLUE);
		if (game == null) {
			game = gameInitService.createGame(playerOneColor, opponentColor, GameMode.SINGLE_PLAYER);
			logger.info("Game created with game id {} ", game.getId());
			resp = populateGameResponse(game);
			session.setAttribute(ApplicationConstant.GAME_ID, gson.toJson(resp));
			return new ResponseEntity<GameResponse>(resp, HttpStatus.OK);
		} else {
			game.setPlayerOneColor(playerOneColor);
			game.setGameBoard(new byte[6][7]);
			game.setOpponentColor(opponentColor);
			game = gameInitService.updateGame(game);
			return new ResponseEntity<GameResponse>(populateGameResponse(game), HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/reStart", method = RequestMethod.PUT)
	public ResponseEntity<? extends BaseResponse> restartGame(HttpSession session) {
		String str = (String) session.getAttribute(ApplicationConstant.GAME_ID);
		GameResponse resp = null;
		if (StringUtils.isNotBlank(str)) {
			resp = gson.fromJson(str, GameResponse.class);
			if (null != resp) {
				Game game = gameInitService.restartGame(resp.getGameId());
				logger.info("Restarted the game");
				return new ResponseEntity<GameResponse>(populateGameResponse(game), HttpStatus.OK);
			} else {
				return populateGameNotFound();
			}
		} else {
			return populateGameNotFound();
		}

	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public ResponseEntity<? extends BaseResponse> deleteGame(HttpSession session) {
		String str = (String) session.getAttribute(ApplicationConstant.GAME_ID);
		GameResponse oldGameResponse = gson.fromJson(str, GameResponse.class);
		if (null != oldGameResponse) {
			gameInitService.delete(oldGameResponse.getGameId());
			session.invalidate();
			logger.info("Deleted the game");
			return new ResponseEntity<BaseResponse>(new BaseResponse(), HttpStatus.OK);
		} else {
			return populateGameNotFound();
		}

	}

	public GameResponse populateGameResponse(Game game) {
		if (game == null) {
			return null;
		}
		GameResponse GameResponse = new GameResponse();
		GameResponse.setGameId(game.getId());
		return GameResponse;
	}

	public ResponseEntity<BaseResponse> populateGameNotFound() {
		BaseResponse resp = new BaseResponse();
		resp.setError(true);
		resp.setErrorMessage("Game not found");
		return new ResponseEntity<BaseResponse>(resp, HttpStatus.PRECONDITION_FAILED);
	}
}
