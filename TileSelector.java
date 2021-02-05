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

//the special class for selecting tiles
public class TileSelector extends JPanel{
    private ArrayList<Integer> allTiles;
    private ArrayList<Color> paletteList;
    public int selectedTile;
    public int buttonCount; 
    public int sPalette;
	public EditorDisplay EdDisp;

    TileSelector(ArrayList<Integer> at, ArrayList<Color> pl, int pb, EditorDisplay ed) {
        this.selectedTile = 0;
        this.allTiles = at;
        this.paletteList = pl;
        sPalette = pb;
		this.EdDisp = ed;
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        Container c = getParent();
        if (c != null) {
            d = c.getSize();
        } else {
            return new Dimension(10, 10);
        }
        int w = (int) d.getWidth();
        int h = (int) d.getHeight();
        int s = (w < h ? w : h);
        return new Dimension(s, s);
    }

	@Override
	public final boolean isOpaque() {
		return false;
	}

    @Override
    protected void paintComponent(Graphics g) {
        /*
        BufferedImage tileSelector = drawTileSelector();
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        //scale the image
        BufferedImage scaledTileSelector = getScaledImage(tileSelector, tileSelector.getWidth()*this.scale, tileSelector.getHeight()*this.scale);
        int x = (getWidth() - scaledTileSelector.getWidth()) / 2;
        int y = (getHeight() - scaledTileSelector.getHeight()) / 2;
        g2d.drawImage(scaledTileSelector, x, y, this);
        g2d.dispose();
        */

        setLayout(new GridLayout(16, 16));
        while(buttonCount < 256) {
            BufferedImage tileIcon = getTileImage(buttonCount, sPalette);
            JButton button = new JButton();//new ImageIcon(tileIcon));
            button.setBounds((buttonCount%16)*8, (buttonCount/16)*8, 8, 8);
			button.setBackground(Color.white);
            //button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(true);
            button.setName(String.valueOf(buttonCount));
            button.addMouseListener(new MouseAdapter() {
                /*
				public void mouseEntered(MouseEvent evt) {
                   button.setBorderPainted(true); button.setContentAreaFilled(false);
                }

                public void mouseExited(MouseEvent evt) {
                    button.setBorderPainted(false);
                    button.setContentAreaFilled(false);
                }
				*/

                public void mouseClicked(MouseEvent evt) {
                    selectedTile = Integer.parseInt(button.getName());
					button.setBackground(Color.gray);
					EdDisp.setLabel(Integer.parseInt(button.getName()));
                }
            });
            button.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    JButton btn = (JButton) e.getComponent();
                    Dimension size = btn.getSize();
                    if (size.width > size.height) {
                        size.width = size.height;
                    } else {
                        size.height = size.width;
                    }
                    Image scaled = getScaledImage(tileIcon, size.width, size.height);
                    btn.setIcon(new ImageIcon(scaled));
                }
            });
            add(button); 
            buttonCount++;
        }
		repaint();
    }

    public BufferedImage getTileImage (int tile, int selectedPalette) {
        BufferedImage bufferedImage = new BufferedImage(8,8,BufferedImage.TYPE_INT_ARGB);
        Graphics g = bufferedImage.getGraphics();
        for(int column = 0; column < 8; column++) {
            for(int row = 0; row < 8; row++) {
                g.setColor(paletteList.get(allTiles.get((64*tile) + (row) + column*8) + (4*selectedPalette)));
                if(allTiles.get((64*tile) + (row) + column*8) + (4*selectedPalette) %4 == 0) {
                    g.setColor(new Color(255, 0, 0, 0));
                } 
                g.fillRect(row, column, 1, 1);
            }
        }
        return bufferedImage;
    }

    public int getSelectedTile() {
        return selectedTile;
    }

    public BufferedImage getScaledImage(Image srcImg, int w, int h){

    //Create a new image with good size that contains or might contain arbitrary alpha values between and including 0.0 and 1.0.
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);

    //Create a device-independant object to draw the resized image
        Graphics2D g2 = resizedImg.createGraphics();

    //This could be changed, Cf. http://stackoverflow.com/documentation/java/5482/creating-images-programmatically/19498/specifying-image-rendering-quality
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

    //Finally draw the source image in the Graphics2D with the desired size.
        g2.drawImage(srcImg, 0, 0, w, h, null);

    //Disposes of this graphics context and releases any system resources that it is using
        g2.dispose();

    //Return the image used to create the Graphics2D 
        return resizedImg;
    }
}