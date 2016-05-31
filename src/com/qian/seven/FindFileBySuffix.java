package com.qian.seven;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author wuhuaiqian
 * 2016��5��31��
 * ����ָ���ļ���׺���˳�ĳ���ļ����������ļ�
 */
public class FindFileBySuffix {
	/**
	 * 
	 * @param file �����ҵĸ�Ŀ¼
	 * @param suffixs ����Ҫ����ļ���׺������
	 * @return 
	 */
	public List<File> getFile(File file, final String  [] suffixs)  {
		if(!file.isDirectory()){
			throw new IllegalArgumentException("is not a Directory");
		}
		else{
			List<File> fileList = new ArrayList<File>();
			// ����������
			FileFilter ff =null;
			if(suffixs==null){
				//���������ļ�
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
							// ������˹���
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
	 * ���ڸ������getFile(File file, final String  [] suffixs)�Ĺ���
	 */
	private List<File> findAllFile(File root, FileFilter ff, List<File> list) {
		File[] childrenfile = root.listFiles(ff);//�г���ǰfile�µ����з�����������file
		for (File file : childrenfile) {
			if (file.isFile())//���ļ�����ӵ�list��
				list.add(file);
			else
				findAllFile(file, ff, list);//��Ŀ¼���ݹ����
		}
		return list;
	}
	/**
	 * ��ȡ��׺�������ж��Ƿ����Ҫ��
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
