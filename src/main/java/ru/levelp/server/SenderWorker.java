package ru.levelp.server;

import ru.levelp.json_serializator.JsonSerializator;

import java.io.PrintWriter;
import java.util.PriorityQueue;

/**
 * Created by Tanya on 26.11.2016.
 */
public class SenderWorker extends Thread {
    private PriorityQueue<String> queue;
    private PrintWriter writer;
    private boolean alive;

    public SenderWorker(PrintWriter writer){
        this.writer = writer;
        queue = new PriorityQueue<String>();
    }

    @Override
    public void run() {
        alive = true;
        while (alive) {
            if (!queue.isEmpty()){
                String message = queue.poll();
                writer.println(message);
                writer.flush();
            } else {
                Thread.yield();
            }
        }
    }

    public void addMessage(String message) {
        queue.add(message);
    }


    public void stopWorker() {
        if (isAlive() && alive) {
            alive = false;
        }
    }
}
