package org.codehaus.griffon.forge;

import org.codehaus.griffon.types.FrameworkTypes;
import org.codehaus.griffon.types.LanguageTypes;
import org.jboss.forge.addon.projects.ProjectFacet;
import org.jboss.forge.furnace.versions.Version;

public interface GriffonFacet extends ProjectFacet {

    Version getVersion();

    void setFramework(FrameworkTypes framework);

    void setLanguage(LanguageTypes language);
}