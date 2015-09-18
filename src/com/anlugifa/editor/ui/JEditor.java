package com.anlugifa.editor.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.TabSet;
import javax.swing.text.TabStop;

import com.anlugifa.editor.base.GroovyFilter;
import com.anlugifa.editor.base.MyUndoableListener;
import com.anlugifa.editor.xml.StyleManager;
import java.io.OutputStream;
import sun.tools.jar.CommandLine;

public class JEditor extends JFrame
{
    private JMenuBar mnuBar = null;
    private JMenu mnuFile = null;
    private JMenu mnuEdit = null;
    private JMenuItem mniNew = null;
    private JMenuItem mniOpen = null;
    private JMenuItem mniSaveAs = null;
    private JMenuItem mniSave = null;
    private JMenuItem mniClose = null;
    private JMenuItem mniCopy = null;
    private JMenuItem mniPaste = null;
    private JMenuItem mniCut = null;
    private JMenuItem mniFind = null;
    private JTextPane textPane;
    private JScrollPane jScrollPane = null;
    private File file = null;
    private JToolBar toolbar = null;
    private JFileChooser fileChooser; // @jve:decl-index=0:visual-constraint="-29,424"

    private boolean changed = false;

    private JPanel pnlText = null;
    private JStatusPanel pnlStatus = null;

    private static JEditor myInstance = null;
    private JMenuItem mniUndo = null;
    private JMenuItem mniRedo = null;
    private MyUndoableListener myUndoable = null; // @jve:decl-index=0:

    private JAbout jAbout = null;
    
    private Action openAction = new OpenAction();
    private Action saveAction = new SaveAction();
    private Action newAction = new NewAction();
    private Action compAction = new CompAction();

    class MyWindowListener extends WindowAdapter // @jve:decl-index=0:
    {
        @Override
        public void windowClosing(WindowEvent e)
        {
            if (hasChanged())
            {
                int result = JOptionPane.showConfirmDialog(JEditor.this,
                        "Would you like to save file ?", "Closing...",
                        JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.OK_OPTION)
                    OnMniSave();
            }
        }
    }

    private GroovyFilter watchDocument;
    private JMenu mnuAbout = null;
    private JMenuItem mniAbout = null;

    /**
     * This method initializes
     */
    private JEditor()
    {
        super();
        initialize();
        centralize();
        prepareDocuments();
    }

    public static JEditor getInstance()
    {
        if (myInstance == null)
            myInstance = new JEditor();
 
        return myInstance;
    }

