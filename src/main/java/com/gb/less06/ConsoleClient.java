package com.gb.less06;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


public class ConsoleClient {

    // Адрес сервера
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 8189;

    // Переменные для общения с сервером
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    // Переменная для чтения из консоли
    private final Scanner scanner = new Scanner(System.in);
    // Индикатор, указывающий установлено или нет соединение
    private boolean connectionOpened = false;


    /**
     * Точка входа в приложение.
     */
    public static void main(String[] args) {

        // Создать клиента
        ConsoleClient client = new ConsoleClient();

        // Установить соединение с сервером
        try {
            client.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            client.closeConnection();
        }

        // Получить сообщение от клиента,
        // и отправить его на сервер
        client.sendMessage();

    }


    /**
     * Метод, запускающий поток-демон, считывающий сообщение клиента
     * из консоли и отправляющий его на сервер.
     * Если соединение разорвано, то поток завершает свою работу.
     */
    public void sendMessage() {

        Thread thread = new Thread(() -> {
            try {

                while (connectionOpened) {
                    String message = scanner.nextLine();
                    out.writeUTF(message);
                }

            }
            catch (Exception e) {
                e.printStackTrace();
                closeConnection();
            }

        });

        thread.setDaemon(true);
        thread.start();
    }




    /**
     * Метод, устанавливающий соединение с сервером.
     * Метод запускает поток, получающий сообщения от сервера
     * и выводящий эти сообщения в консоль.
     * Если поток получает команду "/end", то соединение разрывается,
     * и поток завершает свою работу.
     */
    public void openConnection() throws IOException {

        socket = new Socket(SERVER_IP, SERVER_PORT);

        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        System.out.println("Соединение с сервером установлено");
        connectionOpened = true;

        new Thread(() -> {
            try {

                while (connectionOpened) {
                    String strFromServer = in.readUTF();

                    if (strFromServer.equalsIgnoreCase("/end")) {
                        closeConnection();
                    }
                    else {
                        System.out.printf("Сервер: %s\n", strFromServer);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                closeConnection();
            }
        }).start();
    }


    /**
     * Метод, закрывающий соединение с сервером.
     */
    public void closeConnection() {

        System.out.println("Соединение разорвано");
        connectionOpened = false;

        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
