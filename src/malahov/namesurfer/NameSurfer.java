package malahov.namesurfer;

/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import com.shpp.cs.a.simple.SimpleProgram;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class NameSurfer extends SimpleProgram implements NameSurferConstants {

    private final NameSurferGraph graph = new NameSurferGraph();
    private NameSurferDataBase dataBase;
    private JButton graphButton, clearButton;
    private JTextField nameField;

    /* Method: init() */

    /**
     * This method has the responsibility for reading in the data base
     * and initializing the interactors at the top of the window.
     */
    public void init() {
        nameField = new JTextField(NUM_COLUMNS);
        graphButton = new JButton("Graph");
        clearButton = new JButton("Clear");

        try {
            dataBase = new NameSurferDataBase(NAMES_DATA_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        add(new JLabel("Name: "), NORTH);
        add(nameField, NORTH);
        add(graphButton, NORTH);
        add(clearButton, NORTH);

        nameField.setActionCommand("EnterPressed");
        nameField.addActionListener(this);
        addActionListeners();
        add(graph);
    }

    /* Method: actionPerformed(e) */

    /**
     * This class is responsible for detecting when the buttons are
     * clicked, so you will have to define a method to respond to
     * button actions.
     */
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (e.getSource() == graphButton || cmd.equals("EnterPressed")) {

            String name = nameField.getText();

            try {

                if(dataBase.findEntry(name) != null){
                    graph.addEntry(dataBase.findEntry(name));
                }

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            nameField.setText("");
            graph.update();

        } else if (e.getSource() == clearButton) {
            graph.clear();
            graph.update();
        }
    }
}
