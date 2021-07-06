package com.webcheckers.ui;

import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class GetGameRouteTest {

    private GetGameRoute CuT;

    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private GameManager gameManager;

    static final String TITLE = "Game";

    @BeforeEach
    public void setup(){

        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        when (request.session()).thenReturn(session);
        templateEngine = mock(TemplateEngine.class);

        playerLobby = mock(PlayerLobby.class);
        gameManager = new GameManager();

        CuT = new GetGameRoute(templateEngine, playerLobby, gameManager);
    }

    @Test
    public void makesPage() {

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute(GetGameRoute.TITLE_ATTR, TITLE);
        testHelper.assertViewName(CuT.VIEW_NAME);
    }
}