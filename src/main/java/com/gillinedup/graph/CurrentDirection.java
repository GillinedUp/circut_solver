package com.gillinedup.graph;

public class CurrentDirection {
    private int currentNum;
    private double weight;

    CurrentDirection(int c, double w) {
        this.currentNum = c;
        this.weight = w;
    }

    public int getCurrentNum() {
        return currentNum;
    }

    double getWeight() {
        return weight;
    }
}
