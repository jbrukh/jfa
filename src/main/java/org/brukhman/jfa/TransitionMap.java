package org.brukhman.jfa;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A map that holds FA transitions.
 * <p>
 * 
 * @author jbrukh
 *
 * @param <T>	the type of state that is available
 * @param <S>	the transition symbol
 */
public class TransitionMap<T extends State<?>,S> implements Map<T, Map<S,T>> {

	private final Map<T,Map<S,T>> transitions;
	
	/**
	 * Create a new instance.
	 */
	public TransitionMap() {
		transitions = new HashMap<T,Map<S,T>>();
	}
	
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<java.util.Map.Entry<T, Map<S, T>>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<S, T> get(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<T> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<S, T> put(T key, Map<S, T> value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(Map<? extends T, ? extends Map<S, T>> m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<S, T> remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection<Map<S, T>> values() {
		// TODO Auto-generated method stub
		return null;
	}

}
