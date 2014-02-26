/**
 * This Package holds all the model classes required for extracting explicit dependencies
 * <p>
 * The Namespace qualification is applied at the package level. So that JAXB unmarshalling will use it to convert the XML document into Java Object.
 * @author Vikram Suriyanarayanan
 */
@XmlSchema(namespace = "http://maven.apache.org/POM/4.0.0", elementFormDefault = XmlNsForm.QUALIFIED)
package com.cern.decenter.dependencygraph.model;

import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlSchema;