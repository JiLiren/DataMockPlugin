package com.eelly.dialog;

import com.eelly.InputMange;
import com.eelly.bean.NodeBean;
import com.eelly.model.MockEnum;
import com.eelly.model.MockTableModel;
import com.eelly.tools.NodeTools;
import com.eelly.treeTools.CheckTreeTableManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.source.PsiJavaFileImpl;
import org.apache.http.util.TextUtils;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

import static javax.swing.ListSelectionModel.SINGLE_SELECTION;

public class MockDialog extends JFrame {

    private JPanel mContentPanel;
    private JButton mPositiveBtn;
    private JButton mNegativeBtn;
    private JPanel filedPanel;
    private JScrollPane mScrollPane;
    private JLabel mTitleLabel;
    private JLabel mMsgLabel;
    private JLabel mMsgLeft;


    private Project mProject;
    private Editor mEditor;
    private PsiClass mClass;
    private PsiFile mFile;

    private NodeBean mNodeBean;
    private ArrayList<DefaultMutableTreeTableNode> mNodes;
    /**
     * 勾选的数据和是否都填写了数据类型
     * */
    private boolean isMockCheckSuccess = true;


    protected static final Logger log = Logger.getInstance(MockDialog.class);

    public MockDialog(Project project, Editor editor, PsiFile file, PsiClass clazz) throws HeadlessException {
        mProject = project;
        mEditor = editor;
        mClass = clazz;
        mFile = file;
        setContentPane(mContentPanel);
        getRootPane().setDefaultButton(mNegativeBtn);
        this.setAlwaysOnTop(true);
        setTitle("Data Mock Plug-in");
        mTitleLabel.setText(((PsiJavaFileImpl) file).getPackageName() + "." + file.getName().split("\\.")[0]);

        initData();
        initView();
        initEvent();
    }

    private void initData(){
        //生成数据
        mNodeBean = NodeTools.createNode(mClass);
    }

    private void initView(){
        mMsgLabel.setText(MockEnum.getLabel());
//        mMsgLabel.setText("<html>this line<br>add a new line</html>");
        mMsgLeft.setText("模拟数据类型:");
        mNodes = new ArrayList<>();
        MockTableModel model = new MockTableModel(createData(mNodeBean));
        JXTreeTable treeTable = new JXTreeTable(model);
        CheckTreeTableManager manager = new CheckTreeTableManager(treeTable);
        manager.getSelectionModel().addPathsByNodes(mNodes);
        //设置第一列的宽度
        treeTable.getColumnModel().getColumn(0).setPreferredWidth(180);
        treeTable.expandAll();
        treeTable.setCellSelectionEnabled(false);
        treeTable.setRowHeight(30);
        treeTable.getColumnModel().getColumn(0).setPreferredWidth(180);

        final DefaultListSelectionModel defaultListSelectionModel = new DefaultListSelectionModel();
        treeTable.setSelectionModel(defaultListSelectionModel);
        defaultListSelectionModel.setSelectionMode(SINGLE_SELECTION);
        defaultListSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                defaultListSelectionModel.clearSelection();
            }
        });
        mNodes = null;
        mScrollPane.setViewportView(treeTable);
    }

    private void initEvent(){
        mPositiveBtn.addActionListener(event -> {
            isMockCheckSuccess = true;
            onCheckMockBean(mNodeBean);
            if (!isMockCheckSuccess){
                Messages.showMessageDialog("所选字段与所填类型不匹配！",
                        "Data Mock Plug-in", Messages.getInformationIcon());
                return;
            }
            new InputMange(mFile,mClass,mNodeBean).execute();
            dispose();
        });
    }

    private DefaultMutableTreeTableNode createData(NodeBean bean) {
        DefaultMutableTreeTableNode root = new DefaultMutableTreeTableNode(bean);
        createDataNode(root, bean);
        return root;
    }

    private void createDataNode(DefaultMutableTreeTableNode root, NodeBean innerBean) {
        for (NodeBean bean: innerBean.getFields()){
            DefaultMutableTreeTableNode node = new DefaultMutableTreeTableNode(bean);
            root.add(node);
            if (bean.isField()){
                mNodes.add(node);
            }else {
                createDataNode(node,bean);
            }
        }
    }


    private void onCheckMockBean(NodeBean nodeBean){
        for (NodeBean bean :nodeBean.getFields()){
            if (bean.isField()){
                //判断选中的字段是否有添加模拟类型
                int i = bean.isSelector()?1:0;
                int j = !TextUtils.isEmpty(bean.getAnnotation())?1:0;
                if ( i != j){
                    isMockCheckSuccess = false;
                }
            }else {
                onCheckMockBean(bean);
            }
        }
    }



}
