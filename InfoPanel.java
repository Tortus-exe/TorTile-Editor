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

public class InfoPanel extends JPanel {
	private int currentSpriteNumber;
	Editor editor;

	InfoPanel(Editor ed) {
		setLayout(new GridLayout(-1, 4));
		currentSpriteNumber = 0;
		this.editor = ed;
		JLabel currentSpriteLabel = new JLabel("Current Sprite Number: " + currentSpriteNumber);
		JButton nextButton = new JButton(">");
		JButton prevButton = new JButton("<");
		JButton exportButton = new JButton("EXPORT");
		JButton reloadButton = new JButton("RELOAD");
		JButton flipX = new JButton("FlipX");
		JButton flipY = new JButton("FlipY");
		JButton priority = new JButton("P");
		nextButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
               nextButton.setBorderPainted(true); 
               nextButton.setContentAreaFilled(false);
            }

            public void mouseExited(MouseEvent evt) {
                nextButton.setBorderPainted(false);
                nextButton.setContentAreaFilled(false);
            }

            public void mouseClicked(MouseEvent evt) {
                ed.nextSprite();
                currentSpriteNumber = ed.getCurrentSpriteNum();
				currentSpriteLabel.setText("Current Sprite Number: " + currentSpriteNumber);
            }
        });
        prevButton.addMouseListener(new MouseAdapter() {
        	public void mouseEntered(MouseEvent evt) {
        		prevButton.setBorderPainted(true);
        		prevButton.setContentAreaFilled(false);
        	}

        	public void mouseExited(MouseEvent evt) {
        		prevButton.setBorderPainted(false);
        		prevButton.setContentAreaFilled(false);
        	}

        	public void mouseClicked(MouseEvent evt) {
        		ed.prevSprite();
				currentSpriteNumber = ed.getCurrentSpriteNum();
				currentSpriteLabel.setText("Current Sprite Number: " + currentSpriteNumber);
			}
        });
        exportButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
               exportButton.setBorderPainted(true); 
               exportButton.setContentAreaFilled(false);
            }

            public void mouseExited(MouseEvent evt) {
                exportButton.setBorderPainted(false);
                exportButton.setContentAreaFilled(false);
            }

            public void mouseClicked(MouseEvent evt) {
                ed.triggerExport();
            }
        });
        reloadButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
               reloadButton.setBorderPainted(true); 
               reloadButton.setContentAreaFilled(false);
            }

            public void mouseExited(MouseEvent evt) {
                reloadButton.setBorderPainted(false);
                reloadButton.setContentAreaFilled(false);
            }

            public void mouseClicked(MouseEvent evt) {
                ed.reload();
            }
        });
        flipY.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
               flipY.setBorderPainted(true); 
               flipY.setContentAreaFilled(false);
            }

            public void mouseExited(MouseEvent evt) {
                flipY.setBorderPainted(false);
                flipY.setContentAreaFilled(false);
            }

            public void mouseClicked(MouseEvent evt) {
                ed.FLIPY();
            }
        });
        flipX.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
               flipX.setBorderPainted(true); 
               flipX.setContentAreaFilled(false);
            }

            public void mouseExited(MouseEvent evt) {
                flipX.setBorderPainted(false);
                flipX.setContentAreaFilled(false);
            }

            public void mouseClicked(MouseEvent evt) {
                ed.FLIPX();
            }
        });
        priority.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
               priority.setBorderPainted(true); 
               priority.setContentAreaFilled(false);
            }

            public void mouseExited(MouseEvent evt) {
                priority.setBorderPainted(false);
                priority.setContentAreaFilled(false);
            }

            public void mouseClicked(MouseEvent evt) {
                ed.PRIORITY();
            }
        });
		add(currentSpriteLabel);
        add(prevButton);
        add(nextButton);
        add(exportButton);
        add(reloadButton);
        add(flipY);
        add(flipX);
        add(priority);
	}
}