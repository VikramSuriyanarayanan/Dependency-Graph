package com.cern.decenter.dependencygraph.interfaces;

import java.io.InputStream;
import java.net.URI;

import com.cern.decenter.dependencygraph.exceptions.InvalidMavenObjectException;
import com.cern.decenter.dependencygraph.model.MavenEntity;
import com.sun.jersey.api.client.WebResource;

/**
 * An Interface to extract Explicit Dependencies of a Project.
 * <p>
 * It includes the following modules: a). Creating a Nexus Client b). Getting
 * Response using REST API c). Extracting JAR Dependencies
 * 
 * @author Vikram Suriyanarayanan
 */
public interface NexusClient {

	/**
	 * Creates a NEXUS Client to access POM files of a Project.
	 * 
	 * @param uri
	 *            the URI containing information about pom file. eg:
	 *            "http://repo.manager.cerner.corp/service/local/repositories/jive-internal/content/com/atlassian/maven/plugins/maven-clover2-plugin/2.3.2/maven-clover2-plugin-2.3.2.pom"
	 * @return webresource, if client was created successfully
	 */
	public WebResource createClient(URI uri);

	/**
	 * Get the existing POM information from NEXUS Client.
	 * 
	 * @param webResource
	 *            Contains information about the POM
	 * @return POM information in the form of inputStream
	 *
	 */
	public InputStream getResponse(WebResource webResource);

	/**
	 * Creates an Object from the existing POM information.
	 * 
	 * @param entity
	 *            InputStream containing information about the POM
	 * @return Object containing MAVEN POM information
	 * @throws InvalidMavenObjectException
	 *             if the String to Java Object conversion fails unexpectedly.
	 */
	public MavenEntity getMavenObject(InputStream inputStream)
			throws InvalidMavenObjectException;

}
