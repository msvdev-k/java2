package com.gb.less03;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        System.out.println("\n===========================");
        System.out.println("Первое задание");
        System.out.println("===========================");

        List<String> words = createWordList();
        System.out.println("\nСписок слов:");
        System.out.println(words);

        Set<String> uWords = uniqueWordList(words);
        System.out.println("\nСписок уникальных слов:");
        System.out.println(uWords);

        Map<String, Integer> countWords = countWortList(words);
        System.out.println("\nКоличество повторений слов:");
        for (Map.Entry<String, Integer> entry: countWords.entrySet()) {
            System.out.printf("Слово %s встречается %d раз(а)%n", entry.getKey(), entry.getValue());
        }

        System.out.println("\n===========================");
        System.out.println("Второе задание");
        System.out.println("===========================\n");

        Phonebook phonebook = new Phonebook();

        phonebook.add("Иванов", "9151234567");
        phonebook.add("Петров", "8451234567");
        phonebook.add("Сидоров", "6541234567");
        phonebook.add("Иванов", "7891234567");
        phonebook.add("Иванов", "9631234567");

        System.out.println("Телефоны Иванова:");
        System.out.println(phonebook.get("Иванов"));
        System.out.println("\nТелефоны Петрока:");
        System.out.println(phonebook.get("Петров"));
        System.out.println("\nТелефоны Сидорова:");
        System.out.println(phonebook.get("Сидоров"));
        System.out.println("\nТелефоны Орешкина:");
        System.out.println(phonebook.get("Орешкин"));



    }


    /**
     * Создать массив с набором слов
     * @return список слов
     */
    public static List<String> createWordList() {

        List<String> list = new ArrayList<>();

        list.add("Картофель");
        list.add("Редис");
        list.add("Морковь");
        list.add("Свекла");
        list.add("Капуста");
        list.add("Чеснок");
        list.add("Лук");
        list.add("Репа");
        list.add("Картофель");
        list.add("Редис");
        list.add("Морковь");
        list.add("Свекла");
        list.add("Капуста");
        list.add("Чеснок");
        list.add("Лук");
        list.add("Репа");
        list.add("Картофель");
        list.add("Редис");
        list.add("Морковь");
        list.add("Свекла");
        list.add("Капуста");
        list.add("Картофель");
        list.add("Редис");
        list.add("Морковь");
        list.add("Картофель");
        list.add("Редис");
        list.add("Морковь");
        list.add("Свекла");

        return list;
    }


    /**
     * Найти список уникальных слов
     * @param wordList список слов
     * @return список уникальных слов
     */
    public static Set<String> uniqueWordList(List<String> wordList) {
        return new HashSet<>(wordList);
    }


    /**
     * Подсчитать сколько раз встречается каждое слово
     * @param wordList список слов
     * @return коллекция в которой ключ - слово, а значение - количество повторений этого слова
     */
    public static Map<String, Integer> countWortList(List<String> wordList) {

        Map<String, Integer> msi = new HashMap<>();

        for (String word: wordList) {

            Integer count = 1;

            if (msi.containsKey(word)) {
                count += msi.get(word);
            }

            msi.put(word, count);
        }

        return msi;
    }


    /**
     * Класс - телефонный справочник
     */
    public static class Phonebook {

        private final Map<String, List<String>> phoneNumbers = new HashMap<>();

        /**
         * Добавить запись
         * @param name ФИО
         * @param phone телефонный номер
         */
        public void add(String name, String phone) {

            if (phoneNumbers.containsKey(name)) {
                phoneNumbers.get(name).add(phone);
            }
            else {
                List<String> numbers = new ArrayList<>();
                numbers.add(phone);
                phoneNumbers.put(name, numbers);
            }
        }


        /**
         * Поиск номера по фамилии
         * @param name ФИО
         * @return список телефонных номеров
         * (Если фамилии нет, то список пустой)
         */
        public List<String> get(String name) {

            if (phoneNumbers.containsKey(name)) {
                return new ArrayList<>(phoneNumbers.get(name));
            }

            return new ArrayList<>();
        }



    }


}


