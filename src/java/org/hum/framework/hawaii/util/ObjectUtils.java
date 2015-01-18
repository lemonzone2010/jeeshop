package org.hum.framework.hawaii.util;

import java.util.Collection;

public class ObjectUtils {
	public static boolean equals(Object objA, Object objB) {
		return ((objA == objB) || (((objA != null) && (objB != null)) && objA.equals(objB)));
	}

	public static boolean isEmptyValue(Object obj) {
		if (obj == null) {
			return true;
		}
		if (obj instanceof String) {
			return StringUtils.isBlank(obj.toString());
		}
		if (obj instanceof Collection) {
			Collection<?> collection = (Collection<?>) obj;
			return collection.isEmpty();
		}
		return false;
	}
}
