/**
 * JDirectoryChooser.java (c) 2002.6.1
 *
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.1
 * @see javax.swing.JFileChooser
 * @since Java 2
 */

package jjb.toolbox.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import jjb.toolbox.awt.WindowUtil;
import jjb.toolbox.io.DirectoryOnlyFileFilter;

public class JDirectoryChooser extends JDialog
{

  public static final int  ACCEPT = 1;
  public static final int  CANCEL = 2;

  private static final Dimension SIZE = new Dimension(350,300);

  public static final NodeIconType CD_ROM         = new NodeIconType("CD-ROM");
  public static final NodeIconType DISKETTE_DRIVE = new NodeIconType("Diskette Drive");
  public static final NodeIconType FOLDER         = new NodeIconType("Folder");
  public static final NodeIconType HARD_DRIVE     = new NodeIconType("Hard Drive");
  public static final NodeIconType JAZ_DRIVE      = new NodeIconType("Jaz Drive");
  public static final NodeIconType MY_COMPUTER    = new NodeIconType("My Computer");
  public static final NodeIconType OPEN_FOLDER    = new NodeIconType("Open Folder");
  public static final NodeIconType ZIP_DRIVE      = new NodeIconType("Zip Drive");

  private int    choice;

  private File   selectedDirectory;

  private Frame  owner;

  private JTree  fileSystemTree;

  private Map    iconMap;

  private Set    expandedFolders;

  /**
   * Creates an instance of the JDirectoryChooser class to display
   * the localhost's file system allowing the user to select a
   * directory.
   *
   * @param newOwner is a Ljava.awt.Frame object owning this dialog.
   */
  public JDirectoryChooser(Frame newOwner)
  {
    super(newOwner,"Choose Directory",true);
    owner = newOwner;
    choice = CANCEL;
    iconMap = new HashMap();
    expandedFolders = new HashSet();
    buildUI();
  }

  /**
   * The NodeIconType class is an type-safe enum for setting node
   * icons in a HashMap for the fileSystemTree.  Only icons of one
   * of these specified types is allowed in the HashMap, thus,
   * limiting it's contents.
   */
  private static final class NodeIconType
  {
    private final String desc;

    private NodeIconType(String newDesc)
    {
      desc = newDesc;
    }

    public boolean equals(Object obj)
    {
      return super.equals(obj);
    }

    public int hashCode()
    {
      return desc.hashCode();
    }

    public String toString()
    {
      return desc;
    }
  }

  /**
   * The FileSystemTreeCellRenderer class is responsible for representing the
   * nodes of the fileSystemTree, setting appropriate icons for the nodes that
   * are smbolic to the type of drive/folder in the file system of the
   * localhost.
   */
  public final class FileSystemTreeCellRenderer extends DefaultTreeCellRenderer
  {
    public Component getTreeCellRendererComponent(JTree tree,
                                                  Object value,
                                                  boolean sel,
                                                  boolean expanded,
                                                  boolean leaf,
                                                  int row,
                                                  boolean hasFocus ) {
      super.getTreeCellRendererComponent(tree,value,sel,expanded,leaf,row,hasFocus);
  
      DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
  
      Object userObject = node.getUserObject();
  
      File folder = null;
  
      if (userObject instanceof File)
        folder = (File) userObject;
  
      final int level = node.getLevel();

      Icon nodeIcon = null;

      // Set text & icon
      if (level == 0) {
        if ((nodeIcon = (Icon) iconMap.get(MY_COMPUTER)) != null)
          setIcon(nodeIcon);
      }
      else if (level == 1) {
        setText(folder != null ? folder.getPath() : userObject.toString());
  
        if (folder != null && folder.getAbsolutePath().toLowerCase().startsWith("a:")) {
          if ((nodeIcon = (Icon) iconMap.get(DISKETTE_DRIVE)) != null)
            setIcon(nodeIcon);
        }
        else {
          if ((nodeIcon = (Icon) iconMap.get(HARD_DRIVE)) != null)
            setIcon(nodeIcon);
        }
      }
      else {
        setText(folder != null ? folder.getName() : userObject.toString());

        if ((nodeIcon = (Icon) iconMap.get((tree.isExpanded(new TreePath(node.getPath())) ? OPEN_FOLDER : FOLDER))) != null)
          setIcon(nodeIcon);
      }

      setToolTipText(getText());

      return this;
    }
  }

