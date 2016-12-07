package ru.levelp.dao;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import ru.levelp.json_serializator.JsonSerializator;
import ru.levelp.message.Message;
import ru.levelp.server.ServerExample;

import java.util.List;

/**
 * Created by Tanya on 04.12.2016.
 */
public abstract class BaseMongoService<T> {
    private Datastore db;
    private Class<T> entityType;

    public BaseMongoService(Class<T> entityType) {
        this.entityType = entityType;
        Morphia morphia = new Morphia();
        db = morphia.createDatastore(
                new MongoClient("localhost"), "leveluptest");
        db.ensureIndexes();
    }

    public Datastore request() {
        return db;
    }

}
