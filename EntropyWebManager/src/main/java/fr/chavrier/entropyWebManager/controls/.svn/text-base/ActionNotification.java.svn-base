package fr.chavrier.entropyWebManager.controls;

import javax.management.Notification;

public class ActionNotification extends Notification {
	private static final long serialVersionUID = 2190902700811311088L;

	public enum actionNotifType {
		ASK_CREATING_VM, ASK_MIGRATING_VM, ASK_DESTROYING_VM, ASK_VM_STOP, ASK_VM_START, ASK_VM_SUSPEND, ASK_VM_RESUME,
	};

	private static long nbNotification = 0;

	public ActionNotification(actionNotifType pType, Object pSource) {
		super(pType.toString(), pSource, nbNotification++);
	}
}
