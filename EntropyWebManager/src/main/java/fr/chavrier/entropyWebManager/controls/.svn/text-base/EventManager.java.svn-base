package fr.chavrier.entropyWebManager.controls;

import java.util.ArrayList;
import java.util.List;

import javax.management.Notification;
import javax.management.NotificationListener;

import fr.chavrier.entropyWebManager.controls.ErrorNotification.errorNotifType;

public class EventManager implements NotificationListener {

	private static List<NotificationListener> ihmMessageListeners = new ArrayList<NotificationListener>();
	private static List<NotificationListener> modelListeners = new ArrayList<NotificationListener>();

	public static void addIhmListener(NotificationListener listener) {
		if (!ihmMessageListeners.contains(listener)) {
			ihmMessageListeners.add(listener);
		}
	}

	public static void removeIhmListener(NotificationListener listener) {
		if (ihmMessageListeners.contains(listener)) {
			ihmMessageListeners.remove(listener);
		}
	}

	public static void addModelListener(NotificationListener listener) {
		if (!modelListeners.contains(listener)) {
			modelListeners.add(listener);
		}
	}

	public static void removeModelListener(NotificationListener listener) {
		if (modelListeners.contains(listener)) {
			modelListeners.remove(listener);
		}
	}

	public static void emitActionNotification(ActionNotification n) {
		// System.out.println("Emitting action notif.");
		for (NotificationListener listener : ihmMessageListeners) {
			listener.handleNotification(n, null);
		}
	}

	public static void forwardNotification(Notification n, Object h) {
		for (NotificationListener listener : modelListeners) {
			listener.handleNotification(n, h);
		}
	}

	public static void emitErrorMessage(String message) {
		ErrorNotification n = new ErrorNotification(
				errorNotifType.ERROR_MESSAGE, message);
		for (NotificationListener listener : modelListeners) {
			listener.handleNotification(n, message);
		}
	}

	public void handleNotification(Notification notification, Object handback) {

		// System.out.println("type : " + notification.getType());
		// System.out.println("Source : " + notification.getSource());
		// System.out.println("Userdata : " + notification.getUserData());

		forwardNotification(notification, handback);
	}

}
