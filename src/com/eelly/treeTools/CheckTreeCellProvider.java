/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eelly.treeTools;

import com.eelly.bean.NodeBean;
import org.jdesktop.swingx.renderer.CellContext;
import org.jdesktop.swingx.renderer.ComponentProvider;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;

public class CheckTreeCellProvider extends ComponentProvider<JPanel> {

    private CheckTreeSelectionModel selectionModel;
    private TristateCheckBox mCheckBox = null;
    private JLabel mNameLabel = null;

    public CheckTreeCellProvider(CheckTreeSelectionModel selectionModel) {
        this.selectionModel = selectionModel;
        //创建一个TristateCheckBox实例
        mCheckBox = new TristateCheckBox();
        //设置TristateCheckBox不绘制背景
        mCheckBox.setOpaque(false);
        //创建一个JLabel实例
        mNameLabel = new JLabel();
    }

    @Override
    protected void format(CellContext arg0) {
        //  从CellContext获取tree中的文字和图标
        JTree tree = (JTree) arg0.getComponent();
        DefaultMutableTreeTableNode node = (DefaultMutableTreeTableNode) arg0.getValue();
        Object obj = node.getUserObject();
        NodeBean bean = null;
        if (obj instanceof NodeBean){
            bean = (NodeBean) obj;
        }
        if (bean == null){
            return;
        }
        mNameLabel.setText(bean.getName());
        mCheckBox.setSelector(bean);

//        _label.setIcon(arg0.getIcon());
        //  根据selectionModel中的状态来绘制TristateCheckBox的外观
        TreePath path = tree.getPathForRow(arg0.getRow());
        if (path != null) {
            if (selectionModel.isPathSelected(path, true)) {
                mCheckBox.setState(Boolean.TRUE);
            } else if (selectionModel.isPartiallySelected(path)) {
                mCheckBox.setState(null);   //  注意“部分选中”状态的API
            } else {
                mCheckBox.setState(Boolean.FALSE);
            }
        }
        //  使用BorderLayout布局，依次放置TristateCheckBox和JLabel
        rendererComponent.setLayout(new BorderLayout());
        rendererComponent.add(mCheckBox);
        rendererComponent.add(mNameLabel, BorderLayout.LINE_END);
    }

    @Override
    protected void configureState(CellContext arg0) {
    }

    /**
     * 初始化一个JPanel来放置TristateCheckBox和JLabel
     */
    @Override
    protected JPanel createRendererComponent() {
        JPanel panel = new JPanel();
        return panel;
    }
}