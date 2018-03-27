package done;
/**
 * @autho zehua
 *  下午2:45:03
 */

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**下午2:45:03
 * @author 2017*****下午2:45:03
 *
 */

//判断文件 夹txt文件是否有某个字段值
 public class Main extends JFrame
{ 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JPanel txtpath = new JPanel();
	JPanel downBut = new JPanel();
	JPanel checkPanel = new JPanel();
	public static int FileCount=0;
//	input Txt
	JLabel l = new JLabel("请文件夹输入路徑：");
	JLabel code = new JLabel("查找文本內容：");
	JLabel exeName = new JLabel("运行Exe路径：");
	JLabel bingrone = new JLabel("忽悠文件名前缀(位数)");
	JLabel ingrone = new JLabel("忽悠文件名后缀(位数)");
	
//	input
	JTextField f = new JTextField();
	JTextField codeTxt = new JTextField();
	JTextField exeNameTxt = new JTextField();
	JTextField bingroneTxt = new JTextField();
	JTextField ingroneTxt = new JTextField();
	
	JButton b = new JButton("执行");
	
//	checkBox
//	JCheckBox check=new JCheckBox("Txt");
//	JCheckBox wordCheck=new JCheckBox("Word");
//	JCheckBox Excelcheck=new JCheckBox("Excel");
	
		public Main(){
		super("文件夹路径下查找包含字符的 文本");
		this.setLayout(new BorderLayout());
		txtpath.setLayout(new GridLayout(0, 1));
		txtpath.add(l);
		txtpath.add(f);
		txtpath.add(code);
		txtpath.add(codeTxt);
		txtpath.add(exeName);
		txtpath.add(exeNameTxt);
		txtpath.add(bingrone);
		txtpath.add(bingroneTxt);
		txtpath.add(ingrone);
		txtpath.add(ingroneTxt);
		
		
		this.add(txtpath, BorderLayout.NORTH);
		
		downBut.add(b, BorderLayout.CENTER);
		this.add(downBut, BorderLayout.CENTER);
		
//		checkPanel.add(check, BorderLayout.WEST);
//		checkPanel.add(wordCheck, BorderLayout.WEST);
//		checkPanel.add(Excelcheck, BorderLayout.WEST);
		checkPanel.add(new JLabel("查找的内容为TXT"), BorderLayout.CENTER);
		this.add(checkPanel, BorderLayout.WEST);
		
		this.setSize(500, 300);
		this.setVisible(true);
		f.setText("C:/Users/Administrator/Downloads/SQLdata.zip");
		codeTxt.setText("null");
		exeNameTxt.setText("D://Program Files (x86)//Notepad++//notepad++.exe");
		bingroneTxt.setText("0");
		ingroneTxt.setText("10");
		Map<String, Object> map=new HashMap<String, Object>();
		b.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e){
						String str =f.getText();
						String findCode=codeTxt.getText();
						String findExe=exeNameTxt.getText();
						map.put("beforCode", bingroneTxt.getText());
						map.put("afterCode", ingroneTxt.getText());
						try {
							run(str,findCode,findExe,map);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
		});
		this.addWindowListener(new WindowAdapter() {            
		          public void windowClosing(WindowEvent e) {                    
		          System.exit(0);            
		  }        
		});  
}
public void run(String path,String txt,String exe,Map<String, Object> map) throws IOException{
	FileCount=0;
	List<String> delAllFile = findAllFile(path,txt);
	Set<String> finded=new HashSet<String>();
	for (String string : delAllFile) {
		int valueOf = string.lastIndexOf("\\");
		String substring="";
		String tmp="";
		if(Integer.parseInt((String) map.get("beforCode"))!=0){
			tmp+= string.substring(valueOf+Integer.parseInt((String) map.get("beforCode"))+1);
		}
		if(Integer.parseInt((String) map.get("afterCode"))!=0){
			if(Integer.parseInt((String) map.get("beforCode"))!=0){
				int lastIndexOf = tmp.lastIndexOf(".");
				tmp=tmp.substring(0,lastIndexOf);
				substring+=tmp.substring(0,tmp.length()-Integer.parseInt((String) map.get("afterCode")));
			}else{
				int lastIndexOf = string.lastIndexOf(".");
				substring+=string.substring(valueOf,lastIndexOf-Integer.parseInt((String) map.get("afterCode")));
			}
		}
		if(Integer.parseInt((String) map.get("afterCode"))==0&&Integer.parseInt((String) map.get("beforCode"))==0){
			substring=string.substring(valueOf);
		}
		System.out.println("FileName:"+substring);
				if(!finded.contains(substring)){
					FileCount++;
					finded.add(substring);
					System.out.println("当前文件个数:"+FileCount);
					Runtime.getRuntime().exec(exe + " " + string);
				}
	    
	}
}
public static void main (String args[]){
	new Main();
}
public static List<String> findAllFile(String path,String context) throws IOException {
 	List<String> fileName=new ArrayList<String>();
    boolean flag = false;  
    File file = new File(path);  
    String[] tempList = file.list();  
    File temp = null;  
    for (int i = 0; i < tempList.length; i++) {  
        if (path.endsWith(File.separator)) {  
            temp = new File(path + tempList[i]);  
        } else {  
            temp = new File(path + File.separator + tempList[i]);  
        }  
        if (temp.isFile()) {  
        	String findStr = findStr(temp.getAbsoluteFile(),context);
        	if(findStr!=null){
        		fileName.add(findStr);
        	}
            flag = true;
        }  
        if (temp.isDirectory()) {  
        	findAllFile(path + "/" + tempList[i], context);// 先删除文件夹里面的文件  
            flag = true;  
        }  
    }  
    return fileName;  
}  

public static String findStr(File file,String contxt) throws IOException{
 InputStreamReader read = new InputStreamReader(new FileInputStream(file),"UTF-8");//
 @SuppressWarnings("resource")
BufferedReader bufferedReader = new BufferedReader(read);  
 String line = null;  
 while((line = bufferedReader.readLine()) != null){
	 if(line.contains(contxt)){
		 return file.getAbsolutePath();
	 }
 }
return null;
}
}
