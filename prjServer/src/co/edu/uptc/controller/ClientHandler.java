package co.edu.uptc.controller;

import java.io.*;
import java.net.Socket;
import java.util.List;

import co.edu.uptc.model.Card;
import co.edu.uptc.model.Player;
import co.edu.uptc.net.JsonManager;
import co.edu.uptc.net.Response;

public class ClientHandler implements Runnable {

    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private ServerController serverController;

    private JsonManager jsonManager;

    public ClientHandler(Socket socket, ServerController serverController) {
        this.socket = socket;
        this.serverController = serverController;
        jsonManager = new JsonManager();
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            System.err.println("Error de comunicacion: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            Response requestReponse;
            while ((requestReponse = (Response) input.readObject()) != null) {
                System.out.println("Solicitud recibida: " + requestReponse.getRequest());
                Response response = handleRequest(requestReponse);
                System.out.println("Respuesta: " + response);
                output.writeObject(response);
                output.flush();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Cliente desconectado " + socket.getInetAddress());
        } finally {
            closeConnection();
        }
    }

    private Response handleRequest(Response clientResponse) {
        String request = clientResponse.getRequest();
        String responseBody = clientResponse.getResponseBody();
        Response serverResponse;
        switch (request) {
            case "ADD_PLAYER":
                serverResponse = addPlayer(responseBody);
                break;
            case "HTI":
                serverResponse = hitPlayer(responseBody);
                break;
            case "STAND":
                serverResponse = standPlayer(responseBody);
                break;
            case "DOUBLE":
                serverResponse = doublePlayer(responseBody);
                break;
            case "SURENDER":
                serverResponse = surenderPlayer(responseBody);
                break;
            case "CARDS_IN_GAME":
                serverResponse = cardsInGame();
                break;
            default:
                serverResponse = nullResponse();
                break;
        }
        return serverResponse;
    }

    public Response addPlayer(String responseBody) {
        Player player = jsonManager.deserializeJson(responseBody, Player.class);
        return jsonManager.responseRequest("CONFIRMATION", serverController.addPlayer(player));
    }

    public Response hitPlayer(String responseBody) {
        serverController.playerHit();
        return jsonManager.responseRequest("CONFIRMATION", serverController.playerHit());
    }

    public Response standPlayer(String responseBody) {
        serverController.playerStand();
        return jsonManager.responseRequest("CONFIRMATION", true);
    }

    public Response doublePlayer(String responseBody) {
        serverController.playerDouble();
        return jsonManager.responseRequest("CONFIRMATION", true);
    }

    public Response surenderPlayer(String responseBody) {
        serverController.playerSurrender();
        return jsonManager.responseRequest("CONFIRMATION", true);
    }

    private Response cardsInGame() {
        List<Card> cards = serverController.getCardsInGame();
        return jsonManager.responseRequest("CARDS_IN_GAME", cards);
    }

    public Response nullResponse() {
        Response response = new Response();
        response.setResponseBody("UNKNOWN");
        return response;
    }

    private void closeConnection() {
        try {
            if (socket != null)
                socket.close();
            if (input != null)
                input.close();
            if (output != null)
                output.close();
        } catch (IOException e) {
            System.err.println("Error cerrando conexión: " + e.getMessage());
        }
    }
}
