package com.cerner.devcenter.dependencygraph.interfaces;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.TransactionFailureException;

import com.cerner.devcenter.dependencygraph.model.MavenEntity;

/**
 * An interface for processing dependency information in Data Access Layer (DAL)
 * 
 * @author Vikram Suriyanarayanan VS029807
 * 
 */
public interface DependencyDAO {
	
	/**
	 * Inserts Dependency based information into database
	 * 
	 * @param mavenObject
	 *            the Object which contains information to be inserted
	 * @return true, if inserted successfully. false otherwise
	 * @throws TransactionFailureException
	 *             if the transaction did not succeed
	 */
	public boolean insertIntoDB(MavenEntity mavenObject);

	/**
	 * Removes Node from the database
	 * 
	 * @return true, if removed successfully. false otherwise
	 */
	public boolean removeNode(Node node);
}