package org.codehaus.griffon.forge.utils;

import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.resource.DirectoryResource;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.addon.templates.TemplateFactory;

import java.io.IOException;

/**
 * @author jbuddha
 */
public class JavaFxGroovyInjector extends LanguageFrameworkInjector {


    public JavaFxGroovyInjector(Project project, ResourceFactory resourceFactory, TemplateFactory templateFactory) {
        super(project, resourceFactory, templateFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createConfigFolder(DirectoryResource rootDir) throws IOException {
        throw new RuntimeException("Not Implemented Yet");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createMavenFolder(DirectoryResource rootDir) throws IOException {
        throw new RuntimeException("Not Implemented Yet");

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createGriffonAppFolder(DirectoryResource rootDir) {
        throw new RuntimeException("Not Implemented Yet");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void createSrcFolder(DirectoryResource directoryResource) throws IOException {
        throw new RuntimeException("Not Implemented Yet");
    }


}
