package util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @autho zehua
 *  上午9:45:47
 */

/**上午9:45:47
 * @author 2017*****上午9:45:47
 *
 */

public class ManyFileToOne {
	public static void main(String[] args) throws IOException {
	
//		filesToOne("D:\\1512112873343\\VS_BUSINESS_SITUATION", "C:\\data2017.sql");
		filesToOne("C:\\baiduziyuan", "C:\\zuowensucaiall.sql");
	}
	public static void filesToOne(String s,String fileName) throws IOException{
	/*	//文件夹 路径
		String s="D:\\zazhi-yilin";
		//合并文件路径
		String fileName="C:\\dd.txt";*/
		File file=new File(s);
		if(file.isDirectory()){
			File[] listFiles = file.listFiles();
			FileOutputStream fo=new FileOutputStream(new File(fileName));
			for (File f : listFiles) {
				FileInputStream fi=new FileInputStream(f);
				byte[] bytes=new byte[1024];
				int read;
				while((read=fi.read(bytes))!=-1){
					fo.write(bytes, 0, read);
				}
				fi.close();
			}
			fo.close();
		}
	}
	public static void getAllfilesToOne(String s,String fileName) throws IOException{
		/*	//文件夹 路径
			String s="D:\\zazhi-yilin";
			//合并文件路径
			String fileName="C:\\dd.txt";*/
			List<File> restult=new ArrayList<File>();
			File file=new File(s);
			if(file.isDirectory()){
				File[] listFiles = file.listFiles();
				
				for (File f : listFiles) {
					if(f.isDirectory()){
						System.out.println(f.getAbsolutePath());
						List<File> filesByDir = getFilesByDir(f.getAbsolutePath());
						restult.addAll(filesByDir);
					}
				}
			}
				FileOutputStream fo=new FileOutputStream(new File(fileName));
				for (File f : restult) {
					FileInputStream fi=new FileInputStream(f);
					byte[] bytes=new byte[1024];
					int read;
					while((read=fi.read(bytes))!=-1){
						fo.write(bytes, 0, read);
					}
					fi.close();
				}
				fo.close();
		}
	
	public static List<File> getFilesByDir(String path){
		File file=new File(path);
		File[] listFiles = file.listFiles();
		return Arrays.asList(listFiles);
	}

}
