import java.awt.event.*; 
import java.awt.*;
import java.awt.Color;
import java.awt.image.BufferedImage;

import java.util.Arrays;
import java.lang.Math;
import java.util.ArrayList;
import javax.swing.*;    
import java.io.*;
//literally a bunch of imports that we're gonna use.


public class Main {
    private static ArrayList<Integer> tileArrayList = new ArrayList<Integer>();
    //default "#ffffff", "#bcbcbc", "#7c7c7c", "#000000"
    //the arraylist which contains all the tiles in the CHR file
    private static final String cfgFileName = "Settings.cfg";
    private static String outputDir;
    private static String fileName;
    private static int sizeOfEditor;
    private static ArrayList<Color> ColorKeyList = new ArrayList<Color>(Arrays.asList(
        new Color(124,124,124),
        new Color(0,0,252),
        new Color(0,0,188),
        new Color(68,40,188),
        new Color(148,0,132),
        new Color(168,0,32),
        new Color(168,16,0),
        new Color(136,20,0),
        new Color(80,48,0),
        new Color(0,120,0),
        new Color(0,104,0),
        new Color(0,88,0),
        new Color(0,64,88),
        new Color(0,0,0),
        new Color(0,0,0),
        new Color(0,0,0),
        new Color(188,188,188),
        new Color(0,120,248),
        new Color(0,88,248),
        new Color(104,68,252),
        new Color(216,0,204),
        new Color(228,0,88),
        new Color(248,56,0),
        new Color(228,92,16),
        new Color(172,124,0),
        new Color(0,184,0),
        new Color(0,168,0),
        new Color(0,168,68),
        new Color(0,136,136),
        new Color(0,0,0),
        new Color(0,0,0),
        new Color(0,0,0),
        new Color(248,248,248),
        new Color(60,188,252),
        new Color(104,136,252),
        new Color(152,120,248),
        new Color(248,120,248),
        new Color(248,88,152),
        new Color(248,120,88),
        new Color(252,160,68),
        new Color(248,184,0),
        new Color(184,248,24),
        new Color(88,216,84),
        new Color(88,248,152),
        new Color(0,232,216),
        new Color(120,120,120),
        new Color(0,0,0),
        new Color(0,0,0),
        new Color(252,252,252),
        new Color(164,228,252),
        new Color(184,184,248),
        new Color(216,184,248),
        new Color(248,184,248),
        new Color(248,164,192),
        new Color(240,208,176),
        new Color(252,224,168),
        new Color(248,216,120),
        new Color(216,248,120),
        new Color(184,248,184),
        new Color(184,248,216),
        new Color(0,252,252),
        new Color(248,216,248),
        new Color(0,0,0),
        new Color(0,0,0)
    ));

    private static ArrayList<Color> paletteList = new ArrayList<Color>(Arrays.asList(
        new Color(255, 255, 255),
        new Color(188, 188, 188),
        new Color(124, 124, 124),
        new Color(0, 0, 0),
        new Color(255, 255, 255),
        new Color(188, 188, 188),
        new Color(124, 124, 124),
        new Color(0, 0, 0),
        new Color(255, 255, 255),
        new Color(188, 188, 188),
        new Color(124, 124, 124),
        new Color(0, 0, 0),
        new Color(255, 255, 255),
        new Color(188, 188, 188),
        new Color(124, 124, 124),
        new Color(0, 0, 0)
    ));
    //The arraylist which contains each colour in the palettes
    private static int paletteSelection;
    //The palette which is chosen, 0-3
    private static int tileSelection;
    //the tile which is chosen, 0-63

