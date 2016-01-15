package cn.yiyingli.Weixin.Util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cn.yiyingli.Util.MD5Util;

public class WXMessageUtil {

	private static final String KEY = "6C448DCD81D942A15EE79BD3221E78D4";

	public static String getContent(String xml, String key) {
		int start = xml.indexOf("<" + key + ">") + 2 + key.length();
		int end = xml.indexOf("</" + key + ">");
		String content = xml.substring(start, end);
		if (content.startsWith("<![CDATA[") && content.endsWith("]]>")) {
			content = content.substring(9, content.length() - 3);
		}
		return content;
	}

	public static boolean checkMsg(String responseString) {
		try {
			String sign = getSignFromResponseString(responseString);
			String signFromWX = getContent(responseString, "sign");
			if (signFromWX.equals(sign)) {
				return true;
			} else {
				return false;
			}
		} catch (IOException | SAXException | ParserConfigurationException e) {
			return false;
		}
	}

	public static String getSign(Map<String, Object> map) {
		ArrayList<String> list = new ArrayList<String>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() != "") {
				list.add(entry.getKey() + "=" + entry.getValue() + "&");
			}
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
		String result = sb.toString();
		result += "key=" + KEY;
		// Util.log("Sign Before MD5:" + result);
		result = MD5Util.MD5(result);
		// Util.log("Sign Result:" + result);
		return result;
	}

	public static String getSignFromResponseString(String responseString)
			throws IOException, SAXException, ParserConfigurationException {
		Map<String, Object> map = getMapFromXML(responseString);
		// 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
		map.put("sign", "");
		// 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
		return getSign(map);
	}

	public static InputStream getStringStream(String sInputString) {
		ByteArrayInputStream tInputStringStream = null;
		if (sInputString != null && !sInputString.trim().equals("")) {
			tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
		}
		return tInputStringStream;
	}

	public static Map<String, Object> getMapFromXML(String xmlString)
			throws ParserConfigurationException, IOException, SAXException {

		// 这里用Dom的方式解析回包的最主要目的是防止API新增回包字段
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputStream is = getStringStream(xmlString);
		Document document = builder.parse(is);

		// 获取到document里面的全部结点
		NodeList allNodes = document.getFirstChild().getChildNodes();
		Node node;
		Map<String, Object> map = new HashMap<String, Object>();
		int i = 0;
		while (i < allNodes.getLength()) {
			node = allNodes.item(i);
			if (node instanceof Element) {
				map.put(node.getNodeName(), node.getTextContent());
			}
			i++;
		}
		return map;

	}

}
