package cn.yiyingli.HibernateFunction;

import org.hibernate.dialect.MySQLDialect;

/**
 * <p>
 * Title:CustomSQLDialect
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author sdll18
 * @version
 * @since
 */
public class CustomSQLDialect extends MySQLDialect {
	/** 
	* 
	*/
	public CustomSQLDialect() {
		super();
		registerFunction("bitand", new BitAndFunction());
	}

}