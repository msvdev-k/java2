package com.geekbrains;

public class OwnLinkedList<T> {
    private long size;
    private Node head;
    private Node tail;

    public OwnLinkedList() {
        this.size = 0;
        this.head = null;
        this.tail = null;
    }

    public void addNode(T data) {
        Node newNode = new Node(data);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
        }

        tail = newNode;
        size++;
    }


    /**
     * Добавление элемента по индексу.
     * @param index индекс, указывающий позицию вставки элемента.
     * @param data вставляемые данные.
     */
    public void addNode(int index, T data) {

        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        Node newNode = new Node(data);

        if (index == 0) {
            newNode.next = head;
            head = newNode;
        }
        else if (index == size) {
            tail.next = newNode;
            tail = newNode;
        }
        else {
            // Начальный элемент
            Node current = head;

            // Поиск index - 1 элемента
            for (int i = 1; i < index; i++) {
                current = current.next;
            }

            // Вставка нового элемента на позицию index
            newNode.next = current.next;
            current.next = newNode;
        }

        // Увеличение количества элементов
        size++;
    }


    public void display() {
        Node current = head;

        if (head == null) {
            System.out.println("Односвязный список пуст");
            return;
        }

        while (current != null) {
            System.out.println(current.data + " ");
            current = current.next;
        }

        System.out.println();
    }

    public void display(int index) {
        if (index > size) {
            System.out.println("Размер списка больше чем заданный индекс");
            return;
        }

        Node current = head;

        for (int i = 1; i <= index; i++) {
            current = current.next;
        }

        System.out.println(current.data);
    }


    private class Node {
        T data;
        Node next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }
}
