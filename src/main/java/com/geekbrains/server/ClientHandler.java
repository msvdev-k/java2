package com.geekbrains.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


/**
 * Класс, отвечающий за обмен сообщениями между клиентами и сервером.
 */
public class ClientHandler {

    /**
     * Экземпляр класса сервера.
     */
    private final Server server;

    /**
     * Сокет для взаимодействия клиента с сервером.
     */
    private final Socket socket;

    /**
     * Входящий поток данных от клиента.
     */
    private final DataInputStream inputStream;

    /**
     * Исходящий поток данных клиенту.
     */
    private final DataOutputStream outputStream;

    /**
     * Никнейм пользователя.
     */
    private String nickName;


    /**
     * Основной конструктор класса.
     *
     * @param server экземпляр класса сервера.
     * @param socket сокет для взаимодействия клиента с сервером.
     */
    public ClientHandler(Server server, Socket socket) {

        try {
            this.server = server;
            this.socket = socket;

            this.inputStream = new DataInputStream(socket.getInputStream());
            this.outputStream = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                try {
                    authentication();
                    readMessages();

                } catch (IOException exception) {
                    exception.printStackTrace();

                } finally {
                    closeConnection();
                }
            }).start();

        } catch (IOException exception) {
            throw new RuntimeException("Проблемы при создании обработчика");
        }
    }


    public void authentication() throws IOException {

        while (true) {

            String message = inputStream.readUTF();

            if (message.startsWith(ServerCommandConstants.AUTHORIZATION)) {
                String[] authInfo = message.split("\\s");
                String nickName = server.getAuthService().getNickNameByLoginAndPassword(authInfo[1], authInfo[2]);

                if (nickName != null) {
                    if (!server.isNickNameBusy(nickName)) {

                        sendMessage(ServerCommandConstants.AUTHORIZATION_OK + " " + nickName);
                        this.nickName = nickName;

                        server.broadcastMessage(nickName + " зашёл в чат");
                        server.addConnectedUser(this);

                        return;

                    } else {
                        sendMessage("Учетная запись уже используется");
                    }

                } else {
                    sendMessage("Неверные логин или пароль");
                }

            } else {
                sendMessage("Строка регистрации: /auth login password");
            }
        }
    }


    /**
     * Метод, считывающий сообщения от текущего клиента.
     *
     * @throws IOException стандартное исключение ввода/вывода.
     */
    private void readMessages() throws IOException {

        while (true) {
            String messageInChat = inputStream.readUTF();

            if (messageInChat.startsWith(ServerCommandConstants.COMMAND_TOKEN)) {

                String[] authInfo = messageInChat.split("\\s");

                if (authInfo[0].equals(ServerCommandConstants.SHUTDOWN)) {
                    return;

                } else if (authInfo[0].equals(ServerCommandConstants.MESSAGE_TO_USER)) {
                    String userNickName = authInfo[1];

                    StringBuilder message = new StringBuilder();
                    message.append(nickName);
                    message.append(">>");
                    for (int i = 2; i < authInfo.length; i++) {
                        message.append(" ");
                        message.append(authInfo[i]);
                    }

                    server.broadcastMessage(message.toString(), userNickName);
                }

            } else {
                server.broadcastMessage(nickName + ">> " + messageInChat);
            }
        }
    }


    /**
     * Отправить сообщение текущему клиенту.
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
     * Отключить соединение с текущим клиентом.
     */
    private void closeConnection() {

        server.disconnectUser(this);
        server.broadcastMessage(nickName + " вышел из чата");

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
    }


    /**
     * Получить никнейм клиента.
     *
     * @return никнейм пользователя.
     */
    public String getNickName() {
        return nickName;
    }

}
