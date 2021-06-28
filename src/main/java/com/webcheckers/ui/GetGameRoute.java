package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckerPiece;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.ui.board.BoardView;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class GetGameRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

    public enum Mode {
        PLAY,
        SPECTATOR,
        REPLAY
    }

    // View-and-model attributes
    static final String TITLE_ATTR = "title";
    static final String MESSAGE_ATTR = "message";

    static final String VIEW_NAME = "game.ftl";

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;

    public GetGameRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        //
        LOG.config("GetGameRoute is initialized.");
        this.playerLobby = playerLobby;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Map<String, Object> vm = new HashMap<>();

        final Session httpSession = request.session();

        vm.put(TITLE_ATTR, "Game");
        vm.put("viewMode", Mode.PLAY);

        Player player = httpSession.attribute("player");
        String name = player.getUsername();

        Player opponent;
        if(playerLobby.hasPlayer(httpSession.attribute("opponent"))) {
            opponent = playerLobby.getPlayerList().get(httpSession.attribute("opponent"));
        } else {
            opponent = new Player("jack");
        }


        BoardView boardView = new BoardView( new CheckersGame(123, player, opponent));

        vm.put("board", boardView);
        vm.put("currentUser", player);
        vm.put("redPlayer", player);
        vm.put("whitePlayer", opponent);
        vm.put("activeColor", CheckerPiece.Color.RED);
        vm.put("gameID", "1234");

        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}