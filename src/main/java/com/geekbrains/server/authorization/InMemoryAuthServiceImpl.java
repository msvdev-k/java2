package com.geekbrains.server.authorization;

import java.util.HashMap;
import java.util.Map;

public class InMemoryAuthServiceImpl implements AuthService {
    private final Map<String, UserData> users;

    public InMemoryAuthServiceImpl() {
        users = new HashMap<>();
        users.put("log1", new UserData("log1", "pass1", "user_01"));
        users.put("log2", new UserData("log2", "pass2", "user_02"));
        users.put("log3", new UserData("log3", "pass3", "user_03"));
    }

    @Override
    public void start() {
        System.out.println("Сервис аутентификации инициализирован");
    }

    @Override
    public synchronized String getNickNameByLoginAndPassword(String login, String password) {
        UserData user = users.get(login);
        // Ищем пользователя по логину и паролю, если нашли то возвращаем никнэйм
        if (user != null && user.getPassword().equals(password)) {
            return user.getNickName();
        }

        return null;
    }

    @Override
    public void end() {
        System.out.println("Сервис аутентификации отключен");
    }
}
