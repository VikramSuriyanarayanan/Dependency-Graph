package com.cerner.devcenter.dependencygraph.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model for Dependency of a Project
 * 
 * @author Vikram Suriyanarayanan
 */

@XmlRootElement(name = "dependency")
@XmlAccessorType(XmlAccessType.FIELD)
public class Dependency {
	private String groupId;
	private String artifactId;
	private String version;
	private String scope;

	/**
	 * Fetches the value of GroupId
	 * 
	 * @return groupId of the Dependency
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * Assigns the value of GroupId
	 * 
	 * @param groupId
	 *            GroupId of the Dependency
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * Fetches the value of ArtifactId
	 * 
	 * @return ArtifactId of the Dependency
	 */
	public String getArtifactId() {
		return artifactId;
	}

	/**
	 * Assigns the value of ArtifactId
	 * 
	 * @param artifactId
	 *            ArtifactId of the Dependency
	 */
	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	/**
	 * Fetches the value of Version number
	 * 
	 * @return Version Number of the Dependency
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Assigns the value of Version Number
	 * 
	 * @param version
	 *            Version number of the Dependency
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Fetches the scope level of the Depedency
	 * 
	 * @return Scope of the Dependency
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * Assigns the Scope of the Dependency
	 * 
	 * @param scope
	 *            Scope level of the Dependency
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Dependency)) {
			return false;
		}
		Dependency other = (Dependency) obj;

		if (groupId.equals(other.getGroupId())
				&& (artifactId.equals(other.getArtifactId()))
				&& (version.equals(other.getVersion()))
				&& (scope.equals(other.getScope()))) {
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
		hash = 31 * hash + scope.hashCode();
		
		return hash;
	}
}
