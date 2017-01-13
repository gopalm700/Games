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
		return mongoTemplate.findOne(searchQuery, Game.class);
	}

	public void updateGameBoard(Game game) {
		Query searchQuery = new Query(Criteria.where("id").is(game.getId()));
		Update update = Update.update("gameBoard", game.getGameBoard());
		FindAndModifyOptions options = new FindAndModifyOptions();
		options.returnNew(false);
		mongoTemplate.findAndModify(searchQuery, update, options, Game.class);

	}

	public void removeGameBoard(Game game) {
		mongoTemplate.remove(game);
	}

	public void updateGame(Game game) {
		Query searchQuery = new Query(Criteria.where("id").is(game.getId()));
		Update update = new Update();
		if (game.getGameMode() != null) {
			update.set("gameMode", game.getGameMode());
		}
		if (game.getPlayerOneColor() != null) {
			update.set("playerOneColor", game.getPlayerOneColor());
		}
		if (game.getOpponentColor() != null) {
			update.set("opponentColor", game.getOpponentColor());
		}
		if (game.getGameBoard() != null) {
			update.set("gameBoard", game.getGameBoard());
		}
		FindAndModifyOptions options = new FindAndModifyOptions();
		options.returnNew(false);
		mongoTemplate.findAndModify(searchQuery, update, options, Game.class);
	}

	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

}
