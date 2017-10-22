package com.camile.dao;

import com.camile.common.util.MybatisGeneratorUtil;
import com.camile.common.util.PropertiesFileUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 代码生成类
 * Created by ZhangShuzheng on 2017/1/10.
 */
public class Generator {

	// 根据命名规范，只修改此常量值即可
	private static String ROOT_MODULE = "camile-server";
	private static String MODULE = "camile";
	private static String DATABASE = "camile";
	private static String TABLE_PREFIX = "";
	private static String PACKAGE_NAME = "com.camile";
	private static String JDBC_DRIVER = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.driver");
	private static String JDBC_URL = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.url");
	private static String JDBC_USERNAME = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.username");
	private static String JDBC_PASSWORD = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.password");
	// 需要insert后返回主键的表配置，key:表名,value:主键名
	private static Map<String, String> LAST_INSERT_ID_TABLES = new HashMap<>();
	static {
        LAST_INSERT_ID_TABLES.put("user", "id");
		LAST_INSERT_ID_TABLES.put("role", "id");
		LAST_INSERT_ID_TABLES.put("role_permission", "id");
		LAST_INSERT_ID_TABLES.put("user_role", "id");
		LAST_INSERT_ID_TABLES.put("permission", "id");
	}

	/**
	 * 自动代码生成
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		MybatisGeneratorUtil.generator(JDBC_DRIVER, JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD, ROOT_MODULE, MODULE, DATABASE, TABLE_PREFIX, PACKAGE_NAME, LAST_INSERT_ID_TABLES, false);
	}

}
