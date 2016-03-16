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

	private static void assemble(String content, String imageUrls, String summary, String address, short talkWay,
			float price, float numeral, int count, String quantifier, String title, ServicePro servicePro) {
		servicePro.setImageUrls(imageUrls);
		servicePro.setContent(content);
		servicePro.setSummary(summary);
		servicePro.setPrice(price);
		servicePro.setTitle(title);
		servicePro.setNumber(count);
		servicePro.setNumeral(numeral);
		servicePro.setQuantifier(quantifier);
		servicePro.setAddress(address);
		servicePro.setTalkWay(talkWay);
	}

	private static void assemble(JSONObject jsonServicePro, ServicePro servicePro) {
		assemble(jsonServicePro.getString("content"), jsonServicePro.getString("imageUrls"),
				jsonServicePro.getString("summary"), jsonServicePro.getString("address"),
				Short.valueOf(jsonServicePro.getString("talkWay")), Float.valueOf(jsonServicePro.getString("price")),
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
			short talkway, String address, String freeTime, String tip, String onshow, String onsale, String quantifier,
			String servicetitle, String servicecontent, String imageUrls, String summary, ServicePro servicePro) {
		assemble(servicecontent, imageUrls, summary, address, talkway, price, numeral, count, quantifier, servicetitle,
				servicePro);
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
		assemble(jsonServicePro, servicePro);
		String time = Calendar.getInstance().getTimeInMillis() + "";

		servicePro.setOrderAllNo(0L);
		servicePro.setCommentNo(0L);
		servicePro.setRemove(false);
		servicePro.setOnSale(false);
		servicePro.setPriceTemp(0F);
		servicePro.setAnswerRatio(0F);
		servicePro.setAnswerTime(0L);
		servicePro.setCreateTime(time);
		servicePro.setAcceptNo(0L);
		servicePro.setFinishNo(0L);
		servicePro.setMaskFinishNo(0L);
		servicePro.setMaskNo(0L);
		servicePro.setFreeTime(jsonServicePro.getString("freeTime"));
		servicePro.setLikeNo(0L);
		servicePro.setLookNumber(0L);
		servicePro.setKind(jsonServicePro.getInt("kind"));
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
		
		servicePro.setCommentNo(0L);
		servicePro.setOrderAllNo(0L);
		servicePro.setRemove(false);
		servicePro.setOnSale(false);
		servicePro.setPriceTemp(0F);
		servicePro.setAnswerRatio(0F);
		servicePro.setAnswerTime(0L);
		servicePro.setCreateTime(time);
		servicePro.setAcceptNo(0L);
		servicePro.setFinishNo(0L);
		servicePro.setMaskFinishNo(0L);
		servicePro.setMaskNo(0L);
		servicePro.setFreeTime(jsonServicePro.getString("freeTime"));
		servicePro.setLikeNo(0L);
		servicePro.setLookNumber(0L);
		servicePro.setKind(jsonServicePro.getInt("kind"));
		servicePro.setOnShow(true);
		servicePro.setPraiseRatio(0F);
		servicePro.setScore(0);
		servicePro.setState(ServiceProService.STATE_OK);
		servicePro.setTips(jsonServicePro.getString("tip"));
		servicePro.setUpdateTime(time);
		servicePro.setTeacher(teacher);
	}

	public static void editrByManager(int count, float price, float priceTemp, float numeral, int kind, short talkWay,
			String address, String freeTime, String tip, String onshow, String onsale, String quantifier,
			String servicetitle, String servicecontent, String imageUrls, String summary, ServicePro servicePro) {
		assemble(servicecontent, imageUrls, summary, address, talkWay, price, numeral, count, quantifier, servicetitle,
				servicePro);
		servicePro.setUpdateTime(Calendar.getInstance().getTimeInMillis() + "");
		servicePro.setOnSale(Boolean.valueOf(onsale));
		servicePro.setPriceTemp(priceTemp);
		servicePro.setFreeTime(freeTime);
		// 类别
		servicePro.setKind(kind);
		servicePro.setOnShow(Boolean.valueOf(onshow));
		servicePro.setTips(tip);
	}

	public static void editPriceByTeacher(float price, float numeral, int count, String quantifier, boolean onsale,
			float pricetemp, boolean onshow, String freeTime, ServicePro servicePro) {
		servicePro.setUpdateTime(Calendar.getInstance().getTimeInMillis() + "");
		servicePro.setOnSale(onsale);
		servicePro.setPriceTemp(pricetemp);
		servicePro.setNumeral(numeral);
		servicePro.setFreeTime(freeTime);
		servicePro.setNumber(count);
		servicePro.setQuantifier(quantifier);
		servicePro.setPrice(price);
		servicePro.setOnShow(onshow);
	}

	public static void editCountByTeacher(int count, ServicePro servicePro) {
		servicePro.setUpdateTime(Calendar.getInstance().getTimeInMillis() + "");
		servicePro.setNumber(count);
	}

}
