package cn.yiyingli.toPersistant;

import java.util.Calendar;

import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.ServiceProService;
import net.sf.json.JSONObject;

public class PServiceProUtil {

	// public static void assembleWithTeacherByApplication(Teacher teacher,
	// String imageUrls, String summary,
	// String content, float price, float numeral, int count, String quantifier,
	// String title, String tip,
	// String freeTime, ServicePro servicePro) {
	// assemble(content, imageUrls, summary, price, numeral, count, quantifier,
	// title, servicePro);
	// String time = Calendar.getInstance().getTimeInMillis() + "";
	// servicePro.setOnSale(false);
	// servicePro.setPriceTemp(0F);
	// servicePro.setAnswerRatio(0F);
	// servicePro.setAnswerTime(0L);
	// servicePro.setCreateTime(time);
	// servicePro.setAcceptNo(0L);
	// servicePro.setFinishNo(0L);
	// servicePro.setFreeTime(freeTime);
	// servicePro.setLikeNo(0L);
	// servicePro.setKind(0);
	// servicePro.setOnShow(false);
	// servicePro.setPraiseRatio(0F);
	// servicePro.setScore(0);
	// servicePro.setState(ServiceProService.STATE_CHECKING);
	// servicePro.setTips(tip);
	// servicePro.setUpdateTime(time);
	// servicePro.setTeacher(teacher);
	// servicePro.setStyle(ServiceProService.STYLE_TALK);
	// teacher.getServicePros().add(servicePro);
	// }

	private static void assemble(String content, String imageUrls, String summary, float price, float numeral,
			int count, String quantifier, String title, ServicePro servicePro) {
		servicePro.setImageUrls(imageUrls);
		servicePro.setContent(content);
		servicePro.setSummary(summary);
		servicePro.setPrice(price);
		servicePro.setTitle(title);
		servicePro.setNumber(count);
		servicePro.setNumeral(numeral);
		servicePro.setQuantifier(quantifier);
	}

	private static void assemble(JSONObject jsonServicePro, ServicePro servicePro) {
		assemble(jsonServicePro.getString("content"), jsonServicePro.getString("imageUrls"),
				jsonServicePro.getString("summary"), Float.valueOf(jsonServicePro.getString("price")),
				Float.valueOf(jsonServicePro.getString("numeral")), jsonServicePro.getInt("count"),
				jsonServicePro.getString("quantifier"), jsonServicePro.getString("title"), servicePro);
	}

	public static void assembleByTeacherEdit(JSONObject jsonServicePro, ServicePro servicePro) {
		assemble(jsonServicePro, servicePro);
		String time = Calendar.getInstance().getTimeInMillis() + "";
		servicePro.setOnSale(Boolean.valueOf(jsonServicePro.getString("onsale")));
		servicePro.setPriceTemp(Float.valueOf(jsonServicePro.getString("priceTemp")));
		servicePro.setFreeTime(jsonServicePro.getString("freeTime"));
		// 类别
		servicePro.setKind(jsonServicePro.getInt("kind"));
		servicePro.setOnShow(Boolean.valueOf(jsonServicePro.getString("onshow")));
		// 修改后需要审核
		servicePro.setState(ServiceProService.STATE_CHECKING);
		servicePro.setTips(jsonServicePro.getString("tip"));
		servicePro.setUpdateTime(time);
		servicePro.setRemove(false);
	}

	public static void assembleByTeacherEdit(int count, float price, float priceTemp, float numeral, int kind,
			String freeTime, String tip, String onshow, String onsale, String quantifier, String servicetitle,
			String servicecontent, String imageUrls, String summary, ServicePro servicePro) {
		assemble(servicecontent, imageUrls, summary, price, numeral, count, quantifier, servicetitle, servicePro);
		String time = Calendar.getInstance().getTimeInMillis() + "";
		servicePro.setOnSale(Boolean.valueOf(onsale));
		servicePro.setPriceTemp(priceTemp);
		servicePro.setFreeTime(freeTime);
		// 类别
		servicePro.setKind(kind);
		servicePro.setOnShow(Boolean.valueOf(onshow));
		// 修改需要审核
		servicePro.setState(ServiceProService.STATE_CHECKING);
		servicePro.setTips(tip);
		servicePro.setUpdateTime(time);
		servicePro.setRemove(false);
	}

