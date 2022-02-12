package com.gb.less06;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class ConsoleServer {

    // Порт сервера
    private static final int SERVER_PORT = 8189;

    // Сокет сервера
    private ServerSocket serverSocket;

    // Клиент
    private Client client;

    // Переменная для чтения из консоли
    private final Scanner scanner = new Scanner(System.in);


    /**
     * Точка входа в приложение.
     */
    public static void main(String[] args) {

        // Создать сервер
        ConsoleServer consoleServer = new ConsoleServer();

        // Запустить сервер
        try {
            consoleServer.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * Метод, запускающий работу сервера.
     */
    public void startServer() throws IOException {

        // Запустить сервер
        serverSocket = new ServerSocket(SERVER_PORT);

        // Ожидать подключение клиента
        Socket socket = serverSocket.accept();

        // Создать соединение с клиентом
        client = new Client("Клиент");
        client.openConnection(socket);

        // Запустить поток, считывающий сообщения из консоли
        sendMessage();

    }


    /**
     * Метод, запускающий поток-демон, считывающий сообщение
     * из консоли и отправляющий его пользователю.
     * Если соединение разорвано, то поток завершает свою работу.
     */
    public void sendMessage() {

        Thread thread = new Thread(() -> {
            try {

                while (client.isConnect()) {
                    String message = scanner.nextLine();
                    client.sendMessage(message);
                }

            }
            catch (Exception e) {
                e.printStackTrace();
            }

        });

        thread.setDaemon(true);
        thread.start();
    }


    /**
     * Класс, описывающий соединение с клиентом.
     */
    public static class Client {

        // Имя клиента
        private final String name;

        // Переменные для общения с клиентом
        private Socket socket;
        private DataInputStream in;
        private DataOutputStream out;

        // Индикатор, указывающий установлено или нет соединение
        private boolean connectionOpened = false;


        /**
         * Основной конструктор.
         * @param name имя клиента.
         */
        public Client(String name) {
            this.name = name;
        }

        /**
         * Метод, устанавливающий соединение с клиентом.
         * Метод запускает поток, получающий сообщения от клиента
         * и выводящий эти сообщения в консоль.
         * Если поток получает команду "/end", то соединение разрывается,
         * и поток завершает свою работу.
         */
        public void openConnection(Socket socket) throws IOException {

            this.socket = socket;

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            System.out.printf("Соединение с клиентом %s установлено\n", name);
            connectionOpened = true;

            new Thread(() -> {
                try {

                    while (connectionOpened) {
                        String strFromServer = in.readUTF();

                        if (strFromServer.equalsIgnoreCase("/end")) {
                            closeConnection();
                        }
                        else {
                            System.out.printf("%s: %s\n", name, strFromServer);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    closeConnection();
                }
            }).start();
        }


        /**
         * Метод, отправляющий сообщение клиенту.
         * @param message сообщение отправляемое клиенту.
         */
        public void sendMessage(String message) {

            try {
                if (connectionOpened) {
                    out.writeUTF(message);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                closeConnection();
            }
        }


        /**
         * Метод, закрывающий соединение с клиентом.
         */
        public void closeConnection() {

            System.out.printf("Соединение с клиентом %s разорвано\n", name);
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


        /**
         * Метод, указывающий установлено или нет соединение с клиентом.
         * @return true - соединение установлено, false - соединение разорвано.
         */
        public boolean isConnect() {
            return connectionOpened;
        }


        /**
         * Возвратить имя клиента.
         */
        public String getName() {
            return name;
        }
    }


}
