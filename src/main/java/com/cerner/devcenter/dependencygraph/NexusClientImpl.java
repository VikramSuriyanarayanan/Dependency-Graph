package com.cerner.devcenter.dependencygraph;

import java.io.InputStream;
import java.net.URI;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.xml.sax.InputSource;

import com.cerner.devcenter.dependencygraph.exceptions.InvalidMavenObjectException;
import com.cerner.devcenter.dependencygraph.interfaces.NexusClient;
import com.cerner.devcenter.dependencygraph.model.MavenEntity;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.ClientResponse.Status;

/**
 * Class that implements the NEXUS Client to extract Explicit Dependencies of a
 * Project.
 * <p>
 * This Class helps the consumer to extract MAVEN Dependencies present in a
 * Project.
 * 
 * @author Vikram Suriyanarayanan
 */
public class NexusClientImpl implements NexusClient {

	private static final Logger LOGGER = Logger.getLogger(NexusClientImpl.class
			.getName());

	/**
	 * {@inheritDoc} Logs the URI for which Nexus client is created
	 * 
	 * @throws IllegalArgumentException
	 *             if input URI is Null/Empty
	 */
	public WebResource createClient(URI uri) {

		LOGGER.fine("Creating Client for Nexus API with URI :" + uri);

		if (uri == null || uri.toString().equals("")) {
			throw new IllegalArgumentException(
					"The URI information for Nexus Client is Null/Empty ");
		}

		Client client = Client.create();
		WebResource webResource = client.resource(uri);
		return webResource;
	}

	/**
	 * {@inheritDoc} Logs the web resource information for which the client gets
	 * response
	 * 
	 * @throws IllegalArgumentException
	 *             if input WebResource is NULL
	 * @throws RuntimeException
	 *             if Response status obtained is not 'OK'
	 */
	public InputStream getResponse(WebResource webResource) {

		LOGGER.fine("Getting Response for NEXUS API with webResource: "
				+ webResource);

		if (webResource == null) {
			throw new IllegalArgumentException("The given Webresource is Null");
		}

		ClientResponse response = webResource.accept("application/xml").get(
				ClientResponse.class);

		if (!(response.getClientResponseStatus() == Status.OK)) {
			throw new RuntimeException(
					"Failed to get response - HTTP error code : "
							+ response.getStatus());
		}

		return response.getEntity(InputStream.class);
	}

	/**
	 * {@inheritDoc} Logs the Maven object output with its attributes listed.
	 * 
	 * @throws IllegalArgumentException
	 *             if the given input stream value is Null
	 */
	public MavenEntity getMavenObject(InputStream inputStream)
			throws InvalidMavenObjectException {

		LOGGER.fine("Creating Maven Object for given POM information");

		if (inputStream == null) {
			throw new IllegalArgumentException(
					"The given POM information is Null ");
		}

		InputSource inputSource = new InputSource(inputStream);

		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(MavenEntity.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			MavenEntity mavenObject = (MavenEntity) jaxbUnmarshaller
					.unmarshal(inputSource);

			LOGGER.fine("Created Maven Object for " + mavenObject.getName()
					+ "with GroupId: " + mavenObject.getGroupId()
					+ ", ArtifactId: " + mavenObject.getArtifactId()
					+ " and VersionNo: " + mavenObject.getVersion());

			return mavenObject;

		} catch (JAXBException jaxbException) {
			throw new InvalidMavenObjectException(
					"Unable to create Maven Object from the given POM information",
					jaxbException);
		}
	}
}