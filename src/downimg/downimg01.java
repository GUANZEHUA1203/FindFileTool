package downimg;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @autho zehua
 *  上午10:43:11
 */

/**上午10:43:11
 * @author 2017*****上午10:43:11
 *
 */

//豆瓣 全部数据
public class downimg01 {
	
	public static List<String> rusltAnser=new ArrayList<String>();
	static int count = 0;
	public static List<String> ipadress=new ArrayList<String>();
	private static String httpUrl="https://www.dbmeinv.com/dbgroup/show.htm?pager_offset=";
	static{
		try {
			ipadress = getHtml();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		 TimerTask task = new TimerTask() {
	            @Override
	            public void run() {
	                ++count;
	                try {
						downmain();
					} catch (IOException | InterruptedException e) {
						e.printStackTrace();
					}
	                System.out.println("时间=" + new SimpleDateFormat("yyyy-MM-dd HH:ss:SSS").format(new Date()) + " 执行了" + count + "次"); // 1次
	            }
	        };

	        //设置执行时间
	        Calendar calendar = Calendar.getInstance();
	        int year = calendar.get(Calendar.YEAR);
	        int month = calendar.get(Calendar.MONTH);
	        int day = calendar.get(Calendar.DAY_OF_MONTH);//每天
	        //定制每天的21:09:00执行，
	        calendar.set(year, month, day, 12, 01, 00);
	        Date date = calendar.getTime();
	        Timer timer = new Timer();

	        int period = 2 * 1000;
	        //每天的date时刻执行task，每隔2秒重复执行
//	        timer.schedule(task, date, period);
	        //每天的date时刻执行task, 仅执行一次
	        timer.schedule(task, date);
	}
	public static void downmain() throws IOException, InterruptedException{
		 System.out.println("开始时间=" + new SimpleDateFormat("yyyy-MM-dd HH:ss:SSS").format(new Date()) + " 执行了" + count + "次");
		for (int i = 1; i < 5277; i++) {//
			System.err.println("当前页数******"+i);
			if(i%100==0){
				Thread.sleep(1000*60);
			}
			Thread.sleep(15000);
			List<String> downinfo = downinfo(httpUrl+i);
			for (String string : downinfo) {
				String[] split = string.split("password");
				File f=new File("C:\\img\\");
				if(!f.exists()){
					f.mkdirs();
				}
				if(testUrlWithTimeOut(split[0], 500)){
					InputStream inputStreamByGet = getInputStreamByGet(split[0]);
					if(inputStreamByGet!=null){
						saveData(inputStreamByGet,new File("C:\\img\\"+split[1]));
					}
				}
				
			}
		}
	}
	
//   下载信息
	public static List<String> downinfo(String url) throws IOException, InterruptedException{
		System.out.println("下载信息 URL  "+url);
		Random r=new Random();
		visit(ipadress.get(r.nextInt(ipadress.size()-1)+0));
		List<String> firstLink=new ArrayList<String>();
		System.setProperty("sun.net.client.defaultConnectTimeout", String
                .valueOf(10000));
		System.setProperty("sun.net.client.defaultReadTimeout", String
                .valueOf(10000)); 
		Document parse=null;
		try{
			 parse = Jsoup.connect(url).userAgent("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0; BIDUBrowser 2.x)").timeout(5000).get();
		}catch(Exception e){
			Thread.sleep(8000);
			downinfo(url);
		}
		String imgpath="  ",fileName="  ";
		if(parse!=null){
			Elements select = parse.select(".thumbnails .img_single a img");
			for (Element element : select) {
				imgpath = element.attr("abs:src");
				fileName=imgpath.substring(imgpath.lastIndexOf("/")+1);
				firstLink.add(imgpath+"password"+fileName);
			}
		}
		return firstLink;
		
	}

	 private static List<String> getHtml() throws IOException {
	        Document doc = null;
	        try {
	            doc = Jsoup.connect("http://www.xicidaili.com/nt")
	                    .userAgent("Mozilla")
	                    .get();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        List<String> list = new ArrayList<String>();
	        Elements elements = doc.select("tr.odd");
	        int len = elements.size();
	        Element element = null;
	        for (int i = 0; i < len; i++) {
	            element = elements.get(i);
	            StringBuilder sBuilder = new StringBuilder(20);
	            sBuilder.append(element.child(1).text());
	            sBuilder.append(":");
	            sBuilder.append(element.child(2).text());
	            list.add(sBuilder.toString());
	        }
	        doc = null;
	        elements.clear();
	        elements = null;
	        return list;
	    }
	 
	 public static void visit(String ip) throws InterruptedException{
	        String[] r = ip.split(":");
	        System.getProperties().setProperty("http.proxyHost", r[0]);
	        System.getProperties().setProperty("http.proxyPort", r[1]);
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
	             System.out.println(System.currentTimeMillis()-lo);
	             return true;
	        } catch (Exception e1) {  
	        	Thread.sleep(10000);
	             System.out.println("连接打不开!");  
	             url = null; 
	             System.out.println(System.currentTimeMillis()-lo);
	             return false;
	        }  
	    }
	 public static InputStream getInputStreamByGet(String url) {
	        try {
	            HttpURLConnection conn = (HttpURLConnection) new URL(url)
	                    .openConnection();
	            conn.setReadTimeout(5000);
	            conn.setConnectTimeout(5000);
	            conn.setRequestMethod("GET");

	            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
	                InputStream inputStream = conn.getInputStream();
	                return inputStream;
	            }

	        } catch (IOException e) {
	        	 e.printStackTrace();
	        	return null;
	        }
	        return null;
	    }

	        // 将服务器响应的数据流存到本地文件
	    public static void saveData(InputStream is, File file) {
	        try (BufferedInputStream bis = new BufferedInputStream(is);
	                BufferedOutputStream bos = new BufferedOutputStream(
	                        new FileOutputStream(file));) {
	            byte[] buffer = new byte[1024];
	            int len = -1;
	            while ((len = bis.read(buffer)) != -1) {
	                bos.write(buffer, 0, len);
	                bos.flush();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	

}
