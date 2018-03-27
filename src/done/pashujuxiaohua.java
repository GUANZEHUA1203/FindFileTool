package done;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

/**
 * @autho zehua
 *  上午10:43:11
 */

/**上午10:43:11
 * @author 2017*****上午10:43:11
 *笑话
 */
public class pashujuxiaohua {
	private static int pagenum =2;//页数
	public static int  pathIndex=0;
	public static List<String> rusltAnser=new ArrayList<String>();
	public static List<String> rusltTitle=new ArrayList<String>();
	public static final String DOWNFILE_PATH="/data/sql/xiaohua/";
	
	public static final String[] urlPaht={
			"http://www.jokeji.cn/list29_",
			"http://www.jokeji.cn/list13_",
			"http://www.jokeji.cn/list43_",
			"http://www.jokeji.cn/list5_",
			"http://www.jokeji.cn/list1_",
			"http://www.jokeji.cn/list7_",
			"http://www.jokeji.cn/list4_",
			"http://www.jokeji.cn/list27_",
			"http://www.jokeji.cn/list40_",
			"http://www.jokeji.cn/list16_",
			"http://www.jokeji.cn/list12_",
			"http://www.jokeji.cn/list39_",
			"http://www.jokeji.cn/list18_",
			"http://www.jokeji.cn/list36_",
			"http://www.jokeji.cn/list35_",
			"http://www.jokeji.cn/list30_",
			"http://www.jokeji.cn/list2_",
			"http://www.jokeji.cn/list31_",
			"http://www.jokeji.cn/list34_",
			"http://www.jokeji.cn/list8_",
			"http://www.jokeji.cn/list9_",
			"http://www.jokeji.cn/list6_",
			"http://www.jokeji.cn/list22_",
			"http://www.jokeji.cn/list17_",
			"http://www.jokeji.cn/list15_",
			"http://www.jokeji.cn/list11_",
			"http://www.jokeji.cn/list20_",
			"http://www.jokeji.cn/list38_",
			"http://www.jokeji.cn/list24_",
			"http://www.jokeji.cn/list42_",
			"http://www.jokeji.cn/list23_",
			"http://www.jokeji.cn/list21_",
			"http://www.jokeji.cn/list10_",
			"http://www.jokeji.cn/list3_",
			"http://www.jokeji.cn/list33_",
			"http://www.jokeji.cn/list37_",
			"http://www.jokeji.cn/list41_",
			"http://www.jokeji.cn/list44_",
			"http://www.jokeji.cn/list19_",
			"http://www.jokeji.cn/list14_",
			"http://www.jokeji.cn/list32_",
			"http://www.jokeji.cn/list45_",
			"http://www.jokeji.cn/list46_",
			"http://www.jokeji.cn/list28_",
			"http://www.jokeji.cn/list25_",
			"http://www.jokeji.cn/list26_",
			"http://www.jokeji.cn/hot.asp?me_page="
			};
	public static void main(String[] args) throws IOException, InterruptedException {
		File f=new File(DOWNFILE_PATH);
		if(!f.exists()){
			f.mkdirs();
		}
			
		boolean flag=true;
		while(flag){
			if(pathIndex>=urlPaht.length){
				flag=false;
			}
			String string=urlPaht[pathIndex];
			String url=string+pagenum+".htm";//
			flag = testUrlWithTimeOut(url, 5000);
			if(flag){
				System.out.println(url);
				getAnser(url);
				pagenum++;
			}
		}
		
	}
	
