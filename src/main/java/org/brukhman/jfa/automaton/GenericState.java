package org.brukhman.jfa.automaton;

import com.google.common.base.Predicate;

/**
 * Describes an automaton's state, or node.
 * 
 * @author jbrukh
 *
 * @param <T>
 */
public class GenericState<T> {
	
	/** The name of the state. */
	private final T name;
	
	/** State qualifiers. */
	private boolean isInitial;
	private boolean isFinal;
	
	/** Predicate for finding final states. */
	public final static Predicate<GenericState<?>> isFinalPredicate = new Predicate<GenericState<?>>() {
		@Override
		public boolean apply(GenericState<?> state) {
			return state.isFinal();
		}
	};
	
	/** Predicate for finding final states. */
	public final static Predicate<GenericState<?>> isInitialPredicate = new Predicate<GenericState<?>>() {
		@Override
		public boolean apply(GenericState<?> state) {
			return state.isInitial();
		}
	};
	
	/**
	 * Create a new instance.
	 * 
	 * @param name
	 */
	public GenericState( T name ) {
		this.name = name;
	}

	/**
	 * Return the name of this state.
	 */
	public final T getName() {
		return this.name;
	}
	
	/**
	 * Returns true if and only if this is an initial
	 * state.
	 * 
	 * @return
	 */
	public final boolean isInitial() {
		return this.isInitial;
	}
	
	/**
	 * Set this state to be an initial state.
	 * 
	 * @param isInitial
	 */
	final void setInitial( boolean isInitial ) {
		this.isInitial = isInitial;
	}
	
	/**
	 * Returns true if and only if this is a final
	 * state.
	 * 
	 * @return
	 */
	public final boolean isFinal() {
		return this.isFinal;
	}
	
	/**
	 * Set this state to be an final state.
	 * 
	 * @param isFinal
	 */
	final void setFinal( boolean isFinal ) {
		this.isFinal = isFinal;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		final GenericState<?> other = (GenericState<?>) obj;
		return name.equals(other.name);
	}
	
	@Override
	public String toString() {
		return "State-"+String.valueOf(name);
	}
	
}
