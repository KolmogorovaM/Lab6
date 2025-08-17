package ru.marina.server;

import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(new InetSocketAddress(52828), "test.csv");
        server.initialize();
    }
}
