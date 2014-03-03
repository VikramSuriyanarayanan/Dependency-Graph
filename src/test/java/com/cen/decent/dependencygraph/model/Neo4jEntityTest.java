/**
 * 
 */
package com.cen.decent.dependencygraph.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

import com.cern.decenter.dependencygraph.model.Dependency;
import com.cern.decenter.dependencygraph.model.MavenEntity;
import com.cern.decenter.dependencygraph.model.Neo4jEntity;

/**
 * @author Vikram Suriyanarayanan VS029807
 * 
 */
public class Neo4jEntityTest {

	private GraphDatabaseService mockDB;
	private Node mockNode;
	private Neo4jEntity neo4jObject;
	private Neo4jEntity duplicateObject;
	private Neo4jEntity transitiveObject;

	private MavenEntity mavenObject;
	private Dependency dependency;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		mockDB = Mockito.mock(GraphDatabaseService.class);
		mockNode = Mockito.mock(Node.class);

		neo4jObject = new Neo4jEntity(mockDB);
		duplicateObject = new Neo4jEntity(mockDB);
		transitiveObject = new Neo4jEntity(mockDB);

		mavenObject = new MavenEntity();
		dependency = new Dependency();

		when(mockDB.createNode()).thenReturn(mockNode);
		neo4jObject.setFirstNode(mavenObject);
		neo4jObject.setSecondNode(dependency);
		duplicateObject.setFirstNode(mavenObject);
		duplicateObject.setSecondNode(dependency);
		transitiveObject.setFirstNode(mavenObject);
		transitiveObject.setSecondNode(dependency);

	}

	/**
	 * Test method for
	 * {@link com.cern.decenter.dependencygraph.model.Neo4jEntity#equals(java.lang.Object)}
	 * .
	 */
	@Test
	public void testEqualsObject_Null() {
		assertFalse(neo4jObject.equals(null));
	}
	
	/**
	 * Test method for
	 * {@link com.cern.decenter.dependencygraph.model.Neo4jEntity#equals(java.lang.Object)}
	 * .
	 */
	@Test
	public void testEqualsObject_DifferentInstance() {
		assertFalse(neo4jObject.equals(mavenObject));
	}

	/**
	 * Test method for
	 * {@link com.cern.decenter.dependencygraph.model.Neo4jEntity#equals(java.lang.Object)}
	 * .
	 */
	@Test
	public void testEqualsObject_ReflexiveProperty() {
		assertTrue(neo4jObject.equals(neo4jObject));
	}

	/**
	 * Test method for
	 * {@link com.cern.decenter.dependencygraph.model.Neo4jEntity#equals(java.lang.Object)}
	 * .
	 */
	@Test
	public void testEqualsObject_SymmetricProperty() {
		assertEquals(neo4jObject.equals(duplicateObject),
				duplicateObject.equals(neo4jObject));
	}

	/**
	 * Test method for
	 * {@link com.cern.decenter.dependencygraph.model.Neo4jEntity#equals(java.lang.Object)}
	 * .
	 */
	@Test
	public void testEqualsObject_TransitiveProperty() {
		assertTrue(neo4jObject.equals(duplicateObject));
		assertTrue(duplicateObject.equals(transitiveObject));
		assertTrue(transitiveObject.equals(neo4jObject));
	}

	/**
	 * Test method for
	 * {@link com.cern.decenter.dependencygraph.model.Neo4jEntity#hashCode()}
	 * .
	 */
	@Test
	public void testHashCode_ReflexiveProperty() {
		assertEquals(neo4jObject.hashCode(), neo4jObject.hashCode());
	}

	/**
	 * Test method for
	 * {@link com.cern.decenter.dependencygraph.model.Neo4jEntity#hashCode()}
	 * .
	 */
	@Test
	public void testHashCode_SymmetricProperty() {
		assertEquals(neo4jObject.hashCode(), duplicateObject.hashCode());
		assertEquals(duplicateObject.hashCode(), neo4jObject.hashCode());
	}

	/**
	 * Test method for
	 * {@link com.cern.decenter.dependencygraph.model.Neo4jEntity#hashCode()}
	 * .
	 */
	@Test
	public void testHashCode_TransitiveProperty() {
		assertEquals(neo4jObject.hashCode(), duplicateObject.hashCode());
		assertEquals(duplicateObject.hashCode(), transitiveObject.hashCode());
		assertEquals(transitiveObject.hashCode(), neo4jObject.hashCode());
	}
}
