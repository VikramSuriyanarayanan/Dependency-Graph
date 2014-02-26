/**
 * 
 */
package com.cerner.devcenter.dependencygraph.Neo4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.cern.decenter.dependencygraph.Neo4j.DatabaseBuilderImpl;
import com.cern.decenter.dependencygraph.interfaces.DatabaseBuilder;

/**
 * Test Class for testing the functionality of DatabaseBuilderImpl
 * 
 * @author Vikram Suriyanarayanan
 * 
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ GraphDatabaseFactory.class, DatabaseBuilderImpl.class })
public class DatabaseBuilderImplTest {

	private String testPath = "testPath";
	private DatabaseBuilder testDatabaseBuilder;
	private GraphDatabaseService mockGraphDb;
	private GraphDatabaseFactory mockGraphDbInstance;

	/**
	 * Initializing Objects
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testDatabaseBuilder = new DatabaseBuilderImpl(testPath);
		mockGraphDb = Mockito.mock(GraphDatabaseService.class);

		PowerMockito.mockStatic(GraphDatabaseFactory.class);
		mockGraphDbInstance = mock(GraphDatabaseFactory.class);

	}

	/**
	 * Tests for Null input argument
	 * <p>
	 * Test method for
	 * {@link com.cern.decenter.dependencygraph.Neo4j.DatabaseBuilderImpl#DatabaseBuilderImpl(java.lang.String)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testDatabaseBuilderImplString_Fail() {
		DatabaseBuilder testDatabaseBuilder = new DatabaseBuilderImpl(null);
		testDatabaseBuilder.createDatabase();
	}

	/**
	 * Tests if database is created successfully
	 * <p>
	 * Test method for
	 * {@link com.cern.decenter.dependencygraph.Neo4j.DatabaseBuilderImpl#createDatabase()}
	 * .
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateDatabase() throws Exception {
		PowerMockito.whenNew(GraphDatabaseFactory.class).withNoArguments()
				.thenReturn(mockGraphDbInstance);
		when(mockGraphDbInstance.newEmbeddedDatabase(testPath)).thenReturn(
				mockGraphDb);
		assertEquals(mockGraphDb, testDatabaseBuilder.createDatabase());
	}

	/**
	 * Tests if database creation fails and null is returned
	 * <p>
	 * Test method for
	 * {@link com.cern.decenter.dependencygraph.Neo4j.DatabaseBuilderImpl#createDatabase()}
	 * .
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateDatabase_Failed() throws Exception {
		PowerMockito.whenNew(GraphDatabaseFactory.class).withNoArguments()
				.thenReturn(mockGraphDbInstance);
		when(mockGraphDbInstance.newEmbeddedDatabase(testPath))
				.thenReturn(null);
		assertNull(testDatabaseBuilder.createDatabase());
	}

}
