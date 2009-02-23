package org.swiftdao.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 文件操作工具类
 * 
 * @author Sun Xiaochen
 * @since 2008-2-1
 */

public class FileUtils {
	protected static final Logger logger = LogManager.getLogger(FileUtils.class);

	// 限制实例化
	private FileUtils() {
	}

	/**
	 * 从文本文件中读取所有的行。
	 * 
	 * @param path 文件路径
	 * @return 以数组形式存储的所有行
	 * @throws java.io.IOException 读取文件有误时抛出
	 */
	public static String[] readLines(String path) throws IOException {
		List<String> lines = new ArrayList<String>();
		String line;
		BufferedReader br = new BufferedReader(new FileReader(path));
		line = br.readLine();
		while (line != null) {
			lines.add(line);
			line = br.readLine();
		}
		br.close();

		return lines.toArray(new String[lines.size()]);
	}

	/**
	 * 将字符串数组按行写入文件，如果文件已经存在将被覆盖。
	 * 
	 * @param path 文件路径
	 * @param lines 需要写入的数据
	 * @throws java.io.IOException 写入文件有误时抛出
	 */
	public static void writeLines(String path, String[] lines) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(path));
		for (String line : lines) {
			bw.write(line);
			bw.newLine();
		}
		bw.close();
	}

	/**
	 * 打开文件并得到BufferedReader。
	 * 
	 * @param file 文件
	 * @return BufferedReader 如果文件不存在，返回null
	 * @throws java.io.IOException 读取文件有误时抛出
	 */
	public static BufferedReader getReader(File file) throws IOException {
		if (isFile(file)) {
			return new BufferedReader(new FileReader(file));
		}
		return null;
	}

	/**
	 * 打开文件，如果文件不存在，自动创建，并得到BufferedWriter。
	 * 
	 * @param file 文件
	 * @param allowOverwrite 是否运行覆盖旧文件
	 * @return BufferedWriter，如果文件存在但不运行覆盖，返回null
	 * @throws java.io.IOException 读取或创建新文件有误时抛出
	 */
	public static BufferedWriter getWriter(File file, boolean allowOverwrite) throws IOException {
		if (checkFile(file, allowOverwrite)) {
			return new BufferedWriter(new FileWriter(file));
		}

		return null;
	}

	/**
	 * 打开文件并得到InputStrea
	 * 
	 * @param file 文件
	 * @return FileInputStream，如果文件不存在，返回null
	 * @throws java.io.IOException 读取文件有误时抛出
	 */
	public static FileInputStream getInputStream(File file) throws IOException {
		if (isFile(file)) {
			return new FileInputStream(file);
		}

		return null;
	}

	/**
	 * 打开文件，如果文件不存在，自动创建，并得到FileOutputStream。
	 * 
	 * @param file 文件
	 * @param allowOverwrite 是否运行覆盖旧文件
	 * @return FileOutputStream，如果文件存在但不允许覆盖，返回null
	 * @throws java.io.IOException 读取或创建新文件有误时抛出
	 */
	public static FileOutputStream getOutputStream(File file, boolean allowOverwrite) throws IOException {
		if (checkFile(file, allowOverwrite)) {
			return new FileOutputStream(file);
		}

		return null;
	}

	/**
	 * 关闭输入流
	 * 
	 * @param inputStream 文件输入流
	 */
	public static void close(FileInputStream inputStream) {
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
				logger.error("close FileInputStream fail", e);
			}
		}
	}

	/**
	 * 关闭输出流
	 * 
	 * @param outputStream 文件输出流
	 */
	public static void close(FileOutputStream outputStream) {
		if (outputStream != null) {
			try {
				outputStream.close();
			} catch (IOException e) {
				logger.error("close FileInputStream fail", e);
			}
		}
	}

	/**
	 * 关闭BufferedReader
	 * 
	 * @param br BufferedReader
	 */
	public static void close(BufferedReader br) {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				logger.error("close BufferedReader fail", e);
			}
		}
	}

	/**
	 * 关闭BufferedWriter
	 * 
	 * @param bw BufferedWriter
	 */
	public static void close(BufferedWriter bw) {
		if (bw != null) {
			try {
				bw.close();
			} catch (IOException e) {
				logger.error("close BufferedWriter fail", e);
			}
		}
	}

	/**
	 * 移动文件
	 * 
	 * @param file 文件
	 * @param destPath 目的文件夹
	 */
	public static void moveFile(File file, String destPath) {
		if (isFile(file) && destPath != null) {
			try {
				File destDir = new File(destPath);
				if (!destDir.exists()) {
					destDir.mkdir();
				}

				FileInputStream in = new FileInputStream(file);
				FileOutputStream out = new FileOutputStream(destDir.getPath() + File.separator + file.getName());

				// 传输字节
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
				out.close();

				file.delete();
			} catch (IOException e) {
				// logger.error("can't move {} to {} " , file, destPath);
				logger.error("can't move " + file + " to " + destPath + " ");
			}
		}
	}

	/**
	 * 批量重命名文件夹下的文件，包括文件夹
	 * 
	 * @param dir 目录
	 * @param prefix 重命名的后文件的前缀
	 * @param find 查找
	 * @param replace 替换为
	 * @param suffix 重命名后文件的后缀
	 * @return 被重命名过的文件
	 */
	public static File[] batchRename(File dir, String prefix, String find, String replace, String suffix) {
		List<File> ls = new ArrayList<File>();
		if (isDirectory(dir)) {
			File[] files = dir.listFiles();
			for (File file : files) {
				String oldName = file.getName();
				String newName = prefix + oldName.replaceAll(find, replace) + suffix;
				if (!newName.equals(oldName)) {
					File dest = new File(file.getParent() + File.separator + newName);
					if (file.renameTo(dest)) {
						ls.add(dest);
					}
				}
			}
		}
		return ls.toArray(new File[ls.size()]);
	}

	/**
	 * 批量删除一个目录下的所有文件，包括文件夹
	 * 
	 * @param dir 目录
	 */
	public static void batchDelete(File dir) {
		if (isDirectory(dir)) {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					batchDelete(file);
				}
				file.delete();
			}
		}
	}

	// 检查文件是否存在，如果不存在自动创建。 如果文件存在而且不允许覆盖 -> false
	private static boolean checkFile(File file, boolean allowOverwrite) throws IOException {
		if (file != null) {
			// 检查父目录，不存在时自动创建
			File parent = file.getParentFile();
			boolean parentExist = true;
			if (parent != null && !parent.exists()) {
				parentExist = parent.mkdirs();
			}
			if (parentExist && (file.createNewFile() || (file.exists() && allowOverwrite))) {
				return true;
			}
		}
		return false;
	}

	// 是否是一个存在的文件
	private static boolean isFile(File file) {
		return file != null && file.exists() && file.isFile();
	}

	// 是否是一个存在的文件夹
	private static boolean isDirectory(File dir) {
		return dir != null && dir.exists() && dir.isDirectory();
	}
}