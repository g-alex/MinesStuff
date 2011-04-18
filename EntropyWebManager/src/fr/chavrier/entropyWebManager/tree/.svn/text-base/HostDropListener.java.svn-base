package fr.chavrier.entropyWebManager.tree;

import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

import vmscript.api.PhysicalMachine;
import vmscript.api.VirtualMachine;

class HostDropListener implements org.zkoss.zk.ui.event.EventListener {
	public void onEvent(Event event) throws UiException {

		PhysicalMachine targetHost = (PhysicalMachine) ((Treeitem) ((Treerow) event
				.getTarget()).getParent()).getValue();
		Treerow draggedrow = (Treerow) ((DropEvent) event).getDragged();
		VirtualMachine vm = (VirtualMachine) ((Treeitem) draggedrow.getParent())
		.getValue();
		try {
			vm.migrate(targetHost);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
