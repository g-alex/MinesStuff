package fr.chavrier.entropyWebManager.controls;

import javax.management.Notification;

public class ActionNotification extends Notification {
	private static final long serialVersionUID = 2190902700811311088L;

	public enum notifType {
		ASK_STATE_CHANGING, ASK_CREATING_VM, ASK_MIGRATING_VM, ASK_DESTROYING_VM
	};

	private static long nbNotification = 0;

	public ActionNotification(notifType pType, Object pSource) {
		super(pType.toString(), pSource, nbNotification++);
	}
}
