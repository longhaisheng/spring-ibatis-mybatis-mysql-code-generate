package com.lhs.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.lhs.domain.ReadProperties;

public class ConnectionUtil {

	private ReadProperties properties;

	public ConnectionUtil() {

	}

	public ConnectionUtil(ReadProperties prop) {
		this.properties = prop;
	}

	// public static void main(String args[]) {
	// try {
	// // 装载驱动包类
	// Class.forName("com.mysql.jdbc.Driver");// 加载驱动
	// } catch (ClassNotFoundException e) {
	// System.out.println("装载驱动包出现异常!请查正！");
	// e.printStackTrace();
	// }
	// Connection conn = null;
	// try {
	// /**
	// * 建立jdbc连接，但要注意此方法的第一个参数， 如果127.0.0.1出现CommunicationsException异常，
	// * 可能就需要改为localhost才可以
	// **/
	// // jdbc:mysql://localhost:3306/test，test是数据库
	// conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/sooch",
	// "root", "123456");
	// } catch (SQLException e) {
	// System.out.println("链接数据库发生异常!");
	// e.printStackTrace();
	// }
	// System.out.println(conn.toString());
	// }

	public Connection getConnection() throws IOException {
		String host = properties.getValue("db_host");
		String port = properties.getValue("db_port");
		String schema = properties.getValue("db_schema");
		String user_name = properties.getValue("db_user_name");
		String pass_word = properties.getValue("db_pass_word");
		String jdbcString = "jdbc:mysql://" + host + ":" + port + "/" + schema;
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(jdbcString, user_name, pass_word);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public ReadProperties getProp() {
		return properties;
	}

	public void setProp(ReadProperties prop) {
		this.properties = prop;
	}

}
