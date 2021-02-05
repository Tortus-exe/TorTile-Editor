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

public class Editor extends JPanel {
    private ArrayList<ArrayList<Tile>> spriteList;
    public int currentSprite = 0;
    private ArrayList<Tile> tileList;
    private PaletteBar paletteSelector;
    private TileSelector tileSelector;

    private boolean actuallyClicked = false;
    private double x;
    private double y;
    private int widthComponent;
    private int heightComponent;
    private boolean drag = false;
    //private double zoom;
    private String outputDirectory;
    private String fileName;

    public int size;

    Editor(int selectedTile, PaletteBar ps, TileSelector tsO, String oD, String fn, int s) {
        //zoom = 0;
        size = s;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(SwingUtilities.isLeftMouseButton(e)) {
                    widthComponent = getWidth();
                    heightComponent = getHeight();
                    x = ((double) e.getX())*size/widthComponent;
                    y = ((double) e.getY())*size/heightComponent;
                    paintComponent(getGraphics());
                    actuallyClicked = true;
                    drag = true;
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    tileList.remove(tileList.size()-1);
                    paintComponent(getGraphics());
                }
                repaint();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                drag = false;
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(drag) {
                    tileList.get(tileList.size()-1).setLocation((e.getX())*size/widthComponent, (e.getY())*size/heightComponent);
                    repaint();
                }
            }
        });
        /*
        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getPreciseWheelRotation() < 0) {
                    zoom -= 0.1;
                } else {
                    zoom += 0.1;
                }
//                  zoom += e.getPreciseWheelRotation();
                if (zoom < 0.01) {
                    zoom = 0.01;
                }
                repaint();
            }
        });
        */
		setBackground(Color.GRAY);
        this.spriteList = new ArrayList<ArrayList<Tile>>();
        this.paletteSelector = ps;
        this.tileSelector = tsO;
        this.tileList = new ArrayList<Tile>();
        this.spriteList.add(tileList);
        this.outputDirectory = oD;
        this.fileName = fn;
    }

    public void triggerExport() {
        try {
            export(outputDirectory, fileName);
        } catch (IOException m) {
            m.printStackTrace();
        }
        System.out.println("EXPORTING TO "+ outputDirectory + fileName);
    }

    public void nextSprite() {
        spriteList.set(currentSprite, tileList);
        System.out.println(spriteList.get(currentSprite));
        currentSprite++;
        if(currentSprite == spriteList.size()) {
            tileList = new ArrayList<Tile>();
            spriteList.add(tileList);
        } else {
            tileList = spriteList.get(currentSprite);
        }
        repaint();
    }
	
	public int getCurrentSpriteNum() {
		return currentSprite;
	}

    public boolean prevSprite() {
        System.out.println(currentSprite);
        spriteList.set(currentSprite, tileList);
        if(tileList.size() == 0 && currentSprite != 0) {
            spriteList.remove(currentSprite);
        }
        if(currentSprite  > 0) {
            currentSprite--;
            tileList = spriteList.get(currentSprite);
            repaint();
            return true;
        } else {
            currentSprite = 0;
            tileList = spriteList.get(0);
            repaint();
            return false;
        }
    }
	
	@Override
    public final boolean isOpaque() {
        return false;
    }

    public void reload() {
        repaint();
        System.out.println(tileList);
    }

    public void FLIPY() {
        tileList.get(tileList.size()-1).flipY();
        repaint();
    }

    public void FLIPX() {
        tileList.get(tileList.size()-1).flipX();
        repaint();
    }

    public void PRIORITY() {
        tileList.get(tileList.size()-1).togglePriority();
        repaint();
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
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		g2d.setColor(getBackground());
        widthComponent = getWidth();
        heightComponent = getHeight();
        /*
        double zoomWidth = widthComponent * zoom;
        double zoomHeight = heightComponent * zoom;

        double anchorx = (widthComponent - zoomWidth) / 2;
        double anchory = (heightComponent - zoomHeight) / 2;

        AffineTransform at = new AffineTransform();
        at.translate(anchorx, anchory);
        at.scale(zoom, zoom);
        at.translate(-widthComponent, -heightComponent);
        */
        
        double scale;
        int id;
        int pal;
        Tile tile;
        Image tileImage;
        String outputString = Integer.toString(widthComponent) + " " + Integer.toString(heightComponent);
        if(widthComponent > heightComponent) {
            scale = heightComponent/size;
        } else {
            scale = widthComponent/size;
        }
        id = tileSelector.getSelectedTile();
        pal = paletteSelector.getSelectedPalette();
        if(actuallyClicked) {
            tile = new Tile((int)Math.floor(x), (int)Math.floor(y), id, pal);
            tileList.add(tile);
            actuallyClicked = false;
            System.out.println("new tile added: " + tileList.size());
        }
        tileImage = tileSelector.getTileImage(id, pal);
        for(int b = 0; b < tileList.size(); b++) {
            int size = (int) (8*scale);
            int sizeX = size * tileList.get(b).getFlipX();
            int sizeY = size * tileList.get(b).getFlipY();
            int X = (int) (tileList.get(b).getX()*(scale) + (tileList.get(b).getFlipX() == -1 ? size : 0));
            int Y = (int) (tileList.get(b).getY()*(scale) + (tileList.get(b).getFlipY() == -1 ? size : 0));
            BufferedImage tileWithTransparency = tileSelector.getTileImage(tileList.get(b).getID(), tileList.get(b).getPalette());
            g2d.drawImage(tileWithTransparency, X, Y, sizeX, sizeY, null);
        }
        g2d.dispose();
    }

    public static void writeToOutput(String stringToWrite) {
        try {
            File outputFile = new File("output.txt");
            FileWriter fw = new FileWriter(outputFile);
            BufferedWriter outputBuffer = new BufferedWriter(fw);
            outputBuffer.write(stringToWrite);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void export(String dir, String name) throws IOException {
        File output = new File(dir + name);
        output.createNewFile(); //if the file already exists this won't do anything
		System.out.println(output);
        FileWriter fw = new FileWriter(output);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(name.substring(0, name.length()-4) + "_generatedMetasprite:\r");
        for(int i = 0; i<spriteList.size(); i++) {
            bw.write("DW    sprite_" + Integer.toString(i) + "\r");
        }
        bw.flush();
        for(int x = 0; x<spriteList.size(); x++) {
            bw.write("sprite_"+Integer.toString(x) + ":\rDB   $" + Integer.toHexString((spriteList.get(x).size()*4)).toUpperCase() + "\r");
            for(Tile tile : spriteList.get(x)) {
                //for every tile in each tile list
                bw.write("DB    $" + String.format("%02X", tile.getY()) + ", $" + String.format("%02X", tile.getID()) + ", %");
                if(tile.xFlip) {
                    bw.write("1");
                } else {
                    bw.write("0");
                }
                if(tile.yFlip) {
                    bw.write("1");
                } else {
                    bw.write("0");
                }
                if(tile.priority) {
                    bw.write("1000");
                } else {
                    bw.write("0000");
                }
                switch(tile.palette) {
                    case 0: 
                        bw.write("00");
                        break;
                    case 1:
                        bw.write("01");
                        break;
                    case 2:
                        bw.write("10");
                        break;
                    case 3:
                        bw.write("11");
                        break;
                }
                bw.write(", $");
                bw.write(String.format("%02X", tile.getX()) + "\r");
            }
            bw.flush();
        }
    }
}