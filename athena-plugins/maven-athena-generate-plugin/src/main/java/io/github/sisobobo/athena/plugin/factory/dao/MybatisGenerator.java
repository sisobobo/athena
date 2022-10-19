package io.github.sisobobo.athena.plugin.factory.dao;

import io.github.sisobobo.athena.plugin.utils.GenerateUtil;
import io.github.sisobobo.athena.plugin.utils.JdbcUtil;
import io.github.sisobobo.athena.plugin.model.Condition;
import io.github.sisobobo.athena.plugin.model.Db;
import io.github.sisobobo.athena.plugin.utils.Velocity;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.maven.project.MavenProject;
import org.apache.velocity.VelocityContext;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MybatisGenerator {

    public static void start(Db db, Condition condition, MavenProject project, List<String> warnings) {
        File file = null;
        try {
            VelocityContext context = generateMybatis(db, condition, project, warnings);
            //生成generatorConfig.xml
            file = Velocity.makeFile("generatorConfig.vm", "./generatorConfig.xml", context, true);
            //运行Mybatis generator
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(file);
            //true是覆盖文件
            DefaultShellCallback callback = new DefaultShellCallback(GenerateUtil.isOverwrite(condition));
            MyBatisGenerator generator = new MyBatisGenerator(config, callback, warnings);
            generator.generate(null);
        } catch (Exception e) {
            throw new RuntimeException("MybatisGenerator执行失败", e);
        } finally {
            if (Objects.nonNull(file)) {
                file.delete();
            }
        }
    }

    private static VelocityContext generateMybatis(Db db, Condition condition, MavenProject project, List<String> warnings) throws SQLException {
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("package", project.getGroupId() + ".api");
        velocityContext.put("targetProject_dao", project.getBasedir());
        velocityContext.put("generator_sqlMapGenerator_targetPackage", "mappers");
        velocityContext.put("generator_javaModelGenerator_targetPackage", project.getGroupId() + ".dao.dataobject");
        velocityContext.put("generator_javaClientGenerator_targetPackage", project.getGroupId() + ".dao.mapper");
        velocityContext.put("jdbc_driver", db.getDriver());
        velocityContext.put("jdbc_url", db.getUrl());
        velocityContext.put("jdbc_username", db.getUsername());
        velocityContext.put("jdbc_password", db.getPassword());
        velocityContext.put("useLombok", true);
        List<Pair<String, String>> tables = tables(db, warnings);
        velocityContext.put("tables", tables);
        return velocityContext;
    }

    private static List<Pair<String, String>> tables(Db db, List<String> warnings) throws SQLException {
        List<IntrospectedTable> list = JdbcUtil.getIntrospectedTables(db, warnings);
        List<Pair<String, String>> result = new ArrayList<>(list.size());
        for (IntrospectedTable table : list) {
            String tableName = table.getFullyQualifiedTable().getIntrospectedTableName();
            result.add(Pair.of(tableName, GenerateUtil.lineToHump(tableName) + "DO"));
        }
        return result;
    }


}
