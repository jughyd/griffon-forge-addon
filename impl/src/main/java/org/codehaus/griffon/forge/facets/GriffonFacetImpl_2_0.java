package org.codehaus.griffon.forge.facets;

import org.jboss.forge.addon.projects.dependencies.DependencyInstaller;
import org.jboss.forge.furnace.versions.SingleVersion;
import org.jboss.forge.furnace.versions.Version;

import javax.inject.Inject;
import java.io.IOException;
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

    @Override
    public boolean install() {
        try {
            createFolders();
        } catch (IOException e) {
            log.info(getExceptionAsString(e));
            return false;
        }
        addDependencies();
        addPlugins();
        return true;
    }


    @Override
    protected void createFolders() throws IOException {
        super.createFolders();

    }

    @Override
    protected void addDependencies() {
        super.addDependencies();
        addDependency(GRIFFON_CORE_COMPILE, "2.0.0");
        addDependency(GRIFFON_JAVAFX, "2");
        addDependency(GRIFFON_GUICE, "2");
        addDependency(GRIFFON_CORE_TEST, "2");
        addDependency(GROOVY_ALL, "2");
    }

    @Override
    public Version getVersion() {
        return new SingleVersion("2.0");
    }
}