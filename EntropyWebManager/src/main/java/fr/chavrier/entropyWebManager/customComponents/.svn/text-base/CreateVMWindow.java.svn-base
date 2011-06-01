package fr.chavrier.entropyWebManager.customComponents;

import org.zkoss.zul.Window;

import vmscript.api.PhysicalMachine;
import fr.chavrier.entropyWebManager.controls.ActionManager;

public class CreateVMWindow extends Window{
	PhysicalMachine currentHost;

	public PhysicalMachine getCurrentHost() {
		return this.currentHost;
	}

	public void setCurrentHost(PhysicalMachine currentHost) {
		this.currentHost = currentHost;
	}

	public void createVM(String name, int ram, int nbcpu, int disk, String uuid) {
		// this.currentHost.createVm(name, uuid, ram, nbcpu);
		ActionManager.vmCreate(this.currentHost, name, ram, nbcpu, disk, uuid);
	}

}
