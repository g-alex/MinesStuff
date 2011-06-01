package fr.chavrier.entropyWebManager.controls;

import javax.management.Notification;

public class ErrorNotification extends Notification {
	private static final long serialVersionUID = 2190902700811311088L;

	private static long nbNotification = 0;

	public enum errorNotifType {
		ERROR_MESSAGE,
	};

	public ErrorNotification(errorNotifType pType, Object pSource) {
		super(pType.toString(), pSource, nbNotification++);
	}
}