	public static void assembleWithTeacherByTeacherCreate(Teacher teacher, JSONObject jsonServicePro,
			ServicePro servicePro) {
		// TODO
		assemble(jsonServicePro, servicePro);
		String time = Calendar.getInstance().getTimeInMillis() + "";

		servicePro.setShowWeight1(0);
		servicePro.setShowWeight2(0);
		servicePro.setShowWeight3(0);
		servicePro.setShowWeight4(0);
		servicePro.setShowWeight5(0);
		servicePro.setHomeWeight(0);
		servicePro.setSaleWeight(0);

		servicePro.setOnSale(false);
		servicePro.setPriceTemp(0F);
		servicePro.setAnswerRatio(0F);
		servicePro.setAnswerTime(0L);
		servicePro.setCreateTime(time);
		servicePro.setAcceptNo(0L);
		servicePro.setFinishNo(0L);
		servicePro.setFreeTime(jsonServicePro.getString("freeTime"));
		servicePro.setLikeNo(0L);
		servicePro.setKind(0);
		servicePro.setOnShow(false);
		servicePro.setPraiseRatio(0F);
		servicePro.setScore(0);
		servicePro.setState(ServiceProService.STATE_CHECKING);
		servicePro.setTips(jsonServicePro.getString("tip"));
		servicePro.setUpdateTime(time);
		servicePro.setTeacher(teacher);
	}

	public static void assembleWithTeacherByManager(Teacher teacher, JSONObject jsonServicePro, ServicePro servicePro) {
		assemble(jsonServicePro, servicePro);
		String time = Calendar.getInstance().getTimeInMillis() + "";

		servicePro.setShowWeight1(jsonServicePro.getInt("showWeight1"));
		servicePro.setShowWeight2(jsonServicePro.getInt("showWeight2"));
		servicePro.setShowWeight3(jsonServicePro.getInt("showWeight3"));
		servicePro.setShowWeight4(jsonServicePro.getInt("showWeight4"));
		servicePro.setShowWeight5(jsonServicePro.getInt("showWeight5"));
		servicePro.setHomeWeight(jsonServicePro.getInt("saleWeight"));
		servicePro.setSaleWeight(jsonServicePro.getInt("homeWeight"));

		servicePro.setRemove(false);
		servicePro.setOnSale(false);
		servicePro.setPriceTemp(0F);
		servicePro.setAnswerRatio(0F);
		servicePro.setAnswerTime(0L);
		servicePro.setCreateTime(time);
		servicePro.setAcceptNo(0L);
		servicePro.setFinishNo(0L);
		servicePro.setFreeTime(jsonServicePro.getString("freeTime"));
		servicePro.setLikeNo(0L);
		servicePro.setKind(0);
		servicePro.setOnShow(false);
		servicePro.setPraiseRatio(0F);
		servicePro.setScore(0);
		servicePro.setState(ServiceProService.STATE_OK);
		servicePro.setTips(jsonServicePro.getString("tip"));
		servicePro.setUpdateTime(time);
		servicePro.setTeacher(teacher);
	}

	public static void editrByManager(int count, float price, float priceTemp, float numeral, int kind, String freeTime,
			String tip, String onshow, String onsale, String quantifier, String servicetitle, String servicecontent,
			String imageUrls, String summary, int homeWeight, int saleWeight, ServicePro servicePro) {
		assemble(servicecontent, imageUrls, summary, price, numeral, count, quantifier, servicetitle, servicePro);
		servicePro.setHomeWeight(homeWeight);
		servicePro.setSaleWeight(saleWeight);
		servicePro.setOnSale(Boolean.valueOf(onsale));
		servicePro.setPriceTemp(priceTemp);
		servicePro.setFreeTime(freeTime);
		// 类别
		servicePro.setKind(kind);
		servicePro.setOnShow(Boolean.valueOf(onshow));
		servicePro.setTips(tip);
	}

	public static void editPriceByTeacher(float price, float numeral, int count, String quantifier, String title,
			boolean onsale, float pricetemp, boolean onshow, ServicePro servicePro) {
		servicePro.setOnSale(onsale);
		servicePro.setPriceTemp(pricetemp);
		servicePro.setTitle(title);
		servicePro.setNumeral(numeral);
		servicePro.setNumber(count);
		servicePro.setQuantifier(quantifier);
		servicePro.setTitle(title);
		servicePro.setPrice(price);
		servicePro.setOnShow(onshow);
	}

	public static void editCountByTeacher(int count, ServicePro servicePro) {
		servicePro.setNumber(count);
	}

}
