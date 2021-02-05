//GO THROUGH THIS ENTIRE THING

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


public class PaletteBar extends JPanel{
    public ArrayList<Color> paletteArray;
    public int selectedPaletteBlock;
    private int buttonCount;

    PaletteBar(ArrayList<Color> pa) {
        this.paletteArray = pa;
        setPreferredSize(new Dimension(128, 64));
    }

    @Override
    protected void paintComponent(Graphics g) {
        setLayout(new GridLayout(2, 2));
        while(buttonCount < 4) {
            BufferedImage PaletteIcon = getPaletteBlock(buttonCount);
            JButton palette = new JButton();
            palette.setName(String.valueOf(buttonCount));
            palette.setBounds(buttonCount*4, 0, 8, 32);
            //button.setOpaque(false);
            //palette.setContentAreaFilled(false);
            palette.setBorderPainted(false);
            palette.setIcon(new StretchIcon(PaletteIcon));
            palette.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                   palette.setBorderPainted(true); 
                   //palette.setContentAreaFilled(false);
                }

                public void mouseExited(MouseEvent evt) {
                    palette.setBorderPainted(false);
                    //palette.setContentAreaFilled(false);
                }

                public void mouseClicked(MouseEvent evt) {
                    selectedPaletteBlock = Integer.parseInt(palette.getName());
                }
            });
            palette.setBounds(buttonCount*4, 0, 8, 32);
            add(palette); 
            buttonCount++;
        }
    }

    private BufferedImage getPaletteBlock(int block) {
        BufferedImage bufferedImage = new BufferedImage(4, 1, BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.getGraphics();
        for(int colour = 0; colour < 4; colour++) {
            g.setColor(paletteArray.get(colour + (4*block)));
            g.fillRect(colour, 0, 1, 1);
        }
        return bufferedImage;
    }

    public int getSelectedPalette() {
        return selectedPaletteBlock;
    }

    private BufferedImage getScaledImage(Image srcImg, int w, int h){

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