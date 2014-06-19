package com.lhs.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileUtil {

	public static void createJaveSourceFile(String path, String fileName, String writeString) {
		String allPath = path + File.separator + fileName;
		File file = new File(allPath);
		OutputStream outPut = null;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			outPut = new BufferedOutputStream(new FileOutputStream(file));
			outPut.write(writeString.getBytes());
			outPut.flush();
		} catch (IOException e) {
			e.printStackTrace();// TODO
		} finally {
			try {
				if (outPut != null) {
					outPut.close();
				}
			} catch (IOException e) {
				e.printStackTrace();// TODO
			}
		}
	}

}