package cn.yiyingli.ExchangeData;

import java.util.Map;

public interface  ExDataGetIn<T> {

	public void setUpByJson(String json);

	public void setUpByMap(Map<String, String> map);

	public T toPersistant();

}
