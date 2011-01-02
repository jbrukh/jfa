package org.brukhman.jfa;

import static org.brukhman.jfa.automaton.Symbols.*;

import java.util.Collections;
import java.util.Set;

import org.brukhman.jfa.automaton.Automaton;
import org.brukhman.jfa.automaton.MultiState;

import com.google.common.base.Preconditions;
import com.google.common.collect.Table;

/**
 * A deterministic finite automaton.
 * 
 * @author ybrukhma
 *
 */
public class DFAutomaton implements Automaton<MultiState> {
	
	// FIELDS //
	
	private final MultiState			initialState;
	private final Set<MultiState>		finalStates;
	private final Set<MultiState> 	states; 
	private final Set<Character> 	alphabet;

	private final Table<MultiState, Character, MultiState>
									transitions;
	/**
	 * Create a new instance.
	 * 
	 * @param states
	 * @param alphabet
	 * @param initialState
	 * @param finalStates
	 */
	public DFAutomaton( Table<MultiState, Character, MultiState> transitions, 
						MultiState initialState, 
						Set<MultiState> finalStates ) {
		
		Preconditions.checkArgument( transitions!=null );
		Preconditions.checkArgument( initialState != null, "Must have an initial state." );
		Preconditions.checkArgument( finalStates != null );
		
		this.states = Collections.unmodifiableSet(transitions.rowKeySet());
		this.alphabet = Collections.unmodifiableSet(transitions.columnKeySet());
		this.transitions = transitions;
		this.initialState = initialState;
		this.finalStates = Collections.unmodifiableSet(finalStates);		
		
		// make sure this here table is legit
		checkState();
	}
	
	/**
	 * Check that the inputs are legit.
	 */
	private final void checkState() {
		// check that states and alphabet exist
		Preconditions.checkState( states.size()>0 && alphabet.size()>0, "No states or no alphabet.");
		
		// check that every state has every transition
		for ( MultiState state : states ) {
			for ( Character symbol : alphabet ) {
				Preconditions.checkState( transitions.get(state, symbol)!=null,
						"Transition table is not complete.");
			}
		}
		
		// check that initial and final states are in fact in the machine
		Preconditions.checkState( states.contains(initialState), "Initial state is not in the table." );
		Preconditions.checkState( states.containsAll(finalStates), "Some final state is not in the table." );		
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
	public final Set<MultiState> getStates() {
		return this.states;
	}

	/**
	 * Add a transition to this DFA.
	 * 
	 * @param fromState
	 * @param symbol
	 * @param toState
	 */
	public final void addTransition( MultiState fromState, Character symbol, MultiState toState ) {
		this.transitions.put(fromState, symbol, toState);
	}
	
	@Override
	public MultiState getInitialState() {
		return this.initialState;
	}

	@Override
	public Set<MultiState> getFinalStates() {
		return this.finalStates;
	}

	@Override
	public boolean compute(String input) {
		Preconditions.checkArgument(input!=null, "Input is null.");
		MultiState currentState = this.initialState;
		char[] inputChars = input.toCharArray();
		for ( char inputChar : inputChars ) {
			currentState = this.transitions.get(currentState, inputChar);
		}
		return finalStates.contains(currentState);
	}

}
