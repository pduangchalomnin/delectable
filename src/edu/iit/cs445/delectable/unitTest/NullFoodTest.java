package edu.iit.cs445.delectable.unitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.iit.cs445.delectable.entity.NullFood;

public class NullFoodTest {

	@Test
	public void testIsNil() {
		assertTrue(NullFood.getinstance().isNil());
	}

}
