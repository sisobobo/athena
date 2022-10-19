package io.github.sisobobo.athena.plugin.utils;

import com.google.common.base.CaseFormat;
import io.github.sisobobo.athena.plugin.model.Condition;
import io.github.sisobobo.athena.plugin.model.Db;
import io.github.sisobobo.athena.plugin.model.Model;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.config.ModelType;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.internal.db.DatabaseIntrospector;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class JdbcUtil {


    public static List<Model> getModels(Db db, List<String> warnings) throws SQLException {
        List<IntrospectedTable> introspectedTables = getIntrospectedTables(db, warnings);
        List<Model> models = new ArrayList<>(introspectedTables.size());
        for (IntrospectedTable introspectedTable : introspectedTables) {
            models.add(getModel(introspectedTable));
        }
        return models;
    }

    /**
     * 修改Model名称
     * @param models
     * @param tail
     * @return
     * @throws Exception
     */
    public static List<Model> modifyModelName(List<Model> models, String tail) throws Exception {
        List<Model> list = new ArrayList<>();
        for (Model model : models) {
            Model newModel = (Model) model.clone();
            newModel.setModelName(model.getModelName() + tail);
            list.add(newModel);
        }
        return list;
    }

    private static Model getModel(IntrospectedTable introspectedTable) {
        Model model = new Model();
        String tableName = introspectedTable.getFullyQualifiedTable().getIntrospectedTableName();
        model.setModelName(lineToHump(tableName));
        List<IntrospectedColumn> allColumns = introspectedTable.getAllColumns();
        List<Model.Field> fields = new ArrayList<>(allColumns.size());
        List<String> packages = new ArrayList<>();
        for (IntrospectedColumn column : allColumns) {
            Model.Field field = new Model.Field(column.getFullyQualifiedJavaType().getShortName(), column.getJavaProperty(), column.getRemarks());
            fields.add(field);
            packages.addAll(column.getFullyQualifiedJavaType().getImportList());
        }
        model.setFields(fields);
        packages = packages.stream().distinct().collect(Collectors.toList());
        model.setPackages(packages);
        model.setCreateTime(date2Str(new Date()));
        return model;
    }

    /**
     * 获取表结构
     *
     * @param db
     * @return
     * @throws SQLException
     */
    public static List<IntrospectedTable> getIntrospectedTables(Db db, List<String> warnings) throws SQLException {
        if (Objects.isNull(warnings)) {
            throw new IllegalArgumentException("参数[" + warnings + "]不能为null");
        }
        Context context = new Context(ModelType.CONDITIONAL);
        JDBCConnectionConfiguration config = new JDBCConnectionConfiguration();
        config.setConnectionURL(db.getUrl());
        config.setUserId(db.getUsername());
        config.setPassword(db.getPassword());
        config.setDriverClass(db.getDriver());
        context.setJdbcConnectionConfiguration(config);

        TableConfiguration tableConfiguration = new TableConfiguration(context);
        tableConfiguration.setCatalog(getDBName(db.getUrl()));
        String tableName = Optional.ofNullable(db.getPrefix()).orElse("") + "%";
        tableConfiguration.setTableName(tableName);
        Connection connection = context.getConnection();
        DatabaseIntrospector databaseIntrospector = new DatabaseIntrospector(context, connection.getMetaData(), new JavaTypeResolverDefaultImpl(), warnings);
        List<IntrospectedTable> introspectedTables = databaseIntrospector.introspectTables(tableConfiguration);
        if (StringUtils.isNotBlank(db.getTables())) {
            List<String> tables = getTables(db.getTables());
            introspectedTables = introspectedTables.stream().filter(item -> tables.contains(item.getFullyQualifiedTable().getIntrospectedTableName())).collect(Collectors.toList());
        }
        return introspectedTables;
    }


    public static String date2Str(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/HH:mm");
        return sdf.format(date);
    }

    public static String getDBName(String url) {
        String regex = "jdbc:(?<protocol>\\w+):.*((//)|@)(?<host>.+):(?<port>\\d+)(/|(;DatabaseName=)|:)(?<dbName>\\w+)\\??.*";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(url);
        String dbName = null;
        if (m.find()) {
            dbName = m.group("dbName");
        }
        if (StringUtils.isBlank(dbName)) {
            throw new IllegalArgumentException("jdbc url错误");
        }
        return dbName;
    }

    /**
     * 下划线转驼峰
     */
    public static String lineToHump(String str) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, str);
    }

    private static List<String> getTables(String tables) {
        String[] split = tables.split(",");
        List<String> list = new ArrayList<>();
        for (String s : split) {
            list.add(s.trim());
        }
        return list;
    }

}
