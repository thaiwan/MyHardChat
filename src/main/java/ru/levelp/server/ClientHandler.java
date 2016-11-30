package ru.levelp.server;

import java.io.*;
import java.net.Socket;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import ru.levelp.json_serializator.JsonSerializator;
import ru.levelp.message.Message;

import static java.lang.System.in;
import static java.lang.System.setOut;

/**
 * Created by Tanya on 26.11.2016.
 */
public class ClientHandler extends Thread {

    private Socket socket;
    private ServerExample server;
    SenderWorker senderWorker;

    public ClientHandler(ServerExample server, Socket socket){
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            senderWorker = new SenderWorker(writer);
            senderWorker.start();

            String inputMessage;
            while ((inputMessage = reader.readLine()) != null) {
                Message userMessage = new JsonSerializator().desesializeToMessage(inputMessage);

                if (userMessage.getBody().equals("authorizationMessage")) {
                    server.writeClientsMap(userMessage.getLogin(), this);
                }
                if (userMessage.getReceiver().equals("") && (!(userMessage.getBody().equals("authorizationMessage")))) {

                    server.sendToAll(inputMessage, this);

//                    server.sendToAll(userMessage.getBody(), this);

                } if((!userMessage.getReceiver().equals("")) && (!userMessage.getBody().equals("authorizationMessage"))) {

                    server.sendToOne(inputMessage, userMessage.getReceiver());

//                    server.sendToOne(userMessage.getBody(), userMessage.getReceiver());
                }
            }
            server.disconnectClient(this);
            senderWorker.stopWorker();
            writer.close();
            reader.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        if (senderWorker != null) {
            senderWorker.addMessage(message);
        }
    }
}
