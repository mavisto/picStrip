package temp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.select.Elements;

public class PicStrip {

	public static void main(String[] args) throws IOException {
		PicStrip.GetPictures();
	}

	public static void GetPictures() throws IOException {

		String result = null;
		List<String> urlList = new ArrayList<String>();

		Connection.Response resp = Jsoup
				.connect("http://www.worldwidewives.com/profile/janet-from-hull/?tab=pictures#3").timeout(3000000)
				.method(Connection.Method.GET).execute();

		// Document doc = resp.parse();

		File input = new File("C:\\Users\\dsmav_000\\Desktop\\input.html");
		Document doc = Jsoup.parse(input, "UTF-8");

		FileWriter fw = new FileWriter(new File("C:\\Users\\dsmav_000\\Desktop\\Pics\\page.txt"));
		FormElement form = (FormElement) doc.select("form[id]").first();
		// fw.write(doc.toString());

		// Elements e1 = doc.getElementsByAttributeValueContaining("class","pictures" );

		Elements elmts = doc.getElementsByClass("upload-listings");
		// Elements elmts = doc.getElementsByClass("tab-content");

		// Elements e1 = elmts.attr("class", "upload-listings");

		// Elements elmts = e1.select("upload-listings");

		Elements elmtsa = elmts.select("a");
		for (Element elmt : elmtsa) {
			if (elmt.childNodes().size() > 1) {
				String src = elmt.childNode(1).attr("src");
				// String src = elmt.attr("src");

				src = src.replaceFirst("ts1", "as1");
				urlList.add(src);
				System.out.println(src);
			}
		}

		// String reg = "\\d{1,2}(_|\\W)\\d{3,4}";
		String reg = "\\d{1,2}(_|\\W)\\d{3,4}";
		String reg1 = "tn_\\d{3,4}(_|\\W)[0-9].jpg";
		String reg2 = "tn_\\d{1,2}(_|\\W)\\d{3,4}.jpg";

		Pattern pat1 = Pattern.compile(reg1);
		Pattern pat2 = Pattern.compile(reg2);

		Matcher m1 = null;
		Matcher m2 = null;

		// String sp = "(_|\\W)";
		String sp = "/";

		for (String file : urlList) {
			String[] elements = file.split(sp);

			String name = elements[5];

			m1 = pat1.matcher(name);
			m2 = pat2.matcher(name);

			if (m1.find()) {
				System.out.println(file);
				String[] nameParts = name.split("(\\W|_)");
				for (int i = 1; i <= 10; i++) {
					String newName = nameParts[1] + "_" + i + ".jpg";
					// System.out.println(newName);
					String fileUrl = elements[0] + "//" + elements[2] + "/" + elements[3] + "/" + elements[4] + "/"
							+ newName;
					// System.out.println(fileUrl);

					try {
						FileUtils.copyURLToFile(new URL(fileUrl),
								new File("C:\\Users\\dsmav_000\\Desktop\\Pics\\" + newName));
					} catch (Exception e) {
						System.out.println("File not found");
					}
					// System.out.println(fileUrl);

				}

			}

			if (m2.find()) {
				System.out.println(file);
				String[] nameParts = name.split("(\\W|_)");
				for (int i = 1; i <= 10; i++) {
					String newName = null;
					if (name.matches("tn_\\d{1,2}\\.\\d{3,4}.jpg")) {
						newName = i + "." + nameParts[2] + ".jpg";

					} else {

						newName = i + "_" + nameParts[2] + ".jpg";
					}
					// System.out.println(newName);

					String fileUrl = elements[0] + "//" + elements[2] + "/" + elements[3] + "/" + elements[4] + "/"
							+ newName;
					// System.out.println(fileUrl);

					try {
						FileUtils.copyURLToFile(new URL(fileUrl),
								new File("C:\\Users\\dsmav_000\\Desktop\\Pics\\" + newName));
					} catch (Exception e) {
						System.out.println("File not found");
					}
					// System.out.println(fileUrl);

				}

			}
		}

		fw.write(doc.toString());

		fw.close();
	}
}
