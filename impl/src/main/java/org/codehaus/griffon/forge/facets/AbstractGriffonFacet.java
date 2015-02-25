package org.codehaus.griffon.forge.facets;

import org.codehaus.griffon.GriffonConstants;
import org.codehaus.griffon.forge.GriffonFacet;
import org.codehaus.griffon.types.FrameworkTypes;
import org.codehaus.griffon.types.LanguageTypes;
import org.jboss.forge.addon.dependencies.Coordinate;
import org.jboss.forge.addon.dependencies.DependencyQuery;
import org.jboss.forge.addon.dependencies.DependencyRepository;
import org.jboss.forge.addon.dependencies.DependencyResolver;
import org.jboss.forge.addon.dependencies.builder.CoordinateBuilder;
import org.jboss.forge.addon.dependencies.builder.DependencyBuilder;
import org.jboss.forge.addon.dependencies.builder.DependencyQueryBuilder;
import org.jboss.forge.addon.dependencies.util.NonSnapshotDependencyFilter;
import org.jboss.forge.addon.facets.AbstractFacet;
import org.jboss.forge.addon.maven.plugins.ExecutionBuilder;
import org.jboss.forge.addon.maven.plugins.MavenPluginBuilder;
import org.jboss.forge.addon.maven.projects.MavenPluginFacet;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectFacet;
import org.jboss.forge.addon.projects.dependencies.DependencyInstaller;
import org.jboss.forge.addon.resource.*;
import org.jboss.forge.addon.templates.Template;
import org.jboss.forge.addon.templates.TemplateFactory;
import org.jboss.forge.addon.templates.freemarker.FreemarkerTemplate;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractGriffonFacet extends AbstractFacet<Project>
        implements ProjectFacet, GriffonFacet, GriffonConstants {

    private DependencyBuilder builder;
    private DependencyInstaller installer;

    protected FrameworkTypes framework = null;
    protected LanguageTypes language = null;

    @Inject
    private DependencyResolver dependencyResolver;

    @Inject
    ResourceFactory resourceFactory;

    @Inject
    TemplateFactory templateFactory;

    @Inject
    public AbstractGriffonFacet(final DependencyInstaller installer) {
        this.installer = installer;
    }

    @Override
    public boolean install() {
        if (framework == null || language == null)
            throw new RuntimeException("Either Framework or Language is not set");
        return true;
    }

    @Override
    public boolean isInstalled() {
        return false;
    }

    @Override
    public String toString() {
        return getVersion().toString();
    }

    protected void createFolders() throws IOException {
        Project selectedProject = getFaceted();
        DirectoryResource directoryResource = (DirectoryResource) selectedProject.getRoot();

        createConfigFolder(directoryResource);

        directoryResource.getOrCreateChildDirectory("griffon-app");
        directoryResource.getOrCreateChildDirectory("griffon-app/conf");
        directoryResource.getOrCreateChildDirectory("griffon-app/cotrollers");
        directoryResource.getOrCreateChildDirectory("griffon-app/i18n");
        directoryResource.getOrCreateChildDirectory("griffon-app/lifestyle");
        directoryResource.getOrCreateChildDirectory("griffon-app/models");
        directoryResource.getOrCreateChildDirectory("griffon-app/resources");
        directoryResource.getOrCreateChildDirectory("griffon-app/services");
        directoryResource.getOrCreateChildDirectory("griffon-app/views");

        createMavenFolder(directoryResource);
    }

    private void createMavenFolder(DirectoryResource rootDir) throws IOException {
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

        String projectname = getFaceted().getRoot().getName();

        FileResource projectstartupsh = (FileResource) binDir.getChild(projectname);
        projectstartupsh.createNewFile();

        URL projectstartuptemplateurl = getClass().getResource("/templates" + File.separator + "javafx-java"
                +File.separator + "maven" +File.separator + "distribution"+ File.separator + "bin"
                +File.separator+"project.ftl");
        URLResource projectstartuptemplateresource = resourceFactory.create(projectstartuptemplateurl).reify(URLResource.class);
        Template template = templateFactory.create(projectstartuptemplateresource, FreemarkerTemplate.class);

        Map<String, Object> templateContext = new HashMap<String,Object>();
        templateContext.put("projectname", projectname);
        templateContext.put("JVM_OPTS","${JVM_OPTS[@]}");
        projectstartupsh.setContents(template.process(templateContext));

        FileResource projectstartupbat = (FileResource) binDir.getChild(projectname+".bat");
        projectstartupbat.createNewFile();

        URL projectstartupbattemplateurl = getClass().getResource("/templates" + File.separator + "javafx-java"
                +File.separator + "maven" +File.separator + "distribution"+ File.separator + "bin"
                +File.separator+"project.bat.ftl");
        URLResource projectstartupbattemplateresource = resourceFactory.create(projectstartupbattemplateurl).reify(URLResource.class);
        Template template1 = templateFactory.create(projectstartupbattemplateresource, FreemarkerTemplate.class);

        projectstartupbat.setContents(template1.process(templateContext));
    }

    /**
     * Creates the config folder and its files
     * @param rootDir
     * @throws IOException
     */
    private void createConfigFolder(DirectoryResource rootDir) throws IOException {
        DirectoryResource configDirectory = rootDir.getOrCreateChildDirectory("config");
        DirectoryResource checkStyleDirectory = rootDir.getOrCreateChildDirectory("config/checkstyle");
        DirectoryResource condenarcDirectory = rootDir.getOrCreateChildDirectory("config/codenarc");

        FileResource headerFileTarget = (FileResource) configDirectory.getChild("HEADER");
        headerFileTarget.createNewFile();

        URL headerFileSourceUrl = getClass().getResource("/templates" + File.separator + "config"+File.separator+"HEADER.ftl");
        URLResource headerTemplateResource = resourceFactory.create(headerFileSourceUrl).reify(URLResource.class);
        Template template = templateFactory.create(headerTemplateResource, FreemarkerTemplate.class);

        Map<String, Object> templateContext = new HashMap<String,Object>();
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
     * Copies the given file from templates folder to the target directory
     * @param targetDirectory
     * @param targetFileName
     * @param sourceFileName
     * @throws IOException
     */
    private void copyFileFromTemplates(DirectoryResource targetDirectory, String targetFileName, String sourceFileName) throws IOException {
        URL checkStyleXmlSourceUrl = getClass().getResource("/templates" + File.separator +sourceFileName);
        FileResource checkStyleXmlTarget = (FileResource) targetDirectory.getChild(targetFileName);

        if(checkStyleXmlTarget.exists())
            checkStyleXmlTarget.delete();

        Files.copy(checkStyleXmlSourceUrl.openStream(), Paths.get(checkStyleXmlTarget.getFullyQualifiedName()));
    }

    protected void addDependencies() {
        builder = DependencyBuilder.create();
        addDependency(LOG4J);
        addDependency(SLF4J_LOG4J12);
        addDependency(SPOCK_CORE);
        addDependency(JUNIT);
    }

    protected void addPlugins() {
        addPlugin(JAVAFX_MAVEN_PLUGIN);
        addPlugin(MAVEN_ANTRUN_PLUGIN);
    }

    protected void addPlugin(String baseCoordinate) {
        Coordinate plugInCoordinate = CoordinateBuilder.create(baseCoordinate);
        MavenPluginFacet facet = getFaceted().getFacet(MavenPluginFacet.class);
        MavenPluginBuilder plugin = MavenPluginBuilder.create().setCoordinate(plugInCoordinate);
        facet.addPlugin(plugin);
    }

    protected void addPlugin(String baseCoordinate, String id, String phase, String goal) {
        Coordinate plugInCoordinate = CoordinateBuilder.create(baseCoordinate);
        MavenPluginFacet facet = getFaceted().getFacet(MavenPluginFacet.class);
        MavenPluginBuilder plugin = MavenPluginBuilder.create()
                            .setCoordinate(plugInCoordinate);
        ExecutionBuilder execution = ExecutionBuilder.create()
                                            .addGoal(goal)
                                            .setId(id)
                                            .setPhase(phase);
        plugin.addExecution(execution);
        facet.addPlugin(plugin);
    }

    protected void addDependency(String baseCoordinate) {
        DependencyQuery query = DependencyQueryBuilder
                .create(baseCoordinate)
                .setFilter(new NonSnapshotDependencyFilter())
                .setRepositories(dependencyRepository);
        List<Coordinate> coordinates = dependencyResolver.resolveVersions(query);
        builder.setCoordinate(coordinates.get(coordinates.size() - 1));

        installer.install(getFaceted(), builder);
    }

    protected void addDependency(String baseCoordinate, String majorVersion) {
        DependencyQuery query = DependencyQueryBuilder
                .create(baseCoordinate)
                .setFilter(new NonSnapshotDependencyFilter())
                .setRepositories(dependencyRepository);
        List<Coordinate> coordinates = dependencyResolver.resolveVersions(query);

        int i = 0;
        for(Coordinate coordinate : coordinates){
            if(coordinate.getVersion().startsWith(majorVersion))
                break;
            i++;
        }

        int v = i;
        for(; v < coordinates.size(); v++){
            if(!coordinates.get(v).getVersion().startsWith(majorVersion))
                break;
        }

        v--;

        builder.setCoordinate(coordinates.get(v));

        installer.install(getFaceted(), builder);
    }

    @Override
    public void setFramework(FrameworkTypes framework) {
        this.framework = framework;
    }

    @Override
    public void setLanguage(LanguageTypes language) {
        this.language = language;
    }

    /**
     * Returns the exception as a string so that it can be printed easily
     * @param e
     * @return
     */
    protected String getExceptionAsString(Throwable e){
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return e.getMessage() + System.lineSeparator() + sw.toString();
    }
}