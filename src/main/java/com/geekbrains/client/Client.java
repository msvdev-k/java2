package com.geekbrains.client;

import com.geekbrains.CommonConstants;
import com.geekbrains.server.ServerCommandConstants;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


/**
 * Класс, описывающий логику взаимодействия клиента с сервером.
 */
public class Client {

    /**
     * Сокет, для взаимодействия клиента с сервером.
     */
    private Socket socket;

    /**
     * Входящий поток данных с сервера.
     */
    private DataInputStream inputStream;

    /**
     * Исходящий поток данных на сервер.
     */
    private DataOutputStream outputStream;

    /**
     * Поток данных вводимых с консоли.
     */
    private final Scanner scanner = new Scanner(System.in);


    /**
     * Основной конструктор класса.
     */
    public Client() {
        try {
            openConnection();

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


    /**
     * Метод, устанавливающий соединение с сервером.
     * <p>
     * Метод, запускающий поток, считывающий сообщения клиента
     * из консоли и отправляющий их на сервер.
     * <p>
     * Метод запускает поток, получающий сообщения от сервера
     * и выводящий эти сообщения в консоль.
     *
     * @throws IOException стандартное исключение ввода/вывода.
     */
    private void openConnection() throws IOException {

        initializeNetwork();

        // Поток получающий входящие сообщения от сервера
        new Thread(() -> {
            try {
                while (true) {
                    String messageFromServer = inputStream.readUTF();
                    System.out.println(messageFromServer);
                }

            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }).start();

        // Поток отправляющий сообщения из консоли на сервер
        new Thread(() -> {
            try {
                while (true) {
                    String text = scanner.nextLine();

                    sendMessage(text);

                    if (text.equals(ServerCommandConstants.SHUTDOWN)) {
                        closeConnection();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


    /**
     * Инициализация взаимодействия клиента и сервера по сети.
     *
     * @throws IOException стандартное исключение ввода/вывода.
     */
    private void initializeNetwork() throws IOException {

        socket = new Socket(CommonConstants.SERVER_ADDRESS, CommonConstants.SERVER_PORT);

        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());
    }


    /**
     * Отправить сообщение на сервер.
     *
     * @param message отправляемое сообщение.
     */
    public void sendMessage(String message) {

        try {
            outputStream.writeUTF(message);

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


    /**
     * Закрыть соединения клиента с сервером.
     */
    private void closeConnection() {

        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Завершение приложения клиента
        System.exit(1);
    }


    /**
     * Точка запуска клиентского приложения.
     */
    public static void main(String[] args) {
        new Client();
    }

}
