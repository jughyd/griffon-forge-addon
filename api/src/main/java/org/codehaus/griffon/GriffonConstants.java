package org.codehaus.griffon;

import org.jboss.forge.addon.dependencies.DependencyRepository;

/**
 * Constants used in this project.
 *
 * @author Rajmahendra Hegde <rajmahendra@gmail.com>
 */
public interface GriffonConstants {

    final DependencyRepository dependencyRepository = new DependencyRepository("jcenter", "http://jcenter.bintray.com/");

    final String GRIFFON_JAVAFX = "org.codehaus.griffon:griffon-javafx";
    final String GRIFFON_GUICE = "org.codehaus.griffon:griffon-guice";
    final String GRIFFON_CORE_TEST = "org.codehaus.griffon:griffon-core-test";
    final String GROOVY_ALL = "org.codehaus.groovy:groovy-all";
    final String LOG4J = "log4j:log4j";
    final String SLF4J_LOG4J12 = "org.slf4j:slf4j-log4j12";
    final String SPOCK_CORE = "org.spockframework:spock-core";
    final String JUNIT = "junit:junit";
    final String GRIFFON_CORE_COMPILE = "org.codehaus.griffon:griffon-core-compile";

    final String JAVAFX_MAVEN_PLUGIN = "com.zenjava:javafx-maven-plugin:2.0";
    final String MAVEN_ANTRUN_PLUGIN = "org.apache.maven.plugins:maven-antrun-plugin:1.8";
    final String MAVEN_ASSEMBLY_PLUGIN = "org.apache.maven.plugins:maven-assembly-plugin:2.5.3";
    final String MAVEN_DEPENDENCY_PLUGIN = "org.apache.maven.plugins:maven-dependency-plugin:2.1";
    final String MAVEN_RELEASE_PLUGIN = "org.apache.maven.plugins:maven-release-plugin:2.0";
    final String MAVEN_CLEAN_PLUGIN = "org.apache.maven.plugins:maven-clean-plugin:2.4.1";

}
