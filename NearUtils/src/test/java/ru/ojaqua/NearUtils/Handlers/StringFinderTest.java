package ru.ojaqua.NearUtils.Handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StringFinderTest {

	@Test
	public void last_four_space() {

		StringFinder finder = new StringFinder("jdfhgjkdfkjh   dsjfljklj    ", "   ");
		assertTrue(finder.find());
		assertEquals(12, finder.getCurPosition());
		assertTrue(finder.find());
		assertEquals(24, finder.getCurPosition());
		assertTrue(finder.find());
		assertEquals(25, finder.getCurPosition());
		assertFalse(finder.find());

	}

	@Test
	public void without_space() {

		StringFinder finder = new StringFinder("jdfhgjkdfkjh", "   ");
		assertFalse(finder.find());
		assertEquals(finder.getCurPosition(), 12);

	}
}
