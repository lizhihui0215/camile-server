package com.camile.common.util;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.camile.common.util.StringUtil.lineToHump;

public class MybatisGeneratorUtil {
    // generatorConfig模板路径
    private static String generatorConfig_vm = "/template/generatorConfig.vm";
    // Service模板路径
    private static String service_vm = "/template/Service.vm";
    // ServiceMock模板路径
    private static String serviceMock_vm = "/template/ServiceMock.vm";
    // ServiceImpl模板路径
    private static String serviceImpl_vm = "/template/ServiceImpl.vm";

    /**
     * 根据模板生成generatorConfig.xml文件
     * @param jdbc_driver   驱动路径
     * @param jdbc_url      链接
     * @param jdbc_username 帐号
     * @param jdbc_password 密码
     * @param rootModule
     * @param module        项目模块
     * @param database      数据库
     * @param table_prefix  表前缀
     * @param package_name  包名
     * @param isIncludeTablePrifix
     */
    public static void generator(
            String jdbc_driver,
            String jdbc_url,
            String jdbc_username,
            String jdbc_password,
            String rootModule,
            String module,
            String database,
            String table_prefix,
            String package_name,
            Map<String, String> last_insert_id_tables,
            Map<String, String> alias_of_tables,
            boolean isIncludeTablePrifix) throws Exception{

        generatorConfig_vm = MybatisGeneratorUtil.class.getResource(generatorConfig_vm).getPath();//.replaceFirst("/", "");
        service_vm = MybatisGeneratorUtil.class.getResource(service_vm).getPath();//.replaceFirst("/", "");
        serviceMock_vm = MybatisGeneratorUtil.class.getResource(serviceMock_vm).getPath();//.replaceFirst("/", "");
        serviceImpl_vm = MybatisGeneratorUtil.class.getResource(serviceImpl_vm).getPath();//.replaceFirst("/", "");

        String targetProject = rootModule + "/" + module + "-dao";
        String basePath = MybatisGeneratorUtil.class.getResource("/").getPath().replace("/target/classes/", "").replace(targetProject, "");//.replaceFirst("/", "");
        String generatorConfig_xml = MybatisGeneratorUtil.class.getResource("/").getPath().replace("/target/classes/", "") + "/src/main/resources/generatorConfig.xml";
        targetProject = basePath + targetProject;
        String sql = "SELECT table_name FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '" + database + "' AND table_name LIKE '" + table_prefix + "%';";

        System.out.println("========== 开始生成generatorConfig.xml文件 ==========");
        List<Map<String, Object>> tables = new ArrayList<>();
        try {
            VelocityContext context = new VelocityContext();
            Map<String, Object> table;

            // 查询定制前缀项目的所有表
            JdbcUtil jdbcUtil = new JdbcUtil(jdbc_driver, jdbc_url, jdbc_username, AESUtil.AESDecode(jdbc_password));
            List<Map> result = jdbcUtil.selectByParams(sql, null);
            for (Map map : result) {
                System.out.println(map.get("TABLE_NAME"));
                table = new HashMap<>();
                String table_name = ObjectUtils.toString(map.get("TABLE_NAME"));
                table.put("table_name", table_name);
                String model_name = getGetModelName(isIncludeTablePrifix, table_name, table_prefix);
                table.put("model_name", model_name);
                tables.add(table);
            }
            jdbcUtil.release();

            String targetProject_sqlMap = basePath + rootModule + "/" + module + "-service";
            context.put("tables", tables);
            context.put("generator_javaModelGenerator_targetPackage", package_name + ".dao.model");
            context.put("generator_sqlMapGenerator_targetPackage", package_name + ".dao.mapper");
            context.put("generator_javaClientGenerator_targetPackage", package_name + ".dao.mapper");
            context.put("targetProject", targetProject);
            context.put("targetProject_sqlMap", targetProject_sqlMap);
            context.put("generator_jdbc_password", jdbc_password);
            context.put("last_insert_id_tables", last_insert_id_tables);
            context.put("alias_of_tables", alias_of_tables);
            VelocityUtil.generate(generatorConfig_vm, generatorConfig_xml, context);
            // 删除旧代码
            deleteDir(new File(targetProject + "/src/main/java/" + package_name.replaceAll("\\.", "/") + "/dao/model"));
//            deleteDir(new File(targetProject + "/src/main/java/" + package_name.replaceAll("\\.", "/") + "/dao/mapper"));
//            deleteDir(new File(targetProject_sqlMap + "/src/main/java/" + package_name.replaceAll("\\.", "/") + "/dao/mapper"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("========== 结束生成generatorConfig.xml文件 ==========");

        System.out.println("========== 开始运行MybatisGenerator ==========");
        List<String> warnings = new ArrayList<>();
        File configFile = new File(generatorConfig_xml);
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(false);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        for (String warning : warnings) {
            System.out.println(warning);
        }
        System.out.println("========== 结束运行MybatisGenerator ==========");

        System.out.println("========== 开始生成Service ==========");
        String ctime = new SimpleDateFormat("yyyy/M/d").format(new Date());
        String servicePath = basePath + rootModule + "/" + module + "-api" + "/src/main/java/" + package_name.replaceAll("\\.", "/") + "/api";
        String serviceImplPath = basePath + rootModule + "/" + module + "-service" + "/src/main/java/" + package_name.replaceAll("\\.", "/") + "/service/impl";
        for (int i = 0; i < tables.size(); i++) {
            String model = getGetModelName(isIncludeTablePrifix, ObjectUtils.toString(tables.get(i).get("table_name")), table_prefix);
            String service = servicePath + "/" + model + "Service.java";
            String serviceMock = servicePath + "/" + model + "ServiceMock.java";
            String serviceImpl = serviceImplPath + "/" + model + "ServiceImpl.java";
            // 生成service
            File serviceFile = new File(service);
            if (!serviceFile.exists()) {
                generateVelocity(package_name, ctime, model, service, service_vm, null);
            }
            // 生成serviceMock
            File serviceMockFile = new File(serviceMock);
            if (!serviceMockFile.exists()) {
                generateVelocity(package_name, ctime, model, serviceMock, serviceMock_vm, null);
            }
            // 生成serviceImpl
            File serviceImplFile = new File(serviceImpl);
            if (!serviceImplFile.exists()) {
                String mapper = StringUtil.toLowerCaseFirstOne(model);
                generateVelocity(package_name, ctime, model, serviceImpl ,serviceImpl_vm, mapper);
                System.out.println(serviceImpl);
            }
        }
        System.out.println("========== 结束生成Service ==========");

        System.out.println("========== 开始生成Controller ==========");
        System.out.println("========== 结束生成Controller ==========");
    }

    private static void generateVelocity(String package_name, String ctime, String model, String service, String service_vm, String mapper) throws Exception {
        VelocityContext context = new VelocityContext();
        context.put("package_name", package_name);
        context.put("model", model);
        context.put("ctime", ctime);
        if (StringUtils.isNotBlank(mapper)) context.put("mapper", mapper);
        VelocityUtil.generate(service_vm, service, context);
        System.out.println(service);
    }

    private static String getGetModelName(boolean isIncludeTablePrifix, String tableName, String tablePrifix) {
        String modelName = "";
        if (isIncludeTablePrifix)
            modelName = lineToHump(ObjectUtils.toString(tableName));
        else
            modelName = lineToHump(tableName.substring(tablePrifix.length(), tableName.length()));
        return modelName;
    }

    // 递归删除非空文件夹
    public static void deleteDir(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteDir(files[i]);
            }
        }
        dir.delete();
    }
}
