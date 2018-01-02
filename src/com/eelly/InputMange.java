package com.eelly;

import com.eelly.bean.NodeBean;
import com.eelly.model.MockEnum;
import com.eelly.tools.NodeTools;
import com.intellij.codeInsight.actions.ReformatCodeProcessor;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.openapi.diagnostic.Logger;
import org.apache.http.util.TextUtils;


public class InputMange extends WriteCommandAction.Simple {

    private PsiFile mFile;
    private PsiClass mClass;
    protected Project mProject;
    protected PsiElementFactory mFactory;

    protected NodeBean mBean;

    protected static final Logger log = Logger.getInstance(InputMange.class);


    public InputMange(PsiFile file, PsiClass clazz, NodeBean bean){
        super(clazz.getProject(), "Generate Injections");
        mFile = file;
        mClass = clazz;
        mProject = clazz.getProject();
        mFactory = JavaPsiFacade.getElementFactory(mProject);
        mBean = bean;
    }

    @Override
    public void run() throws Throwable {
        addElement(mBean);
        JavaCodeStyleManager styleManager = JavaCodeStyleManager.getInstance(mProject);
        styleManager.optimizeImports(mFile);
        styleManager.shortenClassReferences(mClass);
        new ReformatCodeProcessor(mProject, mClass.getContainingFile(), null, false).runWithoutProgress();
    }


    private void addElement(NodeBean bean){
        if (bean.isField()){
            if (bean.isSelector() && !TextUtils.isEmpty(bean.getAnnotation())){
                PsiAnnotation annotation = mFactory.createAnnotationFromText(getFieldAnnotation(bean),bean.getField());
                bean.getField().addBefore(annotation,bean.getField());
            }
        }else {
            for (NodeBean node:bean.getFields()){
                addElement(node);
            }
        }
    }

    /**
     * 获取注解内容
     * as没有获取注解的方法，弃用
     * */
    private String getFieldAnnotation(NodeBean bean){
        StringBuilder injection = new StringBuilder();
        injection.append("@DataMockFiled( type = DataMockFiled." + MockEnum.getNameById(bean.getAnnotation()) + ")");
        return injection.toString();
    }
}
