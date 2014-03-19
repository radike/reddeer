package org.jboss.reddeer.swt.condition;

import org.jboss.reddeer.swt.api.Table;


/**
 * Condition is fulfilled when table has rows
 * @author Rastislav Wagner
 *
 */
public class TableHasRows implements WaitCondition {
	private final Table table;

	public TableHasRows(Table table) {
		this.table = table;
	}

	public boolean test() {
		return table.rowCount() > 0;
	}

	@Override
	public String description() {
		return "table contains rows";
	}
}