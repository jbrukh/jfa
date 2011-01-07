package org.brukhman.jfa.automaton;

import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.Table.Cell;


public final class Operations {

	/**
	 * Creates a new NFA which accepts either of the languages
	 * accepted by the two input NFAs.
	 * 
	 * @param one
	 * @param two
	 * @return
	 */
	public final static NFA or( NFA one, NFA two ){
		Preconditions.checkState( 
				Sets.intersection(one.states, two.states).isEmpty(),
				"The two NFA's share states!"
				);
		
		Set<State> allStates = Sets.union(one.states, two.states);
		NFA nfa = new NFA(allStates);
			
		nfa.table.putAll(one.table);
		nfa.table.putAll(two.table);

		nfa.finalStates.addAll(one.finalStates);
		nfa.finalStates.addAll(two.finalStates);
		
		State start = State.next();
		nfa.addStates(start);
		nfa.addTransition(start, Symbols.EPSILON, one.initialState );
		nfa.addTransition(start, Symbols.EPSILON, two.initialState );
		nfa.makeInitial(start);
		
		return nfa;
	}
	
	/**
	 * Create an NFA that accepts the language concatenations of the
	 * the two input NFAs.
	 * 
	 * @param one
	 * @param two
	 * @return
	 */
	public final static NFA concat( NFA one, NFA two ) {
		Preconditions.checkState( 
				Sets.intersection(one.states, two.states).isEmpty(),
				"The two NFA's share states!"
				);
		
		Set<State> allStates = Sets.union(one.states, two.states);
		NFA nfa = new NFA(allStates);
			
		nfa.table.putAll(one.table);
		nfa.table.putAll(two.table);
		nfa.makeInitial( one.initialState );
		nfa.finalStates.addAll(two.finalStates);
		
		for ( State finalState : one.finalStates ) {
			nfa.addTransition(finalState, Symbols.EPSILON, two.initialState);
		}
		
		return nfa;
	}
	
	/**
	 * Given an NFA, returns the Kleene closure of that NFA.
	 * 
	 * @param input
	 * @return
	 */
	public final static NFA kleeneStar( NFA input ) {
		Preconditions.checkNotNull(input);
		NFA nfa = new NFA(input.states);
		nfa.table.putAll(input.table);
		nfa.finalStates.addAll(input.finalStates);
		nfa.finalStates.add(input.initialState);  // accept empty string
		nfa.makeInitial(input.initialState);
		
		for ( State finalState : input.finalStates ) {
			nfa.addTransition(finalState, Symbols.EPSILON, input.initialState);
		}
		
		return nfa;
	}
	
	public final static NFA toDfa( NFA input ) {
		Preconditions.checkNotNull(input);
		input.validate();
		
		Set<Character> alphabet = Sets.newHashSet();
		alphabet.addAll(input.getSymbols());
		alphabet.remove(Symbols.EPSILON);
		
		Queue<MultiState> queue = Lists.newLinkedList();
		BiMap<MultiState,State> newStates = HashBiMap.create();
		
		MultiState current = MultiState.of(input.traverser().epsilonClosureInitial());
		State start = State.next();
		newStates.put(current,start);
		queue.add(current);
		
		NFA dfa = new NFA(start);
		dfa.makeInitial(start);
		
		while ( !queue.isEmpty() ) {
			current = queue.remove();
			for ( Character symbol : alphabet ) {
				MultiState next = MultiState.of(
										input.traverser().transition(current.getName(), symbol)
								  );
				
				if ( !newStates.containsKey(next) ) {
					State state = State.next();
					newStates.put(next,state);
					queue.add(next);
					dfa.addStates(state);
					if ( !Sets.intersection(next.getName(),input.finalStates).isEmpty() ) {
						dfa.makeFinal(state);
					}
				}
				
				dfa.addTransition(newStates.get(current), symbol, newStates.get(next));		
			}
		}
		return dfa;
	}
}
