package co.edu.uptc.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {
    private final int port;
    private ServerSocket serverSocket;
    private List<ClientHandler> users;
    private ServerController serverController;
    private boolean isRunning;

    public Server(ServerController serverController) {
        port = 666;
        users = new ArrayList<>();
        this.serverController = serverController;
        isRunning = true;
    }

    public void disconnectClient(ClientHandler clientHandler) {
        users.remove(clientHandler);
    }

    public void addClient(Socket clientSocket) {
        ClientHandler clientHandler = new ClientHandler(clientSocket, serverController);
        users.add(clientHandler);
        new Thread(clientHandler).start();
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Servidor iniciado en el puerto " + port);
            while (users.size() < 3 && isRunning) { // Limitar a 3 clientes
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());
                addClient(clientSocket);
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        } finally {
            stop();
        }
    }

    public void stop() {
        try {
            if (serverSocket != null)
                serverSocket.close();
        } catch (IOException e) {
            System.err.println("Error cerrando el servidor: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        start();
    }
}
