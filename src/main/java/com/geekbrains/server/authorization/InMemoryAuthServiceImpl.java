package com.geekbrains.server.authorization;

import java.util.HashMap;
import java.util.Map;


/**
 * Класс, реализующий аутентификацию клиента через обычный список клиентов.
 */
public class InMemoryAuthServiceImpl implements AuthService {

    /**
     * Список клиентов зарегистрированных в системе.
     */
    private final Map<String, UserData> users = new HashMap<>();


    /**
     * Инициализация списка зарегистрированных клиентов.
     */
    private void initUsers() {
        users.put("log1", new UserData("log1", "pass1", "user_1"));
        users.put("log2", new UserData("log2", "pass2", "user_2"));
        users.put("log3", new UserData("log3", "pass3", "user_3"));
        users.put("log4", new UserData("log4", "pass4", "user_4"));
    }


    @Override
    public void start() {
        initUsers();
        System.out.println("Сервис аутентификации инициализирован");
    }


    @Override
    public synchronized String getNickNameByLoginAndPassword(String login, String password) {

        UserData user = users.get(login);

        if (user != null && user.getPassword().equals(password)) {
            return user.getNickName();
        }

        return null;
    }


    @Override
    public void end() {
        users.clear();
        System.out.println("Сервис аутентификации отключен");
    }

}