  /**
   * buildToolBar constructs the JDirectoryChooser dialog's tool
   * bar used to accept a directory path and close the dialog.
   *
   * @return a Ljavax.swing.JToolBar object containing the control
   * buttons of the JDirectoryChooser dialog box.
   */
  private JToolBar buildToolBar()
  {
    final JToolBar toolbar = new JToolBar(JToolBar.HORIZONTAL);

    toolbar.setBorder(BorderFactory.createEmptyBorder());
    toolbar.setFloatable(false);
    toolbar.setLayout(new FlowLayout(FlowLayout.CENTER));

    final JButton accept = (JButton) toolbar.add(new JButton("Accept"));
    final JButton cancel = (JButton) toolbar.add(new JButton("Cancel"));

    ActionListener choiceListener = new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();

        if (source == accept)
          choice = ACCEPT;
        else
          choice = CANCEL;

        dispose();
      }
    };

    accept.addActionListener(choiceListener);
    cancel.addActionListener(choiceListener);

    return toolbar;
  }

  /**
   * buildUI contructs the UI components and lays them out on the
   * face of the dialog box.
   */
  private void buildUI()
  {
    setBackground(Color.lightGray);
    setSize(SIZE);

    fileSystemTree = new JTree(new DefaultTreeModel(getFileSystemView()));
    fileSystemTree.setCellRenderer(new FileSystemTreeCellRenderer());

    fileSystemTree.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent me) {
        final DefaultMutableTreeNode treeNode =
          (DefaultMutableTreeNode) fileSystemTree.getLastSelectedPathComponent();

        if (treeNode == null) {
          selectedDirectory = null;
          return;
        }

        Object userObject =  treeNode.getUserObject();

        if (userObject instanceof File)
          selectedDirectory = (File) userObject;
        else // is the root node
          return;

        if (me.getClickCount() > 1) {
          if (!expandedFolders.contains(selectedDirectory)) {
            expandNode((DefaultTreeModel) fileSystemTree.getModel(),treeNode);
            fileSystemTree.expandPath(new TreePath(treeNode.getPath()));
            expandedFolders.add(selectedDirectory);
          }
        }
      }
    });

    fileSystemTree.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_F5) {
          final DefaultMutableTreeNode treeNode =
            (DefaultMutableTreeNode) fileSystemTree.getLastSelectedPathComponent();

          if (treeNode == null)
            return;

          refreshNode((DefaultTreeModel) fileSystemTree.getModel(),treeNode);
        }
      }
    });

    Container contentPane = getContentPane();

    contentPane.add(new JScrollPane(fileSystemTree,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),BorderLayout.CENTER);
    contentPane.add(buildToolBar(),BorderLayout.SOUTH);
  }

  /**
   * expandNode expands the node in the fileSystemTree representing the
   * File object, if the File object is a directory containing other
   * directories.
   *
   * @param treeModel is a Ljavax.swing.tree.DefaultTreeModel object
   * used by the fileSystemTree to model the file system of the local
   * host.
   * @param treeNode is a Ljavax.swing.tree.DefaultMutableTreeNode
   * object being expanded if the File user object represented by this
   * node contains other subdirectories within the File (folder).
   */
  private void expandNode(DefaultTreeModel treeModel,
                          DefaultMutableTreeNode treeNode) {
    if (treeNode == null)
      return;

    File path = (File) treeNode.getUserObject();
    File[] directories = path.listFiles(DirectoryOnlyFileFilter.getInstance());

    if (directories != null) {
      Arrays.sort(directories);
      for (int index = 0; index < directories.length; index++) {
        treeModel.insertNodeInto(new DefaultMutableTreeNode(
          directories[index],true),treeNode,index);
      }
    }
  }

  /**
   * getChoice returns an int value representing the operation that
   * the user selected on the toolbar of the JDirectoryChooser dialog.
   *
   * @return a int value representing the user selected operation.
   */
  public int getChoice() {
    return choice;
  }

  /**
   * getFileSystemView returns the file system view of the localhost.
   *
   * @return a Ljavax.swing.tree.TreeNode structure representing the
   * file system on the local host.
   */
  private TreeNode getFileSystemView() {
    FileSystemView fileSystemView = FileSystemView.getFileSystemView();

    String hostName = "localhost";

    try
    {
      hostName = ((hostName = InetAddress.getLocalHost().getHostName()) == null ?
        "localhost" : hostName);
    }
    catch (Exception ignore)
    {
      hostName = "localhost";
    }

    DefaultMutableTreeNode fileSystemRoot = new DefaultMutableTreeNode(hostName);

    File[] roots = fileSystemView.getRoots();

    for (int index = 0, len = roots.length; index < len; index++)
      fileSystemRoot.add(new DefaultMutableTreeNode(roots[index],true));

    return fileSystemRoot;
  }

  /**
   * getSelectedDirectory returns the directory that the user selected
   * as a File object.
   *
   * @return a Ljava.io.File object representing the user's selection
   * in the file system tree of the JDirectoryChooser dialog box.
   */
  public File getSelectedDirectory() {
    return selectedDirectory;
  }

  /**
   * refreshNode removes all the treeNode's children and re-adds them
   * to the node to reflect the file system change caused by the user.
   *
   * @param treeModel is a Ljavax.swing.tree.DefaultTreeModel object
   * used by the fileSystemTree to model the file system of the local
   * host.
   * @param treeNode is the Ljavax.swing.tree.DefaultMutableTreeNode
   * object being refreshed in the tree view.
   */
  private void refreshNode(DefaultTreeModel treeModel,
                           DefaultMutableTreeNode treeNode) {
    if (treeNode == null)
      return;

    for (int index = treeNode.getChildCount(); --index >= 0; ) {
      treeModel.removeNodeFromParent((DefaultMutableTreeNode)
      treeNode.getChildAt(index));
    }

    expandNode(treeModel,treeNode);
  }

  /**
   * setNodeIcon sets an icon for a node in the fileSystemTree
   * of the JDirectoryChooser dialog box with the specified
   * NodeIconType.
   *
   * @param type is a NodeIconType specifying for which type
   * of folder in the file system of the local host to set
   * an icon image for.
   * @param node is a Ljavax.swing.Icon containing the image
   * for the node of the specified NodeIconType of the
   * fileSystemTree.
   */
  public void setNodeIcon(NodeIconType type,
                          Icon nodeIcon     ) {
    iconMap.put(type,nodeIcon);
    fileSystemTree.updateUI();
  }

  /**
   * show makes the dialog visible.  If the dialog and/or its owner
   * are not yet displayable, both are made displayable. The dialog
   * will be validated prior to being made visible. If the dialog
   * is already visible, this will bring the dialog to the front.
   *
   * If the dialog is modal and is not already visible, this call
   * will not return until the dialog is hidden by calling hide or
   * dispose.
   */
  public void show() {
    showDialog();
  }

  /**
   * showDialog shows the JDirectoryChooser dialog box and returns
   * the user's operation choice, which determines if the user
   * selected a directory or not.
   *
   * @return a int value representing the user selected operation.
   */
  public int showDialog() {
    setLocation(WindowUtil.getDialogLocation(owner,getSize()));
    super.show();
    return choice;
  }

}

