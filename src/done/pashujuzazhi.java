package done;
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

import util.ElementsUtil;

/**
 * @autho zehua
 *  上午10:43:11
 */

/**上午10:43:11
 * @author 2017*****上午10:43:11
 *读者
 */
public class pashujuzazhi {
	
	public static List<String> rusltAnser=new ArrayList<String>();
	
	public static List<String> ipadress=new ArrayList<String>();
	
	static{
		try {
			ipadress = ElementsUtil.getHtml();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws IOException, InterruptedException {
		getAnser("http://www.52duzhe.com", 2);
	}
	private static int tid=1;
	@SuppressWarnings("null")
	public static void getAnser(String url,int type) throws IOException, InterruptedException{
		List<String> into = getInfo(url, "td.time a");
		if(into!=null){
			for (String string : into) {
				List<String> info2 = getInfo(string, "td.title a");
				if(info2!=null&&info2.size()>0){
					for (String string2 : info2) {
						List<String> downinfo = downinfo(string2);
						if(downinfo!=null){
							rusltAnser.addAll(downinfo);
						}
					}
				}
				
			}
		}
		File f=new File("C://zazhi");
		if(!f.exists()){
			f.mkdirs();
		}
		if(rusltAnser.size()>=500){
			FileWriter fw =new FileWriter(new File("C://zazhi//"+new SimpleDateFormat("yyyy-MM-dd-SS-ss-SSS").format(new Date())+".txt"));
			for (String string : rusltAnser) {
				String[] split = string.split("password");
				fw.write("INSERT INTO `aticle` ( `atman`, `attitle`, `atcontext`, `atdate`, `atlabel`, `atstate`) VALUES ( 'admin','"+ElementsUtil.delDom(split[0])+"', '"+ElementsUtil.delDom(split[1])+ElementsUtil.delDom(split[2])+"', now(), '杂志【读者】【意林】', '0');");
			}
			fw.close();
			rusltAnser.clear();	
		}
	}
	
	public static List<String> getInfo(String url,String reg) throws IOException, InterruptedException{
		Random r=new Random();
		ElementsUtil.visit(ipadress.get(r.nextInt(ipadress.size()-1)+0));
		List<String> result=new ArrayList<String>();
		System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(10000));
		System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(10000));
		Document parse=null;
		try{
			parse= Jsoup.connect(url).userAgent("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0; BIDUBrowser 2.x)").timeout(5000).get();
		}catch(Exception e){
			Thread.sleep(5000);
			getInfo(url,reg);
		}
		if(parse==null){
			return null;
		}
		Elements select = parse.select(reg);
		for (Element element1 : select) {
			String attr = element1.attr("abs:href");
			result.add(attr);
		}
		return result;
	}
	public static List<String> downinfo(String url) throws IOException, InterruptedException{
		Random r=new Random();
		ElementsUtil.visit(ipadress.get(r.nextInt(ipadress.size()-1)+0));
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
		String baidupanpath="  ",text="  ",text2="   ";
		if(parse!=null){
			baidupanpath += parse.select("h1").toString();
			text += parse.select("div.artInfo").toString();
			Elements select = parse.select("div.blkContainerSblkCon");
			if(select.is("div.contentAd")){
				select.select("div.contentAd").remove();
			}
			text2+=select.toString();
			System.out.println(baidupanpath);
			System.out.println(text);
			System.out.println("*********"+text2);
			firstLink.add(baidupanpath+"password"+text+"password"+text2);//answer==null?"":.toString()
		}
		return firstLink;
		
	}

	

}
