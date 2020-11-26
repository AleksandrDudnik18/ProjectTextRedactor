package FileStreams;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class FileStreams {
    public static void main(String args[]) {
        FrameWindow frame;
        frame = new FrameWindow("Simplest Java Notepad");
        frame.setVisible(true);
    }
}

// =======================================
// Class FrameWindow
// =======================================
class FrameWindow extends Frame
        implements ActionListener, WindowListener {
    TextArea ta;

    MenuBar mb;

    Menu mFile;
    MenuItem miOpen;
    MenuItem miSave;
    MenuItem miSaveAs;
    MenuItem miExit;

    String szCurrentFilename = "";
    byte[] buf;

    // ============================================
    // FrameWindow
    // ============================================
    public FrameWindow(String szTitle) {
        super(szTitle);
        setSize(400, 300);

        mb = new MenuBar();
        mFile = new Menu("File");

        miOpen = new MenuItem("Open...");
        mFile.add(miOpen);

        miSave = new MenuItem("Save");
        mFile.add(miSave);

        miSaveAs = new MenuItem("Save As...");
        mFile.add(miSaveAs);

        mFile.add("-");

        miExit = new MenuItem("Exit");
        mFile.add(miExit);

        mb.add(mFile);

        miOpen.addActionListener(this);
        miSave.addActionListener(this);
        miSaveAs.addActionListener(this);
        miExit.addActionListener(this);

        setMenuBar(mb);

        this.addWindowListener(this);

        ta = new TextArea(10, 30);
        setLayout(new BorderLayout());
        add("Center", ta);

    }

    // ============================================
    // actionPerformed
    // ============================================
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(miOpen)) {
            FileOpen();
        } else if (e.getSource().equals(miSave)) {
            FileSave();
        } else if (e.getSource().equals(miSaveAs)) {
            FileSaveAs();
        } else if (e.getSource().equals(miExit)) {
            setVisible(false);
            System.exit(0);
        }
    }

    // ============================================
    // windowClosing
    // ============================================
    @Override
    public void windowClosing(WindowEvent e) {
        setVisible(false);
        System.exit(0);
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    // ============================================
    // FileOpen
    // ============================================
    void FileOpen() {
        FileDialog fileDialog;
        FileInputStream is = null;

        fileDialog = new FileDialog(this, "Open file",
                FileDialog.LOAD);
        fileDialog.setVisible(true);

        szCurrentFilename = fileDialog.getDirectory() +
                fileDialog.getFile();

        if (fileDialog.getDirectory() == null ||
                fileDialog.getFile() == null)
            return;

        setTitle("Simplest Java Notepad" + " - " +
                szCurrentFilename);

        try {
            is = new FileInputStream(szCurrentFilename);
        } catch (FileNotFoundException | SecurityException ex) {
            System.out.println(ex.toString());
        }

        try {
            buf = new byte[is.available()];
            is.read(buf);
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }

        ta.selectAll();
        ta.replaceRange("", 0, ta.getSelectionEnd());

        String szStr = new String(buf);
        StringTokenizer st;

        st = new StringTokenizer(szStr, "\r\n");

        while (st.hasMoreElements()) {
            szStr = (String) st.nextElement();
            ta.append(szStr + "\r\n");
        }

        try {
            is.close();
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    // ============================================
    // FileSaveAs
    // ============================================
    void FileSaveAs() {
        FileDialog fileDialog;
        fileDialog = new FileDialog(this, "Save file as...",
                FileDialog.SAVE);

        fileDialog.setVisible(true);


        szCurrentFilename = fileDialog.getDirectory() +
                fileDialog.getFile();

        setTitle("Simplest Java Notepad" + " - " +
                szCurrentFilename);

        FileSave();
    }

    // ============================================
    // FileSave
    // ============================================
    void FileSave() {
        FileOutputStream os;

        String sz = ta.getText();
        buf = sz.getBytes();

        try {
            os = new FileOutputStream(szCurrentFilename);
            os.write(buf);
            os.close();

        } catch (IOException | SecurityException ex) {
            System.out.println(ex.toString());
        }
    }
}



