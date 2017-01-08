package com.games.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.games.dto.GameResponse;
import com.games.enums.Player;

@Controller
public class PlayController {

	Logger logger = LoggerFactory.getLogger(PlayController.class);

	@RequestMapping(value = "/play/player/{player}/coloumn/{columnNo}")
	public GameResponse play(@PathVariable(value = "columnNo") Integer column, @PathVariable("player") Player player) {
		return null;
	}

}
