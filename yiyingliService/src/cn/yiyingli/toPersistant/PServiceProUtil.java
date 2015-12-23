package cn.yiyingli.toPersistant;

import java.util.Calendar;

import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.ServiceProService;

public class PServiceProUtil {

	public static void assembleWithTeacherByApplication(Teacher teacher, String content, float price, float numeral,
			int count, String quantifier, String title, String tip, String freeTime, ServicePro servicePro) {
		assemble(content, price, numeral, count, quantifier, title, servicePro);
		String time = Calendar.getInstance().getTimeInMillis() + "";
		servicePro.setOnSale(false);
		servicePro.setPriceTemp(0F);
		servicePro.setAnswerRatio(0F);
		servicePro.setAnswerTime(0L);
		servicePro.setCreateTime(time);
		servicePro.setAcceptNo(0L);
		servicePro.setFinishNo(0L);
		servicePro.setFreeTime(freeTime);
		servicePro.setLikeNo(0L);
		servicePro.setKind(0);
		servicePro.setOnShow(false);
		servicePro.setPraiseRatio(0F);
		servicePro.setScore(0);
		servicePro.setState(ServiceProService.STATE_CHECKING);
		servicePro.setTips(tip);
		servicePro.setUpdateTime(time);
		servicePro.setTeacher(teacher);
		servicePro.setStyle(ServiceProService.STYLE_TALK);
		teacher.getServicePros().add(servicePro);
	}

	private static void assemble(String content, float price, float numeral, int count, String quantifier, String title,
			ServicePro servicePro) {
		servicePro.setContent(content);
		servicePro.setPrice(price);
		servicePro.setTitle(title);
		servicePro.setNumber(count);
		servicePro.setNumeral(numeral);
		servicePro.setQuantifier(quantifier);
	}

	public static void assembleByTeacherEdit(int count, float price, float priceTemp, float numeral, int kind,
			String freeTime, String tip, String onshow, String onsale, String quantifier, String servicetitle,
			String servicecontent, ServicePro servicePro) {
		assemble(servicecontent, price, numeral, count, quantifier, servicetitle, servicePro);
		String time = Calendar.getInstance().getTimeInMillis() + "";
		servicePro.setOnSale(Boolean.valueOf(onsale));
		servicePro.setPriceTemp(priceTemp);
		servicePro.setFreeTime(freeTime);
		// 类别
		servicePro.setKind(kind);
		servicePro.setOnShow(Boolean.valueOf(onshow));
		// TODO 修改后是否需要审核
		// servicePro.setState(ServiceProService.STATE_CHECKING);
		servicePro.setTips(tip);
		servicePro.setUpdateTime(time);
	}

	public static void editWithTeacherByManager(short state, int count, float price, float priceTemp, float numeral,
			int kind, String freeTime, String tip, String onshow, String onsale, String quantifier, String servicetitle,
			String servicecontent, ServicePro servicePro) {
		assemble(servicecontent, price, numeral, count, quantifier, servicetitle, servicePro);
		servicePro.setOnSale(Boolean.valueOf(onsale));
		servicePro.setPriceTemp(priceTemp);
		servicePro.setFreeTime(freeTime);
		// 类别
		servicePro.setKind(kind);
		servicePro.setOnShow(Boolean.valueOf(onshow));
		servicePro.setState(state);
		servicePro.setTips(tip);
	}

	public static void assembleWithTeacherByManager(Teacher teacher, String content, float price, float numeral,
			int count, String quantifier, String title, String tip, String freeTime, ServicePro servicePro) {
		assemble(content, price, numeral, count, quantifier, title, servicePro);
		String time = Calendar.getInstance().getTimeInMillis() + "";
		servicePro.setOnSale(false);
		servicePro.setPriceTemp(0F);
		servicePro.setAnswerRatio(0F);
		servicePro.setAnswerTime(0L);
		servicePro.setCreateTime(time);
		servicePro.setAcceptNo(0L);
		servicePro.setFinishNo(0L);
		servicePro.setFreeTime(freeTime);
		servicePro.setLikeNo(0L);
		servicePro.setKind(0);
		servicePro.setOnShow(true);
		servicePro.setPraiseRatio(0F);
		servicePro.setScore(0);
		servicePro.setState(ServiceProService.STATE_OK);
		servicePro.setTips(tip);
		servicePro.setUpdateTime(time);
		servicePro.setTeacher(teacher);
		servicePro.setStyle(ServiceProService.STYLE_SERVICE);
		teacher.getServicePros().add(servicePro);
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