    /**
     * This method initializes this
     */
    private void initialize()
    {
        this.setSize(new Dimension(600, 600));
        this.setJMenuBar(getMnuBar());
        this.setLayout(new BorderLayout());
        this.add(BorderLayout.NORTH, createToolbar());
        this.add(BorderLayout.CENTER, getPnlText());
        this.setTitle("LanQ Editor - UNKNOWN");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new MyWindowListener());
    }

    private void centralize()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setBounds((screenSize.width - getWidth()) / 2,
                  (screenSize.height - getHeight()) / 2, 
                   getWidth(), getHeight());
    }
   
    public void centralize(JDialog cmp)
    {
        cmp.setLocation(getX() + ((getWidth()  - cmp.getWidth())  / 2), 
                        getY() + ((getHeight() - cmp.getHeight()) / 2));

    }
    
    /**
     * Create the toolbar. By default this reads the resource file for the
     * definition of the toolbar.
     */
    private Component createToolbar()
    {
        if (toolbar == null)
        {
            toolbar = new JToolBar();

            toolbar.add(createNewBtn());
            toolbar.add(createOpenBtn());
            toolbar.add(createSaveBtn());
            toolbar.add(createCompBtn());

            toolbar.add(Box.createHorizontalStrut(5));
            toolbar.add(Box.createHorizontalGlue());
        }

        return toolbar;
    }

    private Component createNewBtn()
    {
        URL url = getClass().getResource("/com/anlugifa/editor/data/images/new.gif");
        return createBtn(url, newAction);
    }

    private Component createOpenBtn()
    {
        URL url = getClass().getResource("/com/anlugifa/editor/data/images/open.gif");        
        return createBtn(url, openAction);
    }

    private Component createSaveBtn()
    {
        URL url = getClass().getResource("/com/anlugifa/editor/data/images/save.gif");        
        return createBtn(url, saveAction);
    }
    private Component createCompBtn()
    {
        URL url = getClass().getResource("/com/anlugifa/editor/data/images/run.gif");        
        return createBtn(url, compAction);
    }

    private Component createBtn(URL url, Action action)
    {
        JButton b = new JButton(new ImageIcon(url))
        {
            @Override
            public float getAlignmentY()
            {
                return 0.5f;
            }
        };

        b.setRequestFocusEnabled(false);
        b.setMargin(new Insets(1, 1, 1, 1));
        b.addActionListener(action);

        return b;
    }

    private void prepareDocuments()
    {
        StyledDocument doc = getTextPane().getStyledDocument();
        
        StyleManager.getInstance().createStyles(doc);
                
        watchDocument = new GroovyFilter(doc);
        doc.addDocumentListener(watchDocument);        
    }
    
    public void open(File file)
    {
        if (file == null)
            throw new IllegalArgumentException("Null file can not be opened.");

        if (file.isDirectory())
            throw new IllegalArgumentException("File to open is a directory.");

        if (!file.canRead())
            throw new IllegalArgumentException(
                    "Illegal access to file. It is not readable");

        read(file);
    }

    private void setFile(File file)
    {
        if (file == null)
            throw new IllegalArgumentException("File can't be null");

        this.file = file;
    }

    private void read(File file)
    {
        setFile(file);

        StringBuffer reading = new StringBuffer();

        try
        {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(file));

            while ((line = reader.readLine()) != null)
                reading.append(line + "\n");

            reader.close();
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(this, "The file couldn't be read!");
        }

        getTextPane().setText(reading.toString());
        setChanged(false);
    }

    private void save()
    {
        try
        {
            FileWriter writer = new FileWriter(file);
            writer.write(getTextPane().getText());
            writer.flush();
            writer.close();
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(this, "The file couldn't be saved!");
        }

        setChanged(false);
    }

    /**
     * This method initializes mnuBar
     * 
     * @return javax.swing.JMenuBar
     */
    private JMenuBar getMnuBar()
    {
        if (mnuBar == null)
        {
            mnuBar = new JMenuBar();
            mnuBar.add(getMnuFile());
            mnuBar.add(getMnuEdit());
            mnuBar.add(getMnuAbout());
        }
        return mnuBar;
    }

    /**
     * This method initializes mnuFile
     * 
     * @return javax.swing.JMenu
     */
    private JMenu getMnuFile()
    {
        if (mnuFile == null)
        {
            mnuFile = new JMenu();
            mnuFile.setText("File");
            mnuFile.setText("File");
            mnuFile.add(getMniNew());
            mnuFile.add(getMniOpen());
            mnuFile.add(getMniSaveAs());
            mnuFile.add(getMniSave());
            mnuFile.add(new JSeparator());
            mnuFile.add(getMniClose());
            mnuFile.setMnemonic('F');
        }
        return mnuFile;
    }

    /**
     * This method initializes mnuEdit
     * 
     * @return javax.swing.JMenu
     */
    private JMenu getMnuEdit()
    {
        if (mnuEdit == null)
        {
            mnuEdit = new JMenu();
            mnuEdit.setText("Edit");
            mnuEdit.setMnemonic('E');
            mnuEdit.add(getMniUndo());
            mnuEdit.add(getMniRedo());
            mnuEdit.add(new JSeparator());
            mnuEdit.add(getMniCopy());
            mnuEdit.add(getMniPaste());
            mnuEdit.add(getMniCut());
            mnuEdit.add(new JSeparator());
            mnuEdit.add(getMniFind());

        }
        return mnuEdit;
    }

    /**
     * This method initializes mniCopy
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getMniOpen()
    {
        if (mniOpen == null)
        {
            mniOpen = new JMenuItem();
            mniOpen.setText("Open");
            mniOpen.setMnemonic('O');
            mniOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
                    ActionEvent.CTRL_MASK));

            mniOpen.addActionListener(openAction);
        }
        return mniOpen;
    }

    /**
     * This method initializes mniCopy
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getMniNew()
    {
        if (mniNew == null)
        {
            mniNew = new JMenuItem();
            mniNew.setText("New");
            mniNew.setMnemonic('N');
            mniNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
                    ActionEvent.CTRL_MASK));

            mniNew.addActionListener(newAction);
        }
        return mniNew;
    }

    protected void OnMniNew()
    {
        closeDocument();        
        getTextPane().setText("");
        setChanged(false);        
    }
    
    protected void closeDocument()
    {
        if ((file != null) && (hasChanged()))
        {
            int result = JOptionPane.showConfirmDialog(this,
                    "Do you wanna save the file ?");

            if (result == JOptionPane.OK_OPTION)
            {
                OnMniSave();
            }
            else
            {
                if (result == JOptionPane.CANCEL_OPTION)
                    return;
            }
        }
        
        file = null;
    }
    

    protected void OnMniOpen()
    {
        closeDocument();
        
        if (getFileChooser().showOpenDialog(this) == JFileChooser.CANCEL_OPTION)
            return;

        open(getFileChooser().getSelectedFile());
    }

    /**
     * This method initializes mniSaveAs
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getMniSaveAs()
    {
        if (mniSaveAs == null)
        {
            mniSaveAs = new JMenuItem();
            mniSaveAs.setText("Save As ...");
            mniSaveAs.setMnemonic('A');
            mniSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,
                    ActionEvent.CTRL_MASK));
            mniSaveAs.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    OnMniSaveAs();
                }
            });
        }
        return mniSaveAs;
    }

    protected void OnMniSaveAs()
    {
        if (getFileChooser().showOpenDialog(this) == JFileChooser.CANCEL_OPTION)
            return;

        setFile(getFileChooser().getSelectedFile());
        if (file.exists())
            JOptionPane.showMessageDialog(this, "Overwrite ?");

        save();
    }
    
    /**
     * This method initializes mniSave
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getMniSave()
    {
        if (mniSave == null)
        {
            mniSave = new JMenuItem();
            mniSave.setText("Save");
            mniSave.setMnemonic('S');
            mniSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                    ActionEvent.CTRL_MASK));
            mniSave.addActionListener(saveAction);
        }
        return mniSave;
    }

    protected void OnMniSave()
    {
        if (file == null)
            OnMniSaveAs();
        else
        {
            if (hasChanged())
                save();
        }
    }
    protected void OnMniComp()
    {   
        String local = System.getProperty("user.dir");
        String comando="cmd /c java -jar "+local+"\\lanq.jar " +file;
        ExecComando.executar("cmd.exe","/C",comando); 
    }


    /**
     * This method initializes mniClose
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getMniClose()
    {
        if (mniClose == null)
        {
            mniClose = new JMenuItem();
            mniClose.setText("Close");
            mniClose.setMnemonic('C');
            mniClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
                    ActionEvent.CTRL_MASK));
            mniClose.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    OnClose();
                }
            });
        }
        return mniClose;
    }

    protected void OnClose()
    {
        super.processWindowEvent(new WindowEvent(this,
                WindowEvent.WINDOW_CLOSING));
    }

    /**
     * This method initializes Copy
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getMniCopy()
    {
        if (mniCopy == null)
        {
            mniCopy = new JMenuItem();
            mniCopy.setText("Copy");
            mniCopy.setMnemonic('C');
            mniCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
                    ActionEvent.CTRL_MASK));
            mniCopy.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    getTextPane().copy();
                }
            });
        }
        return mniCopy;
    }

    /**
     * This method initializes mniPaste
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getMniPaste()
    {
        if (mniPaste == null)
        {
            mniPaste = new JMenuItem();
            mniPaste.setText("Paste");
            mniPaste.setMnemonic('P');
            mniPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
                    ActionEvent.CTRL_MASK));
            mniPaste.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    getTextPane().paste();
                }
            });
        }
        return mniPaste;
    }

    /**
     * This method initializes mniCut
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getMniCut()
    {
        if (mniCut == null)
        {
            mniCut = new JMenuItem();
            mniCut.setText("Cut");
            mniCut.setMnemonic('t');
            mniCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
                    ActionEvent.CTRL_MASK));
            mniCut.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    getTextPane().cut();
                }
            });
        }
        return mniCut;
    }

    public JTextPane getTextPane()
    {
        if (textPane == null)
        {
            textPane = new JTextPane();
            textPane.setFont( new Font("monospaced", Font.PLAIN, 12) );
            
            textPane.getDocument().addUndoableEditListener(getUndoableListener());
            textPane.getDocument().addDocumentListener(new DocumentListener()
            {
                public void changedUpdate(DocumentEvent e)
                {
                }

                public void insertUpdate(DocumentEvent e)
                {
                    setChanged(true);
                }

                public void removeUpdate(DocumentEvent e)
                {
                    setChanged(true);
                }

            });
            setTabs(4);
        }

        return textPane;
    }

    public void setTabs(int charactersPerTab)
    {
        FontMetrics fm = textPane.getFontMetrics( textPane.getFont() );
        int charWidth = fm.charWidth( 'w' );
        int tabWidth = charWidth * charactersPerTab;
 
        TabStop[] tabs = new TabStop[10];
 
        for (int j = 0; j < tabs.length; j++)
        {
            int tab = j + 1;
            tabs[j] = new TabStop( tab * tabWidth );
        }
 
        TabSet tabSet = new TabSet(tabs);
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setTabSet(attributes, tabSet);
        int length = textPane.getDocument().getLength();
        textPane.getStyledDocument().setParagraphAttributes(0, length, attributes, true);
    }
    
    private MyUndoableListener getUndoableListener()
    {
        if (myUndoable == null)
            myUndoable = new MyUndoableListener();

        return myUndoable;
    }

    private MyUndoableListener createUndoableListener()
    {   
        myUndoable = new MyUndoableListener();
        return myUndoable;
    }
    
    protected void setChanged(boolean changed)
    {
        String title = "LanQ Editor";

        if (file != null)
            title += " - " + file.getName();
        else
            title += " - UNKNOWN";

        if (changed == true)
            title += " *";

        this.setTitle(title);
        this.changed = changed;
    }

    protected boolean hasChanged()
    {
        return changed;
    }

    /**
     * This method initializes jScrollPane
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getJScrollPane()
    {
        if (jScrollPane == null)
        {
            jScrollPane = new JScrollPane();
            jScrollPane.setName("scrlEditorPane");
            jScrollPane.setViewportView(getTextPane());
        }
        return jScrollPane;
    }

    private JFileChooser getFileChooser()
    {
        if (fileChooser == null)
        {
            fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Choose target directory");
        }
        return fileChooser;
    }

    public File getFile()
    {
        return file;
    }

    /**
     * This method initializes mniFind
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getMniFind()
    {
        if (mniFind == null)
        {
            mniFind = new JMenuItem();
            mniFind.setText("Find and Replace");
            mniFind.setMnemonic('F');
            mniFind.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,
                    ActionEvent.CTRL_MASK));
            mniFind.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    OnMniFind();
                }
            });
        }
        return mniFind;
    }

    protected void OnMniFind()
    {
        new JSearcher(this).setVisible(true);
    }


    /**
     * This method initializes pnlText
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getPnlText()
    {
        if (pnlText == null)
        {
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.fill = GridBagConstraints.BOTH;
            gridBagConstraints1.gridx = 0;
            gridBagConstraints1.gridy = 0;
            gridBagConstraints1.ipadx = 0;
            gridBagConstraints1.ipady = 0;
            gridBagConstraints1.weightx = 1.0;
            gridBagConstraints1.weighty = 1.0;
            gridBagConstraints1.insets = new Insets(0, 0, 0, 0);
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.insets = new Insets(0, 0, 0, 0);
            gridBagConstraints.gridy = 1;
            gridBagConstraints.ipadx = 0;
            gridBagConstraints.ipady = 0;
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints.weightx = 0.0;
            gridBagConstraints.gridx = 0;
            pnlText = new JPanel();
            pnlText.setLayout(new GridBagLayout());
            pnlText.add(getPnlStatus(), gridBagConstraints);
            pnlText.add(getJScrollPane(), gridBagConstraints1);
        }
        return pnlText;
    }

    /**
     * This method initializes pnlStatus
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getPnlStatus()
    {
        if (pnlStatus == null)
        {
            pnlStatus = new JStatusPanel(getTextPane());
        }
        return pnlStatus;
    }

    /**
     * This method initializes mniUndo
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getMniUndo()
    {
        if (mniUndo == null)
        {
            mniUndo = new JMenuItem();
            mniUndo.setText("Undo");
            mniUndo.setMnemonic('U');
            mniUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
                    ActionEvent.CTRL_MASK));
            mniUndo.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    getUndoableListener().undo();
                }
            });
        }
        return mniUndo;
    }

    /**
     * This method initializes mniRedo
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getMniRedo()
    {
        if (mniRedo == null)
        {
            mniRedo = new JMenuItem();
            mniRedo.setText("Redo");
            mniRedo.setMnemonic('R');
            mniRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,
                    ActionEvent.CTRL_MASK));

            mniRedo.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    getUndoableListener().redo();
                }
            });
        }
        return mniRedo;
    }
    
    
    protected void renewUndoable()
    {                
        getTextPane().getDocument().removeUndoableEditListener(getUndoableListener());                
        getTextPane().getDocument().addUndoableEditListener(createUndoableListener());
    }

    class NewAction extends AbstractAction
    {
        NewAction()
        {
            super("new");
        }

        public void actionPerformed(ActionEvent e)
        {   
            OnMniNew();
            renewUndoable();
        }
    }
    

    class OpenAction extends AbstractAction
    {
        OpenAction()
        {
            super("open");
        }

        public void actionPerformed(ActionEvent e)
        {            
            OnMniOpen();
            renewUndoable();
        }
    }

    class SaveAction extends AbstractAction
    {
        SaveAction()
        {
            super("save");
        }

        public void actionPerformed(ActionEvent e)
        {
            OnMniSave();
        }
    }
    class CompAction extends AbstractAction
    {
        CompAction()
        {
            super("Compile");
        }

        public void actionPerformed(ActionEvent e)
        {
            OnMniSave();
            OnMniComp();
        }
    }

    /**
     * This method initializes mnuAbout	
     * 	
     * @return javax.swing.JMenu	
     */
    private JMenu getMnuAbout()
    {
        if (mnuAbout == null)
        {
            mnuAbout = new JMenu();
            mnuAbout.setText("About");
            mnuAbout.add(getMniAbout());
        }
        return mnuAbout;
    }

    /**
     * This method initializes mniAbout	
     * 	
     * @return javax.swing.JMenuItem	
     */
    private JMenuItem getMniAbout()
    {
        if (mniAbout == null)
        {
            mniAbout = new JMenuItem();
            mniAbout.setText("Version");
            mniAbout.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {                    
                    getJAbout().setVisible(true);                                        
                }
            });
        }
        return mniAbout;
    }

    protected JAbout getJAbout()
    {
        if (jAbout == null)
        {
            jAbout = new JAbout(this);
            jAbout.setModal(true);
        }
        
        centralize(jAbout);
        return jAbout;
    }

} // @jve:decl-index=0:visual-constraint="10,10"

