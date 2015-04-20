package imagoinspectoris;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
 * @author 0506344
 */
public class ImagoInspectoris extends JFrame{

    //declarations
    private JPanel topPanel;
    private JMenuBar menuBar;
    private JMenu menuFile, menuEdit, menuHelp;
    private JMenuItem menuFileOpen, menuFileSaveTree, menuFileLoadTree, menuFileSaveAs, menuFileExit, menuHelpManual,
            menuHelpAbout, menuFileClear, menuEditAdd, menuEditDelete, menuEditChange;
    private JButton tbAddTag, tbChangeTag, tbDeleteTag, tbOpenPic;
    private JScrollPane treeViewer;
    private picViewer picViewer;
    private tagTree tagTree;
    private File file;
    private Image img;
    private JToolBar toolbar;
    
    private Component frame;

    public ImagoInspectoris() {
        Components();
    }

    private void Components() {

        // set up the viewer itself with alignment for the various parts
        setTitle("Imago Inspectoris v1.0");
        setSize(450,450);
        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        add(topPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        picViewer = new picViewer();
        tagTree = new tagTree();
        treeViewer = new JScrollPane(tagTree.tree);

        //initialise the tree viewer
        treeViewer.setViewportView(tagTree.tree);

        //set the layout
        JScrollPane scrollPane1 = new JScrollPane(picViewer, 
                                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scrollPane1, BorderLayout.CENTER);
        picViewer.setPreferredSize(new Dimension(450, 450));
                JScrollPane scrollPane2 = new JScrollPane(treeViewer, 
                                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scrollPane2, BorderLayout.WEST);
        treeViewer.setPreferredSize(new Dimension(150, 450));
        pack();

        //set up the menu bar
        menuBar = new JMenuBar();

        setJMenuBar(menuBar);
        this.add(topPanel, BorderLayout.NORTH);

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
        menuEditDelete.addActionListener(new ActionListener() {
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
        
        //A toolbar to add/remove/change tags
        
        toolbar = new JToolBar();
        
        tbAddTag = addToolBarButton(toolbar, false, "Add Tag", "addTag", 
                "Add a tag to the selected picture");
        tbAddTag.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                menuEditAdd_actionPerformed(evt);
            }
        });
        tbChangeTag = addToolBarButton(toolbar, false, "Change Tag", "changeTag", 
                "Change the selected tag");
        tbChangeTag.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent evt){
               toolbarChangeTag_actionPerformed(evt);
           }
        });
        
        tbOpenPic = addToolBarButton(toolbar, false, "Open Picture", "openPic",
                "Open the selected picture from your album");
        tbOpenPic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                toolbarOpenPic_actionPerformed(evt);
            }
        });
        tbDeleteTag = addToolBarButton(toolbar, false, "Delete Tag", "deleteTag",
                "Delete the selected tag");
        tbDeleteTag.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                menuEditChange_actionPerformed(evt);
            }
        });
        JTextField tagText = new JTextField(30);
        toolbar.add(tagText);
        tagTree.setTagText(tagText);
        topPanel.add(toolbar, BorderLayout.NORTH);
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
    
    //Helper method for toolbar buttons
    
    public JButton addToolBarButton(JToolBar toolbar, boolean useImage, String buttonText,
            String buttonName, String toolTipText){
    
    JButton b;
    
    if (useImage){
        b = new JButton(new ImageIcon (buttonName + ".gif."));
    }else{
        b = new JButton();
    }
    
    toolbar.add(b);
    
    if(buttonText != null){
        b.setText(buttonText);
    }else{
        b.setMargin(new Insets (0,0,0,0));
    }
    
    if (toolTipText != null)
        b.setToolTipText(toolTipText);
    
    b.setActionCommand("Toolbar:" + buttonName);
    
    return b;
    }

    //Methods for opening files 
    
     private class picViewer extends JPanel{
        public void paint(Graphics g){
            g.drawImage(img, 0, 0, this);
        }

        private void clearImage(Graphics g) {
            
        }
    }
    
    public String getFile() {
        String directory = System.getProperty("user.home");
        FileNameExtensionFilter images = new FileNameExtensionFilter("Image files only (.gif, .jpeg, .png, .jpg)",
                "jpg", "jpeg", "png", "gif");
        JFileChooser jfc = new JFileChooser(directory + "\\My Pictures");
        jfc.setFileFilter(images);
        jfc.setAcceptAllFileFilterUsed(false);
        int result = jfc.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            this.file = jfc.getSelectedFile();
            return file.getPath();
        } else {
            return null;
        }
    }
    //Action Listeners for each button in the menus
 
    public void menuFileOpen_actionPerformed(ActionEvent evt) {
        String file = getFile();
        if (file != null){
            Toolkit myToolKit = Toolkit.getDefaultToolkit();
            img = myToolKit.getImage(file);
            img = img.getScaledInstance(350, -1, Image.SCALE_SMOOTH);
            this.repaint();
        }
    }
      
    private void menuFileSaveTree_actionPerformed(ActionEvent evt) {
        tagTree.saveTreeButton();
    }

    private void menuFileLoadTree_actionPerformed(ActionEvent evt) {
        tagTree.loadTree();
    }

    private void menuFileExit_actionPerformed(ActionEvent evt) {
        System.exit(0);
    }
    
    private void toolbarOpenPic_actionPerformed(ActionEvent evt){
        String file = getFile();
        if (file != null){
            Toolkit myToolkit = Toolkit.getDefaultToolkit();
            img = myToolkit.getImage(file);
            img = img.getScaledInstance(350, -1, Image.SCALE_DEFAULT);
            this.repaint();
        }
    }
    
    private void toolbarChangeTag_actionPerformed(ActionEvent evt){
        tagTree.changeTags();
    }

    private void menuEditAdd_actionPerformed(ActionEvent evt) {
        tagTree.addTags();
    }

    private void menuEditChange_actionPerformed(ActionEvent evt) {
        tagTree.removeTags();
    }

    private void menuFileClear_actionPerformed(ActionEvent evt) {
        this.removeAll();
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

}