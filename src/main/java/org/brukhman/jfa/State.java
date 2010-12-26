package org.brukhman.jfa;

/**
 * Describes an automaton's state, or node.
 * 
 * @author jbrukh
 *
 * @param <T>
 */
public class State<T> {
	
	/** The name of the state. */
	private final T name;
	
	/**
	 * Create a new instance.
	 * 
	 * @param name
	 */
	public State( T name ) {
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
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final State<?> other = (State<?>) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}
