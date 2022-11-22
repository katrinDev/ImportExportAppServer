package org.project.serverMain;

import org.project.utilities.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final int PORT = 3333;
    private static ServerSocket serverSocket;
    private static ClientHandler clientHandler;
    private static Thread thread;
    private static List<Socket> currentSockets = new ArrayList<>();

    public static List<Socket> getCurrentSockets() {
        return currentSockets;
    }

    public static void main(String[] args) throws IOException {
        serverSocket = new ServerSocket(PORT);
        System.out.println("Сервер запущен");

        while(true){
            currentSockets.removeIf(Socket::isClosed);

            Socket clientSocket = serverSocket.accept();
            currentSockets.add(clientSocket);
            String socketInfo = "\nНовый клиент " + clientSocket.getInetAddress() + ":" + clientSocket.getPort();
            System.out.println(socketInfo);
            System.out.println("Количество действующих подключений: " + (long) currentSockets.size());

            clientHandler = new ClientHandler(clientSocket);
            thread = new Thread(clientHandler);
            thread.start();
            System.out.flush();
        }
    }
}

