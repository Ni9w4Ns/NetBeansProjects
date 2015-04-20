package imagoinspectoris;

import java.awt.*;
import java.io.*;
import java.util.Enumeration;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.*;
import javax.swing.tree.*;


public class tagTree implements TreeSelectionListener {

    public JTree tree;
    private DefaultTreeModel model;
    private DefaultMutableTreeNode rootNode;
    private JTextField tagText;
    private tagTree tagTree;
    private File file;
    private Image img;
    private String nodeName;
    
    public JTextField getTagText() {
      return this.tagText;
    }
    
    public void setTagText(final JTextField tagText) {
      this.tagText = tagText;
    }
    
    public tagTree() {
        //DefaultMutableTreeNode philosophersNode = new getPhilosopherTree();
        //model = new DefaultTreeModel(philosophersNode);
        //tree = new JTree(model);
        loadTree();
        getTree();
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

        if (node == null) {
            return;
        }

        Object myNode = node.getUserObject();
            if (node.isLeaf()) {
                if (nodeName != null) {
                    Toolkit myToolkit = Toolkit.getDefaultToolkit();
                    img = myToolkit.getImage(nodeName);
                    img = img.getScaledInstance(300, -1, Image.SCALE_DEFAULT);
                }
         }
    }
    
    private TreePath find(DefaultMutableTreeNode rootpath, String myStr) {
        Enumeration<DefaultMutableTreeNode> e = rootpath.depthFirstEnumeration();
        while (e.hasMoreElements()){
            DefaultMutableTreeNode node = e.nextElement();
            if (node.toString().equalsIgnoreCase(myStr)) {
                return new TreePath(node.getPath());
            }
        }
        return null;
    }
    
    public void addTags() {
        String file = getFile();
        if (file == null) {
            JOptionPane.showMessageDialog(tagTree.tree, "Please open an image file first!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        DefaultMutableTreeNode parent = getSelectedNode();

        String getTagName = getTagText().getText();
        String fname = file.getName();
        int pos = fname.lastIndexOf(".");
        if (pos > 0) {
            fname = fname.substring(0, pos);
        }
        String fileLocation = file.toString();

        if (getTagName.length() == 0) {
            JOptionPane.showMessageDialog(tagTree.tree, "Please enter a tag first!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            rootNode = (DefaultMutableTreeNode) tree.getModel().getRoot();
        }

        if (find(rootNode, getTagName) == null) {
            DefaultMutableTreeNode getTagTree = new DefaultMutableTreeNode(getTagName);

            this.model.insertNodeInto(getTagTree, rootNode, rootNode.getChildCount());

            this.model.insertNodeInto(new DefaultMutableTreeNode(new myTag(getTagName, fileLocation, fname)),
                    getTagTree, getTagTree.getChildCount());
            TreePath newPath = find(rootNode, getTagName);
            tree.setSelectionPath(newPath);
            tree.scrollPathToVisible(newPath);
        } else {
            TreePath newPath = find(rootNode, getTagName);
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) newPath.getLastPathComponent();
            this.model.insertNodeInto(new DefaultMutableTreeNode(new myTag(getTagName, fileLocation, fname)),
                    node, node.getChildCount());
            TreePath myPath = (find(rootNode, getTagName));
            tree.setSelectionPath(myPath);
            tree.scrollPathToVisible(myPath);
        }

        saveTree();
        //tree.validate();
        tree.repaint();
    }

    public void saveTree() {
        try {
            FileOutputStream fos = new FileOutputStream("MyObject");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(model);

        } catch (Exception e) {
            //TODO catch exception here
        }

    }
    
    public void saveTreeButton() {
                    try {
            FileOutputStream fos = new FileOutputStream("MyObject");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(model);
            JOptionPane.showMessageDialog(tagTree.tree, "Your albums have been saved, "
                    + "and will be automatically loaded next time you run the program.",
                    "Save successful", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            //TODO catch exception here
        }
    }
                    
    private DefaultMutableTreeNode getSelectedNode(){
        return (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
    }
    
    public void loadTree() {

        try {
            FileInputStream fis = new FileInputStream("MyObject");
            ObjectInputStream ois = new ObjectInputStream(fis);
            model = (DefaultTreeModel) ois.readObject();
            tree = new JTree(model);
        } catch (Exception e) {
            //TODO: catch exception
        }

        if (model == null) {
            DefaultMutableTreeNode tagNode = getTree();
            model = new DefaultTreeModel(tagNode);
        }
    }

    public void removeTags() {
        DefaultMutableTreeNode selectedNode = getSelectedNode();
        if (selectedNode == null) {
            JOptionPane.showMessageDialog(tagTree.tree, "Select a tag first!", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (selectedNode != null) {
            model.removeNodeFromParent(selectedNode);

        }
    }
    
    public void changeTags(){
        removeTags();
        addTags();
    }

    private DefaultMutableTreeNode getTree(){
        this.rootNode = new DefaultMutableTreeNode("Photo Albums");
        return rootNode;
    }
/*
    private void getTree() {
        this.rootNode = new DefaultMutableTreeNode("Photo Albums");
        DefaultMutableTreeNode ancient = new DefaultMutableTreeNode("Sample");
        rootNode.add(ancient);

        ancient.add(new DefaultMutableTreeNode(new imageTag("Birthday")));

        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("Night out");
        rootNode.add(newNode);

        model = new DefaultTreeModel(rootNode);
        tree = new JTree(model);
    }*/
    
    
}