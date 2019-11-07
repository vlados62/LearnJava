package ru.sbt;

import java.util.ArrayList;
import java.util.List;

public class GameOfLife extends GameField {
    private final int x;
    private final int y;
    private int countCell;
    private final GameObject[][] gameObjects;
    private List<Integer> src = new ArrayList<>();

    public GameOfLife(int x, int y) {
        this.x = x;
        this.y = y;
        gameObjects = new GameObject[x][y];
    }

    public void initialize() {
        setFieldSize(x, y);
        createGame();
    }

    private int sumSrc(int index, int result) {
        return (result + index + 1) * 113 % 10000007;
    }

    private void searchWindowClone(int res) {
        for (Integer window : src) {
            if (res == window) {
                this.countCell = 0;
            }
        }
        src.add(res);
    }

    public void startGame() {
        while (this.countCell != 0) {
            this.countCell = 0;
            int result = 0;
            for (int i = 0; i < y; i++) {
                for (int j = 0; j < x; j++) {
                    if (gameObjects[i][j].isCell()) {
                        this.countCell++;
                        setCellValue(i, j, "*");
                        result += sumSrc(i, result);
                        result += sumSrc(j, result);
                    }
                }
            }
            printGameField();
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countCellNeighbors();
            for (int i = 0; i < y; i++) {
                for (int j = 0; j < x; j++) {
                    if (gameObjects[i][j].isDeathNextStep()) {
                        this.countCell--;
                        gameObjects[i][j].setDeathNextStep(false);
                        gameObjects[i][j].setCell(false);
                        setCellValue(i, j, ".");
                    }
                    if (gameObjects[i][j].isLiveNextStep()) {
                        this.countCell++;
                        gameObjects[i][j].setLiveNextStep(false);
                        gameObjects[i][j].setCell(true);
                        setCellValue(i, j, "*");
                    }
                }
            }
            searchWindowClone(result);
        }
        System.out.println("Конец игры!");
    }

    public boolean setFigure(String figure) {
        if ("Square".equals(figure)) {
            gameObjects[7][7].setCell(true);
            gameObjects[7][8].setCell(true);
            gameObjects[8][7].setCell(true);
            gameObjects[8][8].setCell(true);
            this.countCell = 4;
            countCellNeighbors();

        } else if ("7Cell".equals(figure)) {
            gameObjects[9][6].setCell(true);
            gameObjects[9][7].setCell(true);
            gameObjects[9][8].setCell(true);
            gameObjects[9][9].setCell(true);
            gameObjects[9][10].setCell(true);
            gameObjects[9][11].setCell(true);
            gameObjects[9][12].setCell(true);
            this.countCell = 7;
            countCellNeighbors();
        } else if ("Small Exploder".equals(figure)) {
            gameObjects[7][8].setCell(true);
            gameObjects[8][7].setCell(true);
            gameObjects[8][8].setCell(true);
            gameObjects[8][9].setCell(true);
            gameObjects[9][7].setCell(true);
            gameObjects[9][9].setCell(true);
            gameObjects[10][8].setCell(true);
            this.countCell = 7;
            countCellNeighbors();
        } else if ("Random".equals(figure)) {
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    boolean cell = getRandomNumber(10) == 0;
                    if (cell) this.countCell++;
                    gameObjects[i][j].setCell(cell);
                }
            }
            countCellNeighbors();
        } else if ("Glider".equals(figure)) {
            gameObjects[4][4].setCell(true);
            gameObjects[5][5].setCell(true);
            gameObjects[6][5].setCell(true);
            gameObjects[6][4].setCell(true);
            gameObjects[6][3].setCell(true);
            this.countCell = 5;
            countCellNeighbors();
        } else if ("Exploder".equals(figure)) {
            gameObjects[7][7].setCell(true);
            gameObjects[8][7].setCell(true);
            gameObjects[9][7].setCell(true);
            gameObjects[10][7].setCell(true);
            gameObjects[11][7].setCell(true);
            gameObjects[7][9].setCell(true);
            gameObjects[7][11].setCell(true);
            gameObjects[8][11].setCell(true);
            gameObjects[9][11].setCell(true);
            gameObjects[10][11].setCell(true);
            gameObjects[11][11].setCell(true);
            gameObjects[11][9].setCell(true);
            this.countCell = 5;
            countCellNeighbors();
        } else {
            System.out.println("Неверно введена фигура!");
            return false;
        }
        return true;
    }

    private void createGame() {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                gameObjects[i][j] = new GameObject(i, j, false);
            }
        }
    }


    private void countCellNeighbors() {
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                List<GameObject> list = new ArrayList<>(getNeighbors(this.gameObjects[i][j]));
                this.gameObjects[i][j].setCountCellNeighborsZero();
                for (GameObject object : list) {
                    if (object.isCell()) {
                        this.gameObjects[i][j].setCountCellNeighbors();
                    }
                }
                /**
                 * Если живая клетка
                 */
                if (gameObjects[i][j].isCell()) {
                    if (this.gameObjects[i][j].getCountCellNeighbors() <= 1 || this.gameObjects[i][j].getCountCellNeighbors() > 3) {
                        this.gameObjects[i][j].setDeathNextStep(true);
                    }
                } else {
                    if (this.gameObjects[i][j].getCountCellNeighbors() == 3) {
                        this.gameObjects[i][j].setLiveNextStep(true);
                    }
                }
            }
        }
    }

    private List<GameObject> getNeighbors(GameObject gameObject) {
        List<GameObject> result = new ArrayList<GameObject>();
        //Верх
        try {
            result.add(this.gameObjects[gameObject.getX() - 1][gameObject.getY() - 1]);
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            result.add(this.gameObjects[gameObject.getX() - 1][gameObject.getY()]);
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            result.add(this.gameObjects[gameObject.getX() - 1][gameObject.getY() + 1]);
        } catch (IndexOutOfBoundsException e) {
        }
        //Середина
        try {
            result.add(this.gameObjects[gameObject.getX()][gameObject.getY() + 1]);
        } catch (IndexOutOfBoundsException e) {
        }
        //Низ
        try {
            result.add(this.gameObjects[gameObject.getX() + 1][gameObject.getY() + 1]);
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            result.add(this.gameObjects[gameObject.getX() + 1][gameObject.getY()]);
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            result.add(this.gameObjects[gameObject.getX() + 1][gameObject.getY() - 1]);
        } catch (IndexOutOfBoundsException e) {
        }
        //Середина
        try {
            result.add(this.gameObjects[gameObject.getX()][gameObject.getY() - 1]);
        } catch (IndexOutOfBoundsException e) {
        }

        return result;
    }
}
