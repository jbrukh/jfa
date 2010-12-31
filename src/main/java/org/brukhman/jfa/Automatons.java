package org.brukhman.jfa;

import java.util.HashSet;
import java.util.Set;

/**
 * Various operations on Automatons.
 * 
 * @author ybrukhma
 *
 */
public final class Automatons {

	private final static NFAutomaton EMPTY_STRING;
	
	static {
		// create the EMPTY_STRING
		EMPTY_STRING = new NFAutomaton();
		NFAState initial = EMPTY_STRING.addState();
		EMPTY_STRING.makeInitial(initial);
		EMPTY_STRING.makeFinal(initial);
	
	}
}
