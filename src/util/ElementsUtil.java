/**
 * @autho zehua
 *  上午11:18:21
 */
package util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**上午11:18:21
 * @author 2017*****上午11:18:21
 *
 */
public class ElementsUtil {
//	删除图片和链接
	public static Elements delEel(Elements select){
		 if(select.is("img")){
			 select.select("img").remove();
		}
		 if(select.is("a")){
			 select.select("a").remove();
		}
		return select;
	}
//	数据单双引号问题
	public static String delDom(String s){
		 if(s.contains("'")){
			 s=s.replaceAll("\'", " ");
		 }
		 if(s.contains("\"")){
			 s=s.replaceAll("\"", "\\\\/\"");
		 }
		 if(s.length()==0){
			 s="【查看详情】进行查看";
		 }
		return s;
	 }
	 public static void visit(String ip) throws InterruptedException{
		  // prop.setProperty("http.proxyHost", "183.45.78.31");
	        // 设置http访问要使用的代理服务器的端口
	        // prop.setProperty("http.proxyPort", "8080");
	        String[] r = ip.split(":");
	        System.getProperties().setProperty("http.proxyHost", r[0]);
	        System.getProperties().setProperty("http.proxyPort", r[1]);
//	        try {
//	            // doc = Jsoup.connect("http://www.baidu.com")
//	            Jsoup.connect("http://www.baidu.com")
//	            // .data("query", "Java")
//	                    .userAgent("Mozilla")
//	                    // .cookie("auth", "token")
//	                    // .timeout(3000)
//	                    .get();
//	        } catch (IOException e) {
//	            // TODO Auto-generated catch block
//	            e.printStackTrace();
//	            Thread.sleep(5000);
//	        }
	    }
	 //获取ip列表
	 @SuppressWarnings("unused")
	public static List<String> getHtml() throws IOException {
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
}
