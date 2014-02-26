/**
 * 
 */
package com.cerner.devcenter.dependencygraph.Neo4j;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.TransactionFailureException;

import com.cerner.devcenter.dependencygraph.interfaces.DependencyDAO;
import com.cerner.devcenter.dependencygraph.model.Dependency;
import com.cerner.devcenter.dependencygraph.model.MavenEntity;
import com.cerner.devcenter.dependencygraph.model.Neo4jEntity;

/**
 * Class for processing Dependency information in Data Access Layer (DAL)
 * <p>
 * This class helps the consumer to insert and remove data from the embedded
 * Database.
 * 
 * @author Vikram Suriyanarayanan
 * 
 */
public class DependencyDAOImpl implements DependencyDAO {

	private static final Logger LOGGER = Logger
			.getLogger(DependencyDAOImpl.class.getName());

	private static enum RelationshipTypes implements RelationshipType {
		DEPENDSUPON
	}

	private Node firstNode;
	private Node secondNode;
	private Relationship relationship;
	private GraphDatabaseService graphDb;

	/**
	 * This Constructor gets the graph database as parameter from user as input
	 * 
	 * @param graphDb
	 *            Database where Dependency information needs to stored
	 */
	public DependencyDAOImpl(GraphDatabaseService graphDb) {
		if (null == graphDb)
			throw new IllegalArgumentException("The Embedded Database is NULL");

		this.graphDb = graphDb;
	}

	public boolean insertIntoDB(MavenEntity mavenObject) {

		if (null == mavenObject) {
			throw new IllegalArgumentException("The given input object is null");
		}

		LOGGER.fine("Inserting " + mavenObject.getName() + " into Database");
		Transaction transaction = null;
		Neo4jEntity neo4jObject = new Neo4jEntity(graphDb);

		try {
			transaction = graphDb.beginTx();
			neo4jObject.setFirstNode(mavenObject);
			firstNode = neo4jObject.getFirstNode();

			for (Dependency object : mavenObject.getDependencies()) {

				neo4jObject.setSecondNode(object);
				secondNode = neo4jObject.getSecondNode();
				relationship = firstNode.createRelationshipTo(secondNode,
						RelationshipTypes.DEPENDSUPON);
				relationship.setProperty("message", "Depends On ");

				String information = ((String) firstNode.getProperty("Name"))
						+ ((String) relationship.getProperty("message"))
						+ ((String) secondNode.getProperty("GroupID"));

				transaction.success();
				LOGGER.fine("Inserted " + information
						+ " into database successfully");
			}

			return true;

		} catch (TransactionFailureException transactionException) {
			LOGGER.log(
					Level.SEVERE,
					" Failed  to insert Information for : "
							+ mavenObject.getName() + "with groupId: "
							+ mavenObject.getGroupId() + " ArtifactId: "
							+ mavenObject.getArtifactId() + "Version :"
							+ mavenObject.getVersion(), transactionException);
		} finally {
			transaction.finish();
		}

		return false;
	}

	public boolean removeNode(Node node) {

		if (node == null) {
			throw new IllegalArgumentException(
					"Node to be deleted cannot be Null");
		}

		Transaction transaction = null;

		try {
			transaction = graphDb.beginTx();
			node.getSingleRelationship(RelationshipTypes.DEPENDSUPON,
					Direction.OUTGOING).delete();
			node.delete();
			transaction.success();
			transaction.finish();

			LOGGER.fine("Node deleted successfully from the database");
			return true;

		} catch (TransactionFailureException transactionException) {
			LOGGER.log(Level.SEVERE, " Failed  to delete Node",
					transactionException);
		}

		return false;
	}
}