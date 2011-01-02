package org.brukhman.jfa.automaton;

import static com.google.common.base.Preconditions.*;
/**
 * A transition symbol.
 * 
 * @author jbrukh
 *
 * @param <T>
 */
public class Symbol<T> {

	// FIELDS //
	private final T object;
	
	public final static Symbol<?> EPSILON = Symbol.of('\0');

	/**
	 * Create a new instance, privately.
	 * 
	 * @param object
	 */
	private Symbol( T object ) {
		checkNotNull(object);
		this.object = object;
	}
	
	/**
	 * Create a new instances.
	 * 
	 * @param <T>
	 * @param object
	 * @return
	 */
	public final static <T> Symbol<T> of( T object ) {
		return new Symbol<T>(object);
	}
	
	/**
	 * Return the underlying symbol object.
	 * 
	 * @return
	 */
	public final T get() {
		return object;
	}

	@Override
	public int hashCode() {
		return object.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		
		Symbol<?> other = (Symbol<?>) obj;
		return object.equals(other.object);
	}
	
	@Override
	public String toString() {
		return object.toString();
	}
	
	
}
