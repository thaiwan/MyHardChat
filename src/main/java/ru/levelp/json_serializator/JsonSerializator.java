package ru.levelp.json_serializator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.levelp.message.Message;

import java.net.Socket;

/**
 * Created by Tanya on 27.11.2016.
 */
public class JsonSerializator {

    Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    public String serializeToJson (Message messageForJson) {
        String jsonMessage = gson.toJson(messageForJson);
        return jsonMessage;
    }

    public Message desesializeToMessage (String jsonMessage) {
        System.out.println("jsonMessage - "  + jsonMessage);
        Message parsedMessage = gson.fromJson(jsonMessage, Message.class);
        return parsedMessage;
    }
}
