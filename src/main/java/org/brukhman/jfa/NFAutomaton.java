package org.brukhman.jfa;

import static org.brukhman.jfa.automaton.Symbols.*;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.brukhman.jfa.automaton.Automaton;
import org.brukhman.jfa.automaton.MultiState;
import org.brukhman.jfa.automaton.State;
import org.brukhman.jfa.automaton.GenericState;
import org.brukhman.jfa.automaton.StateMap;

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
class NFAutomaton implements Automaton<State> {

	// FIELDS //

	private State 						initialState;
	private Set<State> 					finalStates;
	private StateMap<State> 				stateMap;

	private int								count;
	
	private final static Predicate<GenericState<?>> isFinalPredicate = new Predicate<GenericState<?>>() {
		@Override
		public boolean apply(GenericState<?> state) {
			return state.isFinal();
		}
	};

	/**
	 * Create a new instance.
	 */
	public NFAutomaton() {
		this.finalStates 	= Sets.newHashSet();
		this.stateMap    	= new StateMap<State>();		
		this.count			= 0;
	}

	/**
	 * Add a state.
	 * 
	 * @param state
	 */
	public final void addState( State state ) {
		this.stateMap.add(state);
	}

	/**
	 * Add a state.
	 * 
	 * @return the {@link State} that was added
	 */
	public final State addState() {
		State state = new State(count++);
		addState(state);
		return state;
	}

	/**
	 * Remove a state from the machine.
	 * 
	 * @param state
	 */
	public final void removeState( State state ) {
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
	public final void makeInitial( State state ) {
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
	public final void makeFinal( State state ) {
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
	public final void clearFinalState( State state ) {
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
	public final void addTransition( State fromState, Character symbol, State toState ) {
		stateMap.add(fromState,symbol,toState);
	}

	final DFAutomaton toDFA() {
		
		Set<Character> alphabet = getAlphabet();
		
		// figure out what the states are going to be
		// by performing a BFS
		Set<MultiState> newStates = Sets.newHashSet();
		Set<MultiState> finalStates = Sets.newHashSet();
		MultiState      initialState;
		
		// the new DFA transitions table
		Table<MultiState, Character, MultiState> transitions = HashBasedTable.create();
		
		// start at the epsilon-closure of the initial state
		MultiState currentState = new MultiState(stateMap.epsilonTransition(this.initialState));
		Queue<MultiState> queue = Lists.newLinkedList();
		
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
				MultiState toState = new MultiState(
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
		Set<State> currentStates = this.stateMap.epsilonTransition(this.initialState);

		for ( char inputChar : inputArray ) {
			currentStates = stateMap.transition(currentStates, inputChar);
		}
		
		return Iterables.any(currentStates, isFinalPredicate);
	}

	@Override
	public Set<State> getFinalStates() {
		return Collections.unmodifiableSet(this.finalStates);
	}

	@Override
	public State getInitialState() {
		return this.initialState;
	}

	@Override
	public Set<State> getStates() {
		return this.stateMap.getStates();
	}
}
