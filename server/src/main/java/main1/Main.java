package main1;

import exceptions.InvalidPortException;
import server.Server;

import javax.sql.RowSet;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        int port = 0;

        String user = "postgres";
        String password = "Qwerty12";
        String url = "jdbc:postgresql://localhost:8080/webapps";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите порт");
        String s = scanner.nextLine();


        try {
            port = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new InvalidPortException();
        }

        Properties settings = new Properties();
        settings.setProperty("url", url);
        settings.setProperty("user", user);
        settings.setProperty("password", password);


        Server server = new Server(port, settings);
        server.start();


    }
}
