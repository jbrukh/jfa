package org.brukhman.jfa;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

/**
 * A deterministic finite automaton.
 * 
 * @author ybrukhma
 *
 */
public class DFAutomaton implements Automaton<DFAState> {
	
	// FIELDS //
	
	private final Set<DFAState> states;
	private final Set<Character> alphabet;
	
	private final DFAState			initialState;
	private final Set<DFAState>		finalStates;
	
	private final Table<DFAState, Character, DFAState>
									transitions;
	/**
	 * Create a new instance.
	 * 
	 * @param states
	 * @param alphabet
	 * @param initialState
	 * @param finalStates
	 */
	public DFAutomaton( Collection<DFAState> states, 
						Collection<Character> alphabet, 
						DFAState initialState, 
						DFAState... finalStates ) {
		
		Preconditions.checkArgument( states!=null && states.size()>0, "Must have states." );
		Preconditions.checkArgument( alphabet!=null, "Must have an alphabet.");
		Preconditions.checkArgument( initialState != null, "Must have an initial state." );
		
		this.states = Collections.unmodifiableSet(
								new HashSet<DFAState>(states)
					  );
		this.alphabet = Collections.unmodifiableSet(
								new HashSet<Character>(alphabet)
					  );
		
		this.initialState = initialState;
		
		Set<DFAState> finalSet = new HashSet<DFAState>();
		Collections.addAll(finalSet,finalStates);
		this.finalStates = Collections.unmodifiableSet(finalSet);		
		this.transitions = HashBasedTable.create();
	}
	
	/**
	 * Return the alphabet of this DFA.
	 * @return
	 */
	public final Set<Character> getAlphabet() {
		return this.alphabet;
	}
	
	/**
	 * Return the states of this DFA.
	 * 
	 * @return
	 */
	public final Set<DFAState> getStates() {
		return states;
	}

	/**
	 * Add a transition to this DFA.
	 * 
	 * @param fromState
	 * @param symbol
	 * @param toState
	 */
	public final void addTransition( DFAState fromState, Character symbol, DFAState toState ) {
		this.transitions.put(fromState, symbol, toState);
	}
	
	@Override
	public DFAState getInitialState() {
		return this.initialState;
	}

	@Override
	public Set<DFAState> getFinalStates() {
		return this.finalStates;
	}

	@Override
	public boolean compute(String input) {
		// TODO Auto-generated method stub
		return false;
	}

}
