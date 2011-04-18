package fr.chavrier.entropyWebManager.controls;

import java.util.ArrayList;
import java.util.List;

import javax.management.Notification;
import javax.management.NotificationListener;

public class EventManager implements NotificationListener {

	private static List<NotificationListener> listeners = new ArrayList<NotificationListener>();

	public static void addNotificationListener(NotificationListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public static void removeNotificationListener(NotificationListener listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}

	public static void emitNotification(Notification n) {
		emitNotification(n, null);
	}

	public static void emitNotification(Notification n, Object h) {
		for (NotificationListener listener : listeners) {
			listener.handleNotification(n, h);
		}
	}

	public void handleNotification(Notification notification, Object handback) {
		emitNotification(notification, handback);
	}

}
