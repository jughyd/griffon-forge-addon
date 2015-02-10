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

    private GriffonFacet griffonFacet;

    @Override
    public Result execute(UIExecutionContext context) throws Exception {

        FrameworkTypes frameworkTypeValue = frameworkType.getValue();
        LanguageTypes languageTypeValue = languageType.getValue();

        switch (frameworkTypeValue.toString() + "-" + languageTypeValue.toString()) {
            case "JavaFx-Java":          // only the language and framework combinations that are implemented and tested will be listed in switch cases
            case "JavaFx-Groovy":        // this is not yet implemented but kept here as an example
                griffonFacet = griffonVersion.getValue();
                break;

            default:
                return Results.fail("Griffon couldn't be installed. Framework & Language Combination not yet implemented");
        }

        griffonFacet.setLanguage(languageTypeValue);
        griffonFacet.setFramework(frameworkTypeValue);

        if (facetFactory.install(getSelectedProject(context.getUIContext()), griffonFacet)) {
            return Results.success("Griffon has been installed.");
        }

        return Results.fail("Your project is couldn't be modified as a Griffon Project.\n Either it is already modified or some unknown error occured.");
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
