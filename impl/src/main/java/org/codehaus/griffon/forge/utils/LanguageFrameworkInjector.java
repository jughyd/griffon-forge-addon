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
import java.util.Map;

/**
 * @author jbuddha
 */
public abstract class LanguageFrameworkInjector {

    protected Project project;

    ResourceFactory resourceFactory;

    TemplateFactory templateFactory;

    public LanguageFrameworkInjector(Project project, ResourceFactory resourceFactory, TemplateFactory templateFactory) {
        this.project = project;
        this.resourceFactory = resourceFactory;
        this.templateFactory = templateFactory;
    }

    /**
     * Creates necessary files and folders
     *
     * @throws IOException
     */
    public void createFolders() throws IOException {
        DirectoryResource directoryResource = (DirectoryResource) project.getRoot();
        createConfigFolder(directoryResource);
        createGriffonAppFolder(directoryResource);
        createMavenFolder(directoryResource);

    }

    /**
     * Utility method to process an ftl file by replacing all the variables with the values provided in templateVariables
     * and copy the final file to the targetDirectory
     *
     * @param targetDirectory
     * @param targetFileName
     * @param templateFilePath
     * @param templateVariables
     * @throws IOException
     */
    void processTemplate(DirectoryResource targetDirectory, String targetFileName, String templateFilePath, Map templateVariables) throws IOException {
        FileResource targetResource = (FileResource) targetDirectory.getChild(targetFileName);
        targetResource.createNewFile();

        URL templateUrl = getClass().getResource("/templates" + File.separator + templateFilePath);
        URLResource templateResource = resourceFactory.create(templateUrl).reify(URLResource.class);
        Template template = templateFactory.create(templateResource, FreemarkerTemplate.class);

        targetResource.setContents(template.process(templateVariables));
    }

    /**
     * Copies the given file from templates folder to the target directory
     *
     * @param targetDirectory
     * @param targetFileName
     * @param sourceFileName
     * @throws java.io.IOException
     */
    protected void copyFileFromTemplates(DirectoryResource targetDirectory, String targetFileName, String sourceFileName) throws IOException {
        URL sourceUrl = getClass().getResource("/templates" + File.separator + sourceFileName);
        FileResource targetResource = (FileResource) targetDirectory.getChild(targetFileName);

        if (targetResource.exists())
            targetResource.delete();

        Files.copy(sourceUrl.openStream(), Paths.get(targetResource.getFullyQualifiedName()));
    }


    /**
     * Utility method to contain all the files inside config folder
     *
     * @param directoryResource
     * @throws IOException
     */
    abstract void createConfigFolder(DirectoryResource directoryResource) throws IOException;

    /**
     * Utility method to contain all the files inside maven folder
     *
     * @param directoryResource
     * @throws IOException
     */
    abstract void createMavenFolder(DirectoryResource directoryResource) throws IOException;

    /**
     * Utility method to contain all the files inside griffon-app folder
     *
     * @param directoryResource
     * @throws IOException
     */
    abstract void createGriffonAppFolder(DirectoryResource directoryResource) throws IOException;
}
