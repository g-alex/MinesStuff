package fr.chavrier.entropyWebManager.controls;

import vmscript.api.LifeCycleController.LifeCycleState;
import vmscript.api.PhysicalMachine;
import vmscript.api.VirtualMachine;
import vmscript.exception.ActionErrorException;
import fr.chavrier.entropyWebManager.controls.ActionNotification.actionNotifType;

public class ActionManager {

	public static void vmStart(VirtualMachine vm) {
		ActionNotification n = new ActionNotification(
				actionNotifType.ASK_VM_START,
				vm);
		n.setUserData(LifeCycleState.running);
		EventManager.emitActionNotification(n);
		try {
			vm.start();
		} catch (ActionErrorException e) {
			System.out.println("Action FAILED");
		}
	}

	public static void vmStop(VirtualMachine vm) {
		ActionNotification n = new ActionNotification(
				actionNotifType.ASK_VM_STOP,
				vm);
		n.setUserData(LifeCycleState.off);
		EventManager.emitActionNotification(n);
		try {
			vm.stop();
		} catch (ActionErrorException e) {
			System.out.println("Action FAILED");
		}
	}

	public static void vmSuspend(VirtualMachine vm) {
		ActionNotification n = new ActionNotification(
				actionNotifType.ASK_VM_SUSPEND,
				vm);
		n.setUserData(LifeCycleState.suspended);
		EventManager.emitActionNotification(n);

		try {
			vm.suspend();
		} catch (ActionErrorException e) {
			System.out.println("Action FAILED");
		}
	}

	public static void vmResume(VirtualMachine vm) {
		ActionNotification n = new ActionNotification(
				actionNotifType.ASK_VM_RESUME,
				vm);
		n.setUserData(LifeCycleState.running);
		EventManager.emitActionNotification(n);

		try {
			vm.resume();
		} catch (ActionErrorException e) {
			System.out.println("Action FAILED");
		}
	}

	public static void vmDestroy(VirtualMachine vm) {
		ActionNotification n = new ActionNotification(
				actionNotifType.ASK_DESTROYING_VM,
				vm);
		n.setUserData(LifeCycleState.running);
		EventManager.emitActionNotification(n);

		try {
			vm.destroy();
		} catch (ActionErrorException e) {
			System.out.println("Action FAILED");
		}
	}

	public static void vmMigrate(VirtualMachine vm, PhysicalMachine pm) {
		ActionNotification n = new ActionNotification(
				actionNotifType.ASK_MIGRATING_VM,
				vm);
		n.setUserData(pm);
		EventManager.emitActionNotification(n);

		try {
			vm.migrate(pm);
		} catch (ActionErrorException e) {
			System.out.println("Action FAILED");
		}
	}

	public static void vmCreate(PhysicalMachine pm, String vmName, int ram,
			int nbcpu, int disk, String uuid) {
		ActionNotification n = new ActionNotification(
				actionNotifType.ASK_CREATING_VM,
				vmName);
		n.setUserData(pm);
		EventManager.emitActionNotification(n);

		try {
			pm.createVm(vmName, uuid, ram, nbcpu);
		} catch (ActionErrorException e) {
			System.out.println("Action FAILED");
		}
	}
}
