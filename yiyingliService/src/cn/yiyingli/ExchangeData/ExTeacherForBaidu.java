package cn.yiyingli.ExchangeData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import cn.yiyingli.Persistant.StudyExperience;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.Tip;
import cn.yiyingli.Persistant.WorkExperience;
import cn.yiyingli.Util.LogUtil;
import cn.yiyingli.Util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//TODO  发给百度的物料数据也要改变
public class ExTeacherForBaidu {

	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static JSONObject assembleTeacher(Teacher teacher) {
		try {
			JSONArray jsonLabels = new JSONArray();
			List<WorkExperience> workExperiences = teacher.getWorkExperiences();
			for (WorkExperience w : workExperiences) {
				JSONObject jsonLabel = new JSONObject();
				jsonLabel.put("Label", w.getCompanyName());
				jsonLabel.put("Weight", 5);
				jsonLabels.add(jsonLabel);
				jsonLabel = new JSONObject();
				jsonLabel.put("Label", w.getPosition());
				jsonLabel.put("Weight", 2);
				jsonLabels.add(jsonLabel);
			}
			List<StudyExperience> studyExperiences = teacher.getStudyExperiences();
			for (StudyExperience s : studyExperiences) {
				JSONObject jsonLabel = new JSONObject();
				jsonLabel.put("Label", s.getSchoolName());
				jsonLabel.put("Weight", 5);
				jsonLabels.add(jsonLabel);
				jsonLabel = new JSONObject();
				jsonLabel.put("Label", s.getMajor());
				jsonLabel.put("Weight", 3);
				jsonLabels.add(jsonLabel);
				jsonLabel = new JSONObject();
				jsonLabel.put("Label", s.getDegree());
				jsonLabel.put("Weight", 2);
				jsonLabels.add(jsonLabel);
			}
			Set<Tip> tips = teacher.getTips();
			JSONArray jsonCategory = new JSONArray();
			for (Tip t : tips) {
				jsonCategory.add(t.getId());
			}
			String Auxiliary = teacher.getSimpleInfo();
			JSONObject toBaidu = new JSONObject();
			toBaidu.put("Version", 1.0);
			toBaidu.put("ItemId", "" + teacher.getId());
			toBaidu.put("DisplaySwitch", teacher.getOnService() ? "On" : "Off");
			toBaidu.put("Url", "http://www.1yingli.cn/teacher/" + teacher.getId());
			JSONObject jsonIndexed = new JSONObject();
			jsonIndexed.put("Title", teacher.getTopic());
			jsonIndexed.put("Content", StringUtil.subStringHTML(teacher.getIntroduce(), 500, ""));
			jsonIndexed.put("Labels", jsonLabels);
			toBaidu.put("Indexed", jsonIndexed);
			JSONObject jsonProperties = new JSONObject();
			jsonProperties.put("Quality", 1);
			jsonProperties.put("Category", jsonCategory);
			DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
			jsonProperties.put("CreateTime", formatter.format(new Date()));
			toBaidu.put("Properties", jsonProperties);
			toBaidu.put("Auxiliary", Auxiliary);
			return toBaidu;
		} catch (Exception e) {
			LogUtil.error(e.getMessage(), ExTeacherForBaidu.class);
		}
		return null;
	}

}
