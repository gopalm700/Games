package com.games.repo;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.games.entity.Game;

@Repository
public class GameRepo {

	
	@Autowired
	private MongoTemplate mongoTemplate;

	public void createGame(Game game) {
		game.setId(UUID.randomUUID().toString());
		mongoTemplate.insert(game);
	}

	public Game getGameById(String gameId) {
		Query searchQuery = new Query(Criteria.where("id").is(gameId));
		return mongoTemplate.findOne(searchQuery,Game.class);
	}

	public Game updateGameBoard(Game game) {
		Query searchQuery = new Query(Criteria.where("id").is(game.getId()));
		Update update = Update.update("gameBoard", game.getGameBoard());
		FindAndModifyOptions options = new FindAndModifyOptions();
		options.returnNew(false);
		return mongoTemplate.findAndModify(searchQuery, update,options, Game.class);
		
		
	}
	
	public void removeGameBoard(Game game) {
		mongoTemplate.remove(game);
	}
	
	public Game updateGame(Game game){
		Query searchQuery = new Query(Criteria.where("id").is(game.getId()));
		Update update = new Update();
		update.set("gameMode", game.getGameMode());
		update.set("playerOneColor", game.getPlayerOneColor());
		update.set("opponentColor", game.getOpponentColor());
		update.set("gameBoard", game.getGameBoard());
		FindAndModifyOptions options = new FindAndModifyOptions();
		options.returnNew(false);
		return mongoTemplate.findAndModify(searchQuery, update,options, Game.class);
	}

}