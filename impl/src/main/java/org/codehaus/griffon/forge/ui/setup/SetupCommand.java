package org.codehaus.griffon.forge.ui.setup;

import org.codehaus.griffon.forge.AbstractGriffonFacet;
import org.codehaus.griffon.forge.CommandRunner;
import org.codehaus.griffon.forge.GriffonFacet;
import org.codehaus.griffon.forge.ui.AbstractGriffonCommand;
import org.codehaus.griffon.types.FrameworkTypes;
import org.codehaus.griffon.types.LanguageTypes;
import org.jboss.forge.addon.dependencies.DependencyQuery;
import org.jboss.forge.addon.dependencies.DependencyResolver;
import org.jboss.forge.addon.dependencies.builder.DependencyBuilder;
import org.jboss.forge.addon.dependencies.builder.DependencyQueryBuilder;
import org.jboss.forge.addon.dependencies.util.NonSnapshotDependencyFilter;
import org.jboss.forge.addon.facets.FacetFactory;
import org.jboss.forge.addon.projects.dependencies.DependencyInstaller;
import org.jboss.forge.addon.ui.context.UIBuilder;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.context.UIExecutionContext;
import org.jboss.forge.addon.ui.hints.InputType;
import org.jboss.forge.addon.ui.input.UIInput;
import org.jboss.forge.addon.ui.input.UISelectOne;
import org.jboss.forge.addon.ui.metadata.UICommandMetadata;
import org.jboss.forge.addon.ui.metadata.WithAttributes;
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.result.Results;
import org.jboss.forge.addon.ui.util.Categories;
import org.jboss.forge.addon.ui.util.Metadata;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
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

	@Inject
	private DependencyBuilder dependencyBuilder;

	@Inject
	private DependencyInstaller dependencyInstaller;

	@Inject
	private  DependencyResolver dependencyResolver;

	@Override
	public Result execute(UIExecutionContext context) throws Exception {

		GriffonFacet griffonFacet = griffonVersion.getValue();

		griffonFacet.setFramework(frameworkType.getValue());
		griffonFacet.setLanguage(languageType.getValue());

		if (facetFactory.install(getSelectedProject(context.getUIContext()),griffonFacet)) {
			return Results.success("Griffon has been installed.");
		}
		DependencyQuery query = DependencyQueryBuilder
				.create("junit:junit")
				.setFilter(new NonSnapshotDependencyFilter());

//		dependencyResolver.resolveVersions(query).get(0);

		DependencyBuilder builder = DependencyBuilder.create();
		builder.setCoordinate(dependencyResolver.resolveVersions(query).get(0));

		dependencyInstaller.install(getSelectedProject(context.getUIContext()), builder);
//		List<String> cmdArguments = new ArrayList<>();
//		cmdArguments.add("create");
//		cmdArguments.add("griffon-javafx-java");
//		cmdArguments.add("1.1.0");
//		cmdArguments.add(getSelectedProject(context).getRoot().getName());
//		CommandRunner cmd = new CommandRunner("lazybones",false, cmdArguments);
//		cmd.setDirectory(getSelectedProject(context).getRoot().getParent().getFullyQualifiedName());
//		List<String> subsequentInputs = new ArrayList<>();
//		subsequentInputs.add("org.buddha");
//		subsequentInputs.add(getSelectedProject(context).getRoot().getName());
//		subsequentInputs.add("");
//		subsequentInputs.add("");
//		subsequentInputs.add("");
//		subsequentInputs.add("");
//		cmd.run(subsequentInputs);
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
