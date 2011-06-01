package fr.chavrier.entropyWebManager.serverPush;

import javax.management.Notification;
import javax.management.NotificationListener;

import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.DesktopUnavailableException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Vlayout;

import vmscript.notifications.ModelNotification.notifType;
import fr.chavrier.entropyWebManager.controls.ErrorNotification;

public class AlertServerPush implements NotificationListener {

	private Desktop desktop;
	private Vlayout layout;

	public AlertServerPush(Vlayout l, Desktop d) {
		this.desktop = d;
		this.layout = l;
	}

	public void addAlert(String str) {
		Label lbl = new Label(str);
		Div div = new Div();
		div.appendChild(lbl);

		try {
			Executions.activate(this.desktop);
			try {
				this.layout.appendChild(div);
			} finally {
				Executions.deactivate(this.desktop);
			}

		} catch (DesktopUnavailableException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void addAlertWODesktopActivation(String str) {
		Label lbl = new Label(str);
		Div div = new Div();
		div.appendChild(lbl);

		this.layout.appendChild(div);
	}

	public void handleNotification(Notification n, Object o) {
		if (n instanceof ErrorNotification) {
			String messageStr = o.toString();
			addAlertWODesktopActivation(messageStr);
		} else {
			if (notifType.valueOf(n.getType()) == notifType.ACTION_FAILURE) {
				// System.out.println("alert action failure notif handled");
				String errorStr = n.getUserData().toString();
				addAlert(errorStr);
			} else {
				String messageStr = o.toString();
				addAlert(messageStr);
			}
		}
	}

}
