/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagoinspectoris;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Ni9w4Ns
 */
public class ImagoInspectoris extends javax.swing.JFrame{// implements ActionListener {
     
    public ImagoInspectoris() {
        Components();
    }
    @SuppressWarnings("unchecked")
    private void Components() {

        // set up the viewer itself with alingment for the various parts
        picViewer = new JScrollPane();
        
        setTitle("Imago Inspectoris v1.0");
        setSize(350, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
   
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(picViewer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(picViewer, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
        );

        pack();

        menuBar = new JMenuBar();

        setJMenuBar(menuBar);

        //The File Menu
        menuFile = new JMenu("File");
        menuFile.setMnemonic(KeyEvent.VK_F);

        menuFileOpen = CreateMenuItem(menuFile, 0, "Open", null, KeyEvent.VK_O, "Open a new file");
        menuFileOpen.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileOpen_actionPerformed(evt);
            }
        });
        menuFileSave = CreateMenuItem(menuFile, 0, "Save", null, KeyEvent.VK_S, "Save the current file");
        menuFileSaveAs = CreateMenuItem(menuFile, 0, "Save As... ", null, KeyEvent.VK_A, "Choose where to save the current file");
        menuFile.addSeparator();
        menuFileClear = CreateMenuItem(menuFile, 0, "Clear", null, KeyEvent.VK_C, "Clear the current file");
        menuFileOpen.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileClear_actionPerformed(evt);
            }
        });
        menuFileExit = CreateMenuItem(menuFile, 0, "Exit", null, KeyEvent.VK_E, "Exit the program");

        menuBar.add(menuFile);

        //The Edit Menu
        menuEdit = new JMenu("Edit");
        menuEdit.setMnemonic(KeyEvent.VK_E);

        menuBar.add(menuEdit);

    //TODO: populate Edit Menu
        //The Help Menu
        menuHelp = new JMenu("Help");
        menuHelp.setMnemonic(KeyEvent.VK_H);

        menuHelpManual = CreateMenuItem(menuHelp, 0, "Manual", null, KeyEvent.VK_M, "View user Manual");
        menuHelpAbout = CreateMenuItem(menuHelp, 0, "About", null, KeyEvent.VK_A, "View version information");

        menuBar.add(menuHelp);
    }

    //Helper method to create Menu Items
    public JMenuItem CreateMenuItem(JMenu menu, int iType, String sText,
            ImageIcon image, int acceleratorKey, String sToolTip) {

        JMenuItem menuItem;

        switch (iType) {
            case 1:
                menuItem = new JRadioButtonMenuItem();
                break;

            case 2:
                menuItem = new JCheckBoxMenuItem();
                break;

            default:
                menuItem = new JMenuItem();
                break;
        }
        menuItem.setText(sText);
        if (image != null) {
            menuItem.setIcon(image);
        }
        if (acceleratorKey > 0) {
            menuItem.setMnemonic(acceleratorKey);
        }
        if (sToolTip != null) {
            menuItem.setToolTipText(sToolTip);
        }
        //menuItem.addActionListener(this);
        menu.add(menuItem);

        return menuItem;
    }

    //Action Listeners for each button in the menus
    
    JLabel jlab = new JLabel();
    
    private void menuFileOpen_actionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser jfc = new JFileChooser();
        
        if(jfc.showOpenDialog(menuFile) == JFileChooser.APPROVE_OPTION){
            java.io.File f = jfc.getSelectedFile();
            
            jlab.setIcon(new ImageIcon(f.toString()));
            
            jlab.setHorizontalAlignment(JLabel.CENTER);
            
            picViewer.getViewport().add(jlab);
        }
    }    

    private void menuFileClear_actionPerformed(java.awt.event.ActionEvent evt) {
        jlab.setIcon(null);
    }
//Main
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        try { //Builds the window, using Windows look and feel
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex){ //Error handling 
            System.out.println("Look and feel not found!");
        }
        
        //Instantiates the ImagoInspectoris class and displays the frame
        ImagoInspectoris mainFrame = new ImagoInspectoris();
        mainFrame.setVisible(true);
    }
    
    private JPanel topPanel;
    private JMenuBar menuBar;
    private JMenu menuFile, menuEdit, menuHelp;
    private JMenuItem menuFileOpen, menuFileSave, menuFileSaveAs, menuFileExit, menuHelpManual,
            menuHelpAbout, menuFileClear;
    private JScrollPane picViewer;
}
