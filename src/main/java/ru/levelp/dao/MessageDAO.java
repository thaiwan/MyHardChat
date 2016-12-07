package ru.levelp.dao;

import ru.levelp.message.Message;

/**
 * Created by Tanya on 04.12.2016.
 */
public interface MessageDAO {

    String FIELD_LOGIN = "login";

    void addMessage(Message message);
    void getMessagesByLogin(String login);
}