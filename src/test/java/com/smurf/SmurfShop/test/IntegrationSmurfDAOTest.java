package com.smurf.SmurfShop.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.smurf.SmurfShop.model.Smurf;
import com.smurf.SmurfShop.data.SmurfDAO;
import com.smurf.SmurfShop.rest.*;
import com.smurf.SmurfShop.test.utils.UtilsDAO;



	//	@FixMethodOrder(MethodSorters.NAME_ASCENDING)
		@RunWith(Arquillian.class)
		public class IntegrationSmurfDAOTest {
			
			@Deployment
			public static Archive<?> createTestArchive() {
				return ShrinkWrap.create(JavaArchive.class, "Test.jar").addClasses(SmurfDAO.class, Smurf.class,JaxRsActivator.class,SmurfWS.class,UtilsDAO.class)
					//	.addPackage(EventCause.class.getPackage())
					//	.addPackage(EventCauseDAO.class.getPackage())
								//this line will pick up the production db
						.addAsManifestResource("META-INF/persistence.xml","persistence.xml").addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

			}

			 
			@EJB
			private SmurfWS SmurfWS;
			
			@EJB
			private SmurfDAO SmurfDAO;
			
			@EJB
			private UtilsDAO utilsDAO;
			 
			@Before
			public void setUp() {
				//this function means that we start with an empty table
				//And add one Smurf
				//it should be possible to test with an in memory db for efficiency
				utilsDAO.deleteTable();
				Smurf Smurf=new Smurf();
				Smurf.setId(1);
				Smurf.setName("testSmurf");
				Smurf.setPrice(100.00);
				Smurf.setDescription("this is a test smurf");
				Smurf.setImage("testimage.jpg");
				Smurf.setInstock(100);
				Smurf.setSize("small");
				Smurf.setSupplier("Ireland Import ltd");
				Smurf.setMaterial("wood");
				SmurfDAO.save(Smurf);
			}
			
			@Test
			public void testGetAllSmurfs() {
				List<Smurf> SmurfList = SmurfDAO.getAllSmurf();
				assertEquals("Data fetch = data persisted", SmurfList.size(), 1);
				

			}
			
			@Test
			public void testDelete() {
				SmurfDAO.delete(1);
				assertEquals(SmurfDAO.getAllSmurf().size(), 0);
				assertEquals(null, SmurfDAO.getSmurf(1));
				
			}
				
			@Test
			public void testGetSmurf() {
				Smurf smurf1 = SmurfDAO.getSmurf(1);
				assertEquals(smurf1.getId(),1);
				assertEquals(smurf1.getInstock(),100);
				assertEquals(smurf1.getPrice(),100,1.0);
				assertEquals(smurf1.getDescription(),"this is a test smurf");
				assertEquals(smurf1.getImage(),"testimage.jpg");
				assertEquals(smurf1.getMaterial(),"wood");
				assertEquals(smurf1.getName(),"testSmurf");
				assertEquals(smurf1.getSize(), "small");
				assertEquals(smurf1.getSupplier(),"Ireland Import ltd");
				
			}
			
			
			@Test
			public void testGetSmurfByName() {
				Smurf smurf1 = SmurfDAO.getSmurfByName("testSmurf").get(0);
				assertEquals(smurf1.getId(),1);
				assertEquals(smurf1.getInstock(),100);
				assertEquals(smurf1.getPrice(),100,1.0);
				assertEquals(smurf1.getDescription(),"this is a test smurf");
				assertEquals(smurf1.getImage(),"testimage.jpg");
				assertEquals(smurf1.getMaterial(),"wood");
				assertEquals(smurf1.getName(),"testSmurf");
				assertEquals(smurf1.getSize(), "small");
				assertEquals(smurf1.getSupplier(),"Ireland Import ltd");
				
			}
			

			@Test
			public void testUpdateSmurf() {
				Smurf SmurfUpdate=new Smurf();
				SmurfUpdate.setId(1);
				SmurfUpdate.setName("testSmurfupdate");
				SmurfUpdate.setPrice(1000.00);
				SmurfUpdate.setDescription("this is a test smurfupdate");
				SmurfUpdate.setImage("testimageupdate.jpg");
				SmurfUpdate.setInstock(1000);
				SmurfUpdate.setSize("smallupdate");
				SmurfUpdate.setSupplier("Ireland Import ltdupdate");
				SmurfUpdate.setMaterial("woodupdate");
				SmurfDAO.update(SmurfUpdate);
				Smurf smurf1 = SmurfDAO.getSmurfByName("testSmurf").get(0);
				assertEquals(smurf1.getId(),1);
				assertEquals(smurf1.getInstock(),1000);
				assertEquals(smurf1.getPrice(),1000,1.0);
				assertEquals(smurf1.getDescription(),"this is a test smurfupdate");
				assertEquals(smurf1.getImage(),"testimageupdate.jpg");
				assertEquals(smurf1.getMaterial(),"woodupdate");
				assertEquals(smurf1.getName(),"testSmurfupdate");
				assertEquals(smurf1.getSize(), "smallupdate");
				assertEquals(smurf1.getSupplier(),"Ireland Import ltdupdate");
				
			}
			
			@Test
			public void testSaveSmurf() {
				Smurf Smurfnew=new Smurf();
				Smurfnew.setId(2);
				Smurfnew.setName("testSmurfnew");
				Smurfnew.setPrice(1000.00);
				Smurfnew.setDescription("this is a test smurfnew");
				Smurfnew.setImage("testimagenew.jpg");
				Smurfnew.setInstock(1000);
				Smurfnew.setSize("smallnew");
				Smurfnew.setSupplier("Ireland Import ltdnew");
				Smurfnew.setMaterial("woodnew");
				SmurfDAO.save(Smurfnew);
				
				Smurf smurf1 = SmurfDAO.getSmurf(2);
				assertEquals(smurf1.getId(),2);
				assertEquals(smurf1.getInstock(),1000);
				assertEquals(smurf1.getPrice(),1000,1.0);
				assertEquals(smurf1.getDescription(),"this is a test smurfnew");
				assertEquals(smurf1.getImage(),"testimagenew.jpg");
				assertEquals(smurf1.getMaterial(),"woodnew");
				assertEquals(smurf1.getName(),"testSmurfnew");
				assertEquals(smurf1.getSize(), "smallnew");
				assertEquals(smurf1.getSupplier(),"Ireland Import ltdnew");
				
			}
			
}
