package cn.yiyingli.ExchangeData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Service.ServiceProService;
import cn.yiyingli.Util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ExServiceProForBaidu {

	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static JSONObject assembleServicePro(ServicePro servicePro) {
		JSONObject toBaidu = new JSONObject();
		toBaidu.put("Version", 1.0);
		toBaidu.put("ItemId", servicePro.getId() + "");
		toBaidu.put("DisplaySwitch", (servicePro.getRemove() || !servicePro.getOnShow()
				|| servicePro.getState().shortValue() != ServiceProService.STATE_OK) ? "Off" : "On");
		toBaidu.put("Url", "http://www.1yingli.cn/service/" + servicePro.getId());
		JSONObject indexed = new JSONObject();
		indexed.put("Title", servicePro.getTitle());
		indexed.put("Content", StringUtil.subStringHTML(servicePro.getContent(), 0xffffff, ""));
		JSONArray labels = new JSONArray();
		JSONObject l = new JSONObject();
		l.put("Label", servicePro.getTeacher().getId() + "");
		l.put("Weight", 1);
		labels.add(l);

		indexed.put("Labels", labels);
		toBaidu.put("Indexed", indexed);

		JSONObject properties = new JSONObject();
		properties.put("Quality", 1);
		JSONArray category = new JSONArray();
		category.add(servicePro.getKind());
		properties.put("Category", category);
		DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		properties.put("CreateTime", formatter.format(new Date()));
		toBaidu.put("Properties", properties);
		toBaidu.put("Auxiliary", "");
		return toBaidu;
	}
}
