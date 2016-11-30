package ru.levelp.client;

import ru.levelp.json_serializator.JsonSerializator;
import ru.levelp.message.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by Tanya on 26.11.2016.
 */
public class ClientRecipient extends Thread {
    private Socket socket;
    private boolean alive;

    public ClientRecipient (Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        alive = true;

            try {
            BufferedReader serverReader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));//тут получение в json дб
            while (alive) {
                try {
                    Message newMessage = new JsonSerializator().desesializeToMessage(serverReader.readLine());
                    System.out.println("client message body - " + newMessage.getBody());
                } catch (SocketException se) {
                    System.out.println("socket was closed");
                }
            }
                serverReader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    public void stopRecipient() {
        if (isAlive() && alive) {
            alive = false;
        }
    }
}
