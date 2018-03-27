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

/**
 * 上午10:43:11
 * 
 * @author 2017*****上午10:43:11
 *
 */

// 随便看看吧 全部数据
public class pashuju_zuowensucai_2 {

	public static List<String> rusltAnser = new ArrayList<String>();

	public static List<String> ipadress = new ArrayList<String>();
	
	public static final String DOWNFILE_PATH="/data/sql/zuowensucai/";

	static {
		try {
			ipadress = ElementsUtil.getHtml();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static String httpUrl = "http://www.sbkk88.com/";
	private static String childUrl[] = { "xiezuosucai/haojuzi/list_2353_", "zhuanti/huati/list_780_",
			"zhuanti/chuzhongshengzuowen/list_731_", "zhuanti/gaozhongshengzuowen/list_730_", "duhougan/list_121_",
			"sanwen/aiqingsanwen/list_1_", "sanwen/shuqingsanwen/list_4_", "sanwen/shanggansanwen/list_5_",
			"zhuanti/youji/list_147_", "html/qinqingsanwen/list_57_" };
	private static int pageNum[] ={  21, 12, 68, 18, 167, 26, 24, 18, 9, 11}; //{ 21, 12, 68, 18, 167, 26, 24, 18, 9, 11 };

	public static void main(String[] args) throws IOException, InterruptedException {
		/*
		 * for(int n=0;n<childUrl.length;n++){ for (int i = 1; i < pageNum[n];
		 * i++) { getAnser(httpUrl+childUrl[n]+pageNum[i]+".html", 8); } }
		 */
		try {
		int i=2,n=0;
		while(i<pageNum[n]){
			System.out.println("-==============>"+httpUrl+childUrl[n]+i+".html");
			getAnser(httpUrl+childUrl[n]+i+".html", 8);
			i++;
			if(i==pageNum[n]){
				i=2;
				n++;
			}
			if(n==pageNum.length-1){
				break;
			}
		}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void getAnser(String url, int type) throws IOException, InterruptedException {
		List<String> into = getInfo(url, ".infoListUL a");
		try {
			if (into != null) {
				for (String s : into) {
					List<String> downinfo = downinfo(s);
					if (downinfo != null) {
						rusltAnser.addAll(downinfo);
					}
					if (rusltAnser.size() > 100) {
						File f = new File(DOWNFILE_PATH);
						if (!f.exists()) {
							f.mkdirs();
						}
						FileWriter fw = new FileWriter(new File(DOWNFILE_PATH
								+ new SimpleDateFormat("yyyy-MM-dd-HH-ss-SSS").format(new Date()) + ".txt"));
						for (String string : rusltAnser) {
							String[] split = string.split("password");
							String sql = "INSERT INTO `aticle` ( `atman`, `attitle`, `atcontext`, `atdate`, `atlabel`, `atstate`) VALUES ( 'admin','"
									+ ElementsUtil.delDom(split[0]) + "', '" + ElementsUtil.delDom(split[1])
									+ "', now(), " + type + ", '0');";
							System.out.println(sql);
							fw.write(sql);
						}
						fw.close();
						rusltAnser.clear();
					}
				}
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	// 下载链接
	public static List<String> getInfo(String url, String reg) throws IOException, InterruptedException {
		List<String> result = new ArrayList<String>();
		try {
			Random r = new Random();
			ElementsUtil.visit(ipadress.get(r.nextInt(ipadress.size() - 1) + 0));
			System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(10000));
			System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(10000));
			Document parse = null;
			try {
				parse = Jsoup.connect(url)
						.userAgent(
								"Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0; BIDUBrowser 2.x)")
						.timeout(5000).get();
			} catch (Exception e) {
				Thread.sleep(new Random().nextInt(5000)+0);
				getInfo(url, reg);
			}
			if (parse == null) {
				return null;
			}
			Elements select = parse.select(reg);
			for (Element element1 : select) {
				String attr = element1.attr("abs:href");
				result.add(attr);
			}
		} catch (Exception e) {
			System.err.println("错误信息2："+e);
		}
		return result;
	}

	// 下载信息
	public static List<String> downinfo(String url) throws IOException, InterruptedException {
		List<String> firstLink = new ArrayList<String>();
		try {
			Random r = new Random();
			ElementsUtil.visit(ipadress.get(r.nextInt(ipadress.size() - 1) + 0));
			System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(10000));
			System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(10000));
			Document parse = null;
			try {
				parse = Jsoup.connect(url)
						.userAgent(
								"Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0; BIDUBrowser 2.x)")
						.timeout(5000).get();
			} catch (Exception e) {
				Thread.sleep(new Random().nextInt(5000)+0);
				downinfo(url);
			}
			String baidupanpath = "  ", text = "  ";
			if (parse != null) {
				baidupanpath += parse.select(".shareBox").text();
				baidupanpath += parse.select(".articleInfo").text();
				Elements select = parse.select(".articleText");
				select = ElementsUtil.delEel(select);
				for (Element element : select) {
					select = element.children().select("p");
				}
				text += select.toString();
				firstLink.add(baidupanpath + "password" + text);
			}
		} catch (Exception e) {
			System.err.println("错误信息："+e);
		}
		return firstLink;
	}

}
