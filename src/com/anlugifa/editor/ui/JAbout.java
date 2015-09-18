package com.anlugifa.editor.ui;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JAbout extends JDialog
{

    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JLabel lblName = null;
    private JLabel lblVersion = null;
    private JLabel lblDate = null;
    private JLabel lblHome = null;

    /**
     * @param owner
     */
    public JAbout(Frame owner)
    {
        super(owner);
        initialize();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize()
    {
        this.setSize(354, 200);
        this.setContentPane(getJContentPane());
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane()
    {
        if (jContentPane == null)
        {
            GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
            gridBagConstraints11.gridx = 0;
            gridBagConstraints11.insets = new Insets(5, 5, 5, 5);
            gridBagConstraints11.gridy = 3;
            lblHome = new JLabel();
            lblHome.setText("Based: http://sourceforge.net/projects/highlighter");
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.gridx = 0;
            gridBagConstraints2.insets = new Insets(5, 5, 5, 5);
            gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints2.gridy = 2;
            lblDate = new JLabel();
            lblDate.setText("Home: http://neidert.com.br/pesquisa/editor-lanq/");
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.gridx = 0;
            gridBagConstraints1.anchor = GridBagConstraints.WEST;
            gridBagConstraints1.insets = new Insets(5, 5, 5, 5);
            gridBagConstraints1.gridy = 1;
            lblVersion = new JLabel();
            lblVersion.setText("Version: 1.0 ");
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.ipadx = 0;
            gridBagConstraints.ipady = 0;
            gridBagConstraints.insets = new Insets(5, 5, 5, 5);
            gridBagConstraints.anchor = GridBagConstraints.WEST;
            gridBagConstraints.gridy = 0;
            lblName = new JLabel();
            lblName.setText("LanQ Editor");
            jContentPane = new JPanel();
            jContentPane.setLayout(new GridBagLayout());
            jContentPane.add(lblName, gridBagConstraints);
            jContentPane.add(lblVersion, gridBagConstraints1);
            jContentPane.add(lblDate, gridBagConstraints2);
            jContentPane.add(lblHome, gridBagConstraints11);
        }
        return jContentPane;
    }

}  //  @jve:decl-index=0:visual-constraint="10,10"
