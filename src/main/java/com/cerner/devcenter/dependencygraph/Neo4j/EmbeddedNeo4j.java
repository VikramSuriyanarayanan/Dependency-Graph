package com.cerner.devcenter.dependencygraph.Neo4j;

import java.io.File;
import java.io.IOException;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.impl.util.FileUtils;

/**
 * @author Vikram Suriyanarayanan
 * 
 */
public class EmbeddedNeo4j {


	public String greeting;
	private Node firstNode;
	private Node secondNode;
	Relationship relationship;

	private static enum RelationshipTypes implements RelationshipType {
		DEPENDSUPON
	}

	public GraphDatabaseService createDatabase(String path) {
		
		clearDatabase(path);
		GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(path);
		registerShutdownHook(graphDb);
		return graphDb;
	}

	public void clearDatabase(String path) {
		try {
			FileUtils.deleteRecursively(new File(path));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void removeData(GraphDatabaseService graphDb) {

		Transaction tx = null;

		try {
			tx = graphDb.beginTx();
			firstNode.getSingleRelationship(RelationshipTypes.DEPENDSUPON,
					Direction.OUTGOING).delete();
			firstNode.delete();
			secondNode.delete();
			tx.success();
		} finally {
			tx.finish();
		}
	}

	public void shutDown(GraphDatabaseService graphDb) {
		System.out.println();
		System.out.println("Shutting down database ...");
		graphDb.shutdown();
	}

	/**
	 * Registers a shutdown hook for the Neo4j instance so that it shuts down
	 * nicely when the VM exits (even if you "Ctrl-C" the running application).
	 * 
	 * @param graphDb
	 */
	private static void registerShutdownHook(final GraphDatabaseService graphDb) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				graphDb.shutdown();
			}
		});
	}
}
