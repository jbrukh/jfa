package org.brukhman.jfa.automaton.atoms;

import org.brukhman.jfa.automaton.NFA;
import org.brukhman.jfa.automaton.State;

import com.google.common.base.Preconditions;

public final class Atoms {
	
	/** NFA that accepts the empty language. */
	public final static NFA epsilonNFA = new NFA();	
	static {
		State only = State.next();
		epsilonNFA.addStates(only);
		epsilonNFA.makeInitial(only);
		epsilonNFA.makeFinal(only);
	}
	
	/** NFA that accepts a single symbol. */
	public final static NFA singleSymbolNFA( Character symbol ) {
		State start = State.next();
		State end   = State.next();
		
		NFA nfa = new NFA(start,end);
		nfa.makeInitial(start);
		nfa.makeFinal(end);
		nfa.addTransition(start, symbol, end);
		
		return nfa;
	}
	
	/** NFA that accepts a single word. */
	public final static NFA singleWordNFA( String word ) {
		Preconditions.checkNotNull(word);
		if ( word.equals("")) { return epsilonNFA; }
		
		State last = State.next(), current = null;
		NFA nfa = new NFA(last);
		nfa.makeInitial(last);
		
		for ( Character symbol : word.toCharArray() ) {
			current = State.next();
			nfa.addStates(current);
			nfa.addTransition(last, symbol, current);
			last = current;
		}
		nfa.makeFinal(current);
		return nfa;
	}
}
