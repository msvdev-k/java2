package com.gb.less05;

public class Main {

    static final int SIZE = 10_000_000;
    static final int HALF = SIZE / 2;


    public static void main(String[] args) {

        System.out.println("Выполнение первого метода:");
        firstMethod();
        firstMethod();
        firstMethod();
        firstMethod();
        firstMethod();

        System.out.println("\nВыполнение второго метода:");
        secondMethod();
        secondMethod();
        secondMethod();
        secondMethod();
        secondMethod();

        // Способ без деления массива пополам
        System.out.println("\nВыполнение третьего метода:");
        thirdMethod();
        thirdMethod();
        thirdMethod();
        thirdMethod();
        thirdMethod();

    }


    public static void firstMethod() {

        // 1) Создать одномерный длинный массив
        float[] arr = new float[SIZE];

        // 2) Заполнить этот массив единицами
        for (int i = 0; i < SIZE; i++) {
            arr[i] = 1;
        }

        // 3) Засечь время выполнения
        long timestamp1 = System.currentTimeMillis();

        // 4) Пройти по всему массиву и для каждой ячейки определить новое значение по формуле
        for (int i = 0; i < SIZE; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + (float) i / 5) * Math.cos(0.2f + (float)i / 5) * Math.cos(0.4f + (float)i / 2));
        }

        // 5) Засечь время окончания метода
        long timestamp2 = System.currentTimeMillis();

        // 6) Вывести в консоль время работы
        System.out.printf("Операция выполнялась %d мс.\n", timestamp2 - timestamp1);

    }


    public static void secondMethod() {

        // 1) Создать одномерный длинный массив
        float[] arr = new float[SIZE];

        // 2) Заполнить этот массив единицами
        for (int i = 0; i < SIZE; i++) {
            arr[i] = 1;
        }

        // 3) Засечь время выполнения
        long timestamp1 = System.currentTimeMillis();

        // 4) Разбить массив на два
        float[] leftHalf = new float[HALF];
        float[] rightHalf = new float[HALF];
        System.arraycopy(arr, 0, leftHalf, 0, HALF);
        System.arraycopy(arr, HALF, rightHalf, 0, HALF);


        // 5) Запустить два потока для двух частей массива
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < HALF; i++) {
                leftHalf[i] = (float)(leftHalf[i] * Math.sin(0.2f + (float) i / 5) * Math.cos(0.2f + (float)i / 5) * Math.cos(0.4f + (float)i / 2));
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < HALF; i++) {
                // Примечание. Не знаю насколько это критично, но чтобы в точности повторить
                // вычисления из первого метода необходимо при вычислении синуса i заменить на i+HALF,
                // иначе будет несоответствие формул. Но если требуется просто засечь время,
                // то этого можно и не делать. Будет ли это ошибкой для текущего задания?
                rightHalf[i] = (float)(rightHalf[i] * Math.sin(0.2f + (float) i / 5) * Math.cos(0.2f + (float)i / 5) * Math.cos(0.4f + (float)i / 2));
            }
        });

        thread1.start();
        thread2.start();

        // 6) Ждать завершения двух потоков
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 7) Склеить массив
        System.arraycopy(leftHalf, 0, arr, 0, HALF);
        System.arraycopy(rightHalf, 0, arr, HALF, HALF);


        // 8) Засечь время окончания метода
        long timestamp2 = System.currentTimeMillis();

        // 9) Вывести в консоль время работы
        System.out.printf("Операция выполнялась %d мс.\n", timestamp2 - timestamp1);

    }



    public static void thirdMethod() {

        // 1) Создать одномерный длинный массив
        float[] arr = new float[SIZE];

        // 2) Заполнить этот массив единицами
        for (int i = 0; i < SIZE; i++) {
            arr[i] = 1;
        }

        // 3) Засечь время выполнения
        long timestamp1 = System.currentTimeMillis();

        // 4) Запустить два потока для двух частей массива
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < HALF; i++) {
                arr[i] = (float)(arr[i] * Math.sin(0.2f + (float) i / 5) * Math.cos(0.2f + (float)i / 5) * Math.cos(0.4f + (float)i / 2));
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = HALF; i < SIZE; i++) {
                arr[i] = (float)(arr[i] * Math.sin(0.2f + (float) i / 5) * Math.cos(0.2f + (float)i / 5) * Math.cos(0.4f + (float)i / 2));
            }
        });

        thread1.start();
        thread2.start();

        // 5) Ждать завершения двух потоков
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 6) Засечь время окончания метода
        long timestamp2 = System.currentTimeMillis();

        // 7) Вывести в консоль время работы
        System.out.printf("Операция выполнялась %d мс.\n", timestamp2 - timestamp1);

    }

}
