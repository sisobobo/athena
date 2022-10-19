package org.mybatis.generator.plugins;

import io.github.sisobobo.athena.plugin.utils.JdbcUtil;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.util.Date;
import java.util.List;
import java.util.Properties;

public class DataObjectPlugin extends PluginAdapter {

    private boolean useLombok;

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        this.useLombok = Boolean.parseBoolean(properties.getProperty("useLombok", "true"));
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (this.useLombok) {
            topLevelClass.addImportedType("lombok.Data");
            topLevelClass.addAnnotation("@Data");
        }
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine("* @author Athena  Generator");
        topLevelClass.addJavaDocLine("* @date " + JdbcUtil.date2Str(new Date()));
        topLevelClass.addJavaDocLine("*/");
        return true;
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return !this.useLombok;
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return !this.useLombok;
    }



}
