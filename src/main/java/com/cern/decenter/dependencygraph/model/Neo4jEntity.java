/**
 * 
 */
package com.cern.decenter.dependencygraph.model;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

/**
 * Model class for getting and setting Node values of Neo4j Embedded database.
 * @author Vikram Suriyanarayanan VS029807
 * 
 */
public class Neo4jEntity {

	private Node firstNode;
	private Node secondNode;
	private GraphDatabaseService graphDB;

	/**
	 * This Constructor gets the graph database as parameter as input
	 * 
	 * @param graphDB
	 */
	public Neo4jEntity(GraphDatabaseService graphDB) {
		if (null == graphDB)
			throw new IllegalArgumentException("The Embedded Database is NULL");

		this.graphDB = graphDB;
	}

	/**
	 * Sets the FirstNode value
	 * @param mavenObject Contains the property values of the project that needs to be set
	 */
	public void setFirstNode(MavenEntity mavenObject) {
		firstNode = graphDB.createNode();
		firstNode.setProperty("Name", mavenObject.getName());
		firstNode.setProperty("GroupID", mavenObject.getGroupId());
		firstNode.setProperty("ArtifactID", mavenObject.getArtifactId());
		firstNode.setProperty("Version", mavenObject.getVersion());
	}

	/**
	 * Gets the information for First Node.
	 * @return FirstNode value
	 */
	public Node getFirstNode() {
		return firstNode;
	}

	/**
	 * Sets the information for SecondNode in Neo4j
	 * @param dependencyObject Contains property values that needs to be set
	 */
	public void setSecondNode(Dependency dependencyObject) {
		secondNode = graphDB.createNode();
		secondNode.setProperty("GroupID", dependencyObject.getGroupId());
		secondNode.setProperty("ArtifactID", dependencyObject.getArtifactId());
		secondNode.setProperty("Version", dependencyObject.getVersion());
	}

	/**
	 * Gets the information for Second Node
	 * @return Second Node value
	 */
	public Node getSecondNode() {
		return secondNode;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Neo4jEntity)) {
			return false;
		}
		Neo4jEntity other = (Neo4jEntity) obj;

		if (firstNode.equals(other.getFirstNode())
				&& (secondNode.equals(other.getSecondNode()))) {
			return true;
		} else {
			return false;
		}
	}

	
	@Override
	public int hashCode() {

		int hash = 17;
		hash = 31 * hash + firstNode.hashCode();
		hash = 31 * hash + secondNode.hashCode();
		return hash;
	}
}
