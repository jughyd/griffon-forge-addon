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


    private String topLevelPackage;
    private String simplename;

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
        createSrcFolder(directoryResource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createSrcFolder(DirectoryResource rootDir) throws IOException {
        DirectoryResource srcDir = rootDir.getOrCreateChildDirectory("src");
        DirectoryResource javaDocDir = srcDir.getOrCreateChildDirectory("javadoc");
        DirectoryResource mainDir = srcDir.getOrCreateChildDirectory("main");
        DirectoryResource mediaDir = srcDir.getOrCreateChildDirectory("media");
        DirectoryResource testDir = srcDir.getOrCreateChildDirectory("test");

        DirectoryResource resourcesDir = javaDocDir.getOrCreateChildDirectory("resources");
        DirectoryResource cssDir = resourcesDir.getOrCreateChildDirectory("css");
        DirectoryResource imgDir = resourcesDir.getOrCreateChildDirectory("img");

        copyFileFromTemplates(cssDir,
                "stylesheet.css",
                "javafx-java/src/javadoc/resources/css/stylesheet.css");

        copyFileFromTemplates(imgDir,
                "background.gif",
                "javafx-java/src/javadoc/resources/img/background.gif");

        copyFileFromTemplates(imgDir,
                "griffon.ico",
                "javafx-java/src/javadoc/resources/img/griffon.ico");

        copyFileFromTemplates(imgDir,
                "tab.gif",
                "javafx-java/src/javadoc/resources/img/tab.gif");

        copyFileFromTemplates(imgDir,
                "titlebar.gif",
                "javafx-java/src/javadoc/resources/img/titlebar.gif");

        copyFileFromTemplates(imgDir,
                "titlebar_end.gif",
                "javafx-java/src/javadoc/resources/img/titlebar_end.gif");

        DirectoryResource izpackDir = mainDir.getOrCreateChildDirectory("izpack");
        DirectoryResource izpackResourcesDir = izpackDir.getOrCreateChildDirectory("resources");
        DirectoryResource javaDir = mainDir.getOrCreateChildDirectory("java");
        DirectoryResource mainResourcesDir = mainDir.getOrCreateChildDirectory("resources");

        copyFileFromTemplates(izpackDir,
                "install.xml",
                "javafx-java/src/main/izpack/install.xml");

        copyFileFromTemplates(izpackResourcesDir,
                "asl2.html",
                "javafx-java/src/main/izpack/resources/asl2.html");

        copyFileFromTemplates(izpackResourcesDir,
                "emptyShortcutSpec.xml",
                "javafx-java/src/main/izpack/resources/emptyShortcutSpec.xml");

        copyFileFromTemplates(izpackResourcesDir,
                "griffon-logo.png",
                "javafx-java/src/main/izpack/resources/griffon-logo.png");

        copyFileFromTemplates(izpackResourcesDir,
                "griffon-splash.png",
                "javafx-java/src/main/izpack/resources/griffon-splash.png");

        copyFileFromTemplates(izpackResourcesDir,
                "pre-uninstall.bat",
                "javafx-java/src/main/izpack/resources/pre-uninstall.bat");

        copyFileFromTemplates(izpackResourcesDir,
                "processSpec.xml",
                "javafx-java/src/main/izpack/resources/processSpec.xml");

        copyFileFromTemplates(izpackResourcesDir,
                "README.html",
                "javafx-java/src/main/izpack/resources/README.html");

        copyFileFromTemplates(izpackResourcesDir,
                "RegistrySpec.xml",
                "javafx-java/src/main/izpack/resources/RegistrySpec.xml");

        copyFileFromTemplates(izpackResourcesDir,
                "target_unix.txt",
                "javafx-java/src/main/izpack/resources/target_unix.txt");

        copyFileFromTemplates(izpackResourcesDir,
                "unixShortcutSpec.xml",
                "javafx-java/src/main/izpack/resources/unixShortcutSpec.xml");

        copyFileFromTemplates(izpackResourcesDir,
                "winShortcutSpec.xml",
                "javafx-java/src/main/izpack/resources/winShortcutSpec.xml");

        Map<String, String> variables = new HashMap<String, String>();
        variables.put("toppackage",topLevelPackage);
        DirectoryResource dir = createTopLevelPackageStructure(javaDir,topLevelPackage);
        processTemplate(dir, "ApplicationEventHandler.java", "javafx-java/src/main/java/ApplicationEventHandler.java", variables);
        processTemplate(dir, "ApplicationModule.java", "javafx-java/src/main/java/ApplicationModule.java", variables);
        processTemplate(dir, "Launcher.java", "javafx-java/src/main/java/Launcher.java", variables);

        copyFileFromTemplates(mainResourcesDir,
                "log4j.properties",
                "javafx-java/src/main/resources/log4j.properties");

        copyFileFromTemplates(mediaDir,
                "griffon.icns",
                "src/media/griffon.icns");
        copyFileFromTemplates(mediaDir,
                "griffon.ico",
                "src/media/griffon.ico");
        copyFileFromTemplates(mediaDir,
                "griffon.png",
                "src/media/griffon.png");
        copyFileFromTemplates(mediaDir,
                "griffon-icon-16x16.png",
                "src/media/griffon-icon-16x16.png");
        copyFileFromTemplates(mediaDir,
                "griffon-icon-24x24.png",
                "src/media/griffon-icon-24x24.png");
        copyFileFromTemplates(mediaDir,
                "griffon-icon-32x32.png",
                "src/media/griffon-icon-32x32.png");
        copyFileFromTemplates(mediaDir,
                "griffon-icon-48x48.png",
                "src/media/griffon-icon-48x48.png");
        copyFileFromTemplates(mediaDir,
                "griffon-icon-64x64.png",
                "src/media/griffon-icon-64x64.png");
        copyFileFromTemplates(mediaDir,
                "griffon-icon-128x128.png",
                "src/media/griffon-icon-128x128.png");
        copyFileFromTemplates(mediaDir,
                "griffon-icon-256x256.png",
                "src/media/griffon-icon-256x256.png");

        DirectoryResource testsJavaDir = testDir.getOrCreateChildDirectory("java");
        DirectoryResource testsResourcesDir = testDir.getOrCreateChildDirectory("resources");
        dir = createTopLevelPackageStructure(testsJavaDir,topLevelPackage);

        variables.put("simplename",simplename);
        processTemplate(dir, simplename+"ControllerTest.java", "javafx-java/src/tests/ControllerTest.java.flt", variables);


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
        DirectoryResource lifeCycleDir = griffonAppDir.getOrCreateChildDirectory("lifecycle");
        DirectoryResource modelsDir = griffonAppDir.getOrCreateChildDirectory("models");
        DirectoryResource resourcesDir = griffonAppDir.getOrCreateChildDirectory("resources");
        DirectoryResource servicesDir = griffonAppDir.getOrCreateChildDirectory("services");
        DirectoryResource viewsDir = griffonAppDir.getOrCreateChildDirectory("views");

        Map<String, String> variables = new HashMap<String, String>();
        variables.put("projectname",project.getRoot().getName());

        // TODO this can be even improved by changing the letter after - or _ to capital Case
        simplename = project.getRoot().getName().replaceAll("[^A-Za-z0-9]","");
        char first = Character.toUpperCase(simplename.charAt(0));
        simplename = first + simplename.substring(1);
        variables.put("simplename",simplename);

        MetadataFacet metadataFacet = project.getFacet(MetadataFacet.class);
        topLevelPackage = metadataFacet.getProjectGroupName();
        if(topLevelPackage == null || topLevelPackage.length() == 0) {
            topLevelPackage = "org.example";
        }
        variables.put("toppackage",topLevelPackage);
        processTemplate(confDir, "Config.java", "javafx-java/griffon-app/conf/Config.java.ftl", variables);

        DirectoryResource dir = createTopLevelPackageStructure(controllersDir, topLevelPackage);

        processTemplate(dir, simplename+"Controller.java", "javafx-java/griffon-app/controllers/Controller.java.ftl", variables);

        processTemplate(lifeCycleDir,"Initialize.java", "javafx-java/griffon-app/lifecycle/Initialize.java", variables);

        copyFileFromTemplates(i18nDir,
                "messages.properties",
                "griffon-app/i18n/messages.properties");

        dir = createTopLevelPackageStructure(modelsDir, topLevelPackage);

        processTemplate(dir, simplename+"Model.java", "javafx-java/griffon-app/models/Model.java.ftl", variables);

        dir = createTopLevelPackageStructure(viewsDir, topLevelPackage);


        processTemplate(dir, simplename+"View.java", "javafx-java/griffon-app/views/View.java.ftl", variables);

        dir = createTopLevelPackageStructure(resourcesDir, topLevelPackage);

        processTemplate(dir, simplename + ".fxml", "javafx-java/griffon-app/resources/fxml.flt", variables);

        copyFileFromTemplates(resourcesDir,
                "application.properties",
                "griffon-app/resources/application.properties");

        copyFileFromTemplates(resourcesDir,
                "griffon.png",
                "griffon-app/resources/griffon.png");
        copyFileFromTemplates(resourcesDir,
                "griffon-icon-16x16.png",
                "griffon-app/resources/griffon-icon-16x16.png");
        copyFileFromTemplates(resourcesDir,
                "griffon-icon-24x24.png",
                "griffon-app/resources/griffon-icon-24x24.png");
        copyFileFromTemplates(resourcesDir,
                "griffon-icon-32x32.png",
                "griffon-app/resources/griffon-icon-32x32.png");
        copyFileFromTemplates(resourcesDir,
                "griffon-icon-48x48.png",
                "griffon-app/resources/griffon-icon-48x48.png");
        copyFileFromTemplates(resourcesDir,
                "griffon-icon-64x64.png",
                "griffon-app/resources/griffon-icon-64x64.png");
        copyFileFromTemplates(resourcesDir,
                "griffon-icon-128x128.png",
                "griffon-app/resources/griffon-icon-128x128.png");
        copyFileFromTemplates(resourcesDir,
                "griffon-icon-256x256.png",
                "griffon-app/resources/griffon-icon-256x256.png");
        copyFileFromTemplates(resourcesDir,
                "resources.properties",
                "griffon-app/resources/resources.properties");
    }




}
