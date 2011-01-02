package org.brukhman.jfa.automaton;

/**
 * An NFA state, which can be labeled with an integer.
 * 
 * @author jbrukh
 *
 */
public final class State extends GenericState<Integer>{
	
	/**
	 * Create a new instance.
	 * 
	 * @param name
	 */
	public State( Integer name ) {
		super(name);
	}
}
