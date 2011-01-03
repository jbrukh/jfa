package org.brukhman.jfa.automaton;


/**
 * A finite automaton.
 * 
 * @author jbrukh
 *
 */
public interface Automaton {
	
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
