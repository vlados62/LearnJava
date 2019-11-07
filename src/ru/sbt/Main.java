package ru.sbt;

import ru.sbt.GameOfLife;

import java.io.*;


public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        GameOfLife game = new GameOfLife(19, 19);
        game.initialize();
        System.out.println("Введите фигуру(Glider, Small Exploder, Exploder, Random или 7Cell):");
        while (true) {
            if (game.setFigure(reader.readLine())) {
                break;
            }
        }
        game.startGame();
    }
}

