package cn.yiyingli.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;



public class ConfigurationXmlUtil {

	private static final String XML_PATH = "/config.xml";

	private static ConfigurationXmlUtil singleInstance;

	public synchronized static ConfigurationXmlUtil getInstance() {
		if (singleInstance == null) {
			singleInstance = new ConfigurationXmlUtil();
			try {
				singleInstance.init();
			} catch (SAXException | IOException | ParserConfigurationException e) {
				e.printStackTrace();
			}
		}
		return singleInstance;
	}

	private Map<String, Map<String, String>> configData;
	private Map<String, String> settingData;

	public Map<String, Map<String, String>> getConfigData() {
		return configData;
	}

	public Map<String, String> getSettingData() {
		return settingData;
	}

	private synchronized void init() throws SAXException, IOException,
			ParserConfigurationException {
		SAXParserFactory saxPF = SAXParserFactory.newInstance();
		SAXParser saxParser = saxPF.newSAXParser();
		InputStream is = new FileInputStream(new File(getClass().getResource(
				XML_PATH).getPath()));
		configData = new HashMap<String, Map<String, String>>();
		settingData = new HashMap<String, String>();
		saxParser.parse(is, new SaxReadHandler(configData, settingData));
	}

	public static void main(String args[]) {
		Map<String, Map<String, String>> data = ConfigurationXmlUtil
				.getInstance().getConfigData();
		Iterator<String> it = data.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			System.out.println("style:" + key);
			Map<String, String> d = data.get(key);
			Iterator<String> i = d.keySet().iterator();
			while (i.hasNext()) {
				String k = i.next();
				System.out.println(k + ":" + d.get(k));
			}
		}
	}
}