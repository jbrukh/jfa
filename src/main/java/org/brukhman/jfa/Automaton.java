package org.brukhman.jfa;

import java.util.Collection;
import java.util.Set;

/**
 * A finite automaton.
 * 
 * @author jbrukh
 *
 */
public interface Automaton<T extends State<?>> {
	
	/**
	 * Returns the set of states.  This set should be immutable.
	 * 
	 * @return
	 */
	public abstract Set<T> getStates();
	
	/**
	 * Return the initial state.
	 * 
	 * @return
	 */
	public abstract T getInitialState();
	
	/**
	 * Return the final states.
	 * 
	 * @return
	 */
	public abstract Set<T> getFinalStates();
	
	/**
	 * Runs the automaton on the given input.
	 * <p>
	 * If the machine accepts the input, then <code>true</code> is returned.  Otherwise,
	 * if the machine rejects the input, then <code>false</code> is returned.  If the machine
	 * falls into an error state, then an exception is thrown.
	 * 
	 * @param input
	 * @return
	 */
	public abstract boolean compute( String input );

}
