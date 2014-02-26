dependencygraph
===============
This Project deals with finding Dependenies in a Project. Dependency is nothing but, it states about the interdependencies present between the other Projects. There are two types of Dependencies. 

* Explicit Dependency
* Implicit Dependency

Explicit depdendencys is something that is explicitly listed in a project. For eg. In POM File , there is a tag for Dependency.

<Dependencies>
  .
  .
  <dependency> ... project1 .... <dependency>
  <dependency> ... project2 .... <dependency>
  .
  .
  <dependency> ... project50 ... <dependency>
  .
  .
<Dependencies>

My Goal is to extract all those values present in <Dependencies> using RESTful API. 

===============

What is RESTful API ? 

  REST - REpresentation State Transfer
  API - Application Programming Interface
  
  To put it in simpler terms, it is just one another way how client-server communication occurs using JAVA. You try to send request and receive response from server in the form of XML/JSON. REST API acts as an interface and provides all methods needed by client for sending request and receiving response. 
  
  Thats why its called as HTTP API. 
  
  For sending request to server, client (Jersey) uses GET/POST methods and it receives the response back from server as XML semi-structured data. 
  
===============

In this project , I have used Jersey Client. 

Ok, what about the POM File? where and how can you access all pom files present in various projects? 
There is an API called MAVEN NEXUS-SONATYPE. 

So, we access NEXUS-SONATYPE API using Jersey Client and get the response back as XML data. This XML Data is then Parsed using JAXB Parser. We get the desired fields/tags from XML response.

JAXB has a technique called UNMARSHALLING. Using unmarshalling, we convert the extracted XML part into Java Object !!

So, there you go.. we have the Java object. Now, all what we need to do is, save the java Object into database. Here I have used Neo4J graphical database. But you can use MySQL or any other database. 

In JUnit test cases, I would have used Mockito and PowerMock, mainly for the purpose of Mocking the functionality of code and get the code coverage. To avoid PowerMock I would have introduced DependencyInjaction. 

In Future, I m planning to make use of BUILDER Design Pattern to attain OO Design. 

Thats all Folks !! :-)
