package org.brukhman.jfa.automaton;

import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

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
	private MultiState( Set<State> name ) {
		super(name);
	}
	
	/**
	 * Create a new instance.
	 * 
	 * @param states
	 * @return
	 */
	public final static MultiState of( Set<State> states ) {
		Preconditions.checkNotNull(states);
		return new MultiState(states);
	}
	
	/**
	 * Create a new instance.
	 * 
	 * @param states
	 * @return
	 */
	public final static MultiState of( State... states ) {
		return of( Sets.newHashSet(states) );
	}
}
