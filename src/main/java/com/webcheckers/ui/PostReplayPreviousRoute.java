package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameManager;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.logging.Logger;

public class PostReplayPreviousRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostReplayPreviousRoute.class.getName());

    private final GameManager gameManager;
    private final Gson gson;

    public PostReplayPreviousRoute(GameManager gameManager, Gson gson) {
        this.gameManager = gameManager;
        this.gson = gson;
        //
        LOG.config("PostReplayPreviousRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session httpSession = request.session();
        final Player player = httpSession.attribute("player");

        this.gameManager.decrementReplayPosition(player.getUsername());

        return this.gson.toJson(Message.info("true"));
    }
}
