package pl.adeks.simplegame.math;

import pl.adeks.simplegame.map.location.Location;

public class Vector implements Cloneable {

    private double x;
    private double y;
    private double z;

    private final double PI = Math.PI;

    public Vector(final double x, final double y, final double z) {
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

    public Vector addVector(final Vector vector) {
        this.x += vector.getX();
        this.y += vector.getY();
        this.z += vector.getZ();
        return this;
    }

    public double fastSin(final double x) {
        return this.fastCos(x - this.PI / 2);
    }
    public double fastCos(double x) { // Native functions like Math.cos, Math.sin are slow
        if(x < 0) x = -x;
        x = (x + this.PI / 2) % (2 * this.PI) - this.PI / 2;
        int scal = 1;
        if(x > this.PI / 2) {
            scal = -1;
            x = this.PI - x;
        }
        return scal * (1- x*x/(2) + x*x*x*x/(2 * 3 * 4) - x*x*x*x*x*x/(2 * 3 * 4 * 5 * 6));
    }

    public Vector rotate(final double roll, final double pitch, final double yaw) {
        final double oldX = this.x;
        final double oldY = this.y;
        final double oldZ = this.z;

        final double cosPitch = Math.cos(pitch);
        final double sinPitch = Math.sin(pitch);
        final double cosYaw = Math.cos(yaw);
        final double sinYaw = Math.sin(yaw);
        final double cosRoll = Math.cos(roll);
        final double sinRoll = Math.sin(roll);

        this.x = oldX * cosPitch * cosYaw - oldY * cosPitch * sinYaw + oldZ * sinPitch;
        this.y = oldX * (sinRoll * sinPitch * cosYaw + cosRoll * sinYaw) +
                oldY * (cosRoll * cosYaw - sinRoll * sinPitch * sinYaw) -
                oldZ * sinRoll * cosPitch;
        this.z = oldX * (sinRoll * sinYaw - cosRoll * sinPitch * cosYaw) +
                oldY * (cosRoll * sinPitch * sinYaw + sinRoll * cosYaw) +
                oldZ * cosRoll * cosPitch;
        return this;
    }

    public Vector setDirection(final double pitch, final double yaw) {

        //this.x = Math.sin(pitch) * Math.cos(yaw);
        //this.y = Math.sin(pitch) * Math.sin(yaw);
        //this.z = Math.cos(pitch);
        //to fix
        final double fastSin = this.fastSin(pitch);
        this.x = fastSin * this.fastCos(yaw);
        this.y = fastSin * this.fastSin(yaw);
        this.z = this.fastCos(pitch);
        return this;
    }

    public Vector multiply(final double val) {
        this.x *= val;
        this.y *= val;
        this.z *= val;
        return this;
    }
    public Vector normalize() {
        final double normalisedValue = Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2) + Math.pow(this.z, 2));
        this.x = this.x / normalisedValue;
        this.y = this.y / normalisedValue;
        this.z = this.z / normalisedValue;
        return this;
    }

    @Override
    public Vector clone() {
        try {
            return (Vector) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public boolean equals(final Object obj) {
        if(!(obj instanceof final Vector vector)) return false;
        return this.x == vector.x && this.y == vector.y && this.z == vector.z;
    }

    @Override
    public String toString() {
        return "Vector{" +
                "x=" + this.x +
                ", y=" + this.y +
                ", z=" + this.z +
                '}';
    }
}
