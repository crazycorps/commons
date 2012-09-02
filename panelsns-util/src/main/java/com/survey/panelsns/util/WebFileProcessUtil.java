package com.survey.panelsns.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import javax.servlet.ServletContext;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.util.WebUtils;

import com.survey.panelsns.core.Constant;

public class WebFileProcessUtil {

	private static ServletContext servletContext = null;

	public static ServletContext getServletContext() {
		return SpringWebApplicationUtil.getWebApplicationContext().getServletContext();
	}

	public static void removeFile(String webPath) {
		if (WebFileProcessUtil.servletContext != null) {
			try {
				String realPath = WebUtils.getRealPath(WebFileProcessUtil.servletContext, webPath);
				File tempFile = new File(realPath);
				if (tempFile.exists()) {
					tempFile.delete();
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static String copyFileToString(String webPath) {
		String ret = null;
		try {
			String realPath = WebUtils.getRealPath(WebFileProcessUtil.servletContext, webPath);
			File tempFile = new File(realPath);
			if (tempFile.exists()) {
				ret = FileCopyUtils.copyToString(new InputStreamReader(new FileInputStream(tempFile),
						Constant.DEFAULT_CHARSET));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static File getWebApplicationFile(String webPath) {
		File ret = null;
		try {
			String realPath = WebUtils.getRealPath(WebFileProcessUtil.servletContext, webPath);
			File tempFile = new File(realPath);
			if (tempFile.exists()) {
				ret = tempFile;
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		return ret;
	}
}
