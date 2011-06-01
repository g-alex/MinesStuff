package fr.chavrier.entropyWebManager.composers;

import javax.management.Notification;
import javax.management.NotificationListener;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.DesktopUnavailableException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Flashchart;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.SimpleCategoryModel;

import vmscript.AllFactories;
import vmscript.api.GridElement;
import vmscript.api.PhysicalMachine;
import vmscript.api.VirtualMachine;
import fr.chavrier.entropyWebManager.customComponents.CreateVMWindow;
import fr.chavrier.entropyWebManager.serverPush.PerfReportServerPush;
import fr.chavrier.entropyWebManager.serverPush.Pushable;

public class HostComposer extends GenericForwardComposer implements
NotificationListener, Pushable, EventSensitiveComposer {

	PhysicalMachine currentHost;
	Listbox vmsLB;

	Label nameLabel;
	Label nbCpuLabel;
	Label cpuArchiLabel;
	Label cpuSpeedLabel;
	Label memorySizeLabel;

	Label usedCpuLabel;
	Label usedMemoryLabel;

	Flashchart cpuChart;
	Flashchart ramChart;

	Button createVMBtn;

	public void onSelect(Object o) {
		this.currentHost = (PhysicalMachine) o;
		refreshVMList();
		AllFactories.getNotifications().addNotificationListener(this, null,
				null);
		PerfReportServerPush.setCurrentItem(this);
	}

	public void refreshVMList(){
		// System.out.println("refresh vm list !");
		int size = this.vmsLB.getItemCount();
		for(int i = 0;i<size;i++){
			//System.out.println("vm in list : "+vmsLB.getItemAtIndex(i));
			this.vmsLB.removeItemAt(size-1-i);
		}
		for (GridElement vmge : this.currentHost.getLowerElements()) {
			VirtualMachine vm = (VirtualMachine) vmge;
			Listitem li = new Listitem();
			li.setParent(this.vmsLB);

			Listcell nameLC = new Listcell(vm.getName());
			nameLC.setParent(li);
			Listcell cpuLC = new Listcell(vm.getCpuCons() + " ");
			cpuLC.setParent(li);
			Listcell ramLC = new Listcell(vm.getMemoryCons() + "/"
					+ vm.getMemory());
			ramLC.setParent(li);
			Listcell stateLC = new Listcell(vm.getState().toString());
			stateLC.setParent(li);

		}
	}

	public void update() {
		// System.out.println("update host");
		updateCpuChart();
		updateRamChart();
		refreshVMList();

		int i = 0;
		for (GridElement vmge : this.currentHost.getLowerElements()) {
			VirtualMachine vm = (VirtualMachine) vmge;
			((Listcell) this.vmsLB.getItemAtIndex(i).getChildren().get(1))
			.setLabel(vm.getCpuCons() + " ");
			((Listcell) this.vmsLB.getItemAtIndex(i).getChildren().get(2))
			.setLabel(vm.getMemoryCons() + "/" + vm.getMemory());
			((Listcell) this.vmsLB.getItemAtIndex(i).getChildren().get(3))
			.setLabel(vm.getState().toString());
			i++;
		}


		// this.nameLabel.setValue("Host name : " + this.currentHost.getName());
		// this.nbCpuLabel.setValue("Number of CPU : "+this.currentHost.getCpuNumber());
		// this.cpuArchiLabel.setValue("CPU architecture : "
		// + this.currentHost.getCpuArch());
		// this.cpuSpeedLabel.setValue("CPU speed : " +
		// this.currentHost.getCpu());
		// this.memorySizeLabel.setValue("Memory size : "
		// + this.currentHost.getMemory());
		// this.usedCpuLabel.setValue("CPU usage : "
		// + this.currentHost.getCpuCons());
		// this.usedMemoryLabel.setValue("Free used : "
		// + this.currentHost.getMemoryCons());
	}

	public void updateCpuChart(){

		// System.out.println("update chart");
		// System.out.println("taille de la liste :"
		// + this.currentHost.getCpuHistory().size());

		int size = this.currentHost.getCpuHistory().size();
		SimpleCategoryModel model;
		if (this.cpuChart.getModel() != null) {
			model = (SimpleCategoryModel) this.cpuChart.getModel();
		} else {
			model = new SimpleCategoryModel();
		}
		for (int i = 0; i < size; i++) {
			model.setValue("CPU in MHz", i, new Integer(this.currentHost
					.getCpuHistory().get(i)));
		}

		this.cpuChart.setModel(model);
	}

	public void updateRamChart(){
		int size = 15;
		SimpleCategoryModel model;
		if (this.ramChart.getModel() != null) {
			model = (SimpleCategoryModel) this.ramChart.getModel();
		} else {
			model = new SimpleCategoryModel();
		}
		for (int i = 0; i < size; i++) {
			model.setValue("RAM", i,
					new Integer(this.currentHost.getMemoryCons()));
		}

		this.ramChart.setModel(model);
	}

	@Override
	public void doBeforeComposeChildren(Component comp) throws Exception {

		super.doBeforeComposeChildren(comp);
		ComposerEventHandler.setHostComposerListerner(this);
		try {
			PerfReportServerPush.start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		this.cpuChart.setType("line");
		this.ramChart.setType("line");

		this.createVMBtn.addEventListener("onClick", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				CreateVMWindow win = (CreateVMWindow) Executions.createComponents("./host/newVMpopup.zul", null, null);
				win.setCurrentHost(HostComposer.this.currentHost);
				win.doModal();
			}
		});
	}

	public void handleNotification(Notification arg0, Object arg1) {
		try {
			Executions.activate(this.desktop);
			try {
				refreshVMList();
			} finally {
				Executions.deactivate(this.desktop);
			}
		} catch (DesktopUnavailableException ex) {
			System.out.println("The server push thread interrupted");
		} catch (InterruptedException e) {
			System.out.println("The server push thread interrupted");
		}
	}

}
