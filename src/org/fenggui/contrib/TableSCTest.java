package org.fenggui.contrib;

import org.fenggui.Container;
import org.fenggui.Display;
import org.fenggui.Label;
import org.fenggui.ScrollContainer;
import org.fenggui.background.PlainBackground;
import org.fenggui.example.ExampleBasisLWJGL;
import org.fenggui.layout.Alignment;
import org.fenggui.layout.StaticLayout;
import org.fenggui.table.ITableModel;
import org.fenggui.table.Table;
import org.fenggui.util.Color;
import org.lwjgl.LWJGLException;

public class TableSCTest {

	/*
	 * My own font TextCellRenderer tcr = new TextCellRenderer();
	 * tcr.setFont(MBCGui.theme.fontBold);
	 */

	private ScrollContainer scroll;
	private Container content;
	private Container disp;

	public static void main(String[] args) {

		ExampleBasisLWJGL e = new ExampleBasisLWJGL();
		try {
			e.initEverything();
		} catch (LWJGLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		TableSCTest t = new TableSCTest();
		t.setDisp(e.getDesk());
		t.init();
		
		e.mainLoop();

	}

	private void setDisp(Display desk) {
		this.disp = desk;
	}

	private void init() {
		Container window = new Container(new StaticLayout());
		window.setSize(250, 400);

		scroll = new ScrollContainer();
		scroll.setMinSize(window.getWidth() - 20, window.getHeight() - 40);
		scroll.setSizeToMinSize();
		scroll.setXY(10, 20);

		content = new Container(new StaticLayout());
		content.setMinSize(scroll.getMinWidth() - 20, Math.max(225, scroll
				.getMinHeight() - 5));
		content.setSizeToMinSize();
		scroll.setInnerWidget(content);

		Label title = new Label("Options");
		title.getAppearance().setTextColor(Color.WHITE);
		title.setSize(title.getMinSize());
		title.setY(content.getHeight() - title.getHeight());

		Table t1 = createNewTable("Sound", new String[] { "On", "Off" });
		t1.setSize(window.getWidth() - 60, 45);
		t1.setY(title.getY() - t1.getHeight() - 20);
		t1.setSelected(0, true);

		Table t2 = createNewTable("Graphics", new String[] { "Low", "Medium",
				"High" });
		t2.setSize(window.getWidth() - 60, 60);
		t2.setY(t1.getY() - t2.getHeight() - 10);
		t2.setSelected(2, true);

		content.addWidget(title);
		content.addWidget(t1);
		content.addWidget(t2);
		window.addWidget(scroll);
		window.layout();
		disp.addWidget(window); // Edit: Forgot to attach window to the display
								// node
	}

	private Table createNewTable(String name, String[] choices) {
		Table t = new Table();
		t.getAppearance().add(new PlainBackground(Color.BLACK_HALF_OPAQUE));
		t.setModel(new OptionsTableModel(name, choices));
		t.getAppearance().setCellHeight(30);
		// t.getAppearance().setFont(MBCGui.theme.fontBold);
		t.getColumn(0).setHeaderAlignment(Alignment.LEFT);
		// t.getColumn(0).setCellRenderer(tcr);
		t.getAppearance().setGridVisible(false);
		t.getAppearance().setHeaderBackgroundColor(Color.BLACK);
		t.getAppearance().setHeadTextColor(Color.WHITE);
		t.getAppearance().setSelectionColor(Color.DARK_YELLOW);
		t.setMultipleSelection(false);

		return t;
	}

	/**
	 * The Table Model for the Tables
	 */
	private class OptionsTableModel implements ITableModel {

		private String name; // Name of the Options
		private String[] list; // List of choices

		public OptionsTableModel(String name, String list[]) {
			this.name = name;
			this.list = list;
		}

		// Every list has one column
		public int getColumnCount() {
			return 1;
		}

		public String getColumnName(int columnIndex) {
			return name;
		}

		public int getRowCount() {
			if (list == null)
				return 0;
			else
				return list.length;
		}

		public Object getValue(int row, int column) {
			return list[row];
		}

	}

}
