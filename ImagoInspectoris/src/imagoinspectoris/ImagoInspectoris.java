/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagoinspectoris;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout.*;
import javax.swing.border.*;

/**
 *
 * @author Ni9w4Ns
 */
public class ImagoInspectoris extends javax.swing.JFrame{
    private Component frame;
// implements ActionListener {
     
    public ImagoInspectoris() {
        Components();
    }
    //@SuppressWarnings("unchecked")
    private void Components() {

        // set up the viewer itself with alingment for the various parts
        picViewer = new JScrollPane();
        treeViewer = new JScrollPane();
        tagTree = new JTree();
        
        setTitle("Imago Inspectoris v1.0");
        setSize(850, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //initialise the tree
        tagTree.setBorder(new MatteBorder(null));
        treeViewer.setViewportView(tagTree);
   
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(treeViewer, GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(picViewer, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addComponent(picViewer)
            .addGroup(layout.createSequentialGroup()
                .addComponent(treeViewer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, 
                GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        menuBar = new JMenuBar();

        setJMenuBar(menuBar);

        //The File Menu
        menuFile = new JMenu("File");
        menuFile.setMnemonic(KeyEvent.VK_F);

        menuFileOpen = CreateMenuItem(menuFile, 0, "Open", null, KeyEvent.VK_O, "Open a new file");
        menuFileOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                menuFileOpen_actionPerformed(evt);
            }
        });
        menuFileSave = CreateMenuItem(menuFile, 0, "Save", null, KeyEvent.VK_S, "Save the current file");
        menuFileSaveAs = CreateMenuItem(menuFile, 0, "Save As... ", null, KeyEvent.VK_A, "Choose where to save the current file");
        menuFile.addSeparator();
        menuFileClear = CreateMenuItem(menuFile, 0, "Clear", null, KeyEvent.VK_C, "Clear the current file");
        menuFileClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                menuFileClear_actionPerformed(evt);
            }
        });
        menuFileExit = CreateMenuItem(menuFile, 0, "Exit", null, KeyEvent.VK_E, "Exit the program");
        menuFileExit.addActionListener(new java.awt.event.ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                menuFileExit_actionPerformed(evt);
            }
        });
        
        menuBar.add(menuFile);

        //The Edit Menu
        menuEdit = new JMenu("Edit");
        menuEdit.setMnemonic(KeyEvent.VK_E);
        
        menuEditAdd = CreateMenuItem(menuEdit, 0, "Add Tag", null, KeyEvent.VK_A, "Add a tag to the current image");
        menuEditChange = CreateMenuItem(menuEdit, 0, "Change Tag", null, KeyEvent.VK_C, "Change a current tag");
        menuEditDelete = CreateMenuItem(menuEdit, 0, "Delete Tag", null, KeyEvent.VK_D, "Delete an existing tag");
        
        menuBar.add(menuEdit);

    
        //The Help Menu
        menuHelp = new JMenu("Help");
        menuHelp.setMnemonic(KeyEvent.VK_H);

        menuHelpManual = CreateMenuItem(menuHelp, 0, "Manual", null, KeyEvent.VK_M, "View user Manual");
        menuHelpAbout = CreateMenuItem(menuHelp, 0, "About", null, KeyEvent.VK_A, "View version information");
        menuHelpAbout.addActionListener(new java.awt.event.ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                menuHelpAbout_actionPerformed(evt);
        }     
    });
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
    private void menuFileExit_actionPerformed(ActionEvent evt) {
        System.exit(0);
    }

    private void menuFileClear_actionPerformed(ActionEvent evt) {
        jlab.setIcon(null);
    }
    
    private void menuHelpAbout_actionPerformed(ActionEvent evt){
        JOptionPane verisonInfo = new JOptionPane();
        versionInfo.showMessageDialog(frame, "Imago Inspectoris\n"
                + "Version 1.0\n"
                + "Published 24/04/2015 by 0506344");
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
    //declarations
    private JPanel topPanel;
    private JMenuBar menuBar;
    private JMenu menuFile, menuEdit, menuHelp;
    private JMenuItem menuFileOpen, menuFileSave, menuFileSaveAs, menuFileExit, menuHelpManual,
            menuHelpAbout, menuFileClear, menuEditAdd, menuEditDelete, menuEditChange;
    private JScrollPane picViewer, treeViewer;
    private JOptionPane versionInfo;
    private JTree tagTree;

}
