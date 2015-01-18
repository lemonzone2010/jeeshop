package org.hum.framework.hawaii.util;

import java.beans.IntrospectionException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

@SuppressWarnings({ "unchecked" })
public class ReflectUtils {

	private static final Logger log = LoggerFactory.getLogger(ReflectUtils.class);
	
	static {
		ConvertUtils.register(new Converter() {
			@SuppressWarnings("rawtypes")
			@Override
			public Object convert(Class arg0, Object arg1) {
				try {
					if (arg1 == null) {
						return null;
					}
					return DateUtils.format2Date(arg1.toString(), "yyyy-MM-dd hh:mm:ss");
				} catch (ParseException e) {
					try {
						return DateUtils.format2Date(arg1.toString(), "yyyy-MM-dd");
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				}
				return null;
			}
		}, Date.class);

		ConvertUtils.register(new Converter() {
			@SuppressWarnings("rawtypes")
			@Override
			public Object convert(Class arg0, Object arg1) {
				if (arg1 == null) {
					return null;
				}
				return arg1;
			}
		}, Integer.class);

		ConvertUtils.register(new Converter() {
			@SuppressWarnings("rawtypes")
			@Override
			public Object convert(Class arg0, Object arg1) {
				if (arg1 == null) {
					return null;
				}
				return arg1;
			}
		}, Short.class);

		ConvertUtils.register(new Converter() {
			@SuppressWarnings("rawtypes")
			@Override
			public Object convert(Class arg0, Object arg1) {
				if (arg1 == null) {
					return null;
				}
				return arg1;
			}
		}, Long.class);
	}

	/**
	 * 从object获取fieldName的值
	 */
	public static Object fetchValue(Object object, String fieldName) {
		Class<?> clazz = object.getClass();
		while (clazz != null) {
			for (Field field : clazz.getDeclaredFields()) {
				if (field.getName().equals(fieldName)) {
					try {
						field.setAccessible(true);
						return field.get(object);
					} catch (Exception e) {
						// TODO
						e.printStackTrace();
					}
				}
			}
			clazz = clazz.getSuperclass();
		}
		return null;
	}

	/**
	 * 获得list每个元素中filedName的值
	 */
	@SuppressWarnings("rawtypes")
	public static <T> List<T> fetchListValue(List list, String fieldName, Class<T> t) {
		List<T> result = new ArrayList<T>();
		for (Object object : list) {
			result.add((T) fetchValue(object, fieldName));
		}
		return result;
	}

	/**
	 * 将list转为map，fieldName将作为map的key，map值类型为valueType
	 */
	public static <T> Map<String, T> list2map(List<T> list, String fieldName, Class<T> valueType) {
		Map<String, T> mapResult = new HashMap<String, T>();
		for (T object : list) {
			mapResult.put(fetchValue(object, fieldName).toString(), object);
		}
		return mapResult;
	}

	/**
	 * 将object的fieldName字段赋值value
	 */
	public static void setValue(Object object, String fieldName, Object value) {
		for (Field field : object.getClass().getDeclaredFields()) {
			if (field.getName().equals(fieldName)) {
				field.setAccessible(true);
				if (field.getType().isAssignableFrom(Integer.class)) {
					value = Integer.parseInt(value.toString());
				} else if (field.getType().isAssignableFrom(Long.class)) {
					value = Long.parseLong(value.toString());
				} else if (field.getType().isAssignableFrom(Short.class)) {
					value = Short.parseShort(value.toString());
				} else if (field.getType().isAssignableFrom(Boolean.class)) {
					value = Boolean.parseBoolean(value.toString());
				} else if (field.getType().isAssignableFrom(Float.class)) {
					value = Float.parseFloat(value.toString());
				} else if (field.getType().isAssignableFrom(Double.class)) {
					value = Double.parseDouble(value.toString());
				} else if (field.getType().isAssignableFrom(Date.class)) {
					try {
						value = new Date(Long.parseLong(value.toString()));
					} catch (Exception ce) {
						try {
							value = DateUtils.format2Date(value.toString(), "yyyy-MM-dd hh:mm:ss");
						} catch (ParseException e) {
							try {
								value = DateUtils.format2Date(value.toString(), "yyyy-MM-dd");
							} catch (ParseException e1) {
								log.error("无法解析request中的时间类型[" + fieldName + "," + value + "]", e1);
							}
						}
					}
				}

				ReflectionUtils.setField(field, object, value);
			}
		}
	}

	/**
	 * 获得非空的字段名称和值
	 * 
	 * @return
	 */
	public static Map<String, Object> fetchNotNullValue(Object object) {
		Map<String, Object> valueMap = new HashMap<String, Object>();
		for (Field field : object.getClass().getDeclaredFields()) {
			try {
				field.setAccessible(true);
				Object val = field.get(object);
				if (val != null) {
					valueMap.put(field.getName(), val);
				}
			} catch (Exception e) {
				// TODO
				e.printStackTrace();
			}
		}
		return valueMap;
	}

	/**
	 * 将Map集合转成对应的Java对象集合
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static <T> List<T> mapList2beanList(Class<T> clzz, List<Map<String, Object>> resultList) {
		if (resultList == null) {
			return Collections.emptyList();
		}
		List<T> beanList = new ArrayList<T>();
		try {
			for (Map<String, Object> map : resultList) {
				T bean = (T) map2Bean(clzz, map);
				beanList.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return beanList;
	}

	/**
	 * Class.forName 泛型
	 */
	public static <T> T forName(String className, Class<T> type) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Object object = Class.forName(className).newInstance();
		return (T) object;
	}

	/**
	 * map 转成 bean
	 */
	public static <T> T map2Bean(Class<T> type, Map<String, Object> map) throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
		T bean = type.newInstance();

		Map<String, Object> mapCopy = new HashMap<String, Object>();
		// 将map中的下划线转成大写字母
		if (map != null && !map.isEmpty()) {
			for (Entry<String, Object> entry : map.entrySet()) {
				mapCopy.put(StringUtils.underLine2UpperCase(entry.getKey().toString()), entry.getValue());
			}
		}

		// 暂时使用apache的工具类来实现，后续可能需要改进，避免依赖
		BeanUtils.populate(bean, mapCopy);
		return bean;
	}

	public static Field getFieldByName(Class<?> type, String fieldName) throws SecurityException, NoSuchFieldException {
		return type.getDeclaredField(fieldName);
	}
}
