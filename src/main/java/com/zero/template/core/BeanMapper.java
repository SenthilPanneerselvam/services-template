package com.zero.template.core;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;

/**
 * A wrapper around dozer bean mapper for processing collections
 * @author senthilkumarpanneerselvam
 *
 */
public class BeanMapper {
	
	private static final DozerBeanMapper mapper;
	
	static {
		mapper = new DozerBeanMapper();
	}
	
	public static<T,V> List<V> map(List<T> source, Class<V> destination) {
		if(source == null)
			return null;
		List<V> dest = new ArrayList<V>();
		source.forEach(s -> dest.add(mapper.map(s, destination)));
		return dest;
	}
	
	public static<T> T map(Object source, Class<T> destinationClass) {
		return mapper.map(source, destinationClass);
	}

}
