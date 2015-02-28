package org.codehaus.griffon.forge.utils;

import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.resource.DirectoryResource;
import org.jboss.forge.addon.resource.FileResource;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.addon.resource.URLResource;
import org.jboss.forge.addon.templates.Template;
import org.jboss.forge.addon.templates.TemplateFactory;
import org.jboss.forge.addon.templates.freemarker.FreemarkerTemplate;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Created by buddha on 2/28/15.
 */
public abstract class LanguageFrameworkInjector {

    protected Project project;

    ResourceFactory resourceFactory;

    TemplateFactory templateFactory;

    public LanguageFrameworkInjector(Project project, ResourceFactory resourceFactory, TemplateFactory templateFactory){
        this.project = project;
        this.resourceFactory = resourceFactory;
        this.templateFactory = templateFactory;
    }

    public void createFolders() throws IOException {
        DirectoryResource directoryResource = (DirectoryResource) project.getRoot();
        createConfigFolder(directoryResource);
        createGriffonAppFolder(directoryResource);
        createMavenFolder(directoryResource);
    }

    void processTemplate(DirectoryResource targetDirectory, String targetFileName, String templateFilePath, Map templateVariables) throws IOException {
        FileResource projectstartupsh = (FileResource) targetDirectory.getChild(targetFileName);
        projectstartupsh.createNewFile();

        URL projectstartuptemplateurl = getClass().getResource("/templates" + File.separator + templateFilePath);
        URLResource projectstartuptemplateresource = resourceFactory.create(projectstartuptemplateurl).reify(URLResource.class);
        Template template = templateFactory.create(projectstartuptemplateresource, FreemarkerTemplate.class);

        projectstartupsh.setContents(template.process(templateVariables));
    }

    /**
     * Copies the given file from templates folder to the target directory
     * @param targetDirectory
     * @param targetFileName
     * @param sourceFileName
     * @throws java.io.IOException
     */
    private void copyFileFromTemplates(DirectoryResource targetDirectory, String targetFileName, String sourceFileName) throws IOException {
        URL checkStyleXmlSourceUrl = getClass().getResource("/templates" + File.separator +sourceFileName);
        FileResource checkStyleXmlTarget = (FileResource) targetDirectory.getChild(targetFileName);

        if(checkStyleXmlTarget.exists())
            checkStyleXmlTarget.delete();

        Files.copy(checkStyleXmlSourceUrl.openStream(), Paths.get(checkStyleXmlTarget.getFullyQualifiedName()));
    }

    public abstract void createConfigFolder(DirectoryResource directoryResource) throws IOException;
    public abstract void createMavenFolder(DirectoryResource directoryResource) throws IOException;
    public abstract void createGriffonAppFolder(DirectoryResource directoryResource) throws IOException;
}
