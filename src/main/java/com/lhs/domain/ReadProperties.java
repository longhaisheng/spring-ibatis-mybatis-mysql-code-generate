package com.lhs.domain;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author longhaisheng <code>Email:longhaisheng20@gmail.com</code>
 */
public class ReadProperties {

	private String path;

	private Properties prop;

	public ReadProperties(String path) throws IOException {
		this.path = path;
		this.prop = getProperty();
	}

	private Properties getProperty() throws IOException {
		InputStream is = new FileInputStream(path);
		Properties prop = new Properties();
		prop.load(is);
		is.close();
		return prop;
	}

	public String getValue(String key) throws IOException {
		return (String) prop.get(key);
	}

}
