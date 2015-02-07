package org.codehaus.griffon.forge;

import javax.inject.Inject;

import org.codehaus.griffon.types.FrameworkTypes;
import org.codehaus.griffon.types.LanguageTypes;
import org.jboss.forge.addon.dependencies.Coordinate;
import org.jboss.forge.addon.dependencies.DependencyQuery;
import org.jboss.forge.addon.dependencies.DependencyResolver;
import org.jboss.forge.addon.dependencies.builder.DependencyBuilder;
import org.jboss.forge.addon.dependencies.builder.DependencyQueryBuilder;
import org.jboss.forge.addon.dependencies.util.NonSnapshotDependencyFilter;
import org.jboss.forge.addon.facets.AbstractFacet;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectFacet;
import org.jboss.forge.addon.projects.dependencies.DependencyInstaller;
import org.jboss.forge.addon.resource.DirectoryResource;

import java.util.List;

public abstract class AbstractGriffonFacet extends AbstractFacet<Project>
		implements ProjectFacet, GriffonFacet {

	private final DependencyInstaller installer;

	FrameworkTypes framework;
	LanguageTypes language;

	@Inject
	public AbstractGriffonFacet(final DependencyInstaller installer) {
		this.installer = installer;
	}

	@Override
	public boolean install() {
		createFolders();
		addDependencies();
		return true;
	}

	@Override
	public boolean isInstalled()
	{
//		Project selectedProject = getFaceted();
//		selectedProject.ge
		return false;
	}

	@Override
	public String toString() {
		return getVersion().toString();
	}

	private void createFolders(){
		Project selectedProject = getFaceted();
		DirectoryResource directoryResource = (DirectoryResource)selectedProject.getRoot();
		directoryResource.getOrCreateChildDirectory("config");

	}

	private void addDependencies(){

	}

	@Override
	public FrameworkTypes getFramework() {
		return framework;
	}

	@Override
	public void setFramework(FrameworkTypes framework) {
		this.framework = framework;
	}

	@Override
	public LanguageTypes getLanguage() {
		return language;
	}

	@Override
	public void setLanguage(LanguageTypes language) {
		this.language = language;
	}
}