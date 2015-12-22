//package cn.yiyingli.toPersistant;
//
//import cn.yiyingli.Persistant.TService;
//import cn.yiyingli.Persistant.Teacher;
//
//public class PTServiceUtil {
//
//	public static void assembleWithTeacherByApplication(Teacher teacher, String advantage, String content, float price,
//			String reason, float time, String title, TService tService) {
//		assemble(advantage, content, price, reason, time, title, tService);
//		tService.setOnSale(false);
//		tService.setPriceTemp(0F);
//		tService.setTeacher(teacher);
//		teacher.settService(tService);
//	}
//
//	private static void assemble(String advantage, String content, float price, String reason, float time, String title,
//			TService tService) {
//		tService.setAdvantage(advantage);
//		tService.setContent(content);
//		tService.setPriceTotal(price);
//		tService.setReason(reason);
//		tService.setTime(time);
//		tService.setTitle(title);
//	}
//
//	public static void assembleByTeacherEdit(String timeperweek, String price, String pricetemp, String time,
//			String onsale, String servicetitle, String servicecontent, TService tService) {
//		assemble("", servicecontent, Float.valueOf(price), "", Float.valueOf(time), servicetitle, tService);
//		tService.setTimesPerWeek(Integer.valueOf(timeperweek));
//		tService.setPriceTemp(Float.valueOf(pricetemp));
//		tService.setOnSale(Boolean.valueOf(onsale));
//	}
//
//	public static void assembleWithTeacherByManager(Teacher teacher, String serviceTitle, String serviceTime,
//			String servicePrice, String serviceTimePerWeek, String serviceContent, TService tService) {
//		assemble("", serviceContent, Float.valueOf(servicePrice), "", Float.valueOf(serviceTime), serviceTitle,
//				tService);
//		tService.setOnSale(false);
//		tService.setPriceTemp(0F);
//		tService.setTimesPerWeek(Integer.valueOf(serviceTimePerWeek));
//		tService.setTeacher(teacher);
//		teacher.settService(tService);
//	}
//
//	public static void editWithTeacherByManager(Teacher teacher, TService tService, String serviceTitle,
//			String serviceTime, String servicePrice, String serviceTimePerWeek, String serviceContent, String onsale,
//			String pricetemp) {
//		assemble("", serviceContent, Float.valueOf(servicePrice), "", Float.valueOf(serviceTime), serviceTitle,
//				tService);
//		tService.setOnSale(Boolean.valueOf(onsale));
//		tService.setPriceTemp(Float.valueOf(pricetemp));
//		tService.setTimesPerWeek(Integer.valueOf(serviceTimePerWeek));
//		tService.setTeacher(teacher);
//		teacher.settService(tService);
//	}
//
//	public static void editPriceByTeacher(float price, float time, String title, boolean onsale, float pricetemp,
//			TService tService) {
//		tService.setOnSale(onsale);
//		tService.setPriceTemp(pricetemp);
//		tService.setTitle(title);
//		tService.setTime(time);
//		tService.setPriceTotal(price);
//	}
//
//	public static void editTimePerWeekByTeacher(int timeperweek, TService tService) {
//		tService.setTimesPerWeek(timeperweek);
//	}
//
//}
