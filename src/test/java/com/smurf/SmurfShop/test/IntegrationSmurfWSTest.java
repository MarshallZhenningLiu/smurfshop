package com.smurf.SmurfShop.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.core.Response;
import org.apache.http.HttpStatus;
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
		public class IntegrationSmurfWSTest {
			
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
				Smurf.setId(2);
				Smurf.setName("testSmurf2");
				Smurf.setPrice(100.00);
				Smurf.setDescription("this is a test smurf2");
				Smurf.setImage("testimage2.jpg");
				Smurf.setInstock(100);
				Smurf.setSize("small2");
				Smurf.setSupplier("Ireland Import ltd2");
				Smurf.setMaterial("wood2");
				SmurfDAO.save(Smurf);
			}
			
			@Test
			public void testWSFindSmurfById() {
				Response response=SmurfWS.findSmurfById(1);
				Smurf smurf1 = (Smurf) response.getEntity();
				assertEquals(HttpStatus.SC_OK, response.getStatus());
				assertEquals(smurf1.getId(), 1);
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
			public void testWSFindSmurfByName() {
				Response response=SmurfWS.findByName("testSmurf");
				List<Smurf> smurfs = (List<Smurf>) response.getEntity();
				Smurf smurf1 = smurfs.get(0);
				assertEquals(HttpStatus.SC_OK, response.getStatus());
				assertEquals(smurf1.getId(), 1);
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
			public void testWSFindAllSmurfs() {
				Response response=SmurfWS.findAllSmurfs();
				List<Smurf> smurfList = (List<Smurf>) response.getEntity();
				assertEquals(HttpStatus.SC_OK, response.getStatus());
				assertEquals(smurfList.size(), 2);
				
				Smurf smurf1 = smurfList.get(0);
				Smurf smurf2 = smurfList.get(1);
				
				assertEquals(smurf1.getName(), "testSmurf");
				assertEquals(smurf2.getName(), "testSmurf2");
			}
			
			@Test
			public void testWSSaveSmurf() {
				Smurf smurf3 = new Smurf();
				smurf3.setId(3);
				smurf3.setName("testSmurf3");
				smurf3.setPrice(100.00);
				smurf3.setDescription("this is a test smurf3");
				smurf3.setImage("testimage3.jpg");
				smurf3.setInstock(100);
				smurf3.setSize("small3");
				smurf3.setSupplier("Ireland Import ltd3");
				smurf3.setMaterial("wood3");			
				
				Response response=SmurfWS.saveSmurf(smurf3);
				assertEquals(HttpStatus.SC_CREATED, response.getStatus());
				
				Smurf smurfSaved = (Smurf) response.getEntity();				
				
				assertEquals(smurfSaved.getId(), 3);
				assertEquals(smurfSaved.getInstock(),100);
				assertEquals(smurfSaved.getPrice(),100,1.0);
				assertEquals(smurfSaved.getDescription(),"this is a test smurf3");
				assertEquals(smurfSaved.getImage(),"testimage3.jpg");
				assertEquals(smurfSaved.getMaterial(),"wood3");
				assertEquals(smurfSaved.getName(),"testSmurf3");
				assertEquals(smurfSaved.getSize(), "small3");
				assertEquals(smurfSaved.getSupplier(),"Ireland Import ltd3");
			}
			
			@Test
			public void testWSUpdateSmurf() {
				Response response = SmurfWS.findSmurfById(1);
				Smurf smurf = (Smurf) response.getEntity();
				smurf.setName("testSmurfNewUpdated");			
				smurf.setMaterial("woodNewUpdated");
				
				response = SmurfWS.updateSmurf(smurf, 1);
				assertEquals(HttpStatus.SC_OK, response.getStatus());
				
				Smurf smurfNewUpdated = (Smurf) response.getEntity();				
				
				assertEquals(smurfNewUpdated.getId(), 1);
				assertEquals(smurfNewUpdated.getInstock(),100);
				assertEquals(smurfNewUpdated.getPrice(),100,1.0);
				assertEquals(smurfNewUpdated.getDescription(),"this is a test smurf");
				assertEquals(smurfNewUpdated.getImage(),"testimage.jpg");
				assertEquals(smurfNewUpdated.getMaterial(),"woodNewUpdated");
				assertEquals(smurfNewUpdated.getName(),"testSmurfNewUpdated");
				assertEquals(smurfNewUpdated.getSize(), "small");
				assertEquals(smurfNewUpdated.getSupplier(),"Ireland Import ltd");
			}
			
			@Test
			public void testWSDeleteSmurf() {
				Response response = SmurfWS.findAllSmurfs();
				List<Smurf> smurfList = (List<Smurf>) response.getEntity();
				assertEquals(smurfList.size(), 2);
				SmurfWS.deleteSmurf(2);
				response = SmurfWS.findAllSmurfs();
				smurfList = (List<Smurf>) response.getEntity();
				assertEquals(smurfList.size(), 1);
				
				response=SmurfWS.findSmurfById(2);
				Smurf smurfDeleted = (Smurf) response.getEntity();
				assertEquals(HttpStatus.SC_OK, response.getStatus());
				assertEquals(null, smurfDeleted);				
			}
			
			
			
			
}
