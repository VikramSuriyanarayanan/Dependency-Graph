/**
 * 
 */
package com.cerner.devcenter.dependencygraph.interfaces;

import org.neo4j.graphdb.GraphDatabaseService;

/**
 * An Interface to Create Embedded Database
 * 
 * @author Vikram Suriyanarayanan
 *
 */
public interface DatabaseBuilder {
	
	/**
	 * Creates Database in the path specified by the class
	 * 
	 * @return Database if created successfully. Null otherwise
	 */
	public GraphDatabaseService createDatabase();
	
	/**
	 * Shuts down the database
	 */
	public void shutDown();
}
