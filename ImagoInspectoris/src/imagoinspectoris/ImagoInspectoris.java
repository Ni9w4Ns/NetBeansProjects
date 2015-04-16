package imagoinspectoris;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout.*;

 /*
 * @author 0506344
 */
public class ImagoInspectoris extends JFrame {

    private Component frame;
// implements ActionListener {

    public ImagoInspectoris() {
        Components();
    }

    private void Components() {

        // set up the viewer itself with alignment for the various parts
        picViewer = new JScrollPane();
        tagTree = new tagTree();
        treeViewer = new JScrollPane(tagTree.tree);


        setTitle("Imago Inspectoris v1.0");
        setSize(850, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //initialise the tree viewer
        treeViewer.setViewportView(tagTree.tree);


        //set the layout
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(treeViewer, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(picViewer, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(picViewer, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
            .addComponent(treeViewer, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();

        //set up the menu bar

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
        menuFileSaveTree = CreateMenuItem(menuFile, 0, "Save Tree", null, KeyEvent.VK_S, "Save the current tags");
        menuFileSaveTree.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                menuFileSaveTree_actionPerformed(evt);
            }
        });
        
        menuFileLoadTree = CreateMenuItem(menuFile, 0, "Load Tree", null, KeyEvent.VK_L, "Load saved tags");
        menuFileLoadTree.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                menuFileLoadTree_actionPerformed(evt);
            }
        });
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
        menuFileExit.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                menuFileExit_actionPerformed(evt);
            }
        });

        menuBar.add(menuFile);

        //The Edit Menu
        menuEdit = new JMenu("Edit");
        menuEdit.setMnemonic(KeyEvent.VK_E);

        menuEditAdd = CreateMenuItem(menuEdit, 0, "Add Tag", null, KeyEvent.VK_A, "Add a tag to the current image");
        menuEditAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                menuEditAdd_actionPerformed(evt);

            }
        });
        menuEditDelete = CreateMenuItem(menuEdit, 0, "Delete Tag", null, KeyEvent.VK_D, "Delete an existing tag");
        menuEditDelete.addActionListener(new ActionListener(){
          @Override
          public void actionPerformed(ActionEvent evt) {
              menuEditChange_actionPerformed(evt);
          }
        });
        
        menuBar.add(menuEdit);


        //The Help Menu
        menuHelp = new JMenu("Help");
        menuHelp.setMnemonic(KeyEvent.VK_H);

        menuHelpManual = CreateMenuItem(menuHelp, 0, "Manual", null, KeyEvent.VK_M, "View user Manual");
        menuHelpAbout = CreateMenuItem(menuHelp, 0, "About", null, KeyEvent.VK_A, "View version information");
        menuHelpAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
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
        menu.add(menuItem);

        return menuItem;
    }
   
    //Action Listeners for each button in the menus
    JLabel jlab = new JLabel();

    private void menuFileOpen_actionPerformed(ActionEvent evt) {
        JFileChooser jfc = new JFileChooser();

        if (jfc.showOpenDialog(menuFile) == JFileChooser.APPROVE_OPTION) {
            java.io.File f = jfc.getSelectedFile();

            jlab.setIcon(new ImageIcon(f.toString()));

            jlab.setHorizontalAlignment(JLabel.CENTER);

            picViewer.getViewport().add(jlab);
        }
    }
    
    private void menuFileSaveTree_actionPerformed(ActionEvent evt) {
        tagTree.saveTree();
    }
    
    private void menuFileLoadTree_actionPerformed(ActionEvent evt) {
        tagTree.loadTree();
    }

    private void menuFileExit_actionPerformed(ActionEvent evt) {
        System.exit(0);
    }
    
    private void menuEditAdd_actionPerformed(ActionEvent evt) {
        tagTree.addTags();
    }
    
    private void menuEditChange_actionPerformed(ActionEvent evt) {
        tagTree.removeTags();
    }

    private void menuFileClear_actionPerformed(ActionEvent evt) {
        jlab.setIcon(null);
    }

    private void menuHelpAbout_actionPerformed(ActionEvent evt) {
        JOptionPane versionInfo = new JOptionPane();
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
        } catch (ClassNotFoundException ex) { //Error handling 
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
    private JMenuItem menuFileOpen, menuFileSaveTree, menuFileLoadTree, menuFileSaveAs, menuFileExit, menuHelpManual,
            menuHelpAbout, menuFileClear, menuEditAdd, menuEditDelete, menuEditChange;
    private JScrollPane picViewer, treeViewer;
    private JOptionPane versionInfo;
    private tagTree tagTree;
}
