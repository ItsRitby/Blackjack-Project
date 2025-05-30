package co.edu.uptc.controller;

import java.util.List;

import co.edu.uptc.model.BlackjackGame;
import co.edu.uptc.model.Card;
import co.edu.uptc.model.Player;

public class ServerController {
    private Server server;
    private BlackjackGame game;

    public ServerController() {
        server = new Server(this);
        game = new BlackjackGame();
    }

    public void startServer() {
        new Thread(server).start();
    }

    public void initGame() {
        game.initialDeal();
    }

    public boolean addPlayer(Player player) {
        if (game.addPlayer(player)) {
            System.out.println("Jugador " + player.getNickName() + " agregado al juego.");
            return true;
        } else {
            System.out.println("No se puede agregar más jugadores al juego.");
            return false;
        }
    }

    public boolean playerHit() {
        return game.playerHit();
    }

    public void playerStand() {
        game.playerStay();
    }

    public void playerDouble() {
        game.playerDouble();
    }

    public void playerSurrender() {
        game.playerSurender();
    }

    public void crupierGame() {
        game.crupierGame();
    }

    public Player playerInGame() {
        return game.playerInGame();
    }

    public List<Card> getCardsInGame() {
        return game.cardsInGame();
    }

    public void stopServer() {
        server.stop();
    }

}
