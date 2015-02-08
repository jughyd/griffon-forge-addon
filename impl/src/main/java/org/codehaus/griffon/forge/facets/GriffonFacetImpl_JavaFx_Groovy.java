package org.codehaus.griffon.forge.facets;

import org.codehaus.griffon.types.FrameworkTypes;
import org.codehaus.griffon.types.LanguageTypes;
import org.jboss.forge.addon.projects.dependencies.DependencyInstaller;

import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Configures the project to use Griffon, JavaFx frameworks and Groovy Language
 * Created by buddha on 2/8/15.
 */
public class GriffonFacetImpl_JavaFx_Groovy extends AbstractGriffonFacet {

    private static Logger log = Logger.getLogger(GriffonFacetImpl_JavaFx_Groovy.class.getName());

    @Inject
    public GriffonFacetImpl_JavaFx_Groovy(DependencyInstaller installer) {
        super(installer, FrameworkTypes.JavaFx, LanguageTypes.Groovy);

        if (log.isLoggable(Level.FINE))
            log.fine("GriffonFacetImpl_JavaFx_Groovy is instantiated");
    }

    @Override
    public boolean install() {
        throw new RuntimeException("Facet not yet implemented");
    }

}
