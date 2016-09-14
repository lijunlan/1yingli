package cn.yiyingli.HibernateFunction;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.QueryException;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.Mapping;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.type.Type;

/**
 * <p>
 * Title:BitAndFunction
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author sdll18
 * @version
 * @since
 */

public class BitAndFunction implements SQLFunction {
	@SuppressWarnings("deprecation")
	public Type getReturnType(Type type, Mapping mapping) {
		return Hibernate.LONG;
	}

	public boolean hasArguments() {
		return true;
	}

	public boolean hasParenthesesIfNoArguments() {
		return true;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String render(Type arg0, List arg1, SessionFactoryImplementor arg2) throws QueryException {
		if (arg1.size() != 2) {
			throw new IllegalArgumentException("BitAndFunction requires 2 arguments!");
		}
		return arg1.get(0).toString() + " & " + arg1.get(1).toString();
	}

}
