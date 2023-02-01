package com.geekbrains.less02;

public class Main {

    public static final int ARRAY_SIZE = 4;

    public static void main(String[] args) {

        String[][] array_str_01 = {
                {"11", "12", "13", "14"},
                {"21", "22", "23", "24"},
                {"31", "32", "33", "34"},
                {"41", "42", "43", "44"}
        };

        String[][] array_str_02 = {
                {"11", "12", "13", "14"},
                {"21", "22", "23", "24", null},
                {"31", "32", "33", "34"},
                {"41", "42", "43", "44"}
        };

        String[][] array_str_03 = {
                {"11", "12", "13", "14"},
                {"21", "22", "FQ", "24"},
                {"31", "32", "33", "34"},
                {"41", "42", "43", "44"}
        };

        try {
            int sum = arraySum(array_str_01);
//            int sum = arraySum(array_str_02);
//            int sum = arraySum(array_str_03);

            System.out.println("Сумма элементов массива:" + sum);
        }
        catch (MyArraySizeException e) {
            System.out.println("Размер массива имеет неверное значение.");
        }
        catch (MyArrayDataException e) {
            System.out.println(e.getMessage());
        }

    }

    public static int arraySum(String[][] arr) throws MyArraySizeException, MyArrayDataException {

        // Проверка размерности массива
        if (arr == null || arr.length != ARRAY_SIZE) {
            throw new MyArraySizeException();
        }
        for (String[] inArr: arr) {
            if (inArr == null || inArr.length != ARRAY_SIZE) {
                throw new MyArraySizeException();
            }
        }


        // Сумирование элементов
        int sum = 0;

        for (int i = 0; i < ARRAY_SIZE; i++)
            for (int j = 0; j < ARRAY_SIZE; j++) {

                try {
                    sum += Integer.parseInt(arr[i][j]);
                }
                catch (NumberFormatException e) {

                    String fStr = "В ячейке String[%d][%d] хранится не числовое значение: %s.";
                    String str = String.format(fStr, i, j, arr[i][j]);
                    throw new MyArrayDataException(str);
                }

            }

        return sum;
    }

    public static class MyArraySizeException extends RuntimeException {

    }

    public static class MyArrayDataException extends RuntimeException {
        public MyArrayDataException(String message) {
            super(message);
        }
    }

}
