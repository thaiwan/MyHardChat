package ru.levelp.dao;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import ru.levelp.json_serializator.JsonSerializator;
import ru.levelp.message.Message;
import ru.levelp.server.ServerExample;

import java.util.List;

/**
 * Created by Tanya on 04.12.2016.
 */
public class MessageServiceMongo extends BaseMongoService<Message> implements MessageDAO {

    MessageServiceMongo() {
        super(Message.class);
    }

    public void getMessagesByLogin(String login) {
        List<Message> messages = request().createQuery(Message.class)
                                    .field(MessageDAO.FIELD_LOGIN).equal(login)
                                    .asList();
        for (Message message : messages) {
            new ServerExample().sendToOne(new JsonSerializator().serializeToJson(message), login);
        }
    }

    public void addMessage(Message message) {
        request().save(message);
    }
}
