package org.codehaus.griffon.forge;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.forge.arquillian.AddonDependency;
import org.jboss.forge.arquillian.Dependencies;
import org.jboss.forge.arquillian.archive.ForgeArchive;
import org.jboss.forge.furnace.repositories.AddonDependencyEntry;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class SetupCommand {

    @Deployment
    @Dependencies({
            @AddonDependency(name = "org.jboss.forge.furnace.container:cdi"),
            @AddonDependency(name = "org.jboss.forge.addon:core"),
            @AddonDependency(name = "org.jboss.forge.addon:dependencies"),
            @AddonDependency(name = "org.jboss.forge.addon:environment"),
            @AddonDependency(name = "org.jboss.forge.addon:facets"),
            @AddonDependency(name = "org.jboss.forge.addon:javaee"),
            @AddonDependency(name = "org.jboss.forge.addon:projects"),
            @AddonDependency(name = "org.jboss.forge.addon:resources"),
            @AddonDependency(name = "org.jboss.forge.addon:ui"),
            @AddonDependency(name = "org.jboss.forge.addon:ui-spi")})
    public static ForgeArchive getDeployment() {
        ForgeArchive archive = ShrinkWrap
                .create(ForgeArchive.class)
                .addBeansXML()
                .addAsAddonDependencies(
                        AddonDependencyEntry
                                .create("org.jboss.forge.furnace.container:cdi"),
                        AddonDependencyEntry
                                .create("org.jboss.forge.addon:core"),
                        AddonDependencyEntry
                                .create("org.jboss.forge.addon:dependencies"),
                        AddonDependencyEntry
                                .create("org.jboss.forge.addon:environment"),
                        AddonDependencyEntry
                                .create("org.jboss.forge.addon:facets"),
                        AddonDependencyEntry
                                .create("org.jboss.forge.addon:javaee"),
                        AddonDependencyEntry
                                .create("org.jboss.forge.addon:projects"),
                        AddonDependencyEntry
                                .create("org.jboss.forge.addon:resources"),
                        AddonDependencyEntry.create("org.jboss.forge.addon:ui"),
                        AddonDependencyEntry
                                .create("org.jboss.forge.addon:ui-spi"));
        return archive;
    }



    @Test
    public void testSetupCommand() {

    }
}