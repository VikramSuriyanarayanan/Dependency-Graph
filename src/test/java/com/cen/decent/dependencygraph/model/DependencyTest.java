package com.cen.decent.dependencygraph.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.cern.decenter.dependencygraph.model.Dependency;
import com.cern.decenter.dependencygraph.model.MavenEntity;

/**
 * Test Class for Dependency Model
 * 
 * @author Vikram Suriyanarayanan
 * 
 */
public class DependencyTest {

	private Dependency mavenDependency;
	private Dependency otherObject;
	private Dependency transitiveObject;
	private MavenEntity testObject;

	/**
	 * Initializes the object and runs before any of the actual tests.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		mavenDependency = new Dependency();
		otherObject = new Dependency();
		testObject = new MavenEntity();
		transitiveObject = new Dependency();
		String groupId = "com.cerner.test";
		String artifactId = "artifactId";
		String version = "1.0.0";
		String scope = "test";
		
		mavenDependency.setGroupId(groupId);
		mavenDependency.setArtifactId(artifactId);
		mavenDependency.setScope(scope);
		mavenDependency.setVersion(version);
		
		otherObject.setGroupId(groupId);
		otherObject.setArtifactId(artifactId);
		otherObject.setScope(scope);
		otherObject.setVersion(version);
		
		transitiveObject.setGroupId(groupId);
		transitiveObject.setArtifactId(artifactId);
		transitiveObject.setScope(scope);
		transitiveObject.setVersion(version);
	}

	/**
	 * Tests if groupId is correctly obtained. Test method for
	 * {@link com.cern.decenter.dependencygraph.model.Dependency#getGroupId()}
	 * .
	 */
	@Test
	public void testGetGroupId() {
		String groupId = "testingGroupId";
		mavenDependency.setGroupId(groupId);
		assertEquals(groupId, mavenDependency.getGroupId());
	}

	/**
	 * Tests if artifactId is correctly obtained. Test method for
	 * {@link com.cern.decenter.dependencygraph.model.Dependency#getArtifactId()}
	 * .
	 */
	@Test
	public void testGetArtifactId() {
		String artifactId = "development";
		mavenDependency.setArtifactId(artifactId);
		assertEquals(artifactId, mavenDependency.getArtifactId());
	}

	/**
	 * Tests if version is correctly obtained. Test method for
	 * {@link com.cern.decenter.dependencygraph.model.Dependency#getVersion()}
	 * .
	 */
	@Test
	public void testGetVersion() {
		String version = "1.0.1";
		mavenDependency.setVersion(version);
		assertEquals(version, mavenDependency.getVersion());
	}

	/**
	 * Tests if Scope is correctly obtained. Test method for
	 * {@link com.cern.decenter.dependencygraph.model.Dependency#getScope()}.
	 */
	@Test
	public void testGetScope() {
		String scope = "test";
		mavenDependency.setScope(scope);
		assertEquals(scope, mavenDependency.getScope());
	}

	/**
	 * Tests for Equals method. Returns false when comparison fails
	 */
	@Test
	public void testEqualsObject_Null() {
		assertFalse(mavenDependency.equals(null));
	}

	/**
	 * Tests for Equals method. Returns false when comparison fails
	 */
	@Test
	public void testEqualsObject_DifferentInstance() {
		assertFalse(mavenDependency.equals(testObject));
	}
	
	/**
	 * Tests for reflexive property of Equals method.
	 */
	@Test
	public void testEqualsObject_ReflexiveProperty() {
		assertTrue(mavenDependency.equals(mavenDependency));
	}
	
	/**
	 * Tests for Symmetric property of Equals method.
	 */
	@Test
	public void testEqualsObject_SymmetricProperty() {
		assertEquals(mavenDependency.equals(otherObject),otherObject.equals(mavenDependency));
	}	
	
	/**
	 * Tests for Transitive property of Equals method.
	 */
	@Test
	public void testEqualsObject_TransitiveProperty() {
		assertTrue(mavenDependency.equals(otherObject));
		assertTrue(otherObject.equals(transitiveObject));
		assertTrue(transitiveObject.equals(mavenDependency));
	}	
	
	/**
	 * Tests for Reflexive property of HashCode method
	 */
	@Test
	public void testHashCode_ReflexiveProperty() {
		assertEquals(mavenDependency.hashCode(), mavenDependency.hashCode());
	}
	
	/**
	 * Tests for Symmetric property of HashCode method
	 */
	@Test
	public void testHashCode_SymmetricProperty() {
		assertEquals(mavenDependency.hashCode(), otherObject.hashCode());
		assertEquals(otherObject.hashCode(),mavenDependency.hashCode());
	}
	
	/**
	 * Tests for Transitive property of HashCode method
	 */
	@Test
	public void testHashCode_TransitiveProperty() {
		assertEquals(mavenDependency.hashCode(), otherObject.hashCode());
		assertEquals(otherObject.hashCode(),transitiveObject.hashCode());
		assertEquals(transitiveObject.hashCode(),mavenDependency.hashCode());
	}
}