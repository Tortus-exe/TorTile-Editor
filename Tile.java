import java.awt.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.AffineTransformOp;
import java.awt.geom.AffineTransform;
import java.awt.event.*; 

import java.lang.Math;
import java.util.ArrayList;
import javax.swing.*;    
import java.io.*;

public class Tile {
    //a tile class which we will use to export the file when we're done
    public int x;
    public int y;
    public int id;
    public int palette;
    public boolean priority; //true = 1 = behind background
    public boolean xFlip;
    public boolean yFlip;
    
    private int index;
    private static int globalIndex;

    //constructor
    Tile (int x, int y, int id, int palette) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.palette = palette;
        this.priority = false;
        this.xFlip = false;
        this.yFlip = false;
        this.index = ++globalIndex;
    }

    public String toString() {
        return Integer.toString(this.id);
    }

    public void flipY() {
        this.yFlip = !this.yFlip;
    }

    public void flipX() {
        this.xFlip = !this.xFlip;
    }

    public int getFlipX() {
        return (this.xFlip ? -1 : 1);
    }

    public int getFlipY() {
        return (this.yFlip ? -1 : 1);
    }

    public int getPriority() {
        return (this.priority ? 0 : 1);
    }
    
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void togglePriority() {
        this.priority = !this.priority;
    }

    public int getID() {
        return this.id;
    }

    public int getPalette() {
        return this.palette;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }
}