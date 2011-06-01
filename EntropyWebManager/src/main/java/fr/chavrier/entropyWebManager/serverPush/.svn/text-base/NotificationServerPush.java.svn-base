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

import vmscript.api.LifeCycleController.LifeCycleState;
import vmscript.api.PhysicalMachine;
import vmscript.api.VirtualMachine;
import vmscript.executor.ActionAbstract;
import vmscript.executor.ActionAbstract.ActionType;
import vmscript.notifications.ModelNotification.notifType;
import fr.chavrier.entropyWebManager.controls.ActionNotification;
import fr.chavrier.entropyWebManager.controls.ActionNotification.actionNotifType;
import fr.chavrier.entropyWebManager.controls.ErrorNotification;

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

	}

	public void addAction(Notification n) {
		actionNotifType type = actionNotifType.valueOf(n.getType());

		// System.out.println("Adding visual notif");

		Label actionLabel = new Label("unamed label");

		if (type == actionNotifType.ASK_CREATING_VM) {
			String vmName = (String) n.getSource();
			PhysicalMachine pm = (PhysicalMachine) n.getUserData();
			actionLabel.setValue("Creating vm '" + vmName + "' on host '"
					+ pm.getName() + "'");
			this.actionList.add("creating" + vmName);
		} else if (type == actionNotifType.ASK_MIGRATING_VM) {
			VirtualMachine vm = (VirtualMachine) n.getSource();
			PhysicalMachine destinationPm = (PhysicalMachine) n.getUserData();
			actionLabel.setValue("Migrating vm '" + vm.getName()
					+ "' from host '" + vm.getPhysicalMachine().getName()
					+ "' to host'" + destinationPm.getName() + "'");
			this.actionList.add("migrating" + vm.getName() + "to"
					+ destinationPm.getName());
		} else if (type == actionNotifType.ASK_VM_START) {
			VirtualMachine vm = (VirtualMachine) n.getSource();
			actionLabel.setValue("Starting vm: '" + vm.getName() + "'");
			this.actionList.add("starting" + vm.getName());
		} else if (type == actionNotifType.ASK_VM_STOP) {
			VirtualMachine vm = (VirtualMachine) n.getSource();
			actionLabel.setValue("Stoping vm: '" + vm.getName() + "'");
			this.actionList.add("stoping" + vm.getName());
		} else if (type == actionNotifType.ASK_VM_SUSPEND) {
			VirtualMachine vm = (VirtualMachine) n.getSource();
			actionLabel.setValue("Suspending vm: '" + vm.getName() + "'");
			this.actionList.add("suspending" + vm.getName());
		} else if (type == actionNotifType.ASK_VM_RESUME) {
			VirtualMachine vm = (VirtualMachine) n.getSource();
			actionLabel.setValue("Resuming vm: '" + vm.getName() + "'");
			this.actionList.add("resuming" + vm.getName());
		}

		Progressmeter bar = new Progressmeter(0);
		bar.setWidth("100%");

		this.layout.appendChild(actionLabel);
		this.layout.appendChild(bar);

		// System.out.println("Visual notif added");

	}

	public void removeNotifAt(int i) {
		try {
			Executions.activate(this.desktop);
			try {
				this.layout.getChildren().remove(i * 2 + 1);
				this.layout.getChildren().remove(i * 2);
				this.actionList.remove(i);
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

		if (n instanceof ActionNotification) {
			// System.out.println("Receiving action notif");
			addAction(n);
		} else if (n instanceof ErrorNotification) {
		} else {
			// System.out.println("receivging model notif");
			if (notifType.valueOf(n.getType()) == notifType.ADDED_VM) {
				VirtualMachine vm = (VirtualMachine) n.getSource();

				int size = this.actionList.size();

				for (int i = 0; i < size; i++) {
					if (this.actionList.get(i).equals("creating" + vm.getName())
							|| this.actionList.get(i).equals(
									"migrating" + vm.getName()+ "to"
									+ vm.getPhysicalMachine().getName())) {
						removeNotifAt(i);
						break;
					}
				}
			} else if (notifType.valueOf(n.getType()) == notifType.STATE_CHANGED) {
				VirtualMachine vm = (VirtualMachine) n.getSource();

				int size = this.actionList.size();
				if (vm.getState().equals(LifeCycleState.off)) {
					for (int i = 0; i < size; i++) {
						if (this.actionList.get(i).equals(
								"stoping" + vm.getName())) {
							removeNotifAt(i);
							break;
						}
					}
				} else if (vm.getState().equals(LifeCycleState.running)) {
					for (int i = 0; i < size; i++) {
						if (this.actionList.get(i).equals(
								"starting" + vm.getName())
								|| this.actionList.get(i).equals(
										"resuming" + vm.getName())) {
							removeNotifAt(i);
							break;
						}
					}
				} else if (vm.getState().equals(LifeCycleState.suspended)) {
					for (int i = 0; i < size; i++) {
						if (this.actionList.get(i).equals(
								"suspending" + vm.getName())) {
							removeNotifAt(i);
							break;
						}
					}
				}
				for (int i = 0; i < size; i++) {
					if (this.actionList.get(i)
							.equals("creating" + vm.getName())
							|| this.actionList
							.get(i)
							.equals("migrating" + vm.getName() + "to"
									+ vm.getPhysicalMachine().getName())) {
						removeNotifAt(i);
						break;
					}
				}
			} else if (notifType.valueOf(n.getType()) == notifType.ACTION_FAILURE) {

				ActionAbstract aa = (ActionAbstract) n.getSource();
				VirtualMachine vm = (VirtualMachine) aa.getActors().get(0);

				int size = this.actionList.size();
				if (aa.getType().equals(ActionType.MIGRATE)) {
					PhysicalMachine destHost = (PhysicalMachine) aa.getActors()
					.get(2);
					for (int i = 0; i < size; i++) {
						if (this.actionList.get(i).equals(
								"migrating"
								+ vm.getName()
								+ "to"
								+ destHost.getName())) {

							removeNotifAt(i);
							break;
						}
					}
				} else if (aa.getType().equals(ActionType.CREATE_VM)) {
					for (int i = 0; i < size; i++) {
						if (this.actionList.get(i).equals(
								"creating" + vm.getName())) {
							removeNotifAt(i);
							break;
						}
					}
				} else if (aa.getType().equals(ActionType.OFF_VM)) {
					for (int i = 0; i < size; i++) {
						if (this.actionList.get(i).equals(
								"stoping" + vm.getName())) {
							removeNotifAt(i);
							break;
						}
					}
				} else if (aa.getType().equals(ActionType.RUNNING_VM)) {
					for (int i = 0; i < size; i++) {
						if (this.actionList.get(i).equals(
								"starting" + vm.getName())
								|| this.actionList.get(i).equals(
										"resuming" + vm.getName())) {
							removeNotifAt(i);
							break;
						}
					}
				} else if (aa.getType().equals(ActionType.SUSPEND)) {
					for (int i = 0; i < size; i++) {
						if (this.actionList.get(i).equals(
								"suspending" + vm.getName())) {
							removeNotifAt(i);
							break;
						}
					}
				}
			}
		}

	}

}
