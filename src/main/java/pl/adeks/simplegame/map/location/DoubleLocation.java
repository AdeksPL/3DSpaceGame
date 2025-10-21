package pl.adeks.simplegame.map.location;

import pl.adeks.simplegame.math.Vector;

public class DoubleLocation {
    private double x;
    private double y;
    private double z;

    public DoubleLocation(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public Location parseToLocation() {
        return new Location((int)Math.floor(this.x), (int)Math.floor(this.y), (int)Math.floor(this.z));
    }

    public void move(final Vector moveVector) {
        this.x += moveVector.getX();
        this.y += moveVector.getY();
        this.z += moveVector.getZ();
    }

    @Override
    public String toString() {
        return "DoubleLocation{" +
                "x=" + this.x +
                ", y=" + this.y +
                ", z=" + this.z +
                '}';
    }
}
