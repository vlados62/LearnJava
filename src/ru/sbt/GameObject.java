package ru.sbt;

public class GameObject {
    private int x;
    private int y;
    private boolean isCell;
    private int countCellNeighbors;
    private boolean deathNextStep;
    private boolean liveNextStep;

    public GameObject(int x, int y, boolean isCell) {
        this.x = x;
        this.y = y;
        this.isCell = isCell;
    }

    public boolean isLiveNextStep() {
        return liveNextStep;
    }

    public void setLiveNextStep(boolean liveNextStep) {
        this.liveNextStep = liveNextStep;
    }

    public boolean isCell() {
        return isCell;
    }

    public void setCell(boolean cell) {
        this.isCell = cell;
    }

    public boolean isDeathNextStep() {
        return this.deathNextStep;
    }

    public void setDeathNextStep(boolean deathNextStep) {
        this.deathNextStep = deathNextStep;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setCountCellNeighbors() {
        this.countCellNeighbors++;
    }

    public int getCountCellNeighbors() {
        return this.countCellNeighbors;
    }

    public void setCountCellNeighborsZero() {
        this.countCellNeighbors = 0;
    }
}
