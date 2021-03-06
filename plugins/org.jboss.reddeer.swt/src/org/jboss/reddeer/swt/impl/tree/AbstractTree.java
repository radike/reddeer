package org.jboss.reddeer.swt.impl.tree;

import java.util.LinkedList;
import java.util.List;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.Tree;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.handler.TreeHandler;
import org.jboss.reddeer.swt.handler.WidgetHandler;

public abstract class AbstractTree implements Tree {

	private static final Logger logger = Logger.getLogger(AbstractTree.class);

	protected org.eclipse.swt.widgets.Tree swtTree;

	private TreeHandler treeHandler = TreeHandler.getInstance();

	protected AbstractTree(org.eclipse.swt.widgets.Tree swtTree) {
		if (swtTree != null) {
			this.swtTree = swtTree;
		} else {
			throw new SWTLayerException(
					"SWT Tree passed to constructor is null");
		}
	}

	public List<TreeItem> getAllItems() {
		List<TreeItem> list = new LinkedList<TreeItem>();
		list.addAll(getAllItemsRecursive(getItems()));
		return list;
	}

	private List<TreeItem> getAllItemsRecursive(List<TreeItem> parentItems) {
		List<TreeItem> list = new LinkedList<TreeItem>();

		for (TreeItem item : parentItems) {
			list.add(item);
			list.addAll(getAllItemsRecursive(item.getItems()));
		}
		return list;
	}

	public List<TreeItem> getItems() {
		return treeHandler.getItems(swtTree);
	}

	public void selectItems(final TreeItem... treeItems) {
		// Tree items should be logged, however, the names needs to 
		// be retrieved in UI thread so it might be a performance issue
		logger.info("Select specified tree items");
		treeHandler.selectItems(treeItems);
	}

	public void setFocus() {
		treeHandler.setFocus(swtTree);
	}

	/**
	 * @see Tree#getColumnCount()
	 */
	public int getColumnCount() {
		return treeHandler.getColumnCount(swtTree);
	}

	public void unselectAllItems() {
		logger.info("Unselect all tree items");
		treeHandler.unselectAllItems(swtTree);
	}

	public org.eclipse.swt.widgets.Tree getSWTWidget() {
		return swtTree;
	}

	@Override
	public boolean isEnabled() {
		return WidgetHandler.getInstance().isEnabled(swtTree);
	}
}
