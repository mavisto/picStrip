package temp;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.FormElement;
import org.jsoup.select.Elements;

public class Numbers {

	public static void main(String[] args) throws IOException {
		Numbers.quantBetCalc();
	}

	public static void quantBetCalc() throws IOException {
		long dividend;
		long divisor;
		long[] numbers = new long[2];

		String result;

		Connection.Response resp = Jsoup.connect("http://quantbet.com/quiz/dev").timeout(3000000)
				.method(Connection.Method.GET).execute();

		Document doc = resp.parse();
		FormElement form = (FormElement) doc.select("form[id]").first();

		String title = doc.title();
		System.out.println("TITLE:\n" + title);
		// String html = doc.html();
		// System.out.println("HTML:\n" + html);

		Elements elements = doc.getElementsByTag("strong");
		System.out.println("ELEMENTS:\n" + elements);
		System.out.println("ELEMENTS SIZE:\n" + elements.size());

		for (int i = 0; i < elements.size(); i++) {
			numbers[i] = Long.parseLong(elements.get(i).text());
			System.out.println("NUMBER(" + i + ") :\n" + numbers[i]);
		}

		dividend = numbers[0];
		divisor = numbers[1];

		if (divisor > dividend) {
			long temp = divisor;
			divisor = dividend;
			dividend = temp;
		}

		result = String.valueOf(doCaluculation(dividend, divisor));

		System.out.println("RESULT  : \n" + result);

		List<FormElement> formElements = doc.getAllElements().forms();
		System.out.println("FORM ELEMENTS:\n" + formElements);

		Elements fe = null;
		Iterator<FormElement> itr = formElements.iterator();

		for (; itr.hasNext();) {
			fe = itr.next().getElementsByTag("input");
		}

		System.out.println("INPUT ELEMENT:\n" + fe);
		fe.val(result);
		System.out.println(fe.val());

		Document resultSet = form.submit().cookies(resp.cookies()).post();
		System.out.println(resultSet.wholeText());
		//System.out.println(resultSet.html());
	}
	

	public static long doCaluculation(long p_dividend, long p_divisor) {

		int quotient = 0;
		long remainder = -1;
		long l_dividend = p_dividend;
		long l_divisor = p_divisor;

		while (remainder != 0) {
			quotient = (int) (l_dividend / l_divisor);
			long tempval = l_divisor * quotient;
			remainder = l_dividend - tempval;
			l_dividend = l_divisor;
			l_divisor = remainder;
		}
		return l_dividend;
	}


}
