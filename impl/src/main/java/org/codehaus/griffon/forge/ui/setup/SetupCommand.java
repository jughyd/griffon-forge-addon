package org.codehaus.griffon.forge.ui.setup;

import org.codehaus.griffon.forge.GriffonFacet;
import org.codehaus.griffon.forge.ui.AbstractGriffonCommand;
import org.codehaus.griffon.types.FrameworkTypes;
import org.codehaus.griffon.types.LanguageTypes;
import org.jboss.forge.addon.facets.FacetFactory;
import org.jboss.forge.addon.ui.context.UIBuilder;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.context.UIExecutionContext;
import org.jboss.forge.addon.ui.hints.InputType;
import org.jboss.forge.addon.ui.input.UISelectOne;
import org.jboss.forge.addon.ui.metadata.UICommandMetadata;
import org.jboss.forge.addon.ui.metadata.WithAttributes;
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.result.Results;
import org.jboss.forge.addon.ui.util.Categories;
import org.jboss.forge.addon.ui.util.Metadata;

import javax.inject.Inject;
import java.util.logging.Logger;

public class SetupCommand extends AbstractGriffonCommand {

    private static final Logger log = Logger
            .getLogger(SetupCommand.class.getName());

    @Inject
    @WithAttributes(required = true, label = "Griffon Version", defaultValue = "2.0", shortName = 'v')
    private UISelectOne<GriffonFacet> griffonVersion;

    @Inject
    @WithAttributes(label = "Framework", type = InputType.DROPDOWN, shortName = 'f')
    private UISelectOne<FrameworkTypes> frameworkType;

    @Inject
    @WithAttributes(label = "Language", type = InputType.DROPDOWN, shortName = 'l')
    private UISelectOne<LanguageTypes> languageType;

    @Inject
    private FacetFactory facetFactory;

    @Override
    public Result execute(UIExecutionContext context) throws Exception {

        GriffonFacet griffonFacet = griffonVersion.getValue();

        griffonFacet.setFramework(frameworkType.getValue());
        griffonFacet.setLanguage(languageType.getValue());

        if (facetFactory.install(getSelectedProject(context.getUIContext()), griffonFacet)) {
            return Results.success("Griffon has been installed.");
        }

        return Results.success("Your project is modified as a Griffon Project");
    }

    @Override
    public void initializeUI(UIBuilder builder) throws Exception {
        builder.add(griffonVersion)
                .add(frameworkType)
                .add(languageType);
    }

    @Override
    protected boolean isProjectRequired() {
        return true;
    }

    @Override
    public UICommandMetadata getMetadata(UIContext context) {
        return Metadata
                .from(super.getMetadata(context), getClass())
                .name("Griffon: Setup Project")
                .description("Setup a Griffon project")
                .category(
                        Categories.create(super.getMetadata(context)
                                .getCategory(), "Griffon"));
    }

}
