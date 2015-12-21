package cn.yiyingli.ExchangeData;

public interface ExDataToShow<T> {

	public String toJson();

	public void setUpByPersistant(T persistant);

}
