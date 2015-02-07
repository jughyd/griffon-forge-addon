package org.codehaus.griffon.forge;

import org.codehaus.griffon.types.FrameworkTypes;
import org.codehaus.griffon.types.LanguageTypes;
import org.jboss.forge.addon.projects.ProjectFacet;
import org.jboss.forge.furnace.versions.Version;

public interface GriffonFacet extends ProjectFacet {


	Version getVersion();

	FrameworkTypes getFramework() ;

	void setFramework(FrameworkTypes framework);

	LanguageTypes getLanguage();

	void setLanguage(LanguageTypes language);
}