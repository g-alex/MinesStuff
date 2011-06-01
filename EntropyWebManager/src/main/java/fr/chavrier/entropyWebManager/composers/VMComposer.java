package fr.chavrier.entropyWebManager.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Flashchart;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.SimpleCategoryModel;
import org.zkoss.zul.Timer;

import vmscript.api.VirtualMachine;
import fr.chavrier.entropyWebManager.charts.LineChartEngine;
import fr.chavrier.entropyWebManager.controls.ActionManager;
import fr.chavrier.entropyWebManager.customComponents.MigrateVMWindow;
import fr.chavrier.entropyWebManager.serverPush.PerfReportServerPush;
import fr.chavrier.entropyWebManager.serverPush.Pushable;

public class VMComposer extends GenericForwardComposer implements Pushable,EventSensitiveComposer{

	Label nameLabel;
	Label stateLabel;
	Label ipLabel;

	Label nbCpuLabel;
	Label memorySizeLabel;

	Label usedCpuLabel;
	Label usedMemoryLabel;
	Label freeMemoryLabel;

	Flashchart cpuChart;
	Flashchart ramChart;

	Button startBtn;
	Button stopBtn;
	Button suspendBtn;
	Button migrateBtn;
	Button deleteBtn;
	Button resumeBtn;

	Timer updateTimer;

	LineChartEngine lce = new LineChartEngine();

	VirtualMachine currentVM;

	@Override
	public void doBeforeComposeChildren(Component comp) throws Exception {
		super.doBeforeComposeChildren(comp);
		ComposerEventHandler.setVmComposerListerner(this);
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

		this.startBtn.addEventListener("onClick", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				// VMComposer.this.currentVM.getVMMBean().start();
				// VMComposer.this.currentVM.start();
				ActionManager.vmStart(VMComposer.this.currentVM);
			}
		});

		this.stopBtn.addEventListener("onClick", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				// VMComposer.this.currentVM.getVMMBean().shutdown();
				// System.out.println("stop button pressed");
				// VMComposer.this.currentVM.stop();
				ActionManager.vmStop(VMComposer.this.currentVM);
			}
		});

		this.suspendBtn.addEventListener("onClick", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				// VMComposer.this.currentVM.getVMMBean().suspend();
				// VMComposer.this.currentVM.suspend();
				ActionManager.vmSuspend(VMComposer.this.currentVM);
			}
		});

		this.migrateBtn.addEventListener("onClick", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				//TODO
				MigrateVMWindow win = (MigrateVMWindow) Executions.createComponents("./VM/migrateVMpopup.zul", null, null);
				win.setCurrentVM(VMComposer.this.currentVM);
				win.doModal();
			}
		});

		this.deleteBtn.addEventListener("onClick", new EventListener() {
			public void onEvent(Event arg0) throws Exception {

				Messagebox
				.show("This will delete this VM and all the datas it contains. Are you sure?",
						"Destroy this VM", Messagebox.OK
						| Messagebox.CANCEL,
						Messagebox.EXCLAMATION,
						new EventListener() {
					public void onEvent(Event evt) {
						switch (((Integer) evt.getData()).intValue()) {
						case Messagebox.OK:
							ActionManager
							.vmDestroy(VMComposer.this.currentVM);
							break;
						case Messagebox.CANCEL:
							break;
						}
					}
				});
			}
		});
		this.resumeBtn.addEventListener("onClick", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				// VMComposer.this.currentVM.getVMMBean().resume();
				// VMComposer.this.currentVM.resume();
				ActionManager.vmResume(VMComposer.this.currentVM);
			}
		});

		this.updateTimer.addEventListener("onTimer", new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				update();
			}
		});
	}

	public void update(){
		this.nameLabel.setValue("Name : "+this.currentVM.getName());
		this.stateLabel.setValue("State : "
				+ this.currentVM.getState().toString());


		this.ipLabel.setValue("IP Address : "+this.currentVM.getIp());

		updateCpuChart();

		updateRamChart();

		;

		this.nbCpuLabel.setValue("Number of virtual CPU : "
				+ this.currentVM.getCpuNumber());

		this.memorySizeLabel.setValue("Memory size : "
				+ this.currentVM.getMemory());

		this.usedMemoryLabel.setValue("Used memory : "
				+ this.currentVM.getMemoryCons());

		this.usedCpuLabel.setValue("CPU usage : " + this.currentVM.getCpuCons()
				+ " Mhz");

	}

	public void onSelect(Object o) {
		this.currentVM = (VirtualMachine) o;
		PerfReportServerPush.setCurrentItem(this);
	}

	// public void startVM(){
	// // System.out.println("button start VM clicked");
	// try {
	// this.currentVM.start();
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	public void updateCpuChart() {
		// System.out.println("update chart");
		// System.out.println("taille de la liste :"
		// + this.currentVM.getCpuHistory().size());
		int size = this.currentVM.getCpuHistory().size();
		SimpleCategoryModel model;
		if (this.cpuChart.getModel() != null) {
			model = (SimpleCategoryModel) this.cpuChart.getModel();
		} else {
			model = new SimpleCategoryModel();
		}
		for (int i = 0; i < size; i++) {
			model.setValue("CPU in MHz", i, new Integer(this.currentVM
					.getCpuHistory().get(i)));
		}

		this.cpuChart.setModel(model);
	}

	public void updateRamChart() {
		int size = 15;
		SimpleCategoryModel model;
		if (this.ramChart.getModel() != null) {
			model = (SimpleCategoryModel) this.ramChart.getModel();
		} else {
			model = new SimpleCategoryModel();
		}
		for (int i = 0; i < size; i++) {
			model.setValue("RAM", i,
					new Integer(this.currentVM.getMemoryCons()));
		}

		this.ramChart.setModel(model);
	}

}