	public static void getAnser(String url) throws IOException, InterruptedException{
		List<String> link1 = getInfo(url, ".list_title ul li b a");//td a .main_14
		if(link1==null||link1.size()==0){
			System.out.println("当前链接没有资源"+url+"            page="+pagenum);
			pagenum=2;
			pathIndex++;
			return;
		}
		if(link1!=null){
			for (String string: link1) {
				if(string!=null){
					downinfo(string);
				}
			}
			if(rusltAnser.size()>=1000||pathIndex==urlPaht.length-1){
				System.out.println("进入下载****");
				Thread.sleep(50000);
				FileWriter fw =new FileWriter(new File(DOWNFILE_PATH+new SimpleDateFormat("yyyy-MM-dd-SS-ss-SSS").format(new Date())+".txt"));
				if(rusltAnser!=null){
					for (int i=0;i<rusltAnser.size();i++) {
						if(i>rusltTitle.size()){
							i=rusltTitle.size();
						}
						String sql1="INSERT INTO `aticle` (`atman`, `attitle`, `atcontext`, `atdate`, `atlabel`, `atstate`) VALUES ('admin', '"+rusltTitle.get(i)+"', '"+rusltAnser.get(i)+"', sysdate, '笑话', '0');";
						System.out.println("sql1"+sql1);
						fw.write(sql1);
						fw.write("\r\n");
					}
					rusltTitle.clear();
					rusltAnser.clear();
				}
				fw.close();
			}
		}
	}
	public static List<String> getInfo(String url,String reg) throws InterruptedException {
		List<String> result=new ArrayList<String>();
		Document parse=null;
		try {
			System.out.println("url"+url);
			
			 if(testUrlWithTimeOut(url,5000)){
				 parse = Jsoup.connect(url).userAgent("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0; BIDUBrowser 2.x)").timeout(5000).get();
				 Thread.sleep(2000);
			 }
			Elements select = parse.select(reg);
			if(select!=null){
				for (Element element1 : select) {
					String attr =null;
					attr = element1.attr("abs:href");
					rusltTitle.add(element1.text());
					result.add(attr);
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			Thread.sleep(new Random().nextInt(60000)+500);
			getInfo(url,reg);
//			pagenum=2;
//			pathIndex++;
//			System.out.println("next Path");
		}
		return result;
		
	}
	public static List<String> downinfo(String url) throws InterruptedException {
		List<String> firstLink=new ArrayList<String>();
		System.out.println(url);
		Document parse = null;
		try{
				Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		        Matcher m = p.matcher(url);
		        if(testUrlWithTimeOut(url,5000)){
			        if (m.find()) {
			        	parse = Jsoup.parse(url);
			        }else{
			        	parse = Jsoup.connect(url).userAgent("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0; BIDUBrowser 2.x)").timeout(5000).get();
			        }
			        Thread.sleep(3000);
		        }
				if(parse!=null){
					Elements baiduPanLink = parse.select("#text110");//
					for (Element element : baiduPanLink) {
						if(element.text()!=null){
							firstLink.add(element.text());
						}
						
					}
				}else{
					firstLink.add("sorry ! data has been delete");
				}
		}catch(Exception e){
			e.printStackTrace();
			Thread.sleep(new Random().nextInt(60000)+500);
			downinfo(url);
//			pagenum=2;
//			pathIndex++;
//			System.out.println("next Path");
		}
		rusltAnser.addAll(firstLink);
		return rusltAnser;
		
	}
	 
    public static boolean testUrlWithTimeOut(String urlString,int timeOutMillSeconds) throws InterruptedException{
        long lo = System.currentTimeMillis();
        URL url;  
        try {  
             url = new URL(urlString);  
             URLConnection co =  url.openConnection();
             co.setConnectTimeout(timeOutMillSeconds);
             co.connect();
             System.out.println("连接可用");  
             return true;
        } catch (Exception e1) {  
        	e1.printStackTrace();
        	Thread.sleep(new Random().nextInt(60000)+500);
             System.out.println("连接打不开!");  
             url = null; 
             pagenum=2;
 			pathIndex++;
 			System.out.println("next Path");
             return false;
        }  
    }
    @Test
    public void test() throws InterruptedException {
    	boolean testUrlWithTimeOut = testUrlWithTimeOut("http://www.jokeji.cn/list29_103.htm", 500);
    	
		
	}



}
