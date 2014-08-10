/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Lite;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.*;

/**
 *
 * @author HafidzTech007
 * Developed from: http://forum.codecall.net/topic/49721-simple-text-editor/
 */

class Beta extends JFrame {
    private JTextArea editor = new JTextArea(20,120);
    private JFileChooser dialog = new JFileChooser(System.getProperty("user.dir"));
    private String newFile = "Untitled";
    private boolean edited = false;

    public Beta() {
        editor.setFont(new Font("Monospaced", Font.PLAIN,12));
        JScrollPane scroll = new JScrollPane(editor, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(scroll,BorderLayout.CENTER);
        
		// Menu menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
		// Add menu: file
        JMenu file = new JMenu("File");
        menuBar.add(file);
		// Action Method
        file.add(New);
        file.add(Open);
        file.add(Save);
        file.add(SaveAs);
        file.addSeparator();
        file.add(Quit);
        file.addSeparator();
		// Menu not have icon
        for(int i=0; i<4; i++)
            file.getItem(i).setIcon(null);
        
		// Add menu: edit
        JMenu edit = new JMenu("Edit");
        menuBar.add(edit);
		// Action Method
        edit.add(Cut);
        edit.add(Copy);
        edit.add(Paste);
        edit.addSeparator();
		// Use JMenuItem
        edit.getItem(0).setText("Cut");
        edit.getItem(1).setText("Copy");
        edit.getItem(2).setText("Paste");
		
        // Add menu button with icon.
        JToolBar tool = new JToolBar();
        add(tool, BorderLayout.NORTH);
        tool.add(New);
        tool.add(Open);
        tool.add(Save);
        tool.addSeparator();
		
        JButton ctrlx = tool.add(Cut), ctrlc = tool.add(Copy), ctrlv = tool.add(Paste);
        ctrlx.setText(null); ctrlx.setIcon(new ImageIcon(getClass().getResource("images/cut.gif")));
        ctrlc.setText(null); ctrlc.setIcon(new ImageIcon(getClass().getResource("images/copy.gif")));
        ctrlv.setText(null); ctrlv.setIcon(new ImageIcon(getClass().getResource("images/paste.gif")));
        
        // For alternative deployment see class KeyListener
        Save.setEnabled(true); // Should be false if key have pressed
        SaveAs.setEnabled(false); // Should be false if no key have pressed
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        editor.addKeyListener(k1);
        setTitle(newFile);
        setVisible(true);
    }
    
    // Place at last page or first page, that's up to you
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void main(String[] argumentsakkarepmu) {
        new Beta();
    }
    
    // Save should be enabled if you press a keyboard
    private KeyListener k1 = new KeyAdapter() {
        public void KeyPressed(KeyEvent e) {
            edited = true;
            Save.setEnabled(true);
            SaveAs.setEnabled(true);
        }
    };
    
    // This is the Action method form java.awt.event.*
    // If now you are using netbeans, use implement Abstract Method
    Action New = new AbstractAction("New", new ImageIcon(getClass().getResource("images/new.gif"))) {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(newFile.equals("Untitled"))
		editor.setText("");
            else
                edited = true;
		saveOld();
                editor.setText("");
                
        }
    };
    
    Action Open = new AbstractAction("Open", new ImageIcon(getClass().getResource("images/open.gif"))) {

        @Override
        public void actionPerformed(ActionEvent e) {
            edited = true;
            saveOld();
            if(dialog.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                readInFile(dialog.getSelectedFile().getAbsolutePath());
            }
            SaveAs.setEnabled(true);
        }
    };
    
    Action Save = new AbstractAction("Save", new ImageIcon(getClass().getResource("images/save.gif"))) {
        
        @Override
        public void actionPerformed(ActionEvent e){
            if(!newFile.equals("Untitled"))
                saveFile(newFile);
            else
                saveFileAs();
        }
    };
    
    Action SaveAs = new AbstractAction("Save as...") {

        @Override
        public void actionPerformed(ActionEvent e) {
            saveFileAs();
        }        
    };
    
    Action Quit = new AbstractAction("Quit") {

        @Override
        public void actionPerformed(ActionEvent e) {
            edited = true;
            saveOld();
            System.exit(0);
        }
    };
    
    ActionMap map = editor.getActionMap();
    Action Cut = map.get(DefaultEditorKit.cutAction);
    Action Copy = map.get(DefaultEditorKit.copyAction);
    Action Paste = map.get(DefaultEditorKit.pasteAction);
    
    // Method
    private void saveFileAs() {
        if(dialog.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
            saveFile(dialog.getSelectedFile().getAbsolutePath());
    }
    
    private void saveOld() {
        if(edited) {
            if(JOptionPane.showConfirmDialog(this, "Would you like to save "+ newFile +" ?","Save",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
                saveFileAs();
        }
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    private void readInFile(String fileName) {
        try {
            FileReader r = new FileReader(fileName);
            editor.read(r, null);
            r.close();
            newFile = fileName;
            setTitle(newFile);
            edited = false;
        }
        catch(IOException e) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(this, "Editor can't find the file called "+fileName);
        }
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    private void saveFile(String fileName) {
        try {
            FileWriter w = new FileWriter(fileName);
            editor.write(w);
            w.close();
            newFile = fileName;
            setTitle(newFile);
            edited = false;
        }
        catch(IOException e) {
            // :D
        }
    }
}
