package malahov.namesurfer;

/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes
 * or the window is resized.
 */

import acm.graphics.GCanvas;
import acm.graphics.GLabel;
import acm.graphics.GLine;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.HashMap;

public class NameSurferGraph extends GCanvas
        implements NameSurferConstants, ComponentListener {


    HashMap<NameSurferEntry, Color> colorMap = new HashMap<>();
    Color[] colorArray = {Color.BLUE,Color.RED,Color.MAGENTA,Color.BLACK};
    int counter = 0;

    /**
     * Creates a new NameSurferGraph object that displays the data.
     */
    public NameSurferGraph() {
        addComponentListener(this);
    }

    /**
     * Clears the list of name surfer entries stored inside this class.
     */
    public void clear() {

        colorMap.keySet().clear();
        counter = 0;
    }


    /* Method: addEntry(entry) */
    /**
     * Adds a new NameSurferEntry to the list of entries on the display.
     * Note that this method does not actually draw the graph, but
     * simply stores the entry; the graph is drawn by calling update.
     */
    public void addEntry(NameSurferEntry entry) {

        if (colorMap.containsKey(entry)) {
            return;
        }

        colorMap.put(entry,colorArray[counter%4]);
        counter++;
        System.out.println(entry);
    }

    /**
     * Updates the display image by deleting all the graphical objects
     * from the canvas and then reassembling the display according to
     * the list of entries. Your application must call update after
     * calling either clear or addEntry; update is also called whenever
     * the size of the canvas changes.
     */
    public void update() {
        removeAll();
        createTable();
        createGraph();
    }

    /**
     * This method creates graph on the canvas.
     */
    private void createGraph() {
        for(NameSurferEntry entry:colorMap.keySet()){
            createLinesOfGraph(entry);
            createInscription(entry);
        }
    }

    /**
     * This method creates inscription near the graph.
     * @param entry NameSurferEntry object.
     */
    private void createInscription(NameSurferEntry entry) {
        for(int i = 0; i < NDECADES; i++){
            //Create name inscription.
            GLabel name = new GLabel(entry.getName() + " " + (entry.getRank(i)==MAX_RANK?"*":entry.getRank(i)));
            name.setColor(colorMap.get(entry));
            add(
                    name,
                    (getWidth()/(NDECADES*1.0))*i,
                    GRAPH_MARGIN_SIZE+entry.getRank(i)*((getHeight() - 2*GRAPH_MARGIN_SIZE)/MAX_RANK));

        }
    }

    /**
     * This method creates line of name popularity on the canvas.
     * @param entry NameSurferEntry object.
     */
    private void createLinesOfGraph(NameSurferEntry entry) {
        for(int d = 0; d < NDECADES - 1; d++){
            GLine graph = new GLine(
                    (getWidth()/(NDECADES*1.0))*d,
                    GRAPH_MARGIN_SIZE+entry.getRank(d)*((getHeight() - 2*GRAPH_MARGIN_SIZE)/MAX_RANK),
                    (getWidth()/(NDECADES*1.0))*(d+1),
                    GRAPH_MARGIN_SIZE+entry.getRank(d+1)*((getHeight() - 2*GRAPH_MARGIN_SIZE)/MAX_RANK));
            graph.setColor(colorMap.get(entry));
            add(graph);
        }
    }

    /**
     * This method creates table in the canvas background.
     */
    private void createTable() {
        //Create top line
        makeLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE);
        //Create bottom line
        makeLine(0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(), getHeight() - GRAPH_MARGIN_SIZE);

        int year = START_DECADE;

        for (int x = 0; x < NDECADES; x++) {
            //Create vertical lines
            makeLine(x * getWidth()/NDECADES, 0,x * getWidth()/NDECADES, getHeight());
            //Create numbers of decade
            GLabel decade = new GLabel("" + year);
            year += 10;
            decade.setFont("Verdana-20");
            add(decade, x * getWidth()/(NDECADES*1.0), getHeight());
        }
    }

    //This method creates line and add it to the canvas.
    private void makeLine(int x1, int y1, int x2, int y2) {
        GLine line = new GLine(x1, y1, x2, y2);
        add(line);
    }

    /* Implementation of the ComponentListener interface */
    public void componentHidden(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentResized(ComponentEvent e) {
        update();
    }

    public void componentShown(ComponentEvent e) {
    }
}
