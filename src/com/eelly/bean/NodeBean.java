package com.eelly.bean;

import com.eelly.model.MockEnum;
import com.eelly.treeTools.Selector;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiType;

import java.util.ArrayList;
import java.util.List;

import com.intellij.openapi.ui.Messages;

/**
 * @author Vurtne 27-Dec-17
 * 节点对象
 *      可以是class 或者field或者内部类
 *      主要体现在树状表里的节点
 * */
public class NodeBean implements Selector,CellProvider{
    private String name;
    private List<NodeBean> fields;
    private boolean isField; //true 是字段，false是类
    private PsiType type;
    private List<PsiAnnotation> annotations;
    /**
     * 原始的字段 ，用来写入的时候
     * */
    private PsiField field;

    /**
     * 是否已有模拟注解
     * */
    private boolean sourceAnnotations;

    /**
     * 模拟的注解
     * */
    private String annotation;


    private boolean selector;


    public NodeBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NodeBean> getFields() {
        return fields == null?new ArrayList<>():fields;
    }

    public void setFields(List<NodeBean> fields) {
        this.fields = fields;
    }

    public boolean isField() {
        return isField;
    }

    public void setField(boolean field) {
        isField = field;
    }

    public PsiType getType() {
        return type;
    }

    public void setType(PsiType type) {
        this.type = type;
    }

    /**
     * AS没有这个方法
     * */
    @Deprecated
    public List<PsiAnnotation> getAnnotations() {
        return annotations == null?new ArrayList<>():annotations;
    }

    /**
     * AS没有这个方法
     * */
    @Deprecated
    public void setAnnotations(List<PsiAnnotation> annotations) {
        this.annotations = annotations;
    }

    public boolean isSelector() {
        return selector;
    }

    public void setSelector(boolean selector) {
        this.selector = selector;
    }

    public String getAnnotation() {
        return annotation == null?"":annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public PsiField getField() {
        return field;
    }

    public void setField(PsiField field) {
        this.field = field;
    }

    public boolean isSourceAnnotations() {
        return sourceAnnotations;
    }

    public void setSourceAnnotations(boolean sourceAnnotations) {
        this.sourceAnnotations = sourceAnnotations;
    }

    @Override
    public void setSelect(boolean select) {
        setSelector(select);
    }

    @Override
    public void setValueAt(int column, String text) {
        if (!isField){
            Messages.showMessageDialog("内部类无法添加注解！",
                    "Data Mock Plug-in", Messages.getInformationIcon());
            return;
        }
        if (!MockEnum.checkMock(text)){
            Messages.showMessageDialog(text+"不是有效的数据类型！",
                "Data Mock Plug-in", Messages.getInformationIcon());
            return;
        }
        if (column == 2){
            this.annotation = text.trim();
        }
    }
}
