package ru.levelp.client;

import ru.levelp.json_serializator.JsonSerializator;
import ru.levelp.message.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Tanya on 26.11.2016.
 */
public class ClientExample {
        public static final String IP = "127.0.0.1";
        public static final int PORT = 7071;
        public static void main(String[] args){
            try {
                Socket socket = new Socket(IP, PORT);
                PrintWriter writer = new PrintWriter(socket.getOutputStream());

                ClientRecipient clientRecipient = new ClientRecipient(socket);
                clientRecipient.start();

                Scanner in = new Scanner(System.in);

                System.out.println("Enter your login");
                String userLogin = in.nextLine();

                Message authorizationMessage = new Message(userLogin, "", "authorizationMessage");
                String jsonMessageAuthorization = new JsonSerializator().serializeToJson(authorizationMessage);
                writer.println(jsonMessageAuthorization);
                writer.flush();


                String clientMessage = "";

                Message messageForJson;

                while (!(clientMessage = in.nextLine()).equals("exit")) {
                    String userName = "";
                    if (clientMessage.contains("@")) {
                        String[] parsedMessage = clientMessage.split(":");
                        userName = parsedMessage[0].replace("@", "");
                        clientMessage = parsedMessage[1];
                    }
                    messageForJson = new Message(userLogin, userName, clientMessage);
                    String gsonMess =  new JsonSerializator().serializeToJson(messageForJson);
                    writer.println(gsonMess);
                    writer.flush();

                }
                clientRecipient.stopRecipient();
                writer.close();
                socket.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
}
