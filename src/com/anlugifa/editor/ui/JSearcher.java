package com.anlugifa.editor.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.anlugifa.editor.base.SearchMachine;


public class JSearcher extends JDialog
{
    private JPanel jContentPane = null;

    private JLabel lblWord = null;

    private JTextField txtWord = null;

    private JButton btnSearch = null;

    private JLabel lblReplace = null;

    private JTextField txtReplace = null;

    private JButton btnReplaceAndNext = null;

    private SearchMachine searchMachine;

    private JEditor scriptEditor;

    private JButton btnNext = null;

    private JButton btnPrevious = null;

    private JButton btnReplaceAll = null;

    /**
     * @param owner
     */
    public JSearcher(JEditor owner)
    {
        super(owner);
        initialize();
        setParent(owner);
        owner.centralize(this);
        searchMachine = new SearchMachine(scriptEditor.getTextPane());        
    }

    private void setParent(JEditor editor)
    {
        this.scriptEditor = editor;
    }

    @Override
    public JEditor getParent()
    {
        return scriptEditor;
    }
    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize()
    {
        this.setSize(316, 202);
        this.setMinimumSize(new Dimension(316, 202));
        this.setResizable(false);
        this.setTitle("Search and Replace");
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
            GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
            gridBagConstraints10.insets = new Insets(5, 5, 5, 5);
            gridBagConstraints10.gridwidth = 2;
            gridBagConstraints10.gridx = 0;
            gridBagConstraints10.gridy = 5;
            gridBagConstraints10.anchor = GridBagConstraints.WEST;
            gridBagConstraints10.gridheight = 1;
            GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
            gridBagConstraints9.insets = new Insets(5, 5, 5, 5);
            gridBagConstraints9.gridwidth = 2;
            gridBagConstraints9.gridx = 0;
            gridBagConstraints9.gridy = 3;
            gridBagConstraints9.ipadx = 0;
            gridBagConstraints9.ipady = 0;
            gridBagConstraints9.anchor = GridBagConstraints.WEST;
            gridBagConstraints9.gridheight = 1;
            GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
            gridBagConstraints8.insets = new Insets(5, 5, 5, 5);
            gridBagConstraints8.gridy = 7;
            gridBagConstraints8.ipadx = 50;
            gridBagConstraints8.ipady = 0;
            gridBagConstraints8.anchor = GridBagConstraints.WEST;
            gridBagConstraints8.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints8.gridx = 2;
            GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
            gridBagConstraints7.insets = new Insets(5, 5, 5, 5);
            gridBagConstraints7.gridy = 7;
            gridBagConstraints7.ipadx = 50;
            gridBagConstraints7.ipady = 0;
            gridBagConstraints7.anchor = GridBagConstraints.WEST;
            gridBagConstraints7.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints7.gridx = 1;
            GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
            gridBagConstraints6.insets = new Insets(5, 5, 5, 5);
            gridBagConstraints6.gridy = 3;
            gridBagConstraints6.ipadx = 50;
            gridBagConstraints6.ipady = 0;
            gridBagConstraints6.anchor = GridBagConstraints.WEST;
            gridBagConstraints6.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints6.gridx = 1;
            GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
            gridBagConstraints5.insets = new Insets(5, 5, 5, 5);
            gridBagConstraints5.gridx = 2;
            gridBagConstraints5.gridy = 3;
            gridBagConstraints5.ipadx = 50;
            gridBagConstraints5.ipady = 0;
            gridBagConstraints5.anchor = GridBagConstraints.WEST;
            gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints5.gridheight = 2;
            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.insets = new Insets(5, 5, 5, 5);
            gridBagConstraints4.gridy = 0;
            gridBagConstraints4.ipady = 0;
            gridBagConstraints4.ipadx = 50;
            gridBagConstraints4.anchor = GridBagConstraints.WEST;
            gridBagConstraints4.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints4.gridx = 2;
            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints3.gridx = 1;
            gridBagConstraints3.gridy = 1;
            gridBagConstraints3.ipadx = 0;
            gridBagConstraints3.weightx = 1.0;
            gridBagConstraints3.insets = new Insets(5, 5, 5, 5);
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.insets = new Insets(5, 5, 5, 5);
            gridBagConstraints2.gridy = 1;
            gridBagConstraints2.ipadx = 2;
            gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints2.gridx = 0;
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints1.gridx = 1;
            gridBagConstraints1.gridy = 0;
            gridBagConstraints1.ipadx = 0;
            gridBagConstraints1.weightx = 1.0;
            gridBagConstraints1.insets = new Insets(5, 5, 5, 5);
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.insets = new Insets(5, 5, 5, 5);
            gridBagConstraints.gridy = 0;
            gridBagConstraints.ipadx = 6;
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints.gridx = 0;
            lblReplace = new JLabel();
            lblReplace.setText("Replace:");
            lblWord = new JLabel();
            lblWord.setText("Find for:");
            jContentPane = new JPanel();
            jContentPane.setLayout(new GridBagLayout());
            jContentPane.add(lblWord, gridBagConstraints);
            jContentPane.add(getTxtWord(), gridBagConstraints1);
            jContentPane.add(lblReplace, gridBagConstraints2);
            jContentPane.add(getTxtReplace(), gridBagConstraints3);
            jContentPane.add(getBtnSearch(), gridBagConstraints4);
            jContentPane.add(getBtnReplaceAndFind(), gridBagConstraints5);
            jContentPane.add(getBtnNext(), gridBagConstraints6);
            jContentPane.add(getBtnPrevious(), gridBagConstraints7);
            jContentPane.add(getBtnReplaceAll(), gridBagConstraints8);
        }
        return jContentPane;
    }

    /**
     * This method initializes txtWord
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTxtWord()
    {
        if (txtWord == null)
        {
            txtWord = new JTextField();
        }
        return txtWord;
    }

    /**
     * This method initializes btnSearch
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnSearch()
    {
        if (btnSearch == null)
        {
            btnSearch = new JButton();
            btnSearch.setText("Find");
            btnSearch.setPreferredSize(new Dimension(50, 20));
            btnSearch.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    OnBtnFind();
                }
            });
        }
        return btnSearch;
    }

    protected void OnBtnFind()
    {
        String text = getTxtWord().getText();
        if (text.equals(""))
            return;

        searchMachine.find(text);
        OnBtnNext();
    }
    
    /**
     * This method initializes btnNext
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnNext()
    {
        if (btnNext == null)
        {
            btnNext = new JButton();
            btnNext.setText("Next");
            btnNext.setPreferredSize(new Dimension(50, 20));
            btnNext.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                	OnBtnNext();
                }
            });
        }
        return btnNext;
    }

    protected void OnBtnNext()
    {
        String text = getTxtWord().getText();
        if (text.equals(""))
            return;

        searchMachine.next(text);
    }

    /**
     * This method initializes txtReplace
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTxtReplace()
    {
        if (txtReplace == null)
        {
            txtReplace = new JTextField();
        }
        return txtReplace;
    }

    /**
     * This method initializes btnReplaceAndFind
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnReplaceAndFind()
    {
        if (btnReplaceAndNext == null)
        {
            btnReplaceAndNext = new JButton();
            btnReplaceAndNext.setText("Replace");
            btnReplaceAndNext.setPreferredSize(new Dimension(50, 20));
            btnReplaceAndNext.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    OnBtnReplace();                    
                }
            });
        }
        return btnReplaceAndNext;
    }

    protected void OnBtnReplace()
    {
        String oldString= getTxtWord().getText();
        if (oldString.equals(""))
            return;

        String newString= getTxtReplace().getText();
        
        searchMachine.replaceSelected(newString);
    }


    /**
     * This method initializes btnPrevious
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnPrevious()
    {
        if (btnPrevious == null)
        {
            btnPrevious = new JButton();
            btnPrevious.setText("Previous");
            btnPrevious.setPreferredSize(new Dimension(50, 20));
            btnPrevious.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    OnBtnPrevious();
                }
            });
        }
        return btnPrevious;
    }

    protected void OnBtnPrevious()
    {
        String text = getTxtWord().getText();
        if (text.equals(""))
            return;

        searchMachine.previous(text);
    }

    /**
     * This method initializes btnReplaceAll	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getBtnReplaceAll()
    {
        if (btnReplaceAll == null)
        {
            btnReplaceAll = new JButton();
            btnReplaceAll.setText("Replace All");
            btnReplaceAll.setPreferredSize(new Dimension(50, 20));
            btnReplaceAll.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    OnBtnReplaceAll();
                }
            });
        }
        return btnReplaceAll;
    }

    protected void OnBtnReplaceAll()
    {
        String oldString= getTxtWord().getText();
        if (oldString.equals(""))
            return;

        String newString= getTxtReplace().getText();
        if (newString.equals(""))
            return;
        
        searchMachine.replaceAll(oldString, newString);
    }


} // @jve:decl-index=0:visual-constraint="10,10"
