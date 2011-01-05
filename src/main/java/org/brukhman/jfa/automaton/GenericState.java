package org.brukhman.jfa.automaton;

import com.google.common.base.Predicate;

/**
 * Describes an automaton's state, or node.
 * 
 * @author jbrukh
 *
 * @param <T>
 */
public class GenericState<T> {
	
	/** The name of the state. */
	private final T name;
	
	/**
	 * Create a new instance.
	 * 
	 * @param name
	 */
	public GenericState( T name ) {
		this.name = name;
	}

	/**
	 * Return the name of this state.
	 */
	public final T getName() {
		return this.name;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		final GenericState<?> other = (GenericState<?>) obj;
		return name.equals(other.name);
	}
	
	@Override
	public String toString() {
		return "State-"+String.valueOf(name);
	}
	
}
