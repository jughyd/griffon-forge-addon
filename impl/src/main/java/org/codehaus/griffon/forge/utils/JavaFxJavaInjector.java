package org.codehaus.griffon.forge.utils;

import org.jboss.forge.addon.maven.projects.MavenFacet;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.facets.MetadataFacet;
import org.jboss.forge.addon.projects.facets.PackagingFacet;
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

        URL headerFileSourceUrl = getClass().getResource(TEMPLATE_DIR + "config/HEADER.ftl");
        URLResource headerTemplateResource = resourceFactory.create(headerFileSourceUrl).reify(URLResource.class);
        Template template = templateFactory.create(headerTemplateResource, FreemarkerTemplate.class);

        Map<String, Object> templateContext = new HashMap<String, Object>();
        templateContext.put("yearvariable", "${year}");
        headerFileTarget.setContents(template.process(templateContext));

        // simply copying the files as there is no template processing required
        copyFileFromTemplates(checkStyleDirectory,
                "checkstyle.xml",
                "config/checkstyle/checkstyle.xml");

        copyFileFromTemplates(condenarcDirectory,
                "codenarc.groovy",
                "config/codenarc/codenarc.groovy");

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createMavenFolder(DirectoryResource rootDir) throws IOException {
        DirectoryResource mavenDir = rootDir.getOrCreateChildDirectory("maven");
        DirectoryResource distributionDir = mavenDir.getOrCreateChildDirectory("distribution");
        DirectoryResource binDir = distributionDir.getOrCreateChildDirectory("bin");

        copyFileFromTemplates(mavenDir,
                "ant-macros.xml",
                "maven/ant-macros.xml");

        copyFileFromTemplates(mavenDir,
                "assembly-descriptor.xml",
                "maven/assembly-descriptor.xml");

        copyFileFromTemplates(mavenDir,
                "post-site.xml",
                "maven/post-site.xml");

        copyFileFromTemplates(mavenDir,
                "prepare-izpack.xml",
                "maven/prepare-izpack.xml");

        copyFileFromTemplates(mavenDir,
                "process-resources.xml",
                "maven/process-resources.xml");

        String projectname = project.getRoot().getName();

        Map<String, Object> templateContext = new HashMap<String, Object>();
        templateContext.put("projectname", projectname);
        templateContext.put("JVM_OPTS", "${JVM_OPTS[@]}");

        String templatePath = "javafx-java/maven/distribution/bin/project.ftl";
        processTemplate(binDir, projectname, templatePath, templateContext);

        String batTemplatePath = "javafx-java/maven/distribution/bin/project.bat.ftl";
        processTemplate(binDir, projectname + ".bat", batTemplatePath, templateContext);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createGriffonAppFolder(DirectoryResource rootDir) throws IOException {
        DirectoryResource griffonAppDir = rootDir.getOrCreateChildDirectory("griffon-app");
        DirectoryResource confDir = griffonAppDir.getOrCreateChildDirectory("conf");
        DirectoryResource controllersDir = griffonAppDir.getOrCreateChildDirectory("controllers");
        DirectoryResource i18nDir = griffonAppDir.getOrCreateChildDirectory("i18n");
        DirectoryResource lifeStyleDir = griffonAppDir.getOrCreateChildDirectory("lifestyle");
        DirectoryResource modelsDir = griffonAppDir.getOrCreateChildDirectory("models");
        DirectoryResource resourcesDir = griffonAppDir.getOrCreateChildDirectory("resources");
        DirectoryResource servicesDir = griffonAppDir.getOrCreateChildDirectory("services");
        DirectoryResource viewsDir = griffonAppDir.getOrCreateChildDirectory("views");

        Map<String, String> variables = new HashMap<String, String>();
        variables.put("projectname",project.getRoot().getName());

        // TODO this can be even improved by changing the letter after - or _ to capital Case
        String simplename = project.getRoot().getName().replaceAll("[^A-Za-z0-9]","");
        char first = Character.toUpperCase(simplename.charAt(0));
        simplename = first + simplename.substring(1);
        variables.put("simplename",simplename);

        MetadataFacet metadataFacet = project.getFacet(MetadataFacet.class);
        String topLevelPackage = metadataFacet.getProjectGroupName();
        if(topLevelPackage == null || topLevelPackage.length() == 0) {
            topLevelPackage = "org.example";
        }
        variables.put("toppackage",topLevelPackage);
        processTemplate(confDir, "Config.java", "javafx-java/griffon-app/conf/Config.java.ftl", variables);

        DirectoryResource dir = controllersDir;
        for(String level: topLevelPackage.split("\\."))
            dir = dir.getOrCreateChildDirectory(level);

        processTemplate(dir, simplename+"Controller.java", "javafx-java/griffon-app/controllers/Controller.java.ftl", variables);

        copyFileFromTemplates(i18nDir,
                "messages.properties",
                "griffon-app/i18n/messages.properties");



    }


}
