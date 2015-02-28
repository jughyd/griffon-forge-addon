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
import java.util.HashMap;
import java.util.Map;

/**
 * @author jbuddha
 */
public class JavaFxJavaInjector extends LanguageFrameworkInjector {


    public JavaFxJavaInjector(Project project, ResourceFactory resourceFactory, TemplateFactory templateFactory) {
        super(project, resourceFactory, templateFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createFolders() throws IOException {
        DirectoryResource directoryResource = (DirectoryResource) project.getRoot();
        createConfigFolder(directoryResource);
        createGriffonAppFolder(directoryResource);
        createMavenFolder(directoryResource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createConfigFolder(DirectoryResource rootDir) throws IOException {
        DirectoryResource configDirectory = rootDir.getOrCreateChildDirectory("config");
        DirectoryResource checkStyleDirectory = rootDir.getOrCreateChildDirectory("config/checkstyle");
        DirectoryResource condenarcDirectory = rootDir.getOrCreateChildDirectory("config/codenarc");

        FileResource headerFileTarget = (FileResource) configDirectory.getChild("HEADER");
        headerFileTarget.createNewFile();

        URL headerFileSourceUrl = getClass().getResource("/templates" + File.separator + "config" + File.separator + "HEADER.ftl");
        URLResource headerTemplateResource = resourceFactory.create(headerFileSourceUrl).reify(URLResource.class);
        Template template = templateFactory.create(headerTemplateResource, FreemarkerTemplate.class);

        Map<String, Object> templateContext = new HashMap<String, Object>();
        templateContext.put("yearvariable", "${year}");
        headerFileTarget.setContents(template.process(templateContext));

        // simply copying the files as there is no template processing required
        copyFileFromTemplates(checkStyleDirectory,
                "checkstyle.xml",
                "config" + File.separator + "checkstyle" + File.separator + "checkstyle.xml");

        copyFileFromTemplates(condenarcDirectory,
                "codenarc.groovy",
                "config" + File.separator + "codenarc" + File.separator + "codenarc.groovy");

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createMavenFolder(DirectoryResource rootDir) throws IOException {
        DirectoryResource mavenDir = rootDir.getOrCreateChildDirectory("maven");
        DirectoryResource distributionDir = rootDir.getOrCreateChildDirectory("maven/distribution");
        DirectoryResource binDir = rootDir.getOrCreateChildDirectory("maven/distribution/bin");

        copyFileFromTemplates(mavenDir,
                "ant-macros.xml",
                "maven" + File.separator + File.separator + "ant-macros.xml");

        copyFileFromTemplates(mavenDir,
                "assembly-descriptor.xml",
                "maven" + File.separator + File.separator + "assembly-descriptor.xml");

        copyFileFromTemplates(mavenDir,
                "post-site.xml",
                "maven" + File.separator + File.separator + "post-site.xml");

        copyFileFromTemplates(mavenDir,
                "prepare-izpack.xml",
                "maven" + File.separator + File.separator + "prepare-izpack.xml");

        copyFileFromTemplates(mavenDir,
                "process-resources.xml",
                "maven" + File.separator + File.separator + "process-resources.xml");

        String projectname = project.getRoot().getName();

        Map<String, Object> templateContext = new HashMap<String, Object>();
        templateContext.put("projectname", projectname);
        templateContext.put("JVM_OPTS", "${JVM_OPTS[@]}");

        String templatePath = "javafx-java" + File.separator + "maven" + File.separator + "distribution" + File.separator + "bin" + File.separator + "project.ftl";
        processTemplate(binDir, projectname, templatePath, templateContext);

        String batTemplatePath = "javafx-java" + File.separator + "maven" + File.separator + "distribution" + File.separator + "bin" + File.separator + "project.bat.ftl";
        processTemplate(binDir, projectname + ".bat", batTemplatePath, templateContext);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createGriffonAppFolder(DirectoryResource rootDir) {
        rootDir.getOrCreateChildDirectory("griffon-app");
        rootDir.getOrCreateChildDirectory("griffon-app/conf");
        rootDir.getOrCreateChildDirectory("griffon-app/cotrollers");
        rootDir.getOrCreateChildDirectory("griffon-app/i18n");
        rootDir.getOrCreateChildDirectory("griffon-app/lifestyle");
        rootDir.getOrCreateChildDirectory("griffon-app/models");
        rootDir.getOrCreateChildDirectory("griffon-app/resources");
        rootDir.getOrCreateChildDirectory("griffon-app/services");
        rootDir.getOrCreateChildDirectory("griffon-app/views");
    }


}
