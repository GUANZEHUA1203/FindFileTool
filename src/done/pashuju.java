package done;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

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
public class pashuju {

	public static List<String> rusltAnser = new ArrayList<String>();

	public static List<String> ipadress = new ArrayList<String>();

	public static final String DOWNFILE_PATH = "/home/newbaidusql/baiduziyuan/";//   /data/sql/baiduziyuan/
	static {
		try {
			ipadress = getHtml();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-SS");
		String urlName[] = { "dyzy", "danjiyx", "gaoq", "tushuzy", "dsjzy", "jiaoyu-2", "yinyue", "tupian", "zongyi",
				"lxys", "bdyz", "rjzy" };// "dyzy","danjiyx","gaoq","tushuzy","dsjzy","jiaoyu-2","yinyue","tupian","zongyi","lxys","bdyz","rjzy"
		int pageNum[] = { 137, 110, 81, 65, 51, 42, 20, 19, 17, 15, 7, 2 };// {137,110,81,65,51,42,20,19,17,15,7,2};
		int type[] = { 2, 2, 2, 2, 45, 2, 42, 43, 2, 2, 2, 2 };
		int i = 2, n = 0;

		File file = new File(DOWNFILE_PATH);
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			
		while (i < pageNum[n]) {
			String url = null;
			url = "http://www.friok.com/category/" + urlName[n] + "/";
			getAnser(url + "page/" + i, type[n]);
			i++;
			if (i == pageNum[n]) {
				i = 2;
				n++;
			}
			if (n == pageNum.length - 1) {
				break;
			}
			System.out.println("-==============>" + url + "page/" + i);
			// 下载sql
			FileWriter fw2 = new FileWriter(new File(DOWNFILE_PATH + sdf.format(new Date()) + ".sql"));
			if (rusltAnser.size() > 1000 || (n == pageNum.length - 1 && i == pageNum[n])) {
				for (String s : rusltAnser) {
					String[] split = s.split("password");
					String sql2 = "INSERT INTO `aticle` ( `atman`, `attitle`, `atcontext`, `atdate`, `atlabel`, `atstate`) VALUES ( 'admin','"
							+ ElementsUtil.delDom(split[1]) + "', '" + ElementsUtil.delDom(split[0])
							+ ElementsUtil.delDom(split[2]) + "', 'now()', '电影', '0');";
					fw2.write(sql2);
					fw2.write("\r\n");
				}
				fw2.close();
				for (int m = 0; m < rusltAnser.size(); m++) {
					rusltAnser.clear();
				}
			}
		}
		} catch (Exception e) {
			System.err.println(e);
		}

	}

	public static void getAnser(String url, int type) throws IOException, InterruptedException {
		List<String> into = getInfo(url, "div.entry h2 a[href]");
		if (into != null) {
			for (String string : into) {
				List<String> info2 = getInfo(string, "strong > a[href]");
				if (info2 != null && info2.size() > 0) {
					List<String> downinfo = downinfo(info2.get(0));
					if (downinfo != null) {
						rusltAnser.addAll(downinfo);
					}
				}

			}
		}

	}

	public static List<String> getInfo(String url, String reg) throws IOException, InterruptedException {
		Random r = new Random();
		visit(ipadress.get(r.nextInt(ipadress.size() - 1) + 0));
		List<String> result = new ArrayList<String>();
		System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(10000));
		System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(10000));
		Document parse = null;
		try {
			parse = Jsoup.connect(url)
					.userAgent(
							"Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0; BIDUBrowser 2.x)")
					.timeout(5000).get();
		} catch (Exception e) {
			Thread.sleep(5000);
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
		return result;
	}

	public static List<String> downinfo(String url) throws IOException, InterruptedException {
		Random r = new Random();
		visit(ipadress.get(r.nextInt(ipadress.size() - 1) + 0));
		List<String> firstLink = new ArrayList<String>();
		System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(10000));
		System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(10000));
		Document parse = null;
		try {
			parse = Jsoup.connect(url)
					.userAgent(
							"Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0; BIDUBrowser 2.x)")
					.timeout(5000).get();
		} catch (Exception e) {
			Thread.sleep(8000);
			downinfo(url);
		}
		String baidupanpath = " ", text = "* ", text2 = "               ";
		if (parse != null) {
			Elements baiduPanLink = parse.select("#google-ads a[href]");//
			baidupanpath += baiduPanLink.attr("abs:href");
			Elements answer = parse.select(".desc");//
			Elements title = answer.select("p:eq(1)");
			text += title.text();
			Elements select = answer.select("p:eq(2)");
			text2 += select.text();
			firstLink.add(baidupanpath+ "password" + text + "password" + text2);// answer==null?"":.toString()
		}
		return firstLink;

	}

	private static List<String> getHtml() throws IOException {
		Document doc = null;
		try {
			doc = Jsoup.connect("http://www.xicidaili.com/nt").userAgent("Mozilla").get();
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

	public static void visit(String ip) throws InterruptedException {
		String[] r = ip.split(":");
		System.getProperties().setProperty("http.proxyHost", r[0]);
		System.getProperties().setProperty("http.proxyPort", r[1]);
	}


}