    private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("TorTile Editor");
        frame.setMinimumSize(new Dimension(600, 500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
        //set up a buncha stuff to do with the frame closing and size prefs

/*
-Frame----------------------------------------------------
| -container-------------------------------------------- |
| | -vertcontainer------------ -editor---------------- | |
| | |                        | |                     | | |
| | |                        | |                     | | |
| | |                        | |                     | | |
| | |                        | |                     | | |
| | |                        | |                     | | |
| | -------------------------- ----------------------- | |
| ------------------------------------------------------ |
----------------------------------------------------------
*/

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

        //this is gonna contain our stuff (see above ascii drawing)

        JPanel vertContainer = new JPanel();
        vertContainer.setLayout(new BoxLayout(vertContainer, BoxLayout.Y_AXIS));
        //vertContainer.setMaximumWidth(512);
        //contains the palette and tile selector objects
		
		JPanel editorContainer = new JPanel();
		editorContainer.setLayout(new BoxLayout(editorContainer, BoxLayout.Y_AXIS));
        
        //make the palette selection panel defined in PaletteBar.java
        PaletteBar paletteBar = new PaletteBar(paletteList);
        //get the selected palette from the palette selector object
        paletteSelection = paletteBar.getSelectedPalette();

		//editorDisplay before tileselector because the tileSelector depends on its definition
		EditorDisplay editorDisplay = new EditorDisplay(tileSelection);

        //make the tile selection panel as defined in TileSelector.java
        TileSelector tileSelector = new TileSelector(tileArrayList, paletteList, paletteSelection, editorDisplay);
        tileSelector.setMinimumSize(new Dimension(64, 64));
        tileSelector.setPreferredSize(new Dimension(128, 128));
        //get the selected tile from the tileselector object
        tileSelection = tileSelector.getSelectedTile();

        //add the two objects onto the vertcontainer

        //make the editor panel
        Editor editor = new Editor(tileSelection, paletteBar, tileSelector, outputDir, fileName, sizeOfEditor);
        editor.setMinimumSize(new Dimension(128,128));
        editor.setPreferredSize(new Dimension(512, 512));
        editor.setFocusable(true);
        editor.requestFocusInWindow();

        InfoPanel infoPanel = new InfoPanel(editor);

        vertContainer.add(tileSelector);  
        vertContainer.add(paletteBar);
        vertContainer.add(infoPanel);
		
		editor.repaint();
		
		editorContainer.add(editor);
		editorContainer.add(editorDisplay);
		
        //add the two objects onto the container
        container.add(vertContainer);
        container.add(editorContainer);
        //add the container on to the frame and draw!
        frame.add(container);
		//frame.setVisible(true);
    }
 
    //!!!! THIS GETS RUN FIRST! ALWAYS!
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                String chrFileDir = "";
                outputDir = "";
                try {
                    File settingsFile = new File(cfgFileName);
                    FileReader fr = new FileReader(settingsFile);
                    BufferedReader br = new BufferedReader(fr);
                    String Line;
                    boolean paletteMode = false;
                    while((Line = br.readLine()) != null) {
                        if(Line.contains("Input_Directory:")) {
                            chrFileDir = Line.substring(16, Line.length());
                        } else if(Line.contains("Output_Directory:")) {
                            outputDir = Line.substring(17, Line.length());
                        } else if (Line.contains("File_Name")) {
                            fileName = Line.substring(10, Line.length());
                        } else if(Line.contains("Palette:")) {
                            paletteMode = true;
                        } else if(Line.contains("ENDPALETTE")) {
                            paletteMode = false;
                        } else if(Line.contains("Size Of Editor:")) {
                            sizeOfEditor = Integer.parseInt(Line.substring(15, Line.length()));
                        }
                        if(paletteMode) {
                            int index = 0;
                            String[] numbers = Line.split(" ");
                            for(String number : numbers) {
                                if(!number.equals("Palette:")) {
                                    paletteList.set(index, ColorKeyList.get(Integer.parseInt(number, 16)));
                                    index++;
                                }
                            }
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                //read the CHR file (no decoding yet)
                ArrayList<Integer> FileAsArrayList = new ArrayList<Integer>();
                try {
                    File inputFile = new File(chrFileDir);
                    InputStream inputStream = new FileInputStream(inputFile);
                    int byteRead;
                    while ((byteRead = inputStream.read()) != -1) {
                        FileAsArrayList.add(byteRead);
                    }
                } catch (IOException e) {
                    System.out.println("No File Found at " + chrFileDir);
                }
                //parse the file in custom function
                tileArrayList = parseFile(FileAsArrayList);
                //see top of this file
                createAndShowGUI();
            }
        });
    }

    //turns the binary in the CHR file into an arrayList which we can read easily
    //input: File as bytes in an arraylist
    //returns: arrayList that is each pixel in each tile, 64 pixels in a tile
    //THIS IS WORKING PROPERLY 100% SURE PLS DONT TOUCH
    public static ArrayList<Integer> parseFile(ArrayList<Integer> FileInput) {
        ArrayList<Integer> firstBitplane = new ArrayList<Integer>();
        ArrayList<Integer> secondBitplane = new ArrayList<Integer>();
        ArrayList<Integer> fullTiles = new ArrayList<Integer>();
        for(int i = 0; i < FileInput.size(); i++) {
            if(Math.floor(i/8)%2 == 0) {
                //high bitplane
                firstBitplane.add((FileInput.get(i) & 128)/128);
                firstBitplane.add((FileInput.get(i) & 64)/64);
                firstBitplane.add((FileInput.get(i) & 32)/32);
                firstBitplane.add((FileInput.get(i) & 16)/16);
                firstBitplane.add((FileInput.get(i) & 8)/8);
                firstBitplane.add((FileInput.get(i) & 4)/4);
                firstBitplane.add((FileInput.get(i) & 2)/2);
                firstBitplane.add(FileInput.get(i) & 1);
            } else {
                secondBitplane.add((FileInput.get(i) & 128)/128);
                secondBitplane.add((FileInput.get(i) & 64)/64);
                secondBitplane.add((FileInput.get(i) & 32)/32);
                secondBitplane.add((FileInput.get(i) & 16)/16);
                secondBitplane.add((FileInput.get(i) & 8)/8);
                secondBitplane.add((FileInput.get(i) & 4)/4);
                secondBitplane.add((FileInput.get(i) & 2)/2);
                secondBitplane.add(FileInput.get(i) & 1);
            }
        }
        for(int i = 0; i < firstBitplane.size(); i++) {
            fullTiles.add(firstBitplane.get(i)+(2*secondBitplane.get(i)));
        }
        while(fullTiles.size() < 32768) {
        	fullTiles.add(0);
        }
        return(fullTiles);
    }
}
