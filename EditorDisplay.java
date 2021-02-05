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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.EventQueue;
import javax.swing.Action;

public class EditorDisplay extends JPanel {
	//display selected tile, 
	
	JLabel CurrentSelectedTileLabel; 
	
	EditorDisplay (int tileSelection) {
		CurrentSelectedTileLabel = new JLabel("Current Selected Tile: " + tileSelection);
		System.out.println(tileSelection);
		add(CurrentSelectedTileLabel);
	}
	
	public void setLabel(int tS) {
		CurrentSelectedTileLabel.setText("Current Selected Tile: " + tS);
	}
}