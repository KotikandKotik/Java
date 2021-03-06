package models;

import java.text.DecimalFormat;

public class Point {
     private Double x,y,z;

    public Point() {
    }

    public Point(Double x, Double y, Double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void printCoords(){
        System.out.println("["+new DecimalFormat("#0.00").format(x)
                +","+new DecimalFormat("#0.00").format(y)
                +","+new DecimalFormat("#0.00").format(z)+"]");
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getZ() {
        return z;
    }

    public void setZ(Double z) {
        this.z = z;
    }
}
