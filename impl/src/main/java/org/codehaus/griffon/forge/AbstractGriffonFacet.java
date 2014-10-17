package org.codehaus.griffon.forge;

import javax.inject.Inject;

import org.jboss.forge.addon.facets.AbstractFacet;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectFacet;
import org.jboss.forge.addon.projects.dependencies.DependencyInstaller;

public abstract class AbstractGriffonFacet extends AbstractFacet<Project>
		implements ProjectFacet, GriffonFacet {

	private final DependencyInstaller installer;

	@Inject
	public AbstractGriffonFacet(final DependencyInstaller installer) {
		this.installer = installer;
	}

	@Override
	public boolean install() {

		return true;
	}

	@Override
	public boolean isInstalled() {
		return true;
	}

	@Override
	public String toString() {
		return getVersion().toString();
	}

}