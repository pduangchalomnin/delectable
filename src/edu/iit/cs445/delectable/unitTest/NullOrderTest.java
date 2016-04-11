package edu.iit.cs445.delectable.unitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.iit.cs445.delectable.entity.NullOrder;

public class NullOrderTest {

	@Test
	public void testIsNil() {
		assertTrue(NullOrder.getinstance().isNil());
	}

}
