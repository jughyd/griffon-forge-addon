package org.codehaus.griffon.forge.utils;

import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.resource.DirectoryResource;
import org.jboss.forge.addon.resource.FileResource;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.addon.resource.URLResource;
import org.jboss.forge.addon.templates.Template;
import org.jboss.forge.addon.templates.TemplateFactory;
import org.jboss.forge.addon.templates.freemarker.FreemarkerTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jbuddha on 2/28/15.
 */
public class JavaFxGroovyInjector extends LanguageFrameworkInjector {


    public JavaFxGroovyInjector(Project project, ResourceFactory resourceFactory, TemplateFactory templateFactory) {
        super(project,resourceFactory,templateFactory);
    }



    /**
     * Creates the config folder and its files
     * @param rootDir
     * @throws java.io.IOException
     */
    public void createConfigFolder(DirectoryResource rootDir) throws IOException {
       throw new RuntimeException("Not Implemented Yet");
    }

    public void createMavenFolder(DirectoryResource rootDir) throws IOException {
        throw new RuntimeException("Not Implemented Yet");

    }

    public void createGriffonAppFolder(DirectoryResource rootDir)
    {
        throw new RuntimeException("Not Implemented Yet");
    }


}
