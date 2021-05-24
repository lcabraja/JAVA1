/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author lcabraja
 */
public class FileTreeModel implements TreeModel {

    private final Map<File, List<File>> map = new HashMap<>();

    private final File root;
    private final File targetfile;

    public FileTreeModel(File root, File targetfile) {
        this.root = root;
        this.targetfile = targetfile;
    }

    @Override
    public Object getRoot() {
        return root;
    }

    @Override
    public Object getChild(Object parent, int index) {
        Optional<List<File>> children = getChildren(parent);
        if (children.isPresent() && children.get().size() > index) {
            return children.get().get(index);
        }
        throw new RuntimeException("No such child");
    }

    @Override
    public int getChildCount(Object parent) {
        Optional<List<File>> children = getChildren(parent);
        return children.isPresent() ? children.get().size() : 0;
    }

    @Override
    public boolean isLeaf(Object node) {
        return ((File) node).isFile();
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        Optional<List<File>> children = getChildren(parent);
        if (children.isPresent()) {
            return children.get().indexOf(child);
        }
        throw new RuntimeException("No such child");
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
    }

    private Optional<List<File>> getChildren(Object node) {
        File parent = (File) node;
        if (!parent.isDirectory()) {
            return Optional.empty();
        }
        List<File> children = map.get(parent);
        if (children == null) {
            File[] files = parent.listFiles();
            children = new ArrayList();
            for (File file : files) {
                if (targetfile.getAbsolutePath().toLowerCase().startsWith(file.getAbsolutePath().toLowerCase())) {
                    children.add(file);
                }
            }
            map.put(root, children);
        }
        
        return Optional.of(children);
    }
}
