package org.brukhman.jfa;

import java.util.Collections;
import java.util.Set;

public final class Transition<T extends State<?>> {

	// FIELDS //
	
	private final T 				state;
	private final Set<Character> 	symbols;
	
	/**
	 * Create a new instance.
	 * 
	 * @param state
	 * @param symbols
	 */
	public Transition( T state, Set<Character> symbols ) {
		if ( state == null || symbols == null || symbols.isEmpty() ) {
			throw new IllegalArgumentException("Must have state and transition symbols.");
		}
		this.state 		= state;
		this.symbols 	= Collections.unmodifiableSet(symbols);
	}
	
	/**
	 * Get the state.
	 * 
	 * @return
	 */
	public final T getState() {
		return this.state;
	}
	
	/**
	 * Get the symbols.
	 * 
	 * @return
	 */
	public final Set<Character> getSymbols() {
		return this.symbols;
	}
}
