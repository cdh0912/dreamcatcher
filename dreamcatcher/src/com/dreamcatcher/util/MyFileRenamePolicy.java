package com.dreamcatcher.util;

import java.io.File;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class MyFileRenamePolicy implements FileRenamePolicy {

	@Override
	public File rename(File file) {
		String fn = file.getName();//a.bc.txt
		String sfn = System.nanoTime() + fn.substring(fn.lastIndexOf('.'));//23423454235324.txt
		return new File(file.getParent() + File.separator + sfn);
	}

}
