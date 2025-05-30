package co.edu.uptc.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import co.edu.uptc.model.Card;
import co.edu.uptc.model.Player;
import co.edu.uptc.net.JsonManager;
import co.edu.uptc.net.Response;
import co.edu.uptc.view.MainFrame;
import co.edu.uptc.view.game.draw.CardImage;
import co.edu.uptc.view.popups.MessageDialog;
import co.edu.uptc.view.reusable.Constants;

public class ClientController {
    private final String host;
    private final int port;
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    private MainFrame view;
    private JsonManager jsonManager;

    public ClientController() {
        host = "localhost";
        port = 666;
        view = new MainFrame(this);
        jsonManager = new JsonManager();
    }

    public void connect() throws IOException {
        socket = new Socket(host, port);
        input = new ObjectInputStream(socket.getInputStream());
        output = new ObjectOutputStream(socket.getOutputStream());
        jsonManager = new JsonManager();
    }

    public Response sendRequest(Response response) {
        try {
            if (output != null) {
                output.writeObject(response);
                output.flush();
                System.out.println("Petición enviada al servidor: " + response);
                return (Response) input.readObject();
            } else {
                throw new IOException();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al enviar la petición: " + e.getMessage());
        }
        return null;
    }

    public boolean login(String loginInfo) {
        try {
            connect();
            System.out.println(loginInfo);
            Player player = new Player(loginInfo, 10000);
            Response response = sendRequest(jsonManager.responseRequest("ADD_PLAYER", player));
            boolean logged = jsonManager.deserializeJson(response.getResponseBody(), boolean.class);
            if (logged) {
                view.getMainPanel().updatePanel(Constants.GAME_KEY);
                return true;
            } else {
                disconnect();
                return false;
            }
        } catch (IOException e) {
            new MessageDialog("Error al conectar: " + e.getMessage()).showPopUp();
        }
        return false;
    }

    public List<CardImage> getCards() {
        try {
            Response response = sendRequest(jsonManager.responseRequest("CARDS_IN_GAME", new Object()));
            List<Card> cards = jsonManager.deserializeJsonList(response.getResponseBody(), Card.class);
            List<CardImage> cardImages = new ArrayList<>();
            for (Card card : cards) {
                cardImages.add(new CardImage(card.getName()));
            }
            return cardImages;
        } catch (Exception e) {
            new MessageDialog("Error al conectar: " + e.getMessage()).showPopUp();
        }
        return Collections.emptyList();
    }

    public void disconnect() {
        try {
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            System.err.println("Error al desconectar: " + e.getMessage());
        }
    }

}
