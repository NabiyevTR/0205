package ru.geekbrains.ntr_0205;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Timer {
    private float[] array;
    private int arraySize;
    private List<ArrayCountThread> threadList;

    public Timer(float[] array) {
        this.array = Arrays.copyOf(array, array.length);
        arraySize = array.length;

    }

    public void setArray(float[] array) {
        this.array = Arrays.copyOf(array, array.length);
        arraySize = array.length;
    }

    public float[] getArray() {
        return Arrays.copyOf(array, array.length);
    }

    public void countOneThread() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < array.length; i++) {
            array[i] = (float) (array[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.printf("Количество потоков: 1. Время выполнения: %dмс.\n",
                System.currentTimeMillis() - startTime);
    }

    public void countMultiThread(int threadsAmount) throws NumberFormatException {
        if (threadsAmount == 1) {
            countOneThread();
            return;
        }
        if (threadsAmount <= 0) throw new NumberFormatException("Количество потоков должно быть больше нуля");
        if (threadsAmount > array.length)
            throw new NumberFormatException("Количество потоков не должно быть больше размера массива");

        threadList = new ArrayList<>((int) (threadsAmount * 1.5));

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < threadsAmount; i++) {
            int partSize = i == threadsAmount - 1 ? arraySize - (arraySize / threadsAmount) * (threadsAmount - 1) : arraySize / threadsAmount;
            float[] partArray = new float[partSize];
            System.arraycopy(array, i * (arraySize / threadsAmount), partArray, 0, partSize);
            threadList.add(new ArrayCountThread(partArray, i * (arraySize / threadsAmount)));
        }

        for (ArrayCountThread thread : threadList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int lastIndex = 0;
        for (ArrayCountThread thread : threadList) {
            System.arraycopy(thread.getArray(), 0, array, lastIndex, thread.getArray().length);
            lastIndex += thread.getArray().length;
        }

        System.out.printf("Количество потоков: %d. Время выполнения: %dмс.\n",
                threadsAmount,
                System.currentTimeMillis() - startTime);

    }
}
