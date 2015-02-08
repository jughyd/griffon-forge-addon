package org.codehaus.griffon.forge.facets;

import org.jboss.forge.addon.projects.dependencies.DependencyInstaller;
import org.jboss.forge.furnace.versions.SingleVersion;
import org.jboss.forge.furnace.versions.Version;

import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by buddha on 2/8/15.
 */
public class GriffonFacetImpl_JavaFx_Java extends AbstractGriffonFacet {

    private static Logger log = Logger.getLogger(GriffonFacetImpl_JavaFx_Java.class.getName());

    @Inject
    public GriffonFacetImpl_JavaFx_Java(DependencyInstaller installer) {
        super(installer);
        if(log.isLoggable(Level.FINE))
            log.fine("GriffonFacetImpl_JavaFx_Java is instantiated");
    }

    @Override
    public boolean install() {
        createFolders();
        addDependencies();
        return true;
    }

    @Override
    protected void createFolders() {
        super.createFolders();

    }

    @Override
    protected void addDependencies() {
        super.addDependencies();
        addDependency(GRIFFON_JAVAFX);
    }

    @Override
    public Version getVersion() {
        return new SingleVersion("JAVAFX-JAVA");
    }
}
