package ru.marina.client;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) {
        try {
            Client client = new Client(InetAddress.getByName("localhost"), 52828); // localhost
            client.launch();
        } catch (UnknownHostException e) {
            System.out.println("Host is unknown.");
        }
    }
}
