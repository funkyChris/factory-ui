package org.qualipso.factory.ui.core.utils.linker;

import com.google.gwt.core.ext.LinkerContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.linker.IFrameLinker;

/**
 * Special linker to create dynamically loadable widget.
 * 
 * @author <a href="mailto:christophe.bouthier@loria.fr">Christophe Bouthier</a>
 * @date 23 October 2009
 */
public class ComponentLinker extends IFrameLinker {

    @Override
    public String getDescription() {
        return "Component";
    }

    @Override
    protected String getSelectionScriptTemplate(TreeLogger logger, LinkerContext context) {
        return "org/qualipso/factory/ui/core/utils/linker/ComponentTemplate.js";
    }

}