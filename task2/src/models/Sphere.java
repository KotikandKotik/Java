package models;

public class Sphere {
    Point center = new Point();
    Double r;

    public Sphere() {
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public Double getR() {
        return r;
    }

    public void setR(Double r) {
        this.r = r;
    }

    public Sphere(Point center, Double r) {
        this.center = center;
        this.r = r;
    }
}
