package com.qian.seven;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author wuhuaiqian
 * 2016年5月31日
 * 根据指定文件后缀过滤出某个文件夹下所有文件
 */
public class FindFileBySuffix {
	/**
	 * 
	 * @param file 待查找的根目录
	 * @param suffixs 符合要求的文件后缀的数组
	 * @return 
	 */
	public List<File> getFile(File file, final String  [] suffixs)  {
		if(!file.isDirectory()){
			throw new IllegalArgumentException("is not a Directory");
		}
		else{
			List<File> fileList = new ArrayList<File>();
			// 创建过滤器
			FileFilter ff =null;
			if(suffixs==null){
				//查找所有文件
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
							// 定义过滤规则
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
	 * 用于辅助完成getFile(File file, final String  [] suffixs)的功能
	 */
	private List<File> findAllFile(File root, FileFilter ff, List<File> list) {
		File[] childrenfile = root.listFiles(ff);//列出当前file下的所有符合条件的子file
		for (File file : childrenfile) {
			if (file.isFile())//是文件，添加到list中
				list.add(file);
			else
				findAllFile(file, ff, list);//是目录，递归调用
		}
		return list;
	}
	/**
	 * 截取后缀名，并判断是否符合要求
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
