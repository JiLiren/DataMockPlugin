package com.eelly.tools;

import com.eelly.bean.NodeBean;
import com.eelly.dialog.MockDialog;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NodeTools {

    protected static final Logger log = Logger.getInstance(NodeTools.class);

    public static NodeBean createNode(PsiClass clazz){
        return createNodeBean(clazz);
    }

    private static NodeBean createNodeBean(PsiClass clazz){
        NodeBean classBean = new NodeBean();
        classBean.setField(false);
        classBean.setName(clazz.getName());
        NodeBean field = null;
        List<NodeBean> list = new ArrayList<>();
        for (PsiField bean:clazz.getAllFields()){
            field = new NodeBean();
            field.setName(bean.getName());
            field.setType(bean.getType());
            field.setField(bean);
            field.setField(true);
            list.add(field);
        }
        NodeBean bb = null;
        for (PsiClass bean:clazz.getAllInnerClasses()){
            bb = createNodeBean(bean);
            list.add(bb);
        }
        classBean.setFields(list);
        return classBean;
    }

    public static boolean isAnnotation(NodeBean bean){
        PsiAnnotation[] array = bean.getField().getAnnotations();
        for (PsiAnnotation annotation : array){
            if (annotation.getQualifiedName() != null && annotation.getQualifiedName().startsWith("DataMockFiled")){
                return true;
            }
        }
        return false;
    }
}
