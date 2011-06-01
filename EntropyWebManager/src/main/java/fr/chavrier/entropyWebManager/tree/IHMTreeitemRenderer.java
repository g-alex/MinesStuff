package fr.chavrier.entropyWebManager.tree;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.SimpleTreeNode;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

import vmscript.AllFactories;
import vmscript.api.GridElement;
import vmscript.api.PhysicalMachine;
import vmscript.api.VirtualMachine;
import fr.chavrier.entropyWebManager.controls.ActionManager;
import fr.chavrier.entropyWebManager.customComponents.CreateVMWindow;
import fr.chavrier.entropyWebManager.customComponents.MigrateVMWindow;

public class IHMTreeitemRenderer implements TreeitemRenderer{

	// TODO decommenter les commentaire utiles

	public void render(Treeitem item, Object entropyTreeNode) throws Exception {
		SimpleTreeNode treeNode = (SimpleTreeNode) entropyTreeNode;
		Object nodeData = treeNode.getData();

		final GridElement e = (GridElement) nodeData;

		item.setValue(e);
		item.setLabel(e.getName());

		if (e.getType().equals(AllFactories.getFactory().getLastLogicalType())) {

			final VirtualMachine vm = (VirtualMachine) e;

			Menupopup popup = new Menupopup();
			Menuitem migrateVM = new Menuitem("Migrate");
			migrateVM.addEventListener("onClick", new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					MigrateVMWindow win = (MigrateVMWindow) Executions
					.createComponents("./VM/migrateVMpopup.zul", null,
							null);
					win.setCurrentVM(vm);
					win.doModal();
				}
			});
			migrateVM.setParent(popup);
			Menuitem startVM = new Menuitem("Start");
			startVM.addEventListener("onClick", new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					// vm.start();
					ActionManager.vmStart(vm);
				}
			});
			startVM.setParent(popup);
			Menuitem stopVM = new Menuitem("Stop");
			stopVM.addEventListener("onClick", new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					// vm.stop();
					ActionManager.vmStop(vm);
				}
			});
			stopVM.setParent(popup);
			Menuitem suspendVM = new Menuitem("Suspend");
			suspendVM.addEventListener("onClick", new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					// vm.suspend();
					ActionManager.vmSuspend(vm);
				}
			});
			suspendVM.setParent(popup);
			Menuitem resumeVM = new Menuitem("Resume");
			resumeVM.addEventListener("onClick", new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					// vm.resume();
					ActionManager.vmResume(vm);
				}
			});
			resumeVM.setParent(popup);
			popup.setParent(item.getTree().getParent());
			item.setContext(popup);

			Treerow itemTreerow = item.getTreerow();
			itemTreerow.setDraggable("true");
			itemTreerow.setDroppable("true");
			itemTreerow.addEventListener("onDrop", new VMDropListener());
		} else if (e.getType().equals(
				AllFactories.getFactory().getLastPhysicalType())) {

			final PhysicalMachine host = (PhysicalMachine) e;

			Menupopup popup = new Menupopup();
			Menuitem createVM = new Menuitem("Create VM");
			createVM.addEventListener("onClick", new EventListener() {
				public void onEvent(Event arg0) throws Exception {
					CreateVMWindow win = (CreateVMWindow) Executions
					.createComponents("./host/newVMpopup.zul", null,
							null);
					win.setCurrentHost(host);
					win.doModal();
				}
			});
			createVM.setParent(popup);
			popup.setParent(item.getTree().getParent());
			item.setContext(popup);
			Treerow itemTreerow = item.getTreerow();
			itemTreerow.setDroppable("true");
			itemTreerow.addEventListener("onDrop", new HostDropListener());
		}
	}

}
