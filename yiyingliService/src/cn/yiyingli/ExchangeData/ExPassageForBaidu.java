package cn.yiyingli.ExchangeData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.yiyingli.Persistant.Passage;
import cn.yiyingli.Util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ExPassageForBaidu {

	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static JSONObject assemblePassage(Passage passage) {
		JSONObject toBaidu = new JSONObject();
		toBaidu.put("Version", 1.0);
		toBaidu.put("ItemId", passage.getId() + "");
		toBaidu.put("DisplaySwitch", (passage.getRemove() && passage.getOnshow()) ? "Off" : "On");
		toBaidu.put("Url", "http://www.1yingli.cn/passage/" + passage.getId());
		JSONObject indexed = new JSONObject();
		indexed.put("Title", passage.getTitle());
		indexed.put("Content", StringUtil.subStringHTML(passage.getContent(), 0xffffff, ""));
		JSONArray labels = new JSONArray();
		String[] tags = passage.getTag().split(",");
		for (String t : tags) {
			JSONObject l = new JSONObject();
			l.put("Label", t);
			l.put("Weight", 5);
			labels.add(l);
		}
		indexed.put("Labels", labels);
		toBaidu.put("Indexed", indexed);

		JSONObject properties = new JSONObject();
		properties.put("Quality", 1);
		JSONArray category = new JSONArray();
		category.add(passage.getEditorName());
		properties.put("Category", category);
		DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		properties.put("CreateTime", formatter.format(new Date()));
		toBaidu.put("Properties", properties);
		toBaidu.put("Auxiliary", "");
		return toBaidu;
	}
}
