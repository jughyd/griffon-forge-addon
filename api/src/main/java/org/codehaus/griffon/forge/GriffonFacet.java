package org.codehaus.griffon.forge;

import org.codehaus.griffon.types.FrameworkTypes;
import org.codehaus.griffon.types.LanguageTypes;
import org.jboss.forge.addon.projects.ProjectFacet;

public interface GriffonFacet extends ProjectFacet {

    void setFramework(FrameworkTypes framework);

    void setLanguage(LanguageTypes language);
}