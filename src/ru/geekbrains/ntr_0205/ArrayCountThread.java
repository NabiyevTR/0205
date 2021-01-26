package ru.geekbrains.ntr_0205;

import java.util.Arrays;

public class ArrayCountThread extends Thread {
    private float[] array;
    private int startIndex;

    public ArrayCountThread(float[] array, int startIndex) {
        this.array =array;
        this.startIndex = startIndex;
        start();
    }

    public float[] getArray() {
        return array;
    }

    @Override
    public void run() {
        for (int i = 0; i < array.length; i++) {
            array[i] = (float) (array[i] * Math.sin(0.2f + (i + startIndex) / 5) * Math.cos(0.2f + (i + startIndex) / 5) * Math.cos(0.4f + (i + startIndex) / 2));
        }
    }
}
