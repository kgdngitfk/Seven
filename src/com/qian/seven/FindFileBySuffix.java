package com.qian.seven;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * find file with specified  suffix in the root directory
 * @author wuhuaiqian
 * 2016年6月1日
 */
public class FindFileBySuffix {
	/**
	 * 
	 * @param file ，the root directory
	 * @param suffixs ,array of the acceptable suffixes
	 * @return List of specified suffix files;
	 */
	public List<File> getFile(File file, final String  [] suffixs)  {
		if(!file.isDirectory()){
			throw new IllegalArgumentException("is not a Directory");
		}
		else{
			List<File> fileList = new ArrayList<File>();
			// 
			FileFilter ff =null;
			if(suffixs==null){
				//接受所有文件
				ff = new FileFilter() {
					@Override
					public boolean accept(File file) {
						return file.isDirectory()||file.isFile();
					}
				};
			}
			else{
				 ff = new FileFilter() {
						@Override
						public boolean accept(File file) {
							// 包含特定后缀的文件
							if (file.isFile() && isAcceptSuffix(file, suffixs))
								return true;
							else if (file.isDirectory())
								return true;
							else
								return false;
						}
					};
				
			}
			
			return findAllFile(file, ff, fileList);
		}
		
	}
	/**
	 * Assistant getFile(File file, final String  [] suffixes)
	 */
	private List<File> findAllFile(File root, FileFilter ff, List<File> list) {
		File[] childrenfile = root.listFiles(ff);//根据过滤器列出文件
		for (File file : childrenfile) {
			if (file.isFile())//是文件添加到list中
				list.add(file);
			else
				findAllFile(file, ff, list);//递归查找目录
		}
		return list;
	}
	/**
	 * 
	 * @param file
	 * @param suffixs
	 * @return
	 */
	private boolean isAcceptSuffix(File file,String [] suffixs){
		String name = file.getName();
		int lastIndexOf = name.lastIndexOf(".");
		String suffix = name.substring(lastIndexOf+1);
		for (int i = 0; i < suffixs.length; i++) {
			if(suffixs[i].equalsIgnoreCase(suffix))
				return true;
		}
		return false;
	}
	

}
