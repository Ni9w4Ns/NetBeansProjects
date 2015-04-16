package imagoinspectoris;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;
import javax.swing.JOptionPane;
import javax.swing.border.MatteBorder;
import javax.swing.event.*;
import javax.swing.tree.*;

class imageTag implements
        java.io.Serializable {

    public String name;

    public imageTag(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}

public class tagTree implements TreeSelectionListener {//extends JFrame 

    public static JTree tree;
    private DefaultTreeModel model;
    private DefaultMutableTreeNode rootNode;

    public tagTree() {
        //DefaultMutableTreeNode philosophersNode = new getPhilosopherTree();
        //model = new DefaultTreeModel(philosophersNode);
        //tree = new JTree(model);
        //loadTree();
        getTree();
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

        if (node == null) {
            return;
        }

        if (node.getUserObject() instanceof imageTag) {
            if (node.isLeaf()) {
                imageTag i = (imageTag) node.getUserObject();
                System.out.println(i.name + " ");
            } else {
                System.out.println("Branch");
            }
        }
    }

    public void addTags() {
        DefaultMutableTreeNode parent = getSelectedNode();
        if (parent == null) {
            JOptionPane.showMessageDialog(tagTree.tree, "Select a tag first!", "Error",
                    JOptionPane.ERROR_MESSAGE);

            return;
        }
        String name = JOptionPane.showInputDialog(tagTree.tree, "Enter tag name:");
        model.insertNodeInto(new DefaultMutableTreeNode(new imageTag(name)), parent, parent.getChildCount());

    }

    public void saveTree() {
        try {
            FileOutputStream fos = new FileOutputStream("MyObject");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(model);

        } catch (Exception e) {
            System.out.println("not save it");

        }

    }

    public void loadTree() {

        try {
            FileInputStream fis = new FileInputStream("MyObject");
            ObjectInputStream ois = new ObjectInputStream(fis);
            model = (DefaultTreeModel) ois.readObject();
            tree = new JTree(model);
            System.out.println("got it");
        } catch (Exception e) {
            System.out.println("not got it");
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

    private DefaultMutableTreeNode getSelectedNode() {
        return (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
    }

    private void getTree() {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Image Tags");
        DefaultMutableTreeNode ancient = new DefaultMutableTreeNode("Sample");
        rootNode.add(ancient);

        ancient.add(new DefaultMutableTreeNode(new imageTag("Birthday")));

        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("Night out");
        rootNode.add(newNode);

        model = new DefaultTreeModel(rootNode);
        tree = new JTree(model);
    }

    public static void main(String args[]) {
        new tagTree();
    }
}
