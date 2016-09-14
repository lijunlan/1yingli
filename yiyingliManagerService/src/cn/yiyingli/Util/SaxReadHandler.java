package cn.yiyingli.Util;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;

public class SaxReadHandler extends DefaultHandler {

	private Map<String, Map<String, String>> data;
	private Map<String, String> methodData;
	private Map<String, String> settingData;
	private String tagName;

	public SaxReadHandler(Map<String, Map<String, String>> d,
			Map<String, String> s) {
		data = d;
		settingData = s;
	}

	public void startDocument() throws SAXException {
		// 开始解析
	}

	public void endDocument() throws SAXException {
		// 解析结束
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// System.out.println(localName + "\t" + qName);
		tagName = qName;
		if ("style".equals(tagName)) {
			String name = attributes.getValue("name");
			methodData = new HashMap<String, String>();
			data.put(name, methodData);
		} else if ("method".equals(tagName)) {
			String name = attributes.getValue("name");
			String beanName = attributes.getValue("beanName");
			methodData.put(name, beanName);
		} else {
			String value = attributes.getValue("value");
			settingData.put(tagName, value);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equals("style")) {
			methodData = null;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
	}
}
