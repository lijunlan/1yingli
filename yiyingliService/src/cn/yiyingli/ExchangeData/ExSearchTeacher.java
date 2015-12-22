package cn.yiyingli.ExchangeData;

import cn.yiyingli.Persistant.Teacher;
import net.sf.json.JSONObject;

//TODO 搜索导师展现的东西
public class ExSearchTeacher implements ExDataToShow<Teacher> {

	private String teacherId;

	private String iconUrl;

	private String price;

	private String introduce;

	private String number;

	private String name;

	private String tServiceId;

	private String title;

	private String firstIdentity;

	private String level;

	private String serviceContent;

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String gettServiceId() {
		return tServiceId;
	}

	public void settServiceId(String tServiceId) {
		this.tServiceId = tServiceId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstIdentity() {
		return firstIdentity;
	}

	public void setFirstIdentity(String firstIdentity) {
		this.firstIdentity = firstIdentity;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Override
	public String toJson() {
		SuperMap map = new SuperMap();
		map.put("teacherId", teacherId);
		map.put("iconUrl", iconUrl);
		map.put("price", price);
		map.put("introduce", introduce);
		map.put("name", name);
		map.put("tServiceId", tServiceId);
		map.put("number", number);
		map.put("title", title);
		map.put("firstIdentity", firstIdentity);
		map.put("level", level);
		map.put("serviceContent", serviceContent);
		return map.finishByJson();
	}

	@Override
	public void setUpByPersistant(Teacher persistant) {
		teacherId = String.valueOf(persistant.getId());
		iconUrl = persistant.getIconUrl();
		// price = String.valueOf(persistant.gettService().getPriceTotal());
		introduce = persistant.getIntroduce();
		name = persistant.getName();
		// tServiceId = String.valueOf(persistant.gettService().getId());
		// number = String.valueOf(persistant.gettService().getTimesPerWeek());
		// title = persistant.gettService().getTitle();
		firstIdentity = persistant.getFirstIdentity();
		// serviceContent = persistant.gettService().getContent();
		level = String.valueOf(persistant.getLevel());
	}

	@Override
	public JSONObject finish() {
		SuperMap map = new SuperMap();
		map.put("teacherId", teacherId);
		map.put("iconUrl", iconUrl);
		map.put("price", price);
		map.put("introduce", introduce);
		map.put("name", name);
		map.put("tServiceId", tServiceId);
		map.put("number", number);
		map.put("title", title);
		map.put("firstIdentity", firstIdentity);
		map.put("level", level);
		map.put("serviceContent", serviceContent);
		return map.finish();
	}

}
