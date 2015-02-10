package org.codehaus.griffon.forge.ui;

import org.jboss.forge.addon.projects.ProjectFactory;
import org.jboss.forge.addon.projects.ProjectProvider;
import org.jboss.forge.addon.projects.ui.AbstractProjectCommand;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.metadata.UICommandMetadata;
import org.jboss.forge.addon.ui.util.Categories;
import org.jboss.forge.addon.ui.util.Metadata;
import org.jboss.forge.furnace.services.Imported;

import javax.inject.Inject;

public abstract class AbstractGriffonCommand extends AbstractProjectCommand {

    @Inject
    protected ProjectFactory projectFactory;

    @Inject
    protected ResourceFactory resourceFactory;

    @Inject
    protected Imported<ProjectProvider> buildSystems;

    @Override
    public UICommandMetadata getMetadata(UIContext context) {
        return Metadata.from(super.getMetadata(context), getClass()).category(
                Categories.create("Griffon"));
    }

    @Override
    protected ProjectFactory getProjectFactory() {
        return projectFactory;
    }
}
