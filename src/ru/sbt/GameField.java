package ru.sbt;

import java.util.Random;

abstract public class GameField {
    private int height;
    private int width;
    private String[][] gameField;
    private static Random random = new Random();

    abstract void initialize();

    public void setCellValue(int x, int y, String value) {
        this.gameField[x][y] = value;
    }

    public void setFieldSize(int width, int height) {
        this.height = height;
        this.width = width;
        this.gameField = new String[height][width];
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                this.gameField[x][y] = ".";
            }
        }
    }

    public void printGameField() {
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                System.out.print(this.gameField[x][y] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public int getRandomNumber(int num) {
        return random.nextInt(num);
    }
}
