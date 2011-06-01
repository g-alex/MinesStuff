package fr.chavrier.entropyWebManager.composers;

import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.KeyEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timer;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.api.Iframe;
import org.zkoss.zul.api.Panel;

import vmscript.AllFactories;
import vmscript.api.GridElement;
import vmscript.api.Machine;
import fr.chavrier.entropyWebManager.controls.EventManager;
import fr.chavrier.entropyWebManager.serverPush.AlertServerPush;
import fr.chavrier.entropyWebManager.serverPush.NotificationServerPush;
import fr.chavrier.entropyWebManager.serverPush.TreeUpdateServerPush;
import fr.chavrier.entropyWebManager.tree.IHMTreeModel;
import fr.chavrier.entropyWebManager.tree.IHMTreeitemRenderer;
import fr.chavrier.entropyWebManager.tree.TreeFactory;

public class IndexComposer extends GenericForwardComposer {

	Tree tree;
	IHMTreeModel model;
	IHMTreeitemRenderer treeRenderer;
	Object selectedItem;
	Include include;
	Tab monitoredItem;

	Panel treePanel;

	Vlayout actionVLayout;
	Vlayout alertVLayout;

	Radio stopRadio;
	Radio semiRadio;
	Radio autoRadio;

	Menuitem helpBtn;
	Menuitem aboutBtn;

	Textbox consoleInput;
	Vlayout consoleOutput;

	Button applyBtn;
	Button notApplyBtn;

	Iframe guacamole;

	Timer timer;

	Registry registry;

	final int COMMANDLINES = 500;
	int commandNumber = 0;
	List<String> commandHistory = new ArrayList<String>();

	static final String NEWLINE = System.getProperty("line.separator");

	public void onClick$VNCTab(Event event) {
		if (this.selectedItem instanceof Machine) {
			String ip = ((Machine) this.selectedItem).getIp();
			try {
				((ServletContext) this.application.getNativeContext())
				.getContext("/guacamole").setAttribute("host", ip);
				this.guacamole.setSrc("http://localhost:8080/guacamole"); // TODO
				// put
				// this
				// in
				// some
				// config
				// file
			} catch (Exception e) {
				EventManager.emitErrorMessage(e.toString());
			}
		} else {
			this.guacamole.setSrc(null);
		} // if
		this.guacamole.invalidate();
	} // void onClick$VNCTab(Event)


	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		// File f = new File(".");
		// System.out.println(f.getAbsolutePath());

		if (!this.desktop.isServerPushEnabled()) {
			System.out.println("Server push not already started : starting");
			this.desktop.enableServerPush(true);
		}

		this.treeRenderer = new IHMTreeitemRenderer();
		this.tree.setTreeitemRenderer(this.treeRenderer);

		TreeFactory treeFactory = new TreeFactory();
		this.model = new IHMTreeModel(treeFactory.computeVMSTree());
		this.tree.setModel(this.model);

		EventListener el = new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				onSelect();
			}
		};
		this.tree.addEventListener("onSelect", el);

		TreeUpdateServerPush treeServerPush = new TreeUpdateServerPush(
				this.tree, this.desktop);

		NotificationServerPush notifServerPush = new NotificationServerPush(
				this.actionVLayout, this.desktop);

		AlertServerPush alertServerPush = new AlertServerPush(
				this.alertVLayout, this.desktop);

		EventManager manager = new EventManager();
		EventManager.addIhmListener(notifServerPush);
		EventManager.addModelListener(notifServerPush);
		EventManager.addModelListener(treeServerPush);
		EventManager.addModelListener(alertServerPush);

		AllFactories.getNotifications().addNotificationListener(manager, null,
				null);

		this.consoleInput.addEventListener(org.zkoss.zk.ui.event.Events.ON_OK,
				new EventListener() {
			public void onEvent(Event event) throws Exception {

				IndexComposer.this.commandNumber = 0;

				String commandStr = IndexComposer.this.consoleInput
				.getValue();
				IndexComposer.this.addToHistory(commandStr);

				if (commandStr.equals("clear")) {
					IndexComposer.this.consoleOutput.getChildren()
					.clear();
					IndexComposer.this.consoleInput.setValue("");
				} else {

					Label command = new Label(commandStr);
					Div commandDiv = new Div();
					commandDiv.appendChild(command);

					try {
						Label commandAnswer = new Label(AllFactories
								.getConsole()
								.analyze(commandStr, false).toString());

						Div commandAnswerDiv = new Div();
						commandAnswerDiv.appendChild(commandAnswer);

						IndexComposer.this.consoleOutput
						.appendChild(commandDiv);
						IndexComposer.this.consoleOutput
						.appendChild(commandAnswerDiv);
						Clients.scrollIntoView(commandAnswerDiv);
						IndexComposer.this.consoleInput.setValue("");
					} catch (vmscript.exception.ResultException e) {
						Label commandAnswer = new Label(e.toString());
						Div commandAnswerDiv = new Div();
						commandAnswerDiv.appendChild(commandAnswer);
						IndexComposer.this.consoleOutput
						.appendChild(commandAnswerDiv);
						Clients.scrollIntoView(commandAnswerDiv);
						IndexComposer.this.consoleInput.setValue("");
					}
				}
			}
		});

		this.consoleInput.addEventListener("onCtrlKey", new EventListener() {
			public void onEvent(Event event) throws Exception {
				KeyEvent ke = (KeyEvent) event;
				int kc = ke.getKeyCode();
				switch (kc) {
				case KeyEvent.DOWN:
					if (IndexComposer.this.commandNumber > 0) {
						IndexComposer.this.commandNumber--;
						IndexComposer.this.consoleInput
						.setValue(IndexComposer.this.commandHistory
								.get(IndexComposer.this.commandNumber));
					} else if (IndexComposer.this.commandNumber == 0) {
						IndexComposer.this.consoleInput.setValue("");
					}
					break;
				case KeyEvent.UP:
					if (IndexComposer.this.commandNumber < IndexComposer.this.commandHistory
							.size()) {
						IndexComposer.this.consoleInput
						.setValue(IndexComposer.this.commandHistory
								.get(IndexComposer.this.commandNumber));
						IndexComposer.this.commandNumber++;
					}
					break;
				}
			}
		});

	}

	public void addToHistory(String command) {
		this.commandHistory.add(0, command);
		if (this.commandHistory.size() > this.COMMANDLINES) {
			this.commandHistory.remove(this.commandHistory.size());
		}
	}

	public void setPageToDisplay(String page){
		this.include.setSrc(page);
	}

	public void onSelect(){

		this.selectedItem = this.tree.getSelectedItem().getValue();
		if (((GridElement) this.selectedItem).getType().equals(
				AllFactories.getFactory().getLastLogicalType())) {

			setPageToDisplay("./VM/vm.zul");
			this.monitoredItem.setLabel(((GridElement) this.selectedItem)
					.getName());
			ComposerEventHandler.fireOnSelectNotification(
					ComposerEventHandler.getVmComposerListerner(),
					this.selectedItem);


		} else if (((GridElement) this.selectedItem).getType().equals(
				AllFactories.getFactory().getLastPhysicalType())) {

			setPageToDisplay("./host/host.zul");
			this.monitoredItem.setLabel(((GridElement) this.selectedItem)
					.getName());
			ComposerEventHandler.fireOnSelectNotification(ComposerEventHandler.getHostComposerListerner(), this.selectedItem);

		} else {
			setPageToDisplay("error404.zul");
		}

	}
}
