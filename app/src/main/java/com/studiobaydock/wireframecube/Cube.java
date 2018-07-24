package com.studiobaydock.thecube;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;

@SuppressWarnings("unused")
public class Cube extends View{
    private float rotationX = 0;
    private float rotationY = 0;
    private float rotationZ = 0;
    private Paint paint = new Paint();
    private Vector3D[] cubePoints = new Vector3D[8];
    private Vector3D[] start = new Vector3D[]{
            new Vector3D(1,1,1)/*1*/,
            new Vector3D(1,1,-1)/*2*/,
            new Vector3D(1,-1,1)/*3*/,
            new Vector3D(1,-1,-1)/*4*/,
            new Vector3D(-1,1,1)/*5*/,
            new Vector3D(-1,1,-1)/*6*/,
            new Vector3D(-1,-1,1)/*7*/,
            new Vector3D(-1,-1,-1)/*8*/};
    private int[][] connections = new int[][]{
            /*1 to*/{2,3,5},
            /*2 to*/{4,6},
            /*3 to*/{4,7},
            /*4 to*/{8},
            /*5 to*/{6,7},
            /*6 to*/{8},
            /*7 to*/{8}};

    public Cube(Context context){
        super(context);
        paint.setColor(0xFF000000);
        paint.setStrokeWidth(5f);
    }

    public Cube(Context context, AttributeSet attrs){
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.Cube, 0, 0);
        try {
            rotationX = a.getFloat(R.styleable.Cube_rotationX, 0);
            rotationY = a.getFloat(R.styleable.Cube_rotationY, 0);
            rotationZ = a.getFloat(R.styleable.Cube_rotationZ, 0);
            paint.setStrokeWidth(a.getDimension(R.styleable.Cube_wireThickness, 5f));
            paint.setColor(a.getColor(R.styleable.Cube_wireColor, 0xFF000000));
        }
        finally {
            a.recycle();
        }
    }

    protected void onDraw(Canvas canvas){
        for(int x=0; x<8; x++){
            cubePoints[x] = start[x].rotateX(rotationX).rotateY(rotationY).rotateZ(rotationZ);
            cubePoints[x] = cubePoints[x].project(getWidth(), getHeight(), getWidth()<getHeight()?getWidth():getHeight(), 4);
        }
        for(int x=0; x<7; x++){
            for(int y=0; y<connections[x].length; y++){
                int other = connections[x][y]-1;
                canvas.drawLine(cubePoints[x].x, cubePoints[x].y, cubePoints[other].x, cubePoints[other].y, paint);
            }
        }
        for(int x=0; x<8; x++){
            canvas.drawCircle(cubePoints[x].x, cubePoints[x].y, paint.getStrokeWidth()/2, paint);
        }
    }

    public float getRotationX(){
        return rotationX;
    }

    public float getRotationY(){
        return rotationY;
    }

    public float getRotationZ(){
        return rotationZ;
    }

    public void setRotation(float xd, float yd, float zd){
        rotationX = xd%360;
        rotationY = yd%360;
        rotationZ = zd%360;
        postInvalidate();
    }

    public void setRotationXY(float xd, float yd){
        setRotation(xd, yd, rotationZ);
    }

    public void setRotationYZ(float yd, float zd){
        setRotation(rotationX, yd, zd);
    }

    public void setRotationZX(float zd, float xd){
        setRotation(xd, rotationY, zd);
    }

    public void setRotationX(float xd){
        setRotation(xd, rotationY, rotationZ);
    }

    public void setRotationY(float yd){
        setRotation(rotationX, yd, rotationZ);
    }

    public void setRotationZ(float zd){
        setRotation(rotationX, rotationY, zd);
    }

    @ColorInt
    public int getWireColor(){
        return paint.getColor();
    }

    public void setWireColor(@ColorInt int color){
        paint.setColor(color);
        postInvalidate();
    }

    public float getWireThickness(){
        return paint.getStrokeWidth();
    }

    public void setWireThickness(float thickness){
        paint.setStrokeWidth(thickness);
        postInvalidate();
    }
}
