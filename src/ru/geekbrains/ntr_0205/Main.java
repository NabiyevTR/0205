package ru.geekbrains.ntr_0205;

import javax.swing.*;
import java.sql.Array;
import java.util.Arrays;

public class Main {
    static final int SIZE = 10000000;

    public static void main(String[] args) {
        float [] array = new float[SIZE];
        Arrays.fill(array,1);

        Timer oneThreadTimer = new Timer(array);
        oneThreadTimer.countOneThread();

        Timer multiThreadTimer = new Timer(array);
        try {
            multiThreadTimer.countMultiThread(2);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println( Arrays.compare(oneThreadTimer.getArray(),
                multiThreadTimer.getArray()) == 0 ? "Массивы равны." : "Массивы не равны");



    }
}
