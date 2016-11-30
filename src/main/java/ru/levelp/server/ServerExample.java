package ru.levelp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Tanya on 26.11.2016.
 */
public class ServerExample {
    private ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();

    private static HashMap<String, ClientHandler> clientsMap = new HashMap<String, ClientHandler>();

    public static void main(String[] args) {
        new ServerExample().go();
    }

    private void go() {
        try {
            ServerSocket serverSocket = new ServerSocket(7071);
            System.out.println("Server started...");
            while (true) {
                Socket clientSocket = serverSocket.accept();//блокирующий метод
                ClientHandler clientHandler = new ClientHandler(this, clientSocket);
                clientHandler.start();
                clients.add(clientHandler);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendToAll(final String message, final ClientHandler sender) {
        new Thread(new Runnable() {
            public void run() {
                for (ClientHandler c : clients) {
                    if (c != sender) {
                        c.sendMessage(message);
                    }
                }
            }
        }).start();

    }


    public void sendToOne(final  String message, final  String receiverLogin) {
        new Thread(new Runnable() {
            public void run() {
                clientsMap.get(receiverLogin).sendMessage(message);
            }
        }).start();
    }

        public void writeClientsMap (String userLogin, ClientHandler clientHandler) {
            if (!clientsMap.containsKey(userLogin)) {
                clientsMap.put(userLogin, clientHandler);
            }
    }

    public void disconnectClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

}



/*
-сделать прием через json
-чистить сообщения клиента из очереди отпраки



 29.11 можно сделать запрос "кто онлайн" типа login:server, body:get users

 */
