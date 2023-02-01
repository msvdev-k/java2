package com.geekbrains;

public class OwnLinkedListMain {
    public static void main(String[] args) {
        OwnLinkedList<Integer> integerOwnLinkedList = new OwnLinkedList<>();
        integerOwnLinkedList.addNode(150);
        integerOwnLinkedList.addNode(347);
        integerOwnLinkedList.addNode(111);
        integerOwnLinkedList.addNode(789);
        integerOwnLinkedList.addNode(222);
        integerOwnLinkedList.addNode(345);
        integerOwnLinkedList.display();

        integerOwnLinkedList.addNode(0, 11111);
        integerOwnLinkedList.display();

//        integerOwnLinkedList.addNode(8, 11111); // <--- Exception

        integerOwnLinkedList.addNode(7, 55555);
        integerOwnLinkedList.display();

        integerOwnLinkedList.addNode(3, 44444);
        integerOwnLinkedList.display();


//        integerOwnLinkedList.remove(9); // <--- Exception

        int removedNode = integerOwnLinkedList.remove(0);
        System.out.println("Удалённый элемент:" + removedNode);
        integerOwnLinkedList.display();

        removedNode = integerOwnLinkedList.remove(2);
        System.out.println("Удалённый элемент:" + removedNode);
        integerOwnLinkedList.display();

        removedNode = integerOwnLinkedList.remove(6);
        System.out.println("Удалённый элемент:" + removedNode);
        integerOwnLinkedList.display();


        for (int i = 0 ; i < 6; i++) {
            removedNode = integerOwnLinkedList.remove(0);
            System.out.println("Удалённый элемент:" + removedNode);
            integerOwnLinkedList.display();
        }


    }

}
