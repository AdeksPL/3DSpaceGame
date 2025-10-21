package pl.adeks.simplegame.map.elements.camera;

import pl.adeks.simplegame.math.Vector;

public class Camera {

    private double cameraYaw = 0;
    public double cameraRelativePitch = 0;

    private final double PI = Math.PI;

    private double clamp(final double value, final double min, final double max) {
        return Math.max(Math.min(max, value), min);
    }

    public void naturalRotate(final double pitch, final double yaw) {
        this.relativeRotate(pitch, yaw);
        this.cameraRelativePitch = this.clamp(this.cameraRelativePitch, -this.PI / 2., this.PI / 2.);
    }

    private void relativeRotate(final double pitch, final double yaw) {
        this.cameraYaw += yaw;
        this.cameraRelativePitch += pitch;
    }


    public Vector getPlaneX() {
        return new Vector(0, 0, 0).setDirection(Math.PI / 2, this.cameraYaw).multiply(0.66);
    }

    public Vector getPlaneZ() {
        return new Vector(0, 0, 0).setDirection(this.cameraRelativePitch, this.cameraYaw + Math.PI / 2).multiply(0.33);
    }

    public Vector getDirection() {
        return new Vector(0, 0, 0).setDirection(this.cameraRelativePitch + Math.PI / 2 , this.cameraYaw + Math.PI / 2);
    }

    public Vector getLookingDirection() {
        return new Vector(0, 0, 0).setDirection(Math.PI / 2, this.cameraYaw + Math.PI / 2).normalize();
    }

    @Override
    public String toString() {
        return "Camera{" +
                "cameraYaw=" + this.cameraYaw +
                ", cameraRelativePitch=" + this.cameraRelativePitch +
                '}';
    }
}
