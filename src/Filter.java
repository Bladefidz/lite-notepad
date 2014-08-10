import java.io.*;
import javax.swing.filechooser.FileFilter;

class Filter extends FileFilter {

    @Override
    public boolean accept(File f) {
        return f.isDirectory() || f.getAbsolutePath().endsWith(".txt");
    }

    @Override
    public String getDescription() {
        return "Text doxuments (*.txt)";
    }

}	
