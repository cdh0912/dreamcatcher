package com.dreamcatcher.util;

import java.io.File;

public class FileManager {
	
	public static boolean isExistingDirectory(File file){
		boolean flag = false;
		if(file.exists() && file.isDirectory())
			flag = true;
		return flag;
	}
	

	public static boolean deleteEmptyDirectory(String path) {
		boolean flag = false;
		File directory = new File(path);
		if( isExistingDirectory(directory)) {
			File[] fileArr = directory.listFiles();
			if(fileArr.length == 0)
				directory.delete();
			flag = true;
		}
		return flag;
	}
	
	public static String getParentDirectoryPath(String path){
		String parentPath = "";
		File currDirectory = new File(path);
		File parentDirectory = null;
		if (isExistingDirectory(currDirectory)) {
			parentDirectory = currDirectory.getParentFile();
			parentPath = parentDirectory.getPath();
		}
		return parentPath;
	}
	
	public static boolean deleteFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if(file.exists() && file.isFile()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

}
