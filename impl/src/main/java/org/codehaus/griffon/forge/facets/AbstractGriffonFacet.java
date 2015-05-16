package org.codehaus.griffon.forge.facets;

import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.apache.maven.model.Repository;
import org.codehaus.griffon.GriffonConstants;
import org.codehaus.griffon.forge.GriffonFacet;
import org.codehaus.griffon.forge.utils.JavaFxGroovyInjector;
import org.codehaus.griffon.forge.utils.JavaFxJavaInjector;
import org.codehaus.griffon.forge.utils.LanguageFrameworkInjector;
import org.codehaus.griffon.types.FrameworkTypes;
import org.codehaus.griffon.types.LanguageTypes;
import org.jboss.forge.addon.dependencies.Coordinate;
import org.jboss.forge.addon.dependencies.DependencyQuery;
import org.jboss.forge.addon.dependencies.DependencyResolver;
import org.jboss.forge.addon.dependencies.builder.CoordinateBuilder;
import org.jboss.forge.addon.dependencies.builder.DependencyBuilder;
import org.jboss.forge.addon.dependencies.builder.DependencyQueryBuilder;
import org.jboss.forge.addon.dependencies.util.NonSnapshotDependencyFilter;
import org.jboss.forge.addon.facets.AbstractFacet;
import org.jboss.forge.addon.maven.plugins.ExecutionBuilder;
import org.jboss.forge.addon.maven.plugins.MavenPluginBuilder;
import org.jboss.forge.addon.maven.projects.MavenFacet;
import org.jboss.forge.addon.maven.projects.MavenPluginFacet;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectFacet;
import org.jboss.forge.addon.projects.dependencies.DependencyInstaller;
import org.jboss.forge.addon.projects.facets.DependencyFacet;
import org.jboss.forge.addon.projects.facets.MetadataFacet;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.addon.templates.TemplateFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

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
        LanguageFrameworkInjector injector = null;
        switch (framework.toString() + language.toString()) {
            case "JavaFxJava":
                injector = new JavaFxJavaInjector(getFaceted(), resourceFactory, templateFactory);
                break;
            case "JavaFxGroovy":
                injector = new JavaFxGroovyInjector(getFaceted(), resourceFactory, templateFactory);
                break;
            default:
                throw new RuntimeException("Framework and Language combination not yet implemented");
        }

        injector.createFolders();
    }

    protected void addDependencies() {
        Parent p = new Parent();
        p.setGroupId(GRIFFON_GROUP_ID);
        p.setArtifactId(GRIFFON_MASTERPOM_ARTIFACT_ID);
        p.setVersion("1.0.0");
        MavenFacet mavenFacet = getFaceted().getFacet(MavenFacet.class);
        Model model = mavenFacet.getModel();
        model.setParent(p);
        mavenFacet.setModel(model);

        DependencyFacet  facet = getFaceted().getFacet(DependencyFacet.class);
        facet.addRepository("jcenter","http://jcenter.bintray.com");
        facet.addRepository("griffon-plugins","http://dl.bintray.com/griffon/griffon-plugins");

        MetadataFacet mdfacet = getFaceted().getFacet(MetadataFacet.class);
        mdfacet.setDirectProperty("griffon.version","2.2.0");
        mdfacet.setDirectProperty("application.main.class",mdfacet.getProjectGroupName()+".Launcher");
        mdfacet.setDirectProperty("maven.compiler.source","1.8");
        mdfacet.setDirectProperty("maven.compiler.target","1.8");

        builder = DependencyBuilder.create();
        addDependency(LOG4J);
        addDependency(SLF4J_LOG4J12);
        addDependency(SPOCK_CORE);
        addDependency(JUNIT);
    }

    protected void addPlugins() {
        addPlugin(JAVAFX_MAVEN_PLUGIN);
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
        for (Coordinate coordinate : coordinates) {
            if (coordinate.getVersion().startsWith(majorVersion))
                break;
            i++;
        }

        int v = i;
        for (; v < coordinates.size(); v++) {
            if (!coordinates.get(v).getVersion().startsWith(majorVersion))
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
     *
     * @param e
     * @return
     */
    protected String getExceptionAsString(Throwable e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return e.getMessage() + System.lineSeparator() + sw.toString();
    }
}