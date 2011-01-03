package org.brukhman.jfa.automaton;

/**
 * An NFA state, which can be labeled with an integer.
 * 
 * @author jbrukh
 *
 */
public class State extends GenericState<Integer>{
	
	private static int stateCount = 0;
	
	/**
	 * Create a new instance.
	 * 
	 * @param name
	 */
	public State( Integer name ) {
		super(name);
	}
	
	/**
	 * Create a new state from an internal count.
	 * 
	 * @return
	 */
	public final static State next() {
		return new State(++stateCount);
	}
}
