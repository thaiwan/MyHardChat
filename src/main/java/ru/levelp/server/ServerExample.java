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

-Mongo

-    /*
    1) Все запросы, передаваемые между клиентом и сервером должны быть в формате json
    2) Структура запроса:
        sender
        receiver
        body
        timestamp
    3) Сохранение сообщений в БД (только сообщения между пользователями)
    4) Получение клиентом истории от сервера:
        sender = name
        receiver = server
        body = getHistory
        timestamp = now
     */

/*ДОМАШНЕЕ ЗАДАНИЕ:
как удалять оптимально по id (session.load(User.class, id) - получение по id)
в чат в класс сообщений добавить поле время когда отправлено
-запрос не считается сообщением и не записывается в БД.
-(при отправке серсером ответа на запрос местами меняются sender и recipient(в body всю историю которую нужно отправить)




 29.11 можно сделать запрос "кто онлайн" типа login:server, body:get users

 */
