package com.gillinedup.graph;

public class CurrentDirection {
    private int currentNum;
    private boolean direction;
    private double weight;

    public CurrentDirection(int c, boolean d, double w) {
        this.currentNum = c;
        this.direction = d;
        this.weight = w;
    }

    public int getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(int currentNum) {
        this.currentNum = currentNum;
    }

    public boolean getDirection() {
        return direction;
    }

    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
