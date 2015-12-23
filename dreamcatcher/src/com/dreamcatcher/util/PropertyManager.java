package com.dreamcatcher.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

public class PropertyManager {

	private static PropertyManager rmsProperies;

	private Properties props = new Properties();

	private FileOutputStream fos = null;

	private String filePath = null;

	public static PropertyManager getInstance(String filePath) {

		synchronized (PropertyManager.class) {

			if (rmsProperies == null) {

				rmsProperies = new PropertyManager();

				rmsProperies.init(filePath);
			}
		}
		return rmsProperies;
	}


	public void init(String filePath) {

		FileInputStream fis = null;

		this.filePath = filePath;

		try {

			fis = new FileInputStream(filePath);

			props.load(new BufferedInputStream(fis));

			Iterator<String> it = props.stringPropertyNames().iterator();

			String key = null;

			while (it.hasNext()) {
				key = (String) it.next();
			}
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getProperty(String key) {

		return (String) props.get(key);

	}

	public synchronized void setProperty(String key, String value) {

		try {

			fos = new FileOutputStream(filePath);

			props.setProperty(key, value);
			
			props.store(new BufferedOutputStream(fos), "Auth-Code-Property");

		} catch (IOException e) {

			e.printStackTrace();

			try {
				fos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public synchronized void removeProperty(String key) {

		try {

			fos = new FileOutputStream(filePath);

			props.remove(key);
			
			props.store(new BufferedOutputStream(fos), "Auth-Code-Property");

		} catch (IOException e) {

			e.printStackTrace();

			try {
				fos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
}
