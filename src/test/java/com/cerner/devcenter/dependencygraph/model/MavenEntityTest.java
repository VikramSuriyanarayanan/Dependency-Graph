package com.cerner.devcenter.dependencygraph.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Test Class for Maven Entity Model
 * 
 * @author Vikram Suriyanarayanan
 * 
 */
public class MavenEntityTest {

	private MavenEntity mavenObject;
	private MavenEntity otherObject;
	private MavenEntity transitiveObject;
	private Dependency testObject;
	private List<Dependency> dependencies;
	
	/**
	 * Initializes the object and runs before any of the actual tests.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		mavenObject = new MavenEntity();
		otherObject = new MavenEntity();
		transitiveObject = new MavenEntity();
		
		String groupId = "com.cerner.test";
		String artifactId = "artifactId";
		String version = "1.0.0";
		String name = "testName";
		dependencies = new ArrayList<Dependency>();
		Dependency testDependency = new Dependency();
		testDependency.setArtifactId(artifactId);
		testDependency.setGroupId(groupId);
		testDependency.setScope("test");
		testDependency.setVersion(version);
		dependencies.add(testDependency);
		
		mavenObject.setGroupId(groupId);
		mavenObject.setArtifactId(artifactId);
		mavenObject.setVersion(version);
		mavenObject.setName(name);
		mavenObject.setDependencies(dependencies);
		
		otherObject.setGroupId(groupId);
		otherObject.setArtifactId(artifactId);
		otherObject.setVersion(version);
		otherObject.setName(name);
		otherObject.setDependencies(dependencies);

		
		transitiveObject.setGroupId(groupId);
		transitiveObject.setArtifactId(artifactId);
		transitiveObject.setVersion(version);
		transitiveObject.setName(name);
		transitiveObject.setDependencies(dependencies);


	}

	/**
	 * Tests if groupId is correctly obtained. Test method for
	 * {@link com.cerner.devcenter.dependencygraph.model.MavenEntity#getGroupId()}
	 * .
	 */
	@Test
	public void testGetGroupId() {
		assertEquals("com.cerner.test", mavenObject.getGroupId());
	}

	/**
	 * Tests if artifactId is correctly obtained. Test method for
	 * {@link com.cerner.devcenter.dependencygraph.model.MavenEntity#getArtifactId()}
	 * .
	 */
	@Test
	public void testGetArtifactId() {
		assertEquals("artifactId", mavenObject.getArtifactId());
	}

	/**
	 * Tests if version is correctly obtained. Test method for
	 * {@link com.cerner.devcenter.dependencygraph.model.MavenEntity#getVersion()}
	 * .
	 */
	@Test
	public void testGetVersion() {
		assertEquals("1.0.0", mavenObject.getVersion());
	}

	/**
	 * Tests if Name is correctly obtained. Test method for
	 * {@link com.cerner.devcenter.dependencygraph.model.MavenEntity#getName()}.
	 */
	@Test
	public void testGetName() {
		assertEquals("testName", mavenObject.getName());
	}

	/**
	 * Tests if Dependencies are correctly obtained. Test method for
	 * {@link com.cerner.devcenter.dependencygraph.model.MavenEntity#getDependencies()}
	 * .
	 */
	@Test
	public void testGetDependencies() {
		assertEquals(dependencies, mavenObject.getDependencies());
	}
	
	/**
	 * Tests for Equals method. Returns false when comparison fails
	 */
	@Test
	public void testEqualsObject_Null() {
		assertFalse(mavenObject.equals(null));
	}
	
	/**
	 * Tests for Equals method. Returns false when comparison fails
	 */
	@Test
	public void testEqualsObject_DifferentInstance() {
		assertFalse(mavenObject.equals(testObject));
	}
	
	/**
	 * Tests for reflexive property of Equals method.
	 */
	@Test
	public void testEqualsObject_ReflexiveProperty() {
		assertTrue(mavenObject.equals(mavenObject));
	}
	
	/**
	 * Tests for Symmetric property of Equals method.
	 */
	@Test
	public void testEqualsObject_SymmetricProperty() {
		assertEquals(mavenObject.equals(otherObject),otherObject.equals(mavenObject));
	}	
	
	/**
	 * Tests for Transitive property of Equals method.
	 */
	@Test
	public void testEqualsObject_TransitiveProperty() {
		assertTrue(mavenObject.equals(otherObject));
		assertTrue(otherObject.equals(transitiveObject));
		assertTrue(transitiveObject.equals(mavenObject));
	}	

	/**
	 * Tests for Reflexive property of HashCode method
	 */
	@Test
	public void testHashCode_ReflexiveProperty() {
		assertEquals(mavenObject.hashCode(), mavenObject.hashCode());
	}
	
	/**
	 * Tests for Symmetric property of HashCode method
	 */
	@Test
	public void testHashCode_SymmetricProperty() {
		assertEquals(mavenObject.hashCode(), otherObject.hashCode());
		assertEquals(otherObject.hashCode(),mavenObject.hashCode());
	}
	
	/**
	 * Tests for Transitive property of HashCode method
	 */
	@Test
	public void testHashCode_TransitiveProperty() {
		assertEquals(mavenObject.hashCode(), otherObject.hashCode());
		assertEquals(otherObject.hashCode(),transitiveObject.hashCode());
		assertEquals(transitiveObject.hashCode(),mavenObject.hashCode());
	}
}
