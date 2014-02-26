package com.cerner.devcenter.dependencygraph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.xml.sax.InputSource;

import com.cerner.devcenter.dependencygraph.exceptions.InvalidMavenObjectException;
import com.cerner.devcenter.dependencygraph.interfaces.NexusClient;
import com.cerner.devcenter.dependencygraph.model.Dependency;
import com.cerner.devcenter.dependencygraph.model.MavenEntity;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

/**
 * Test Class for NexusClient Implementation
 * 
 * @author Vikram Suriyanarayanan
 * 
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ Builder.class, NexusClientImpl.class, JAXBContext.class })
public class NexusClientImplTest {

	private NexusClient nexusClient;
	private WebResource mockWebResource;
	private ClientResponse mockResponse;
	private Builder mockBuilder;
	private InputSource mockInputSource;
	private JAXBContext mockJAXBContext;
	private Unmarshaller mockUnmarshaller;
	private MavenEntity testMavenObject;
	private InputStream testInputStream;
	private List<Dependency> dependencies;

	/**
	 * Initializes the object and runs before any of the actual tests.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		nexusClient = new NexusClientImpl();
		mockWebResource = mock(WebResource.class);
		mockResponse = mock(ClientResponse.class);

		PowerMockito.mockStatic(Builder.class);
		mockBuilder = mock(Builder.class);

		PowerMockito.mockStatic(InputSource.class);
		mockInputSource = mock(InputSource.class);

		PowerMockito.mockStatic(JAXBContext.class);
		mockJAXBContext = mock(JAXBContext.class);

		mockUnmarshaller = mock(Unmarshaller.class);

		dependencies = new ArrayList<Dependency>();
		Dependency testDependency = new Dependency();
		testDependency.setArtifactId("test artifact");
		testDependency.setGroupId("test groupid");
		testDependency.setScope("test");
		testDependency.setVersion("test 1.0");
		dependencies.add(testDependency);

		testMavenObject = new MavenEntity();
		testMavenObject.setName("devCenter Project");
		testMavenObject.setGroupId("com.cerner.test");
		testMavenObject.setArtifactId("development");
		testMavenObject.setVersion("1.1");
	}

	/**
	 * Tests if Jersey client is created and web Resource is returned
	 * successfully
	 * 
	 * Test method for
	 * {@link com.cerner.devcenter.dependencygraph.NexusClientImpl#createClient(java.net.URI)}
	 * .
	 * 
	 * @throws URISyntaxException
	 */
	@Test
	public void testCreateClient() throws URISyntaxException {

		URI testURI = new URI("local.test.com");
		assertNotNull(nexusClient.createClient(testURI));
	}

	/**
	 * Tests for Null URI value and throws IllegalArgumentException.
	 * 
	 * Test method for
	 * {@link com.cerner.devcenter.dependencygraph.NexusClientImpl#createClient(java.net.URI)}
	 * .
	 * 
	 * @throws URISyntaxException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCreateClient_NullURI() throws URISyntaxException {
		URI testURI = null;
		nexusClient.createClient(testURI);
	}

	/**
	 * Tests for Empty URI value and throws IllegalArgumentException.
	 * 
	 * Test method for
	 * {@link com.cerner.devcenter.dependencygraph.NexusClientImpl#createClient(java.net.URI)}
	 * .
	 * 
	 * @throws URISyntaxException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCreateClient_EmptyURI() throws URISyntaxException {

		URI testURI = new URI("");
		nexusClient.createClient(testURI);
	}

	/**
	 * Tests for getting Nexus API Client response.
	 * 
	 * Test method for
	 * {@link com.cerner.devcenter.dependencygraph.NexusClientImpl#getResponse(com.sun.jersey.api.client.WebResource)}
	 * .
	 */
	@Test
	public void testGetResponse() {
		when(mockWebResource.accept("application/xml")).thenReturn(mockBuilder);
		when(mockBuilder.get(ClientResponse.class)).thenReturn(mockResponse);
		when(mockResponse.getClientResponseStatus()).thenReturn(Status.OK);
		when(mockResponse.getEntity(InputStream.class)).thenReturn(
				testInputStream);

		assertEquals(testInputStream, nexusClient.getResponse(mockWebResource));
	}

	/**
	 * Tests for getting Nexus API Client response with Null input.
	 * 
	 * Test method for
	 * {@link com.cerner.devcenter.dependencygraph.NexusClientImpl#getResponse(com.sun.jersey.api.client.WebResource)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetResponse_NullWebResource() {
		nexusClient.getResponse(null);
	}

	/**
	 * Tests for getting Nexus API Client response. Receives a response status
	 * which is not OK.
	 * 
	 * Test method for
	 * {@link com.cerner.devcenter.dependencygraph.NexusClientImpl#getResponse(com.sun.jersey.api.client.WebResource)}
	 * .
	 */
	@Test(expected = RuntimeException.class)
	public void testGetResponse_ResponseCodeError() {
		when(mockWebResource.accept("application/xml")).thenReturn(mockBuilder);
		when(mockBuilder.get(ClientResponse.class)).thenReturn(mockResponse);
		when(mockResponse.getClientResponseStatus()).thenReturn(
				Status.FORBIDDEN);

		nexusClient.getResponse(mockWebResource);
	}

	/**
	 * Test method for getting Maven Object
	 * {@link com.cerner.devcenter.dependencygraph.NexusClientImpl#getMavenObject(java.lang.String)}
	 * .
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetMavenObject() throws Exception {

		PowerMockito.whenNew(InputSource.class).withAnyArguments()
				.thenReturn(mockInputSource);
		InputStream testInputStream = new FileInputStream("pom.xml");
		when(JAXBContext.newInstance(MavenEntity.class)).thenReturn(
				mockJAXBContext);
		when(mockJAXBContext.createUnmarshaller()).thenReturn(mockUnmarshaller);
		when(mockUnmarshaller.unmarshal(mockInputSource)).thenReturn(
				testMavenObject);

		assertEquals(testMavenObject,
				nexusClient.getMavenObject(testInputStream));
	}

	/**
	 * Test method for getting Maven Object where input String is Null
	 * {@link com.cerner.devcenter.dependencygraph.NexusClientImpl#getMavenObject(java.lang.String)}
	 * .
	 * 
	 * @throws Exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetMavenObject_NullInput() throws Exception {
		InputStream testInputStream = null;
		nexusClient.getMavenObject(testInputStream);
	}

	/**
	 * Test method for getting Maven Object. It throws
	 * InvalidMavenObjectException
	 * {@link com.cerner.devcenter.dependencygraph.NexusClientImpl#getMavenObject(java.lang.String)}
	 * .
	 * 
	 * @throws Exception
	 */
	@Test(expected = InvalidMavenObjectException.class)
	public void testGetMavenObject_InvalidMavenObjectException()
			throws Exception {

		PowerMockito.whenNew(InputSource.class).withAnyArguments()
				.thenReturn(mockInputSource);
		when(JAXBContext.newInstance(MavenEntity.class)).thenThrow(
				new JAXBException("Error"));
		InputStream testInputStream = new FileInputStream("pom.xml");
		assertEquals(testMavenObject,
				nexusClient.getMavenObject(testInputStream));
	}
}