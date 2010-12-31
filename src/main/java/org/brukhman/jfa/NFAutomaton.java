package org.brukhman.jfa;

import static org.brukhman.jfa.Symbols.*;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;

/**
 * The base for constructing NFAs.
 *     
 * @author jbrukh
 *
 */
class NFAutomaton implements Automaton<NFAState> {

	// FIELDS //

	private NFAState 						initialState;
	private Set<NFAState> 					finalStates;
	private StateMap<NFAState> 				stateMap;

	private int								count;
	
	private final static Predicate<State<?>> isFinalPredicate = new Predicate<State<?>>() {
		@Override
		public boolean apply(State<?> state) {
			return state.isFinal();
		}
	};

	/**
	 * Create a new instance.
	 */
	public NFAutomaton() {
		this.finalStates 	= Sets.newHashSet();
		this.stateMap    	= new StateMap<NFAState>();		
		this.count			= 0;
	}

	/**
	 * Add a state.
	 * 
	 * @param state
	 */
	public final void addState( NFAState state ) {
		this.stateMap.add(state);
	}

	/**
	 * Add a state.
	 * 
	 * @return the {@link NFAState} that was added
	 */
	public final NFAState addState() {
		NFAState state = new NFAState(count++);
		addState(state);
		return state;
	}

	/**
	 * Remove a state from the machine.
	 * 
	 * @param state
	 */
	public final void removeState( NFAState state ) {
		this.stateMap.remove(state);

		if ( state.isInitial() ) {
			state.setInitial(false);
			clearInitialState();
		}
	}

	/**
	 * Returns the alphabet of this NFA.
	 * 
	 * @return
	 */
	public final Set<Character> getAlphabet() {
		return this.stateMap.getAlphabet();
	}
	
	/**
	 * Make a state an initial state.  The state must be in the machine.  If an initial state is
	 * already set, it is cleared.
	 * 
	 * @param state
	 */
	public final void makeInitial( NFAState state ) {
		Preconditions.checkArgument(this.stateMap.getStates().contains(state),
				"The state must be in the machine.");
		clearInitialState();
		state.setInitial(true);
		this.initialState = state;
	}

	/**
	 * Make a state a final state.  The state must be in the machine.  This method has no effect
	 * if the state is already final.
	 * 
	 * @param state
	 */
	public final void makeFinal( NFAState state ) {
		Preconditions.checkArgument(this.stateMap.getStates().contains(state),
				"The state must be in the machine.");
		state.setFinal(true);
		this.finalStates.add(state);
	}

	/**
	 * Clear the initial state.
	 * 
	 */
	public final void clearInitialState() {
		if ( this.initialState != null ) {
			this.initialState.setInitial(false);
			this.initialState = null;
		}
	}

	/**
	 * Revert a final state to a regular state.
	 * 
	 * @param state
	 */
	public final void clearFinalState( NFAState state ) {
		Preconditions.checkArgument(this.stateMap.getStates().contains(state),"The state must be in the machine.");
		Preconditions.checkArgument(finalStates.contains(state),"The state must be a final state.");
		this.finalStates.remove(state);
		state.setFinal(false);
	}
	
	/**
	 * Add a transition to the automaton.
	 * 
	 * @param fromState
	 * @param symbol
	 * @param toState
	 */
	public final void addTransition( NFAState fromState, Character symbol, NFAState toState ) {
		stateMap.add(fromState,symbol,toState);
	}

	final DFAutomaton toDFA() {
		
		Set<Character> alphabet = getAlphabet();
		
		// figure out what the states are going to be
		// by performing a BFS
		Set<DFAState> newStates = Sets.newHashSet();
		Set<DFAState> finalStates = Sets.newHashSet();
		DFAState      initialState;
		
		// the new DFA transitions table
		Table<DFAState, Character, DFAState> transitions = HashBasedTable.create();
		
		// start at the epsilon-closure of the initial state
		DFAState currentState = new DFAState(stateMap.epsilonTransition(this.initialState));
		Queue<DFAState> queue = Lists.newLinkedList();
		
		// if the start state is final...
		if ( Iterables.any(currentState.getName(), NFAutomaton.isFinalPredicate) ) {
			finalStates.add(currentState);
		}
	
		// 
		initialState = currentState;

		queue.add(currentState);
		newStates.add(currentState);
				
		while ( !queue.isEmpty() ) {
			currentState = queue.remove();
			
			for ( Character symbol : alphabet ) {
				DFAState toState = new DFAState(
										stateMap.transition(currentState.getName(), symbol)
									);
				
				// if any of these states are final, then
				// this new state is final as well
				if ( Iterables.any(toState.getName(), NFAutomaton.isFinalPredicate) ) {
					finalStates.add(toState);
				}
				
				// add it if we haven't seen it before
				if ( !newStates.contains(toState) ) {
					queue.add(toState);
					newStates.add(toState);
				}
				
				// add the transition
				transitions.put(currentState, symbol, toState);
			}
		}

		DFAutomaton newAutomaton = new DFAutomaton(transitions, initialState, finalStates);
		return newAutomaton;
		
	}
	
	@Override
	public boolean compute(String input) {
		Preconditions.checkNotNull(input);
		
		char[] inputArray = input.toCharArray();
		Set<NFAState> currentStates = this.stateMap.epsilonTransition(this.initialState);

		for ( char inputChar : inputArray ) {
			currentStates = stateMap.transition(currentStates, inputChar);
		}
		
		return Iterables.any(currentStates, isFinalPredicate);
	}

	@Override
	public Set<NFAState> getFinalStates() {
		return Collections.unmodifiableSet(this.finalStates);
	}

	@Override
	public NFAState getInitialState() {
		return this.initialState;
	}

	@Override
	public Set<NFAState> getStates() {
		return this.stateMap.getStates();
	}
}
