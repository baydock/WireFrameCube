package com.studiobaydock.thecube;

public class Vector3D {
    protected float x;
    protected float y;
    protected float z;

    public Vector3D() {
        x = y = z = 0;
    }

    public Vector3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D rotateX(float angle) {
        float rad, cosa, sina, yn, zn;

        rad = angle * (float)Math.PI / 180;
        cosa = (float)Math.cos(rad);
        sina = (float)Math.sin(rad);
        yn = this.y * cosa - this.z * sina;
        zn = this.y * sina + this.z * cosa;

        return new Vector3D(this.x, yn, zn);
    }

    public Vector3D rotateY(float angle) {
        float rad, cosa, sina, xn, zn;

        rad = angle * (float)Math.PI / 180;
        cosa = (float)Math.cos(rad);
        sina = (float)Math.sin(rad);
        zn = this.z * cosa - this.x * sina;
        xn = this.z * sina + this.x * cosa;

        return new Vector3D(xn, this.y, zn);
    }

    public Vector3D rotateZ(float angle) {
        float rad, cosa, sina, xn, yn;

        rad = angle * (float)Math.PI / 180;
        cosa = (float)Math.cos(rad);
        sina = (float)Math.sin(rad);
        xn = this.x * cosa - this.y * sina;
        yn = this.x * sina + this.y * cosa;

        return new Vector3D(xn, yn, this.z);
    }

    public Vector3D project(int viewWidth, int viewHeight, float fov, float viewDistance) {
        float factor, xn, yn;

        factor = fov / (viewDistance + this.z);
        xn = this.x * factor + viewWidth / 2;
        yn = this.y * factor + viewHeight / 2;

        return new Vector3D(xn, yn, this.z);
    }
}