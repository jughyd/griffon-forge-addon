package org.codehaus.griffon.forge.facets;

import org.codehaus.griffon.forge.GriffonFacet;
import org.codehaus.griffon.types.FrameworkTypes;
import org.codehaus.griffon.types.LanguageTypes;
import org.jboss.forge.addon.dependencies.Coordinate;
import org.jboss.forge.addon.dependencies.DependencyQuery;
import org.jboss.forge.addon.dependencies.DependencyRepository;
import org.jboss.forge.addon.dependencies.DependencyResolver;
import org.jboss.forge.addon.dependencies.builder.DependencyBuilder;
import org.jboss.forge.addon.dependencies.builder.DependencyQueryBuilder;
import org.jboss.forge.addon.dependencies.util.NonSnapshotDependencyFilter;
import org.jboss.forge.addon.facets.AbstractFacet;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectFacet;
import org.jboss.forge.addon.projects.dependencies.DependencyInstaller;
import org.jboss.forge.addon.resource.DirectoryResource;

import javax.inject.Inject;
import java.util.List;

public abstract class AbstractGriffonFacet extends AbstractFacet<Project>
        implements ProjectFacet, GriffonFacet {

    private static final DependencyRepository dependencyRepository = new DependencyRepository("jcenter", "http://jcenter.bintray.com/");

    public static final String GRIFFON_JAVAFX = "org.codehaus.griffon:griffon-javafx";
    public static final String GRIFFON_GUICE = "org.codehaus.griffon:griffon-guice";
    public static final String GRIFFON_CORE_TEST = "org.codehaus.griffon:griffon-core-test";
    public static final String GROOVY_ALL = "org.codehaus.groovy:groovy-all";
    public static final String LOG4J = "log4j:log4j";
    public static final String SLF4J_LOG4J12 = "org.slf4j:slf4j-log4j12";
    public static final String SPOCK_CORE = "org.spockframework:spock-core";
    public static final String JUNIT = "junit:junit";
    public static final String GRIFFON_CORE_COMPILE = "org.codehaus.griffon:griffon-core-compile";

    private DependencyBuilder builder;
    private DependencyInstaller installer;

    protected FrameworkTypes framework = null;
    protected LanguageTypes language = null;

    @Inject
    private DependencyResolver dependencyResolver;

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

    protected void createFolders() {
        Project selectedProject = getFaceted();
        DirectoryResource directoryResource = (DirectoryResource) selectedProject.getRoot();

        directoryResource.getOrCreateChildDirectory("config");
        directoryResource.getOrCreateChildDirectory("config/checkstyle");
        directoryResource.getOrCreateChildDirectory("config/codenarc");

        directoryResource.getOrCreateChildDirectory("griffon-app");
        directoryResource.getOrCreateChildDirectory("griffon-app/conf");
        directoryResource.getOrCreateChildDirectory("griffon-app/cotrollers");
        directoryResource.getOrCreateChildDirectory("griffon-app/i18n");
        directoryResource.getOrCreateChildDirectory("griffon-app/lifestyle");
        directoryResource.getOrCreateChildDirectory("griffon-app/models");
        directoryResource.getOrCreateChildDirectory("griffon-app/resources");
        directoryResource.getOrCreateChildDirectory("griffon-app/services");
        directoryResource.getOrCreateChildDirectory("griffon-app/views");

        directoryResource.getOrCreateChildDirectory("maven");
        directoryResource.getOrCreateChildDirectory("maven/distribution");
        directoryResource.getOrCreateChildDirectory("maven/distribution/bin");

    }

    protected void addDependencies() {
        builder = DependencyBuilder.create();
        addDependency(LOG4J);
        addDependency(SLF4J_LOG4J12);
        addDependency(SPOCK_CORE);
        addDependency(JUNIT);
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
}