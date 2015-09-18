package com.anlugifa.editor.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;

public class JStatusPanel extends JPanel
{
    private class MyCaretListener implements ChangeListener
    { 
        public void stateChanged(ChangeEvent e)
        {            
            final int line = getLineAtCaret();
            final int col  = getColumnAtCaret();
            
            SwingUtilities.invokeLater(new Runnable()
            {
               public void run()
               {
                   lblLine.setText("Ln " + line);
                   lblColumn.setText("Col " + col);
               }
            });
            
        }
        
        private int getLineAtCaret() 
        {
            int caretPosition = comp.getCaretPosition();
            Element root = comp.getDocument().getDefaultRootElement();
     
            return root.getElementIndex( caretPosition ) + 1;
        }
        
        private int getColumnAtCaret()
        {
            int caretPosition = comp.getCaretPosition();
            Element root = comp.getDocument().getDefaultRootElement();
            int line = root.getElementIndex( caretPosition );
            int lineStart = root.getElement( line ).getStartOffset();
     
            return caretPosition - lineStart + 1;
        }
    }
    
    private static final long serialVersionUID = 1L;
    private JLabel lblLine = null;
    private JLabel lblColumn = null;
    private JPanel pnlSpacelyLeft = null;
    
    private JTextComponent comp;

    /**
     * This is the default constructor
     */
    public JStatusPanel(JTextComponent comp)
    {
        super();        
        
        initialize();
        setTextPane(comp);        
    }

    private void setTextPane(JTextComponent comp)
    {
        this.comp = comp;
        comp.getCaret().addChangeListener(new MyCaretListener());        
    }
    
    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize()
    {
        GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
        gridBagConstraints3.insets = new Insets(0, 0, 0, 0);
        gridBagConstraints3.gridy = 0;
        gridBagConstraints3.ipadx = 1;
        gridBagConstraints3.ipady = 0;
        gridBagConstraints3.fill = GridBagConstraints.BOTH;
        gridBagConstraints3.weightx = 1.0;
        gridBagConstraints3.gridx = 0;
        GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
        gridBagConstraints2.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints2.gridy = 0;
        gridBagConstraints2.gridx = 2;
        GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
        gridBagConstraints1.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints1.gridy = 0;
        gridBagConstraints1.gridx = 1;                
        this.setLayout(new GridBagLayout());
        this.setSize(75, 20);
        this.setPreferredSize(new Dimension(75, 20));
        this.add(getLblLine(), gridBagConstraints1);
        this.add(getLblColumn(), gridBagConstraints2);
        this.add(getPnlSpacelyLeft(), gridBagConstraints3);
    }

    private Component getLblColumn()
    {
        if (lblColumn == null)
        {
            lblColumn = new JLabel();
            lblColumn.setText("Col 1");
        }
        return lblColumn;
    }

    private Component getLblLine()
    {
        if (lblLine == null)
        {
            lblLine = new JLabel();
            lblLine.setText("Ln 1");
            lblLine.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    String line = JOptionPane.showInputDialog(JEditor.getInstance(), "Go to line ?");
                    
                    if (line != null && !line.equals(""))
                        gotoLine(Integer.parseInt(line));
                }
                
                public void gotoLine(int line)
                {
                    Element root = comp.getDocument().getDefaultRootElement();
                    line = Math.max(line, 1);
                    line = Math.min(line, root.getElementCount());
                    comp.setCaretPosition( root.getElement( line - 1 ).getStartOffset() );            
                }
            });
        }
        return lblLine;
    }

    /**
     * This method initializes pnlSpacelyLeft	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getPnlSpacelyLeft()
    {
        if (pnlSpacelyLeft == null)
        {
            pnlSpacelyLeft = new JPanel();
            pnlSpacelyLeft.setLayout(new GridBagLayout());
        }
        return pnlSpacelyLeft;
    }

}  //  @jve:decl-index=0:visual-constraint="10,10"
