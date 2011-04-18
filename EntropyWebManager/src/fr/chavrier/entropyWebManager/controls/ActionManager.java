package fr.chavrier.entropyWebManager.controls;

import vmscript.api.LifeCycleController.LifeCycleState;
import vmscript.api.PhysicalMachine;
import vmscript.api.VirtualMachine;
import vmscript.exception.ActionErrorException;
import fr.chavrier.entropyWebManager.controls.ActionNotification.notifType;

public class ActionManager {

	public static void vmStart(VirtualMachine vm) {
		ActionNotification n = new ActionNotification(
				notifType.ASK_STATE_CHANGING,
				vm);
		n.setUserData(LifeCycleState.running);
		EventManager.emitNotification(n);
		try {
			vm.start();
		} catch (ActionErrorException e) {
			System.out.println("Action FAILED");
		}
	}

	public static void vmStop(VirtualMachine vm) {
		ActionNotification n = new ActionNotification(
				notifType.ASK_STATE_CHANGING,
				vm);
		n.setUserData(LifeCycleState.off);
		EventManager.emitNotification(n);
		try {
			vm.stop();
		} catch (ActionErrorException e) {
			System.out.println("Action FAILED");
		}
	}

	public static void vmSuspend(VirtualMachine vm) {
		ActionNotification n = new ActionNotification(
				notifType.ASK_STATE_CHANGING,
				vm);
		n.setUserData(LifeCycleState.suspended);
		EventManager.emitNotification(n);

		try {
			vm.suspend();
		} catch (ActionErrorException e) {
			System.out.println("Action FAILED");
		}
	}

	public static void vmResume(VirtualMachine vm) {
		ActionNotification n = new ActionNotification(
				notifType.ASK_STATE_CHANGING,
				vm);
		n.setUserData(LifeCycleState.running);
		EventManager.emitNotification(n);

		try {
			vm.resume();
		} catch (ActionErrorException e) {
			System.out.println("Action FAILED");
		}
	}

	public static void vmDestroy(VirtualMachine vm) {
		ActionNotification n = new ActionNotification(
				notifType.ASK_DESTROYING_VM,
				vm);
		n.setUserData(LifeCycleState.running);
		EventManager.emitNotification(n);

		try {
			vm.destroy();
		} catch (ActionErrorException e) {
			System.out.println("Action FAILED");
		}
	}

	public static void vmMigrate(VirtualMachine vm, PhysicalMachine pm) {
		ActionNotification n = new ActionNotification(
				notifType.ASK_MIGRATING_VM,
				vm);
		n.setUserData(pm);
		EventManager.emitNotification(n);

		try {
			vm.migrate(pm);
		} catch (ActionErrorException e) {
			System.out.println("Action FAILED");
		}
	}

	public static void vmCreate(PhysicalMachine pm, String vmName, int ram,
			int nbcpu, int disk, String uuid) {
		ActionNotification n = new ActionNotification(
				notifType.ASK_CREATING_VM,
				vmName);
		n.setUserData(pm);
		EventManager.emitNotification(n);

		try {
			pm.createVm(vmName, uuid, ram, nbcpu);
		} catch (ActionErrorException e) {
			System.out.println("Action FAILED");
		}
	}
}
