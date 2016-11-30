package ru.levelp.message;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Calendar;

/**
 * Created by Tanya on 27.11.2016.
 */
public class Message implements Serializable {
    private String login;
    private String receiver;
    private String body;
    private long departureTime;
    @Expose
    private Calendar calendar = Calendar.getInstance();


    public Message (String login, String receiver, String body) {
        this.login = login;
        this.receiver = receiver;
        this.body = body;
        this.departureTime = calendar.getTimeInMillis();
    }

    public String getLogin() {
        return login;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getBody() {
        return body;
    }
}
