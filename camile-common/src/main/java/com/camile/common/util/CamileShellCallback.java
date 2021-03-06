package com.camile.common.util;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.google.common.collect.Lists;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static org.mybatis.generator.api.dom.OutputUtilities.javaIndent;
import static org.mybatis.generator.api.dom.OutputUtilities.newLine;

public class CamileShellCallback extends DefaultShellCallback {
    /**
     * Instantiates a new default shell callback.
     *
     * @param overwrite the overwrite
     */
    CamileShellCallback(boolean overwrite) {
        super(overwrite);
    }

    private String getNewJavaFile(String newFileSource, String existingFileFullPath)  {
        CompilationUnit newCompilationUnit = JavaParser.parse(newFileSource);
        CompilationUnit existingCompilationUnit = null;
        try {
            existingCompilationUnit = JavaParser.parse(new File(existingFileFullPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return mergerFile(newCompilationUnit,existingCompilationUnit);
    }



    private String mergerFile(CompilationUnit newCompilationUnit, CompilationUnit existingCompilationUnit){
        //合并imports
        Set<ImportDeclaration> importSet = new HashSet<>();
        importSet.addAll(newCompilationUnit.getImports());
        importSet.addAll(existingCompilationUnit.getImports());
        newCompilationUnit.setImports(new NodeList<>(importSet));

        NodeList<TypeDeclaration<?>> types = newCompilationUnit.getTypes();
        NodeList<TypeDeclaration<?>> oldTypes = existingCompilationUnit.getTypes();

        // 合并 字段方法
        for (TypeDeclaration typeDeclaration : oldTypes) {
            List<FieldDeclaration> fields = typeDeclaration.getFields();
            List<FieldDeclaration> customFields = this.getCustomFields(fields);
            List<MethodDeclaration> methods = typeDeclaration.getMethods();
            List<MethodDeclaration> customMethods = this.getCustomMethods(methods);


            TypeDeclaration newTypeDeclaration = this.findNewDeclaration(newCompilationUnit, typeDeclaration);
            for (FieldDeclaration field : customFields) {
                newTypeDeclaration.addMember(field);
            }

            for (MethodDeclaration customMethod : customMethods) {
                newTypeDeclaration.addMember(customMethod);
            }


        }

//        for (int i = 0;i<types.size();i++) {
//            //截取Class
//            String classNameInfo = types.get(i).toString().substring(0, types.get(i).toString().indexOf("{")+1);
//            TypeDeclaration<?> typeDeclaration = types.get(i);
//
//
//            sb.append(classNameInfo);
//            newLine(sb);
//            newLine(sb);
//            //合并fields
//            List<FieldDeclaration> fields = types.get(i).getFields();
//            List<FieldDeclaration> oldFields = oldTypes.get(i).getFields();
//
//            for (FieldDeclaration f: fields){
//                javaIndent(sb, 1);
//                sb.append(f.toString());
//                newLine(sb);
//                newLine(sb);
//            }
//
//            for (FieldDeclaration f: oldFields){
//                boolean flag = true;
//                for (String tag : MergeConstants.OLD_ELEMENT_TAGS) {
//                    if (f.toString().contains(tag)) {
//                        flag = false;
//                        break;
//                    }
//                }
//                if (flag){
//                    javaIndent(sb, 1);
//                    sb.append(f.toString());
//                    newLine(sb);
//                    newLine(sb);
//                }
//            }
//
//            //合并methods
//            List<MethodDeclaration> methods = types.get(i).getMethods();
//            List<MethodDeclaration> existingMethods = oldTypes.get(i).getMethods();
//            for (MethodDeclaration f: methods){
//                sb.append(f.toString());
//                newLine(sb);
//                newLine(sb);
//            }
//            for (MethodDeclaration m:existingMethods){
//                boolean flag = true;
//                for (String tag : MergeConstants.OLD_ELEMENT_TAGS) {
//                    if (m.toString().contains(tag)) {
//                        flag = false;
//                        break;
//                    }
//                }
//                if (flag){
//                    sb.append(m.toString());
//                    newLine(sb);
//                    newLine(sb);
//                }
//            }
//
//            //判断是否有内部类
//            types.get(i).getChildNodes();
//            for (Node n:types.get(i).getChildNodes()){
//                if (n.toString().contains("static class")){
//                    sb.append(n.toString());
//                }
//            }
//        }



        return newCompilationUnit.toString();
    }

    private List<FieldDeclaration> getCustomFields(List<FieldDeclaration> fields) {
        ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();

        for (FieldDeclaration field : fields) {
            boolean add = true;
            for (String tag : MergeConstants.OLD_ELEMENT_TAGS) {
                if (field.toString().contains(tag)) {
                    add = false;
                    break;
                }
            }

            if (add) fieldDeclarations.add(field);
        }

        return fieldDeclarations;
    }

    private List<MethodDeclaration> getCustomMethods(List<MethodDeclaration> methods) {
        ArrayList<MethodDeclaration> methodDeclarations = new ArrayList<>();

        for (MethodDeclaration method : methods) {
            boolean add = true;
            for (String tag : MergeConstants.OLD_ELEMENT_TAGS) {
                if (method.toString().contains(tag)) {
                    add = false;
                    break;
                }
            }

            if (add) methodDeclarations.add(method);
        }

        return methodDeclarations;
    }

    private TypeDeclaration findNewDeclaration(CompilationUnit newCompilationUnit, TypeDeclaration typeDeclaration) {
        for (TypeDeclaration<?> declaration : newCompilationUnit.getTypes()) {
            if (declaration.getNameAsString().equals(typeDeclaration.getNameAsString())) {
                return declaration;
            }
        }
        return null;
    }


    @Override
    public boolean isMergeSupported() {
        return true;
    }

    @Override
    public String mergeJavaFile(String newFileSource, String existingFileFullPath, String[] javadocTags, String fileEncoding) throws ShellException {
        return this.getNewJavaFile(newFileSource,existingFileFullPath);
    }
}
