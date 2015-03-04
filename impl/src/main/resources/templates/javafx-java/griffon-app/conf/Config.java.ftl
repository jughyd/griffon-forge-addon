import griffon.util.AbstractMapResourceBundle;

import javax.annotation.Nonnull;
import java.util.Map;

import static java.util.Arrays.asList;
import static griffon.util.CollectionUtils.map;

public class Config extends AbstractMapResourceBundle {
    @Override
    protected void initialize(@Nonnull Map<String, Object> entries) {
        map(entries)
            .e("application", map()
                .e("title", "${projectname}")
                .e("startupGroups", asList("${simplename}"))
                .e("autoShutdown", true)
            )
            .e("mvcGroups", map()
                .e("${simplename}", map()
                    .e("model", "${toppackage}.${simplename}Model")
                    .e("view", "${toppackage}.${simplename}View")
                    .e("controller", "${toppackage}.${simplename}Controller")
                )
            );
    }
}