package org.codehaus.griffon.forge;

import org.jboss.forge.addon.projects.ProjectFacet;
import org.jboss.forge.furnace.versions.Version;

public interface GriffonFacet extends ProjectFacet {

	Version getVersion();
}