package com.eelly.model;

import com.eelly.bean.CellProvider;
import com.eelly.bean.NodeBean;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.TreeTableNode;

public class MockTableModel extends DefaultTreeTableModel {

    public MockTableModel(TreeTableNode node) {
        super(node);
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    /**
     * 列的类型
     */
    @Override
    public Class getColumnClass(int col) {
        return Object.class;
    }

    /**
     * 列的名称
     */
    @Override
    public String getColumnName( int column ) {
        switch( column ) {
            case 0: return "name";
            case 1: return "type";
            default: return "mock annotation";
        }
    }

    @Override
    public Object getValueAt(Object node, int column) {
        NodeBean bean = null;
        if (node instanceof DefaultMutableTreeTableNode) {
            DefaultMutableTreeTableNode mutableNode = (DefaultMutableTreeTableNode) node;
            Object obj = mutableNode.getUserObject();
            if (obj != null && obj instanceof NodeBean){
                bean = (NodeBean) obj;
            }
        }
        String result = "";
        if (bean == null){
            return "";
        }
        switch (column){
            case 0:
                result = bean.getName();
                break;
            case 1:
                result = bean.isField()?bean.getType().getPresentableText():bean.getName();
                break;
            default:
                result = bean.getAnnotation();
                break;
        }
        return result;
    }


    @Override
    public void setValueAt(Object value, Object node, int column) {
        super.setValueAt(value, node, column);
        if (node instanceof DefaultMutableTreeTableNode) {
            DefaultMutableTreeTableNode mutableNode = (DefaultMutableTreeTableNode) node;
            Object obj = mutableNode.getUserObject();
            if (obj != null && obj instanceof CellProvider){
                CellProvider cellProvider = (CellProvider) obj;
                cellProvider.setValueAt(column,value.toString());
            }
        }
    }


    //设置可编辑的单元格
    @Override
    public boolean isCellEditable(Object node, int column) {
        if (column == 2) {
            return true;
        }
        return false;
    }
}
