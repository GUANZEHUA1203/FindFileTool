import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @autho zehua
 *  上午9:57:11
 */

/**上午9:57:11
 * @author 2017*****上午9:57:11
 *
 */
public class TestFile {
	public static void main(String[] args) throws IOException, InterruptedException {
		
//		String url="http://www.52duzhe.com";
//		String reg="td.time a";
		
//		String url="http://www.52duzhe.com/2017_06/index.html";
//		String reg="td.title a";
			
		String url="http://www.sbkk88.com/xiezuosucai/haojuzi/452853.html";
		String reg=".articleInfo";//shareBox；articleText；articleInfo
		
		//.thumbnails .img_single a img ;https://www.dbmeinv.com/dbgroup/show.htm?pager_offset=5200
		//.panel-body markdown img  https://www.dbmeinv.com/dbgroup/6933
		
		System.out.println("url"+url);
		List<String> result=new ArrayList<String>();
		Document parse = Jsoup.connect(url).userAgent("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0; BIDUBrowser 2.x)").timeout(5000).get();
		Thread.sleep(5000);
		Elements select = parse.select(reg);
		select.select("img").remove();
		System.out.println("size"+select.size());
		for (Element element1 : select) {
			
			System.out.println(element1.text());//div:not(.likearticle,.bdlikebutton,.banner468Ad1,.context,#SOHUCS)
//			System.out.println(element1.select(".likearticle").remove());
//			System.out.println(element1.select(".likearticle").remove().select(".bdlikebutton").remove().select(".banner468Ad1").remove().select("#SOHUCS").remove().select("context").remove());
//			String attr = element1.attr("abs:src");
//			System.out.println(attr);
//			element1.attr("abs:src");
//			System.out.println(attr.substring(attr.lastIndexOf("/")+1));
		}
		System.out.println(delDom("<div class=\"adt-comment\">"));
	}
	public static String delDom(String s) throws IOException{
		
		FileWriter fw=new FileWriter(new File("c:\\cc.txt"));
		for (int i = 9830; i < 9844; i++) {
			fw.write("INSERT INTO `mysql170930a6f3_db`.`pre_forum_post_tableid` (`pid`) VALUES ('"+i+"');");
			fw.write("\r\n");
		}
		fw.close();
//		 if(s.contains("'")){
//			 s=s.replaceAll("\'", " ");
//		 }
//		 if(s.contains("\"")){
//			 s=s.replaceAll("\"", "\\\\\"");
//		 }
		return s;
	 }
}
