package ru.levelp.message;

import com.google.gson.annotations.Expose;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import javax.persistence.*;

@Entity
@Table(name = "chat_messages")

/**
 * Created by Tanya on 27.11.2016.
 */
public class Message implements Serializable {
    @Id
    @Column(name="id_chat_message")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "login")
    @Expose
    private String login;
    @Column(name = "receiver")
    @Expose
    private String receiver;
    @Column(name = "body")
    @Expose
    private String body;
    @Column(name = "departure_time")
    @Expose
    private Timestamp departureTime;




    public Message (String login, String receiver, String body) {
        this.login = login;
        this.receiver = receiver;
        this.body = body;
        this.departureTime = new Timestamp(System.currentTimeMillis());
//        this.departureTime = calendar.getTimeInMillis();
    }

    public Message() {}

    public String getLogin() {
        return login;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getBody() {
        return body;
    }

    public long getId() {
        return id;
    }

    public Timestamp getDepartureTime() {
        return departureTime;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setDepartureTime(Timestamp departureTime) {
        this.departureTime = departureTime;
    }

}
