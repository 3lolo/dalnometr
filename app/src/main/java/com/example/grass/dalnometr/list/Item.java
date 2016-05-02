package com.example.grass.dalnometr.list;

/**
 * Created by Grass on 29.04.2016.
 */
public class Item {
    Double height;
    Double meger;
    Double angle;
    Double uMerge;
    Double error;

    public Item(Double height, Double meger, Double angle, Double uMerge, Double error) {
        this.height = height;
        this.meger = meger;
        this.angle = angle;
        this.uMerge = uMerge;
        this.error = error;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getMeger() {
        return meger;
    }

    public void setMeger(Double meger) {
        this.meger = meger;
    }

    public Double getAngle() {
        return angle;
    }

    public void setAngle(Double angle) {
        this.angle = angle;
    }

    public Double getuMerge() {
        return uMerge;
    }

    public void setuMerge(Double uMerge) {
        this.uMerge = uMerge;
    }

    public Double getError() {
        return error;
    }

    public void setError(Double error) {
        this.error = error;
    }
}
