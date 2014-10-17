package org.codehaus.griffon.forge;

import javax.inject.Inject;

import org.jboss.forge.addon.projects.dependencies.DependencyInstaller;
import org.jboss.forge.furnace.versions.SingleVersion;
import org.jboss.forge.furnace.versions.Version;

public class GriffonFacetImpl_2_0 extends AbstractGriffonFacet {

	@Inject
	public GriffonFacetImpl_2_0(DependencyInstaller installer) {
		super(installer);
	}

	@Override
	public Version getVersion() {
		return new SingleVersion("2.0");
	}
}