package main;

import client.Client;
import exceptions.ConnectionException;
import exceptions.InvalidPortException;

import java.util.Scanner;

import static io.OutputManager.print;
import static io.OutputManager.printErr;

public class Main {
    public static void main(String args[]) throws Exception {
        Scanner scanner = new Scanner(System.in);
        args = new String[]{"localhost"};
        String addr = "";
        int port = 0;
        try {
            addr = args[0];
            try {
                System.out.println("Введите порт");
                String s = scanner.nextLine();
                port = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                throw new InvalidPortException();
            }
            Client client = new Client(addr, port);
            client.start();
        } catch (ConnectionException e) {
            print(e.getMessage());
        }

    }
}

