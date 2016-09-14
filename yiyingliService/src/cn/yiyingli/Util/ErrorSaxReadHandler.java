package cn.yiyingli.Util;

import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ErrorSaxReadHandler extends DefaultHandler {

	private Map<String, String> settingData;
	private String tagName;

	public ErrorSaxReadHandler(Map<String, String> s) {
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
		if ("error".equals(tagName)) {
			String name = attributes.getValue("name");
			String value = attributes.getValue("value");
			settingData.put(name, value);
		} else {
			String value = attributes.getValue("value");
			settingData.put(tagName, value);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// DO NOTHING
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
	}
}
