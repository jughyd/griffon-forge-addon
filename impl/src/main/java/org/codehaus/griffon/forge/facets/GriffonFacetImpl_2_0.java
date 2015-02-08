package org.codehaus.griffon.forge.facets;

import javax.inject.Inject;

import org.codehaus.griffon.forge.facets.AbstractGriffonFacet;
import org.jboss.forge.addon.projects.dependencies.DependencyInstaller;
import org.jboss.forge.furnace.versions.SingleVersion;
import org.jboss.forge.furnace.versions.Version;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GriffonFacetImpl_2_0 extends AbstractGriffonFacet {

	private static Logger log = Logger.getLogger(GriffonFacetImpl_2_0.class.getName());

	@Inject
	public GriffonFacetImpl_2_0(DependencyInstaller installer) {
		super(installer);
		if(log.isLoggable(Level.FINE))
			log.fine("GriffonFacetImpl_2_0 is instantiated");
	}

	@Override
	public Version getVersion() {
		return new SingleVersion("2.0");
	}


}