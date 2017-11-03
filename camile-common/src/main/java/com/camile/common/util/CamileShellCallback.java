package com.camile.common.util;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        StringBuilder sb = new StringBuilder(newCompilationUnit.getPackageDeclaration().get().toString());
        newCompilationUnit.removePackageDeclaration();

        //合并imports
        NodeList<ImportDeclaration> imports = newCompilationUnit.getImports();
        imports.addAll(existingCompilationUnit.getImports());
        Set importSet = new HashSet<ImportDeclaration>();
        importSet.addAll(imports);

        NodeList<ImportDeclaration> newImports = new NodeList<>();
        newImports.addAll(importSet);
        newCompilationUnit.setImports(newImports);
        for (ImportDeclaration i:newCompilationUnit.getImports()) {
            sb.append(i.toString());
        }
        newLine(sb);
        NodeList<TypeDeclaration<?>> types = newCompilationUnit.getTypes();
        NodeList<TypeDeclaration<?>> oldTypes = existingCompilationUnit.getTypes();

        for (int i = 0;i<types.size();i++) {
            //截取Class
            String classNameInfo = types.get(i).toString().substring(0, types.get(i).toString().indexOf("{")+1);
            sb.append(classNameInfo);
            newLine(sb);
            newLine(sb);
            //合并fields
            List<FieldDeclaration> fields = types.get(i).getFields();
            List<FieldDeclaration> oldFields = oldTypes.get(i).getFields();
            List<FieldDeclaration> newFields = new ArrayList<>();
            HashSet<FieldDeclaration> fieldDeclarations = new HashSet<>();
            fieldDeclarations.addAll(fields);
            fieldDeclarations.addAll(oldFields);
            newFields.addAll(fieldDeclarations);
            for (FieldDeclaration f: newFields){
                sb.append(f.toString());
                newLine(sb);
                newLine(sb);
            }

            //合并methods
            List<MethodDeclaration> methods = types.get(i).getMethods();
            List<MethodDeclaration> existingMethods = oldTypes.get(i).getMethods();
            for (MethodDeclaration f: methods){
                sb.append(f.toString());
                newLine(sb);
                newLine(sb);
            }
            for (MethodDeclaration m:existingMethods){
                boolean flag = true;
                for (String tag : MergeConstants.OLD_ELEMENT_TAGS) {
                    if (m.toString().contains(tag)) {
                        flag = false;
                        break;
                    }
                }
                if (flag){
                    sb.append(m.toString());
                    newLine(sb);
                    newLine(sb);
                }
            }

            //判断是否有内部类
            types.get(i).getChildNodes();
            for (Node n:types.get(i).getChildNodes()){
                if (n.toString().contains("static class")){
                    sb.append(n.toString());
                }
            }

        }

        return sb.append(System.getProperty("line.separator")+"}").toString();
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
