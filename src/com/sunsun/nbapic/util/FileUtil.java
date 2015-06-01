package com.sunsun.nbapic.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;

import org.apache.http.util.EncodingUtils;

public class FileUtil {
	/**
	 * 判断文件是否可读写
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean isFileCanReadAndWrite(String filePath) {
		if (null != filePath && filePath.length() > 0) {
			File f = new File(filePath);
			if (null != f && f.exists()) {
				return f.canRead() && f.canWrite();
			}
		}
		return false;
	}

	/**
	 * 读文件
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static String readAsString(String fileName) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		FileInputStream fin = new FileInputStream(fileName);
		try {
			stream(fin, out);
		} finally {
			try {
				fin.close();
			} catch (IOException e) {
			}
		}
		return EncodingUtils.getString(out.toByteArray(), "UTF-8");
	}

	public static void stream(InputStream in, OutputStream out)
			throws IOException {
		byte[] buf = new byte[8192];
		int len;
		while ((len = in.read(buf)) != -1) {
			out.write(buf, 0, len);
		}
	}

	/**
	 * 写到文件
	 * 
	 * @param fileName
	 * @param message
	 * @throws IOException
	 */
	public static void writeString(String fileName, String message)
			throws IOException {
		FileOutputStream fout = new FileOutputStream(new File(fileName));
		try {
			BufferedOutputStream buffer = new BufferedOutputStream(fout);
			byte[] bs = message.getBytes();
			buffer.write(bs);
			buffer.flush();
			buffer.close();
			fout = null;
		} finally {
			if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * 删除文件 删除文件夹里面的所有文件
	 * 
	 * @param path
	 *            String 路径
	 */
	public static void deleteFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {// 如果是文件，则删除文件
			file.delete();
			return;
		}
		File files[] = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				deleteFile(files[i].getAbsolutePath());// 先删除文件夹里面的文件
			}
			files[i].delete();
		}
		file.delete();
	}

	/**
	 * 
	 * @return 文件夹的文件数量
	 */
	public static int getFileCount(String filePath) {
		int count = 0;
		File file = new File(filePath);
		if (!file.exists()) {
			return count;
		}
		if (!file.isDirectory()) {
			return 1;
		} else {
			File[] fileList = file.listFiles();
			for (int i = 0; i < fileList.length; i++) {
				if (fileList[i].isDirectory()) {
					count = count + getFileCount(fileList[i].getAbsolutePath());
				} else {
					count++;
				}
			}
		}
		return count;

	}

	/**
	 * 
	 * @return 文件的大小，带单位(MB、KB等)
	 */
	public static String getFileLength(String filePath) {
		try {
			File file = new File(filePath);
			return fileLengthFormat(getFileSize(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取文件大小
	 * 
	 * @param file
	 * @throws Exception
	 */
	public static long getFileSize(File file) throws Exception {
		long size = 0;
		if (!file.exists()) {
			return size;
		}
		if (!file.isDirectory()) {
			size = file.length();
		} else {
			File[] fileList = file.listFiles();
			for (int i = 0; i < fileList.length; i++) {
				if (fileList[i].isDirectory()) {
					size = size + getFileSize(fileList[i]);
				} else {
					size = size + fileList[i].length();
				}
			}
		}
		return size;
	}

	/**
	 * 
	 * @return 文件的大小，带单位(MB、KB等)
	 */
	public static String fileLengthFormat(long length) {
		String lenStr = "";
		DecimalFormat formater = new DecimalFormat("###.0");
		// if (length < 1024) {
		// lenStr = "0MB";
		// } else
		if (length < 1024 * 1024) {
			lenStr = "0 MB";
		} else if (length < 1024 * 1024 * 1024) {
			lenStr = formater.format(length / (1024 * 1024.0f)) + " MB";
		} else {
			lenStr = formater.format(length / (1024 * 1024 * 1024.0f)) + " GB";
		}
		return lenStr;
	}
}
