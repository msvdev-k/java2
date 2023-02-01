package com.geekbrains.server;


/**
 * Класс, описывающий команды для управления сервером со
 * стороны клиента.
 */
public class ServerCommandConstants {

    /**
     * Символ с которого начинаются команда сервера.
     */
    public static final String COMMAND_TOKEN = "/";

    /**
     * Команда авторизации.
     */
    public static final String AUTHORIZATION = "/auth";

    /**
     * Команда отключения клиента от сервера.
     */
    public static final String SHUTDOWN = "/end";

    /**
     * Команда для пересылки личного сообщения конкретному пользователю.
     */
    public static final String MESSAGE_TO_USER = "/w";

    /**
     * Аутентификация прошла успешно.
     */
    public static final String AUTHORIZATION_OK = "/authok";

}
