package com.cern.decenter.dependencygraph.Neo4j;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.impl.util.FileUtils;

import com.cern.decenter.dependencygraph.interfaces.DatabaseBuilder;

/**
 * Class to create Database for storing Dependency information.
 * <p>
 * This class helps the consumer to create embedded Neo4j Database
 * @author Vikram Suriyanarayanan
 * 
 */
public class DatabaseBuilderImpl implements DatabaseBuilder {
	
	private static final Logger LOGGER = Logger
			.getLogger(DatabaseBuilderImpl.class.getName());
	
	private static final String DB_PATH = "target/neo4j-dependency-db";
	
	private GraphDatabaseService graphDb;
	private String path;

	/**
	 * Default constructor. It creates Database in Default Path Location.
	 */
	public DatabaseBuilderImpl() { 
		path = DB_PATH;
	}

	/**
	 * Constructor which creates the database at the user defined path location
	 * 
	 * @param path
	 *            the location where database needs to be created
	 * @throws IllegalArgumentException
	 *             If the given input parameter is Null.
	 */
	public DatabaseBuilderImpl(String path) {
		if (null == path)
			throw new IllegalArgumentException("The given Path is invalid");

		this.path = path;
	}

	public GraphDatabaseService createDatabase() {

		LOGGER.fine("Creating Database for storing Dependency information");

		clearDatabase();
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(path);

		if (null != graphDb)
			return graphDb;
	
		return null;

	}

	private void clearDatabase() {
		LOGGER.fine("Clearing the already existing database in the given path");
		try {
			FileUtils.deleteRecursively(new File(path));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void shutDown() {
		graphDb.shutdown();
	}
}

