package com.cen.decent.dependencygraph;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.neo4j.graphdb.GraphDatabaseService;

import com.cern.decenter.dependencygraph.NexusClientImpl;
import com.cern.decenter.dependencygraph.Neo4j.DependencyDAOImpl;
import com.cern.decenter.dependencygraph.Neo4j.EmbeddedNeo4j;
import com.cern.decenter.dependencygraph.interfaces.DependencyDAO;
import com.cern.decenter.dependencygraph.model.MavenEntity;
import com.sun.jersey.api.client.WebResource;

public class DemoTestRun {

	public static void main(String[] args) throws URISyntaxException,
			IOException {

		NexusClientImpl client = new NexusClientImpl();
		URI uri = new URI(
				"http://repo.manager.cerner.corp/service/local/repositories/jive-internal/content/com/atlassian/maven/plugins/maven-clover2-plugin/2.3.2/maven-clover2-plugin-2.3.2.pom");
		WebResource webResource = client.createClient(uri);
		InputStream inputStream = client.getResponse(webResource);
		MavenEntity mavenObject = client.getMavenObject(inputStream);
		EmbeddedNeo4j databaseBuilder = new EmbeddedNeo4j();
		GraphDatabaseService graphDb = databaseBuilder
				.createDatabase("C:/Users/VS029807/Documents/Neo4j/demoTestRun.graphdb");
		DependencyDAO dependencyDAO = new DependencyDAOImpl(graphDb);
		System.out.println("Neo4j GraphDatabase update: "+dependencyDAO.insertIntoDB(mavenObject));
	}
}