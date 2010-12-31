package org.brukhman.jfa;

import java.util.Set;

/**
 * A DFA state, whose names will be sets of integers.
 * 
 * @author jbrukh
 *
 */
public class DFAState extends State<Set<NFAState>>{

	/**
	 * Create a new instance.
	 * 
	 * @param name
	 */
	public DFAState( Set<NFAState> name ) {
		super(name);
	}
}
