package ru.levelp.dao;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import ru.levelp.json_serializator.JsonSerializator;
import ru.levelp.managers.HibernateManager;
import ru.levelp.message.Message;
import ru.levelp.server.ServerExample;

import java.util.List;

/**
 * Created by Tanya on 01.12.2016.
 */
public class MessageServiceHibernate implements MessageDAO {
    private Session session;

    public MessageServiceHibernate() {
        this.session = HibernateManager.getInstance().getSession();
    }

    public void addMessage(Message message) {
        session.beginTransaction();
        System.out.println("begin saving + message - " + message);
        session.save(message);
        session.getTransaction().commit();
    }

    public void getMessagesByLogin(String login) {
        List<Message> messages = session.createCriteria(Message.class)
                .add(Restrictions.eq(MessageDAO.FIELD_LOGIN, login))
                .list();
        for (Message message : messages) {
            new ServerExample().sendToOne(new JsonSerializator().serializeToJson(message), login);
        }
    }
}
