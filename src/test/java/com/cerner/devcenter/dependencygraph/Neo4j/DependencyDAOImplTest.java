/**
 * 
 */
package com.cerner.devcenter.dependencygraph.Neo4j;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.TransactionFailureException;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.cerner.devcenter.dependencygraph.interfaces.DependencyDAO;
import com.cerner.devcenter.dependencygraph.model.Dependency;
import com.cerner.devcenter.dependencygraph.model.MavenEntity;
import com.cerner.devcenter.dependencygraph.model.Neo4jEntity;

/**
 * Test class for testing the functionality of DependencyDAOImpl
 * 
 * @author Vikram Suriyanarayanan
 * 
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ Neo4jEntity.class, DependencyDAOImpl.class })
public class DependencyDAOImplTest {

	private DependencyDAO testDependency;
	private GraphDatabaseService mockGraphDb;
	private MavenEntity testMavenObject;
	private Transaction mockTransaction;
	private Node mockNode;
	private Dependency dependency;
	private Relationship mockRelationship;
	private Neo4jEntity mockNeo4jEntity;

	/**
	 * Initialize objects
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		mockGraphDb = Mockito.mock(GraphDatabaseService.class);
		testDependency = new DependencyDAOImpl(mockGraphDb);
		testMavenObject = new MavenEntity();
		mockNode = Mockito.mock(Node.class);
		mockTransaction = Mockito.mock(Transaction.class);
		mockRelationship = Mockito.mock(Relationship.class);
		mockNeo4jEntity = Mockito.mock(Neo4jEntity.class);
		
		testMavenObject = new MavenEntity();
		dependency = new Dependency();
		List<Dependency> dependencyList = new ArrayList<Dependency>();
		dependencyList.add(dependency);
		testMavenObject.setDependencies(dependencyList);
	}

	/**
	 * Tests if values are inserted properly into embedded Database
	 * <p>
	 * Test method for
	 * {@link com.cerner.devcenter.dependencygraph.Neo4j.DependencyDAOImpl#insertIntoDB(com.cerner.devcenter.dependencygraph.model.MavenEntity)}
	 * .
	 * @throws Exception 
	 */
	@Test
	public void testInsertIntoDB() throws Exception {
		PowerMockito.whenNew(Neo4jEntity.class).withArguments(mockGraphDb).thenReturn(mockNeo4jEntity);
		when(mockGraphDb.beginTx()).thenReturn(mockTransaction);
		when(mockGraphDb.createNode()).thenReturn(mockNode);
		when(mockNeo4jEntity.getFirstNode()).thenReturn(mockNode);
		when(mockNeo4jEntity.getSecondNode()).thenReturn(mockNode);
		when(
				mockNode.createRelationshipTo(Mockito.any(Node.class),
						Mockito.any(RelationshipType.class))).thenReturn( 
				mockRelationship);
		assertTrue(testDependency.insertIntoDB(testMavenObject));
		Mockito.verify(mockTransaction).success();
		Mockito.verify(mockTransaction).finish();
	}

	/**
	 * Tests whether the IllegalArgumentException is thrown when Null parameter
	 * is passed as input
	 * <p>
	 * Test method for
	 * {@link com.cerner.devcenter.dependencygraph.Neo4j.DependencyDAOImpl#insertIntoDB(com.cerner.devcenter.dependencygraph.model.MavenEntity)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInsertIntoDB_IllegalArgumentException() {
		testDependency.insertIntoDB(null);
	}

	/**
	 * Tests whether the method returns true when node is removed
	 * <p>
	 * Test method for
	 * {@link com.cerner.devcenter.dependencygraph.Neo4j.DependencyDAOImpl#removeNode(com.cerner.devcenter.dependencygraph.model.MavenEntity)}
	 * .
	 */
	@Test
	public void testRemoveNode() {
		when(mockGraphDb.beginTx()).thenReturn(mockTransaction);
		when(
				mockNode.getSingleRelationship(
						Mockito.any(RelationshipType.class),
						Mockito.any(Direction.class))).thenReturn(
				mockRelationship);
		assertTrue(testDependency.removeNode(mockNode));
		Mockito.verify(mockRelationship).delete();
		Mockito.verify(mockTransaction).success();
		Mockito.verify(mockTransaction).finish();
	}

	/**
	 * Tests whether the method returns false when Node is not removed
	 * <p>
	 * Test method for
	 * {@link com.cerner.devcenter.dependencygraph.Neo4j.DependencyDAOImpl#removeNode(com.cerner.devcenter.dependencygraph.model.MavenEntity)}
	 * .
	 */
	@Test
	public void testRemoveData_TransactionFailureException() {
		when(mockGraphDb.beginTx()).thenThrow(
				new TransactionFailureException(null, null));
		assertFalse(testDependency.removeNode(mockNode));
	}
	
	/**
	 * Tests whether the method throws exception when Node is null
	 * <p>
	 * Test method for
	 * {@link com.cerner.devcenter.dependencygraph.Neo4j.DependencyDAOImpl#removeNode(com.cerner.devcenter.dependencygraph.model.MavenEntity)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRemoveData_IllegalArgumentException() {
		testDependency.insertIntoDB(null);
	}
}