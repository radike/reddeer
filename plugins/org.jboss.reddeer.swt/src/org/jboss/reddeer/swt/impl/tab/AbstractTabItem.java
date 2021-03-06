package org.jboss.reddeer.swt.impl.tab;

import org.eclipse.swt.SWT;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.TabItem;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.handler.TabItemHandler;
import org.jboss.reddeer.swt.handler.WidgetHandler;

/**
 * Abstract class for all TabItem implementations
 * 
 * @author Andrej Podhradsky
 * @author Vlado Pakan
 * 
 */
public abstract class AbstractTabItem implements TabItem {

	private static final Logger logger = Logger.getLogger(AbstractTabItem.class);

	protected org.eclipse.swt.widgets.TabItem swtTabItem;
	protected org.eclipse.swt.widgets.TabFolder swtParent;
	private TabItemHandler tabItemHandler = TabItemHandler.getInstance();

	protected AbstractTabItem(final org.eclipse.swt.widgets.TabItem swtTabItem) {
		if (swtTabItem != null) {
			this.swtTabItem = swtTabItem;
			this.swtParent = tabItemHandler.getTabFolder(swtTabItem);
		} else {
			throw new SWTLayerException("SWT TabItem passed to constructor is null");
		}
	}

	/**
	 * See {@link TabItem}
	 */
	@Override
	public void activate() {
		logger.info("Activate " + this.getText());
		tabItemHandler.select(swtTabItem);
		tabItemHandler.notifyTabFolder(
			tabItemHandler.createEventForTabItem(swtTabItem,SWT.Selection),
			swtParent);
	}
	/**
	 * See {@link TabItem}
	 */
	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(swtTabItem);
	}
	
	public org.eclipse.swt.widgets.TabItem getSWTWidget(){
		return swtTabItem;
	}
	
	@Override
	public boolean isEnabled() {
		return WidgetHandler.getInstance().isEnabled(swtParent);
	}
}
