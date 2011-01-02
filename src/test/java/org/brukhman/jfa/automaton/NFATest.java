package org.brukhman.jfa.automaton;

import static org.brukhman.jfa.automaton.Symbols.EPSILON;

import junit.framework.TestCase;


public class NFATest extends TestCase {
	
	private NFA nfa;
	private State s,a,b,e,d,o,h;
	
	public void setUp() {
		
		// states
		s = State.next();
		a = State.next();
		b = State.next();
		e = State.next();
		d = State.next();
		o = State.next();
		h = State.next();
		
		// machine
		nfa = new NFA(s,a,b,e,d,o,h);
		nfa.makeInitial(s);
		
		// accept abe*
		nfa.addTransition(a, 'a', 		b);
		nfa.addTransition(b, 'b', 		e);
		nfa.addTransition(e, 'e', 		e);
		nfa.makeFinal(e);
		
		// accept doh*
		nfa.addTransition(d, 'd', 		o);
		nfa.addTransition(o, 'o', 		h);
		nfa.addTransition(h, 'h', 		h);
		nfa.makeFinal(h);
		
		// accept either of the above
		nfa.addTransition(s, EPSILON, 	a);
		nfa.addTransition(s, EPSILON, 	d);
		
		// ...many times
		nfa.addTransition(e, EPSILON, s);
		nfa.addTransition(h, EPSILON, s);
		
	}
	
	public void testCompute() {
		assertTrue(nfa!=null);
		
		assertTrue( nfa.compute("ab") );
		assertTrue( nfa.compute("abe") );
		assertTrue( nfa.compute("abee") );
		assertTrue( nfa.compute("abeee") );
		assertTrue( nfa.compute("abeeee") );
		
		assertTrue( nfa.compute("do") );
		assertTrue( nfa.compute("doh") );
		assertTrue( nfa.compute("dohh") );
		assertTrue( nfa.compute("dohhh") );
		assertTrue( nfa.compute("dohhhh") );
		
		assertTrue( nfa.compute("dohabedohabe") );
		assertTrue( nfa.compute("abedohabedoh") );
		assertTrue( nfa.compute("dohdohdohdohdoh") );
		assertTrue( nfa.compute("abeabeabeabeabe") );
		assertTrue( nfa.compute("abeeedohabeedohhdohhhhabeeeeeeeeedohdohdoh"));
		assertTrue( nfa.compute("abdoabdoababababdododoabeedodoabeeedohhhdododododododohhhh"));
		
		
		assertFalse( nfa.compute("") );
		nfa.makeFinal(s);
		assertTrue( nfa.compute("") );
		nfa.clearFinal(s);
		assertFalse( nfa.compute("") );

		assertFalse( nfa.compute("haha") );
		assertFalse( nfa.compute("haha!") );
		assertFalse( nfa.compute("doo") );
		assertFalse( nfa.compute("d") );
		assertFalse( nfa.compute("a") );
		assertFalse( nfa.compute("aaa") );
		assertFalse( nfa.compute("ddd") );
		assertFalse( nfa.compute("jake") );
		assertFalse( nfa.compute(" ") );
		
	}
	

}
