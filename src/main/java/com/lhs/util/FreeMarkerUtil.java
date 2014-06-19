package com.lhs.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMarkerUtil {

	/**
	 * 根据模板生成文件
	 * 
	 * @param templatePath
	 *            模板路径
	 * @param templateName
	 *            模板名称
	 * @param fileName
	 *            要生成的文件名
	 * @param root
	 *            输出的内容
	 */
	public static void generateFile(String templatePath, String templateName, String fileName, Map<?, ?> root) {
		try {
			Configuration config = new Configuration();
			config.setDirectoryForTemplateLoading(new File(templatePath));
			config.setObjectWrapper(new DefaultObjectWrapper());
			Template template = config.getTemplate(templateName, "UTF-8");
			FileOutputStream fileOutputStream = new FileOutputStream(fileName);
			Writer writer = new OutputStreamWriter(fileOutputStream, "UTF-8");
			template.process(root, writer);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (TemplateException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		Map<String, String> userMap = new HashMap<String, String>();
		userMap.put("userName", "test create");
		userMap.put("userPassword", "123");

		Map<String, Object> root = new HashMap<String, Object>();
		root.put("user", userMap);
		String templatesPath = "E:\\newworkspace\\generate\\webapp\\template";
		String templateFile = "/a.ftl";
		String htmlFile = templatesPath + "/user.html";
		FreeMarkerUtil.generateFile(templatesPath, templateFile, htmlFile, root);
	}
}
