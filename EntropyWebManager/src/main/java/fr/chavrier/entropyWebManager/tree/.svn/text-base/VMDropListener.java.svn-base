package fr.chavrier.entropyWebManager.tree;

import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

import vmscript.api.PhysicalMachine;
import vmscript.api.VirtualMachine;
import fr.chavrier.entropyWebManager.controls.ActionManager;

public class VMDropListener implements org.zkoss.zk.ui.event.EventListener {
	public void onEvent(Event event) throws UiException {
		Treerow draggedrow = (Treerow) ((DropEvent) event).getDragged();
		Treeitem draggedItem = (Treeitem) draggedrow.getParent();

		VirtualMachine vm = (VirtualMachine) ((Treeitem) draggedrow.getParent())
		.getValue();

		// Treeitem item =
		// (Treeitem)((Treerow)event.getTarget()).getParent().getParent().getParent();

		PhysicalMachine targetHost = (PhysicalMachine) ((Treeitem) ((Treerow) event
				.getTarget()).getParent().getParent().getParent()).getValue();

		try {
			// vm.migrate(targetHost);
			ActionManager.vmMigrate(vm, targetHost);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
