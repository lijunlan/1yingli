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

public class ErrorTransformUtil {
	private static final String XML_PATH = "/error.xml";

	private static ErrorTransformUtil singleInstance;

	public synchronized static ErrorTransformUtil getInstance() {
		if (singleInstance == null) {
			singleInstance = new ErrorTransformUtil();
			try {
				singleInstance.init();
			} catch (SAXException | IOException | ParserConfigurationException e) {
				e.printStackTrace();
			}
		}
		return singleInstance;
	}

	private Map<String, String> settingData;

	public Map<String, String> getSettingData() {
		return settingData;
	}

	private synchronized void init() throws SAXException, IOException, ParserConfigurationException {
		SAXParserFactory saxPF = SAXParserFactory.newInstance();
		SAXParser saxParser = saxPF.newSAXParser();
		InputStream is = new FileInputStream(new File(getClass().getResource(XML_PATH).getPath()));
		settingData = new HashMap<String, String>();
		saxParser.parse(is, new SaxErrorReadHandler(settingData));
	}

	public static void main(String args[]) {
		Map<String, String> data = ErrorTransformUtil.getInstance().getSettingData();
		Iterator<String> it = data.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			String d = data.get(key);

			System.out.println(key + ":" + d);

		}
	}
}
