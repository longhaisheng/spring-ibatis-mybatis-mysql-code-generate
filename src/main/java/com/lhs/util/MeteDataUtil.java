package com.lhs.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lhs.domain.ColumnDO;
import com.lhs.domain.ReadProperties;

public class MeteDataUtil {

	public static List<ColumnDO> queryColumnDOList(ReadProperties prop, String tableName) throws SQLException, IOException {
		String schema = prop.getValue(GenerateDOUtil.DB_SCHEMA);
		String sql = "SELECT COLUMN_NAME, DATA_TYPE, COLUMN_COMMENT,COLUMN_KEY FROM information_schema.columns WHERE table_schema='"
				+ schema + "' and table_name =  '" + tableName + "' ";
		ConnectionUtil connectionUtil = new ConnectionUtil();
		connectionUtil.setProp(prop);
		Connection connection = connectionUtil.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();

		List<ColumnDO> columnList = new ArrayList<ColumnDO>();
		ColumnDO columnDO;
		while (resultSet.next()) {
			String name = resultSet.getString(1).trim();
			String type = resultSet.getString(2).trim();
			String comment = resultSet.getString(3) == null ? "" : resultSet.getString(3).trim();
			String columnKey = resultSet.getString(4);

			columnDO = new ColumnDO();
			if ("PRI".equalsIgnoreCase(columnKey)) {
				comment = GenerateDOUtil.PRIMARY_CONSTANT;
				columnDO.setPrimary(true);
			}

			columnDO.setName(name);
			columnDO.setType(type);
			columnDO.setComment(comment);
			columnList.add(columnDO);
		}

		resultSet.close();
		preparedStatement.close();
		connection.close();
		return columnList;
	}

	public static Map<String, String> queryTables(ReadProperties prop) throws SQLException, IOException {
		String schema = prop.getValue(GenerateDOUtil.DB_SCHEMA);
		String sql = "SELECT TABLE_NAME,TABLE_COMMENT FROM information_schema.tables WHERE table_schema='" + schema + "' ";
		ConnectionUtil connectionUtil = new ConnectionUtil();
		connectionUtil.setProp(prop);
		Connection connection = connectionUtil.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(sql);

		Map<String, String> returnMap = new HashMap<String, String>();
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			returnMap.put(resultSet.getString(1), resultSet.getString(2));
		}

		resultSet.close();
		preparedStatement.close();
		connection.close();

		return returnMap;
	}

}