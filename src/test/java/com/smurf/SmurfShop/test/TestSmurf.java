package com.smurf.SmurfShop.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.smurf.SmurfShop.model.Smurf;


public class TestSmurf {

	Smurf smurf;
	
	@Before
	public void setUp(){
		smurf = new Smurf();
		smurf.setId(1000);
		smurf.setInstock(10000);
		smurf.setPrice(15.00);
		smurf.setName("testSmurf");
		smurf.setSize("large");
		smurf.setMaterial("rubber");
		smurf.setSupplier("Ireland Import ltd");
		smurf.setDescription("This is a test smurf");
		smurf.setImage("testSmurf.jpg");
	}
	
	@Test
	public void testGetId() {
		assertEquals(1000, smurf.getId());
	}	
	
	@Test
	public void testGetInstock() {
		assertEquals(10000, smurf.getInstock());
	}	
	
	@Test
	public void testGetPrice() {
		assertEquals(15, smurf.getPrice(),0.1);
	}
	
	@Test
	public void testGetName() {
		assertEquals("testSmurf", smurf.getName());
	}	
	
	@Test
	public void testGetSize() {
		assertEquals("large", smurf.getSize());
	}
	
	@Test
	public void testGetMaterial() {
		assertEquals("rubber", smurf.getMaterial());
	}	
	
	@Test
	public void testGetSupplier() {
		assertEquals("Ireland Import ltd", smurf.getSupplier());
	}	

	@Test
	public void testGetDescription() {
		assertEquals("This is a test smurf", smurf.getDescription());
	}
	
	@Test
	public void testGetImage() {
		assertEquals("testSmurf.jpg", smurf.getImage());
	}
	
}
