package org.brukhman.jfa.automaton;

import java.util.Collection;

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;

public final class NFA<StateType extends GenericState<?>, SymbolType extends Symbol<?>> extends 
				TableAutomaton<StateType,SymbolType>{

	// FIELDS //
	
	/** Predicate for finding final states. */
	private final static Predicate<GenericState<?>> isFinalPredicate = new Predicate<GenericState<?>>() {
		@Override
		public boolean apply(GenericState<?> state) {
			return state.isFinal();
		}
	};
	
	/**
	 * Create a new instance.
	 * 
	 */
	private NFA() {
		this(null);
	}
	
	/**
	 * Create a new instance.
	 * 
	 * @param states
	 */
	private NFA( Collection<StateType> states ) {
		if ( states != null ) {
			this.states.addAll(states);
		}
	}
	
	/**
	 * Create a new instance.
	 * 
	 * @param <StateType>
	 * @param <SymbolType>
	 * @return
	 */
	public final static <StateType extends GenericState<?>, SymbolType extends Symbol<?>>
	NFA<StateType,SymbolType> create() {
		return new NFA<StateType, SymbolType>();
	}

	/**
	 * Create a new instance.
	 * 
	 * @param <StateType>
	 * @param <SymbolType>
	 * @param states
	 * @return
	 */
	public final static <StateType extends GenericState<?>, SymbolType extends Symbol<?>>
	NFA<StateType,SymbolType> create( StateType... states ) {
		return new NFA<StateType, SymbolType>( Sets.newHashSet(states) );
	}
	

	@Override
	public boolean compute(String input) {
		// TODO Auto-generated method stub
		return false;
	}

}
