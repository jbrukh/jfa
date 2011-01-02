package org.brukhman.jfa.automaton;

import java.util.Set;

/**
 * A multistate.  This kind of state describes a node as a set of
 * some previous states.
 * 
 * @author jbrukh
 *
 */
public class MultiState extends GenericState<Set<State>>{

	/**
	 * Create a new instance.
	 * 
	 * @param name
	 */
	public MultiState( Set<State> name ) {
		super(name);
	}
}
