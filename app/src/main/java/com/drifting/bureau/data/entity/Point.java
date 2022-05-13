package com.drifting.bureau.data.entity;

public class Point {
    private int x;
    private int y;
    private double width;
    private double height;

    public Point(int x, int y,double width,double height) {
        this.x = x;
        this.y = y;
        this.width=width;
        this.height=height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
