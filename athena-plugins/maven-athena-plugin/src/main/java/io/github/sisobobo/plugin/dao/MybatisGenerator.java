package io.github.sisobobo.plugin.dao;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Pair;
import cn.hutool.db.Db;
import cn.hutool.db.DbUtil;
import cn.hutool.db.ds.simple.SimpleDataSource;
import com.google.common.base.CaseFormat;
import io.github.sisobobo.plugin.utils.Jdbc;
import io.github.sisobobo.plugin.utils.Velocity;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.project.MavenProject;
import org.apache.velocity.VelocityContext;
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

    private static final Logger log = LoggerFactory.getLogger(MybatisGenerator.class);

    public static SimpleDataSource simpleDataSource(String url, String username, String pass, String driver) {
        SimpleDataSource dataSource = new SimpleDataSource(url, username, pass, driver);
        return dataSource;
    }

    public static void start(String url, String user, String pass, String tablePrefix, Boolean overwrite, String driver, MavenProject project , Boolean useLombok) {
        SimpleDataSource dataSource = simpleDataSource(url, user, pass, driver);
        VelocityContext context = generateMybatis(tablePrefix, dataSource, project , useLombok);
        File file = null;
        try {
            //生成generatorConfig.xml
            file = new File("./generatorConfig.xml");
            Velocity.makeFile("generatorConfig.vm", file, context);
            //运行Mybatis generator
            List<String> warnings = new ArrayList<>();
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(file);
            //true是覆盖文件

            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator generator = new MyBatisGenerator(config, callback, warnings);
            generator.generate(null);
            warnings.forEach(s -> log.warn(s));
        } catch (Exception e) {
            log.error("", e);
            throw new RuntimeException("MybatisGenerator执行失败");
        } finally {
            if (Objects.nonNull(file)) {
                file.delete();
            }
        }

    }


    public static VelocityContext generateMybatis(String tablePrefix, SimpleDataSource dataSource, MavenProject project , Boolean useLombok) {
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("generator_javaModelGenerator_targetPackage", project.getGroupId() + ".dao.dataobject");
        velocityContext.put("targetProject_dao", project.getBasedir());
        velocityContext.put("generator_sqlMapGenerator_targetPackage", "mappers");
        velocityContext.put("generator_javaClientGenerator_targetPackage", project.getGroupId() + ".dao.mapper");
        velocityContext.put("jdbc_driver", dataSource.getDriver());
        velocityContext.put("jdbc_url", dataSource.getUrl());
        velocityContext.put("jdbc_username", dataSource.getUser());
        velocityContext.put("jdbc_password", dataSource.getPass());
        velocityContext.put("useLombok" , useLombok);
        List<Pair<String, String>> tables = findTables(dataSource, tablePrefix);
        velocityContext.put("tables", tables);
        return velocityContext;
    }

    public static List<Pair<String, String>> findTables(SimpleDataSource dataSource, String tablePrefix) {
        Db db = DbUtil.use(dataSource);
        Jdbc jdbc = new Jdbc(dataSource.getUrl());
        String dataBase = jdbc.getDbName();
        StringBuffer tableBuffer = new StringBuffer();
        tableBuffer.append("select table_name from information_schema.tables where").append(" ")
                .append("table_schema = '").append(dataBase).append("'").append(" ");
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableBuffer.append("and table_name like '").append(tablePrefix).append("%'");
        }
        String sql = tableBuffer.toString();
        try {
            List<String> tables = db.query(sql, String.class);
            List<Pair<String, String>> list = new ArrayList<>();
            if (CollectionUtil.isNotEmpty(tables)) {
                tables.forEach(s -> list.add(new Pair<>(s, dataObjectName(s))));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("执行sql[" + sql + "]异常");
        }

    }

    private static String dataObjectName(String s) {
        StringBuffer buffer = new StringBuffer(lineToHump(s));
        buffer.append("DO");
        return buffer.toString();
    }

    /**
     * 下划线转驼峰
     */
    private static String lineToHump(String str) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, str);
    }


}
