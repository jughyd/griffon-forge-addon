package org.codehaus.griffon.forge.facets;

import org.jboss.forge.addon.projects.dependencies.DependencyInstaller;

import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GriffonFacetImpl_2_0 extends AbstractGriffonFacet {

    private static Logger log = Logger.getLogger(GriffonFacetImpl_2_0.class.getName());

    @Inject
    public GriffonFacetImpl_2_0(DependencyInstaller installer) {
        super(installer);
        if (log.isLoggable(Level.FINE))
            log.fine("GriffonFacetImpl_2_0 is instantiated");

    }

}