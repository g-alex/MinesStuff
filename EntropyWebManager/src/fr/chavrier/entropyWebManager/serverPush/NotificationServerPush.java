package fr.chavrier.entropyWebManager.serverPush;

import java.util.ArrayList;
import java.util.List;

import javax.management.Notification;
import javax.management.NotificationListener;

import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.DesktopUnavailableException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.East;
import org.zkoss.zul.Label;
import org.zkoss.zul.Progressmeter;
import org.zkoss.zul.Vlayout;

import vmscript.api.VirtualMachine;
import vmscript.notifications.ModelNotification.notifType;

public class NotificationServerPush implements NotificationListener {

	private Desktop desktop;
	private Vlayout layout;

	private List<String> actionList = new ArrayList<String>();

	public NotificationServerPush(Vlayout layout, Desktop desktop) {
		super();
		this.desktop = desktop;
		this.layout = layout;
	}

	public Desktop getDesktop() {
		return this.desktop;
	}

	public void setDesktop(Desktop desktop) {
		this.desktop = desktop;
	}

	public Vlayout getEast() {
		return this.layout;
	}

	public void setEast(East east) {
		this.layout = this.layout;
	}

	public void addAction(notifType type, String[] params) {
		// params :
		// if notif type is "CREATING_VM" : params[0] is the vm name, params[1]
		// is the pm name
		// if notif type is "MIGRATING_VM" : params[0] is the vm name, params[1]
		// is the origin pm name, params[2] is the destination pn name
		// if notif type is "STATE_CHANGED" : params[0] is the pm/vm name,
		// params[1] is the new state


		Label actionLabel = new Label("unamed label");

		if (type == notifType.CREATING_VM) {
			actionLabel.setValue("Creating vm '" + params[0] + "' on host '"
					+ params[1] + "'");
			this.actionList.add("creating" + params[0]);
		} else if (type == notifType.MIGRATING_VM) {
			actionLabel.setValue("Migrating vm '" + params[0] + "' from host '"
					+ params[1] + "' to host'" + params[2] + "'");
			this.actionList.add("migrating" + params[0] + "to" + params[2]);
		}


		Progressmeter bar = new Progressmeter(0);
		bar.setWidth("100%");

		try {
			Executions.activate(this.desktop);
			try {
				this.layout.appendChild(actionLabel);
				this.layout.appendChild(bar);
			} finally {
				Executions.deactivate(this.desktop);
			}

		} catch (DesktopUnavailableException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void handleNotification(Notification n, Object handback) {

		// System.out.println("type : " + n.getType());
		// System.out.println("handback : " + handback);
		// System.out.println("Source : " + n.getSource());
		// System.out.println("Userdata : " + n.getUserData());

		if (notifType.valueOf(n.getType()) == notifType.ADDED_VM) {
			VirtualMachine vm = (VirtualMachine) n.getSource();

			// System.out.println("notif : " + n.getType());

			int size = this.actionList.size();

			for (int i = 0; i < size; i++) {
				if (this.actionList.get(i).equals("creating" + vm.getName())
						|| this.actionList.get(i).equals(
								"migrating" + vm.getName()+ "to"
								+ vm.getPhysicalMachine().getName())) {

					try {
						Executions.activate(this.desktop);
						try {
							// System.out.println("children before :"
							// + this.layout.getChildren());
							this.layout.getChildren().remove(i * 2 + 1);
							this.layout.getChildren().remove(i * 2);
							// System.out.println("children after :"
							// + this.layout.getChildren());
							this.actionList.remove(i);
						} finally {
							Executions.deactivate(this.desktop);
						}

					} catch (DesktopUnavailableException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					break;
				}
			}
		}


	}

}
