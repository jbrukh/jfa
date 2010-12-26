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
}
