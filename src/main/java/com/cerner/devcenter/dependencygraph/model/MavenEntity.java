package com.cerner.devcenter.dependencygraph.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model for Maven POM information of a Project
 * 
 * @author Vikram Suriyanarayanan
 */
@XmlRootElement(name = "project")
@XmlAccessorType(XmlAccessType.FIELD)
public class MavenEntity {
	private String groupId;
	private String artifactId;
	private String version;
	private String name;

	@XmlElementWrapper(name = "dependencies")
	@XmlElement(name = "dependency")
	private List<Dependency> dependencies;

	/**
	 * Fetches the value of GroupId
	 * 
	 * @return GroupId of the Project
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * Assigns the value of GroupId
	 * 
	 * @param groupId
	 *            GroupId of the Project
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * Fetches the value of ArtifactId
	 * 
	 * @return ArtifactId of the Project
	 */
	public String getArtifactId() {
		return artifactId;
	}

	/**
	 * Assigns the value of ArtifactId
	 * 
	 * @param artifactId
	 *            ArtifactId of the Project
	 */
	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	/**
	 * Fetches the value of Version Number
	 * 
	 * @return Version Number of the Project
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Assigns the value of Version Number
	 * 
	 * @param version
	 *            Version Number of the Project
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Fetches the Project Name
	 * 
	 * @return Name of the Project
	 */
	public String getName() {
		return name;
	}

	/**
	 * Assigns the name of the Project
	 * 
	 * @param name
	 *            Project Name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Fetches all the dependencies present in a Project
	 * 
	 * @return List of all explicit Dependencies
	 */
	public List<Dependency> getDependencies() {
		return dependencies;
	}

	/**
	 * Assigns value to the List of Dependencies in a Project
	 * 
	 * @param dependencies
	 *            List of all explicit Dependencies
	 */
	public void setDependencies(List<Dependency> dependencies) {
		this.dependencies = dependencies;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}
		if (!(obj instanceof MavenEntity)) {
			return false;
		}
		MavenEntity other = (MavenEntity) obj;

		if (groupId.equals(other.getGroupId())
				&& (artifactId.equals(other.getArtifactId()))
				&& (version.equals(other.getVersion()))
				&& (name.equals(other.getName())) 
				&& (dependencies.equals(other.getDependencies()))) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {

		int hash = 17;
		hash = 31 * hash + groupId.hashCode();
		hash = 31 * hash + artifactId.hashCode();
		hash = 31 * hash + version.hashCode();
		hash = 31 * hash + name.hashCode();
		hash = 31 * hash + dependencies.hashCode();
		
		return hash;
	}
}
