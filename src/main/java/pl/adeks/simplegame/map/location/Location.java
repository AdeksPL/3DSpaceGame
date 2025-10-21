package pl.adeks.simplegame.map.location;

import java.util.Objects;

public class Location {
    private int x;
    private int y;
    private int z;

    public Location(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }
    public void setX(final int x) {
        this.x = x;
    }
    public void setY(final int y) {
        this.y = y;
    }
    public void setZ(final int z) {
        this.z = z;
    }
    public void addX(final int x) {
        this.x = this.x + x;
    }
    public void addY(final int y) {
        this.y = this.y + y;
    }
    public void addZ(final int z) {
        this.z = this.z + z;
    }

    @Override
    public boolean equals(final Object obj) {
        if(!(obj instanceof final Location location)) return false;
        return this.x == location.x && this.y == location.y && this.z == location.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y, this.z);
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + this.x +
                ", y=" + this.y +
                ", z=" + this.z +
                '}';
    }
}
