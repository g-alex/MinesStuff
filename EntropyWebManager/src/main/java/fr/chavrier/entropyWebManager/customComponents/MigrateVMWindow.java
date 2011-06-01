package fr.chavrier.entropyWebManager.customComponents;

import java.util.Set;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import vmscript.AllFactories;
import vmscript.api.PhysicalMachine;
import vmscript.api.VirtualMachine;
import fr.chavrier.entropyWebManager.controls.ActionManager;

public class MigrateVMWindow extends Window{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	VirtualMachine currentVM;

	public void onCreate() {
		Vlayout vl = new Vlayout();
		vl.setParent(this);

		Listbox lb = new Listbox();
		lb.setParent(vl);

		Set<PhysicalMachine> hosts = (Set<PhysicalMachine>) AllFactories
		.getFactory()
		.getFromType(
				AllFactories.getFactory().getLastPhysicalType());
		for (PhysicalMachine host : hosts) {
			if (host != this.currentVM.getPhysicalMachine()) {
				final Listitem li = new Listitem(host.getName());
				li.addEventListener("onClick", new EventListener() {
					public void onEvent(Event arg0) throws Exception {
						PhysicalMachine targetHost = (PhysicalMachine) AllFactories
						.getFactory()
						.getFromName(li.getLabel());
						ActionManager.vmMigrate(MigrateVMWindow.this.currentVM,
								targetHost);
						// MigrateVMWindow.this.currentVM.migrate(targetHost);
					}
				});
				li.setParent(lb);
			}
		}
	}

	public VirtualMachine getCurrentVM() {
		return this.currentVM;
	}

	public void setCurrentVM(VirtualMachine currentHost) {
		this.currentVM = currentHost;
	}
}
