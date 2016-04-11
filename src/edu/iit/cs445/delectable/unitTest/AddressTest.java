package edu.iit.cs445.delectable.unitTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.iit.cs445.delectable.entity.Address;

public class AddressTest {
	
	Address adr;
	
	@Before
	public void createAddress(){
		adr = new Address("22", "S State St","Chicago","IL","60616");
	}

	@Test
	public void testToString() {
		
		assertEquals("22 S State St, Chicago IL 60616", adr.toString());
	}

	@Test
	public void testIsMatchNoting() {
		assertFalse(adr.isMatch("NOTING"));
	}
	
	@Test
	public void testIsMatchHouseNumber(){
		assertTrue(adr.isMatch("2"));
	}
	
	@Test
	public void testIsMatchStreet() {
		assertTrue(adr.isMatch("S St"));
	}
	
	@Test
	public void testIsMatchCity() {
		assertTrue(adr.isMatch("Chi"));
	}
	
	@Test
	public void testIsMatchState() {
		assertTrue(adr.isMatch("IL"));
	}
	
	@Test
	public void testIsMatchZipcode() {
		assertTrue(adr.isMatch("606"));
	}
	
	@Test 
	public void testIsMatchPartialAddres(){
		assertTrue(adr.isMatch("22 S S"));
	}
	
	@Test
	public void testIsMatchEntireWord(){
		assertTrue(adr.isMatch("22 S State St, Chicago IL 60616"));
	}
}
