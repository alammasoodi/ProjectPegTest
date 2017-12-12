package com.example.alam.pegtest;

/**
 * Created by alam on 11/12/17.
 */

public class FingerTouch {
    public final static int DPAD_FINGER = 0;
    public final static int SCREEN_FINGER = 1;
    public float x, y;
    public int type;
    public int id;

    public FingerTouch(float x, float y,int id) {
        this.id = id;
        checkType(x, y);
    }

    public void checkType(float x, float y) {

//        if(x>Dpad.x && x < Dpad.x+Dpad.Width && y> Dpad.y && y<Dpad.y+Dpad.Height){
//            System.out.println("inside DPAD");
//            type = DPAD_FINGER;
//        }else{
            System.out.println("Outside DPAD");
            type = SCREEN_FINGER;
       // }

    }

    public void setPos(float x, float y){
        this.x = x;
        this.y = y;
    }



}