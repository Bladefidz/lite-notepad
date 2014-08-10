import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.*;

/**
*
* Created by HafidzTech
* Developed from: http://forum.codecall.net/topic/49721-simple-text-editor/
*/

class Notepad extends JFrame {
	private JTextArea editor = new JTextArea(20,50);
	private JFileChooser dialog = new JFileChooser(System.getProperty("user.dir"));
	private String newFile = "Untitled";
	private boolean edited = false;
	
	public Notepad() {
		editor.setFont(new Font("Monoscaped", Font.PLAIN,12));
		JScrollPane scroll = new JScrollPane(editor, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(scroll, BorderLayout.CENTER);
		
		// Menu bar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		// Popup menu
		JMenu file = new JMenu("File");
		menuBar.add(file);
		JMenu edit = new JMenu("Edit");
		menuBar.add(edit);
		// Each menu is Action Method
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
		edit.add(Cut);
		edit.add(Copy);
		edit.add(Paste);
		edit.addSeparator();
		// Use JMenuItem
		edit.getItem(0).setText("Cut");
		edit.getItem(1).setText("Copy");
		edit.getItem(2).setText("Paste");
		
		// Add second menu: button with icon
		JToolBar tool = new JToolBar();
		add(tool, BorderLayout.NORTH);
		tool.add(New);
		tool.add(Open);
		tool.add(Save);
		tool.addSeparator();
		// Use Action Map for Cut, Copy, Paste
		JButton ctrlx = tool.add(Cut), ctrlc = tool.add(Copy), ctrlv = tool.add(Paste);
		ctrlx.setText(null); ctrlx.setIcon(new ImageIcon("Images/cut.gif"));
		ctrlc.setText(null); ctrlc.setIcon(new ImageIcon("Images/copy.gif"));
		ctrlv.setText(null); ctrlv.setIcon(new ImageIcon("Images/paste.gif"));
		
		// Enable if KeyListener detect a Key Stroke
		Save.setEnabled(false);
		SaveAs.setEnabled(false);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack(); // java.awt.window - cause fit window
		editor.addKeyListener(k1);
		setTitle(newFile);
		setVisible(true);
	}
	
	// Main class
	public static void main(String[] args) {
		new Notepad();
	}
	
	// KeyListener detect a key stroke
	private KeyListener k1 = new KeyAdapter() {
		public void KeyPressed(KeyEvent e) {
			edited = true;
			Save.setEnabled(true);
			SaveAs.setEnabled(true);
		}
	};
	
	// The Action Methods here
	Action New = new AbstractAction("New", new ImageIcon("Images/new.gif")) {
		// Implement Abstract Method
		public void actionPerformed(ActionEvent e) {
			if(!newFile.equals("Untitled"))
				saveOld();
			else
				editor.setText("");
		}
	};
	
	Action Open = new AbstractAction("Open", new ImageIcon("Images/open.gif")) {
		public void actionPerformed(ActionEvent e) {
			saveOld();
                        File f = dialog.getSelectedFile();
			if(dialog.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				readInFile(f.getAbsolutePath());
			}
                        else {
                              System.out.println("Open file canceled");  
                        }
                        SaveAs.setEnabled(true);
		}
	};
	
	Action Save = new AbstractAction("Save", new ImageIcon("Images/save.gif")) {
		public void actionPerformed(ActionEvent e) {
			if(!newFile.equals("Untitled"))
				saveFile(newFile);
			else
				saveFileAs();
		}
	};
	
	Action SaveAs = new AbstractAction("Save as...") {
		public void actionPerformed(ActionEvent e) {
			saveFileAs();
		}
	};
	
	Action Quit = new AbstractAction("Quit") {
		public void actionPerformed(ActionEvent e) {
			saveOld();
			System.exit(0);
		}
	};
	
	ActionMap map = editor.getActionMap();
	Action Cut = map.get(DefaultEditorKit.cutAction);
	Action Copy = map.get(DefaultEditorKit.copyAction);
	Action Paste = map.get(DefaultEditorKit.pasteAction);
	
	// Methods
	private void saveFileAs() {
            File f = dialog.getSelectedFile();
		if(dialog.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
			saveFile(f.getAbsolutePath());
	}
	
	private void saveOld() {
		if(edited) {
			if(JOptionPane.showConfirmDialog(this, "Would you like save "+ newFile +" ?","Save",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				saveFileAs();
		}
	}
	
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
			// ?
		}
	}
	
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
}
class Filter extends javax.swing.filechooser.FileFilter {

    @Override
    public boolean accept(File f) {
        return f.isDirectory() || f.getAbsolutePath().endsWith(".txt");
    }

    @Override
    public String getDescription() {
        return "Text doxuments (*.txt)";
    }

}	
