package pl.adeks.simplegame.map.elements;

import pl.adeks.simplegame.map.elements.camera.Camera;
import pl.adeks.simplegame.map.location.DoubleLocation;
import pl.adeks.simplegame.math.Vector;

public class Player {

    private final DoubleLocation location;
    private final Camera camera;
    private final Vector standardVector;

    public Player(final DoubleLocation location, final Camera camera) {
        this.location = location;
        this.camera = camera;
        this.standardVector = new Vector(0, 0, 0.5);
    }

    public DoubleLocation getLocation() {
        return this.location;
    }

    public Camera getCamera() {
        return this.camera;
    }

    public void move(final double speed) {
        this.location.move(this.camera.getLookingDirection().clone().multiply(speed));
    }

    public void moveUpDown(final double speed) {
        this.location.move(this.standardVector.clone().multiply(-speed));
    }

    @Override
    public String toString() {
        return "Player{" +
                "location=" + this.location +
                ", camera=" + this.camera +
                ", standardVector=" + this.standardVector +
                '}';
    }

}
