//package ru.levelp.server;
//
//import java.io.PrintWriter;
//import java.util.PriorityQueue;
//
///**
// * Created by Tanya on 26.11.2016.
// */
//public class MultipleSenderWorker extends Thread {
//    private PriorityQueue<String> queue;
//    private boolean alive;
//
//    public MultipleSenderWorker(PrintWriter writer){
//        this.writer = writer;
//        queue = new PriorityQueue<String>();
//    }
//
//    @Override
//    public void run() {
//        alive = true;
//        while (alive) {
//            if (!queue.isEmpty()){
//                String message = queue.poll();
//                writer.println(message);
//                writer.flush();
//            } else {
//                Thread.yield();
//            }
//        }
//    }
//
//    public void addMessage(String message, ClientHandler ignoredClient) {
//        queue.add(message);
//    }
//
//    public void stopWorker() {
//        if (isAlive() && alive) {
//            alive = false;
//        }
//    }
//}
