package Perform;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
//import java.io.StringWriter;
//import java.util.Map;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.Document;
//import org.json.simple.JSONObject;
//import org.bson.json.JsonObject;
//import org.json.simple.*;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
import org.json.JSONObject;
import org.json.JSONTokener;

//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

//import org.json.simple.JSONObject;

@WebServlet("/TextPage")
public class TextPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int textCount;
	char[][] control = new char[100][100];
	char[] finalText;

	int p = 0;
	int countText = 0;
	int lst = 1;
	// String temp1;

	public boolean CanConcat(String text1, String text2) {

		boolean retVal = Boolean.FALSE;
		for (int m = 0; m < (text2.length() - 1); m++) {
			retVal = text1.toLowerCase().startsWith(text2.toLowerCase().substring(m));
			System.out.print("retval" + retVal);
		}

		return retVal;
	}

	public int ControlOfSentence(String txt) {

		int sayac = 1;

		for (int i = 0; i < txt.length(); i++) {
			if (txt.charAt(i) == ' ') {
				sayac++;
			}
		}

		return sayac;
	}

	public boolean commonPrefixUtil(String str1, String str2) {
		String result = "";
		int n1 = str1.length(), n2 = str2.length();

		for (int i = 0, j = 0; i <= n1 - 1 && j <= n2 - 1; i++, j++) {
			if (str1.toLowerCase().charAt(i) != str2.toLowerCase().charAt(j)) {
				break;
			}
			result += str1.charAt(i);
		}

		if (result.length() > 1) {
			return true;
		} else
			return false;
	}

	public int getIndex(String[] get1, String[] get2) {

		boolean cnt = false;
		int index1;

		for (int k = 0; k < get1.length; k++) {

			for (int s = 0; s < get2.length; s++) {

				cnt = commonPrefixUtil(get1[k], get2[s]);
				if (cnt == true) {
					index1 = k;
					return index1;
					// System.out.println("index:"+index1);
					// break;

				}
			}
		}

		return -1;

	}

	public String getCommon(String get1, String get2) {

		String result = "";
		int n1 = get1.length(), n2 = get2.length();
		int start = 0;
		// int i=0;

		for (int i = 0, j = 0; i <= n1 - 1 && j <= n2 - 1; i++, j++) {
			if (get1.toLowerCase().charAt(i) != get2.toLowerCase().charAt(j)) {
				break;
			}
			result += get1.charAt(i);
		}

		/*
		 * for( i=start;i<n1;i++) { for(int j=0;j<n2;j++) { if
		 * (get1.toLowerCase().charAt(i) != get2.toLowerCase().charAt(j)) {
		 * 
		 * break;//start++; } else { result += get1.charAt(i);
		 * 
		 * } }
		 * 
		 * }
		 */

		return result;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();
		String[] txts = request.getParameterValues("addButton");
		JSONObject obj = new JSONObject();
		long beginTime = 0, endTime = 0;

		int e = 1;
		for (int z = 0; z < txts.length; z++) {
			obj.put("Metin " + (e++), txts[z]);
		}

		// HttpSession session = null;
		String cap = request.getParameter("s1");

		int i = 0;
		pw.write("<head>" + "\r\n" + "\r\n" + "<script type=\"text/javascript\" src=\"getTextbook.js\"></script>\r\n"
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html\" charset=\"UTF-8\">"
				+ "<link rel=\"stylesheet\" href=\"Separate.css\">\r\n" + "\r\n" + "</head>");
		pw.write("<center><h1 class=baslik>KELIME BENZERLIK VE FREKANS HESAPLAMA SITESINE HOSGELDINIZ</h1></center>\r\n"
				+ "		<br>\r\n" + "		<left>\r\n" + "		<form id=frm method=get action=TextPage>\r\n"
				+ "		\r\n" + "          	\r\n" + "\r\n"
				+ "			<a class=renk>Metin 1:<input class=txt type=text name=addButton></a>&nbsp \r\n"
				+ "			<a class=renk>Metin 2:<input class=txt type=text name=addButton></a>\r\n"
				+ "			  \r\n" + "			    \r\n"
				+ "			    <input id=ekle type=button class=button value=\"EKLE\" onclick=\"addTextBox()\">&nbsp\r\n"
				+ "			    <input type=submit class=button name=s1 value=\"BIRLESTIR\" onclick=\"getTextboxName()\">&nbsp\r\n"
				+ "				<input type=submit class=button name=s1 value=\"KAYDET\">\r\n" + "				\r\n"
				+ "				<br>\r\n" + "				\r\n" + "				\r\n" + "\r\n" + "		</form>\r\n"
				+ "		</left>");

		pw.write("<br><br>");
		pw.write("<center>");

		/*
		 * for (String text : txts) { pw.write(
		 * "<a style=\"background-color:white;border:2px solid red;height:10px;margin:10px;padding:2px;width:20px;font-size:30px;\">"
		 * + text + "</a>"); countText++; for (int s = 0; s < text.length(); s++) {
		 * control[i][s] = text.charAt(i); } i++;
		 * 
		 * }
		 */

		// System.out.println("Txt uzunluğu:" + txts.length);
		int control1 = 0;
		int control2 = 0;
		String[] txt1 = null;
		String[] txt2 = null;
		String[] got = null;
		char[] Result;
		boolean cnt;
		int t1 = 0;
		int t2 = 1;

		String fin = "";
		String result = "";
		String other = "";
		int size = 0;
		int hold = 0;
		int index1 = 0;
		String cmmn = "";
		beginTime = System.currentTimeMillis();
		if (cap.equals("BIRLESTIR")) {// BİRLEŞTİRME KODU
			// String[] txts = request.getParameterValues("addButton");
			// session.setAttribute("Metin",txts);
			// response.sendRedirect("TextPage.java");

			String temp1 = txts[t1];
			String temp2 = txts[t2];
			pw.write("<div>");
			pw.write("<a style=\"font-size:20px\">" + lst + "." + "</a>");

			while (t2 < txts.length) {

				control1 = ControlOfSentence(temp1);
				control2 = ControlOfSentence(txts[t2]);
				System.out.println("temp1" + temp1);
				System.out.println("t2" + txts[t2]);

				pw.write(
						"<a class=\"texts\" style=\"background-color:white;border:3px solid green;height:50px;margin:10px;padding:2px;width:150px;font-size:16px;\">"
								+ temp1.toString() + "</a>");
				// pw.write("<hr style=\"width: 1px; height:2px; color:black;\" /> ");
				size += 100;

				if ((control1 >= 1 && control2 > 1) || (control1 > 1 && control2 >= 1)) {// İkiden fazla kelimeyse

					String[] get1 = temp1.split(" ", 0);
					String[] get2 = txts[t2].split(" ", 0);

					index1 = getIndex(get1, get2);
					System.out.println("index1:" + index1);

					if (index1 != -1) {
						for (int d = 0; d < index1; d++) {
							result = result + get1[d] + " ";
						}

						for (int h = index1; h < get1.length; h++) {// 1. kelimenin alınmayan tarafları
							other = other + get1[h] + " ";
						}
						if (other.length() > 0) {
							pw.write(
									"<a class=\"texts\" style=\"background-color:white;border:2px solid red;height:50px;margin:10px;padding:2px;width:150px;font-size:16px;\">"
											+ other.toString() + "</a>");
						}
						size += 100;
						pw.write(
								"<a class=\"texts\" style=\"background-color:white;border:2px solid red;height:50px;margin:10px;padding:2px;width:150px;font-size:16px;\">"
										+ result.toString() + "</a>");
						size += 100;

						for (int d2 = 0; d2 < get2.length; d2++) {
							result = result + get2[d2].toLowerCase() + " ";
						}
						// temp1="";
						temp1 = result;
						fin = result;
						if (result.length() > 0) {
							hold = 1;// Tuttu
						}
						// System.out.println("temp1:"+temp1);
						// System.out.println("Result:"+result);
						System.out.println("t2" + t2);
						result = "";
						other = "";
						pw.write(
								" <a class=\"texts\" style=\"background-color:white;border:3px solid green;height:50px;margin:10px;padding:2px;width:150px;font-size:16px;\">"
										+ txts[t2].toString() + "</a>");
						size += 100;
						t2++;

					}

					else {

						if (t1 != (txts.length - 1) && fin.length() > 1) {
							// t1++;
							pw.write(
									" <a class=\"texts\" style=\"background-color:white;border:3px solid green;height:50px;margin:10px;padding:2px;width:150px;font-size:16px;\">"
											+ txts[t2].toString() + "</a>");
							t2++;
						} else if (t1 != (txts.length - 1) && fin.length() == 0) {
							pw.write(
									" <a class=\"texts\" style=\"background-color:white;border:3px solid green;height:50px;margin:10px;padding:2px;width:150px;font-size:16px;\">"
											+ txts[t2].toString() + "</a>");
							t1++;
							temp1 = txts[t1];
							t2++;
						}
					}

				}

				else if (control1 == 1 && control2 == 1) {

					System.out.println("270. satıra girdi.");
					cmmn = getCommon(temp1, txts[t2]);
					System.out.println("common:" + cmmn);
					String a1 = temp1;
					String a2 = txts[t2];

					if (cmmn.length() > 1) {
						hold = 1;
						int al1 = a1.indexOf(cmmn);

						result = a2;
						for (int s = cmmn.length(); s < a1.length(); s++) {
							other = other + a1.charAt(s);
						}
						if (other.length() > 0) {
							pw.write(
									"<a class=\"texts\" style=\"background-color:white;border:3px solid red;height:50px;margin:10px;padding:2px;width:150px;font-size:16px;\">"
											+ other.toString() + "</a>");
							size += 100;
						}
						pw.write(
								"<a class=\"texts\" style=\"background-color:white;border:3px solid red;height:50px;margin:10px;padding:2px;width:150px;font-size:16px;\">"
										+ cmmn.toString() + "</a>");
						size += 100;
						temp1 = result;
						fin = result;
						result = "";
						other = "";

						pw.write(
								"<a class=\"texts\" style=\"background-color:white;border:3px solid green;height:50px;margin:10px;padding:2px;width:150px;font-size:16px;\">"
										+ a2.toString() + "</a>");
						size += 100;
						t2++;

					}

					else {

						if (t1 != (txts.length - 1) && fin.length() > 1) {
							// t1++;
							pw.write(
									" <a class=\"texts\" style=\"background-color:white;border:3px solid green;height:50px;margin:10px;padding:2px;width:150px;font-size:16px;\">"
											+ a2.toString() + "</a>");
							size += 100;
							t2++;
						} else if (t1 != (txts.length - 1) && fin.length() == 0) {
							pw.write(
									" <a class=\"texts\" style=\"background-color:white;border:3px solid green;height:50px;margin:10px;padding:2px;width:150px;font-size:16px;\">"
											+ a2.toString() + "</a>");
							size += 100;
							t1++;
							temp1 = txts[t1];
							t2++;
						}
					}

				}

				// pw.write("<hr style=\"width: 1px; height:1px;\" /> ");
				// size+=100;

			}
			pw.write("</div>");
			pw.write("<hr width=" + size + "  color=\"black\" />");
			// session.setAttribute("temp", temp1);
			// response.sendRedirect("TextPage.java");
			if (hold == 0) {
				System.out.println("Birleştirilemez");
				pw.write(
						"<div style=\"background-color:white;border:2px solid red;height:50px;margin:10px;padding:2px;width:170px;font-size:20px;\">"
								+ "BİRLEŞTİRİLEMEZ" + "</div>");

				obj.put("Sonuç", "BİRLEŞTİRİLEMEZ");

			}

			else {
				System.out.println(temp1);
				pw.write("<br><br>");
				pw.write(
						"<div style=\"background-color:white;border:2px solid red;height:50px;margin:10px;padding:2px;width:170px;font-size:20px;\">"
								+ temp1.toString() + "</div>");
				obj.put("Sonuç", temp1.toString());

			}

			pw.write("<br><br><br>");
			endTime = System.currentTimeMillis();
			double time = ((double) (endTime - beginTime)) / 1000;
			System.out.println("Süre:" + time);
			obj.put("Süre", time);
			pw.write("<a style=\"font-size:20px;color:red;\">Çalışma Süresi:" + time + " sn" + "</a>");

			System.out.println("size" + size);
			StringWriter out = new StringWriter();
			obj.write(out);

			String jsonText = out.toString();
			System.out.println(jsonText);

			try {
				FileWriter file = new FileWriter("C:/Users/sulta/eclipse-workspace/TextAddingProject/output.json");
				file.write(obj.toString());
				System.out.println("Succesfull");
				file.close();
			} catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}

			lst++;

		}

		if (cap.equals("KAYDET")) {
			String resourceName = "C:/Users/sulta/eclipse-workspace/TextAddingProject/output.json";

			try {
				String contents = new String((Files.readAllBytes(Paths.get(resourceName))));
				JSONObject object = new JSONObject(contents);
				// JSONParser parser = new JSONParser();
				// FileReader file=new FileReader();
				int count = object.length();

				try {
                    
					pw.write("<h2 style=\"color:red;\">BAŞARIYLA KAYDEDİLDİ</h2>");
					MongoClient mongoclient = MongoClients.create("mongodb://localhost:27017");

					MongoDatabase db = mongoclient.getDatabase("Metinler");

					// MongoCollection<org.bson.Document> collection=db.getCollection("texts");
					MongoCollection<Document> collect = db.getCollection("Texts");
					Document doc = new Document("Metin 1", object.get("Metin 1"));
					
					for (int p = 2; p <= (count-2); p++) {
						doc.append("Metin " + p, object.get("Metin "+p));

					}
					 doc.append("Sonuç", object.get("Sonuç"));
					 doc.append("Süre", object.get("Süre"));

					 collect.insertOne(doc);

					// MongoCollection<org.bson.Document> Texts=db.getCollection("Texts");
					// db.texts.insertMany();
					System.out.println("Bağlantı başarılı");

					mongoclient.close();

				} catch (Exception ex) {
					System.out.println(ex.getMessage());
					pw.write("<h2 style=\"color:red;\">KAYDEDİLEMEDİ</h2>");
				}

				
				System.out.println("count:" + count);
			} catch (IOException o) {
				o.printStackTrace();
			}

		}

		/*
		 * for(int k=0;k<txts.length;k++) {
		 * 
		 * for(int l=1;l<txts.length;l++) {
		 * 
		 * control1=ControlOfSentence(txts[k]); control2=ControlOfSentence(txts[l]);
		 * 
		 * if(control1>0 && control2>0) {//İkisi de birden fazla kelimeden oluşuyorsa
		 * 
		 * txt1=txts[k].split(" ");//Cümle kelime olarak atandı.
		 * txt2=txts[l].split(" ");
		 * 
		 * }
		 * 
		 * for(int m=0;m<txt2.length;m++) { for(int p=0;p<txt1.length;p++) {
		 * 
		 * } }
		 * 
		 * }
		 * 
		 * }
		 */

		// pw.write("<a>"+"Son Metin:"+finalText.toString()+"</a>");

		pw.write("</center>");

		/*
		 * Document doc=Jsoup.parse("index.html"); for (Element input :
		 * doc.select("input")){ System.out.println("fora girdi.");
		 * if(input.attr("name").equals("s1")) { System.out.println("ife girdi.");
		 * textCount=Integer.parseInt(input.attr("id"));
		 * System.out.println("Textcount:"+textCount); break; } }
		 */

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request,response);

	}

}
