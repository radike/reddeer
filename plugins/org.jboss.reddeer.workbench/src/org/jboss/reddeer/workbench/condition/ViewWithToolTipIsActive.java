package org.jboss.reddeer.workbench.condition;

import org.eclipse.ui.IViewReference;
import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.lookup.WorkbenchLookup;

/**
 * Tests if view with entered toolTip value is active in 
 * current workbench
 * 
 * @author jjankovi
 *
 */
public class ViewWithToolTipIsActive implements WaitCondition {

	private String toolTip;
	
	public ViewWithToolTipIsActive(String toolTip) {
		this.toolTip = toolTip;
	}
	
	@Override
	public boolean test() {
		for (IViewReference view : WorkbenchLookup.findAllViews()) {
			if (view.getPartName().equals(toolTip)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String description() {
		return "view with title '" + toolTip + "' is active";
	}

}
