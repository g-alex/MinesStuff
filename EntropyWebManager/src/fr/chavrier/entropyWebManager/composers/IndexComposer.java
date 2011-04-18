package fr.chavrier.entropyWebManager.composers;

import java.rmi.RMISecurityManager;
import java.rmi.registry.Registry;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.KeyEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Center;
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

import vmscript.AllFactories;
import vmscript.api.GridElement;
import fr.chavrier.entropyWebManager.controls.EventManager;
import fr.chavrier.entropyWebManager.serverPush.NotificationServerPush;
import fr.chavrier.entropyWebManager.serverPush.TreeUpdateServerPush;
import fr.chavrier.entropyWebManager.tree.IHMTreeModel;
import fr.chavrier.entropyWebManager.tree.IHMTreeitemRenderer;
import fr.chavrier.entropyWebManager.tree.TreeFactory;
import javax.servlet.ServletContext;
import org.zkoss.zul.Iframe;
import vmscript.api.Machine;

public class IndexComposer extends GenericForwardComposer {

    Tree tree;
    IHMTreeModel model;
    IHMTreeitemRenderer treeRenderer;
    Object selectedItem;
    Include include;
    Center mainFrame;
    Tab monitoredItem;
    Tab VNCTab;
    Vlayout actionVLayout;
    Radio stopRadio;
    Radio semiRadio;
    Radio autoRadio;
    Menuitem helpBtn;
    Menuitem aboutBtn;
    Textbox consoleInput;
    Vlayout consoleOutput;
    Button applyBtn;
    Button notApplyBtn;
    Label planLabel;
    Timer timer;
    Registry registry;
    Iframe guacamole;
    final int COMMANDLINES = 500;
    int commandNumber = 0;
    List<String> commandHistory = new ArrayList<String>();
    static final String NEWLINE = System.getProperty("line.separator");

    @Override
    public void doBeforeComposeChildren(Component comp) throws Exception {
        super.doBeforeComposeChildren(comp);

        if (System.getSecurityManager() == null) {
            RMISecurityManager securityManager = new RMISecurityManager() {

                @Override
                public void checkConnect(String host, int port) {
                }

                @Override
                public void checkConnect(String host, int port, Object context) {
                }

                @Override
                public void checkPermission(Permission perm) {
                }

                @Override
                public void checkPermission(Permission perm, Object context) {
                }
            };
            System.setSecurityManager(securityManager);
        }

    }

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



        // System.out.println("test 1");

        TreeFactory treeFactory = new TreeFactory();
        this.model = new IHMTreeModel(treeFactory.computeVMSTree());
        this.tree.setModel(this.model);

        // System.out.println("test 2");

        this.tree.setWidth("100%");
        this.tree.setStyle("border:0px");
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

        EventManager manager = new EventManager();
        EventManager.addNotificationListener(notifServerPush);
        EventManager.addNotificationListener(treeServerPush);

        AllFactories.getNotifications().addNotificationListener(manager, null,
                null);

        this.helpBtn.addEventListener("onClick", new EventListener() {

            public void onEvent(Event arg0) throws Exception {
                alert("help");
            }
        });


        this.aboutBtn.addEventListener("onClick", new EventListener() {

            public void onEvent(Event arg0) throws Exception {
                alert("about");
            }
        });

        this.consoleInput.addEventListener(
                org.zkoss.zk.ui.event.Events.ON_OK,
                new EventListener() {

                    public void onEvent(Event event) throws Exception {

                        IndexComposer.this.commandNumber = 0;

                        String commandStr = IndexComposer.this.consoleInput.getValue();
                        IndexComposer.this.addToHistory(commandStr);


                        // System.out.println("commande : '" + commandStr +
                        // "'");

                        if (commandStr.equals("clear")) {
                            IndexComposer.this.consoleOutput.getChildren().clear();
                            IndexComposer.this.consoleInput.setValue("");
                        } else {

                            Label command = new Label(commandStr);
                            Div commandDiv = new Div();
                            commandDiv.appendChild(command);

                            try {
                                Label commandAnswer = new Label(AllFactories.getConsole().analyze(commandStr, false).toString());

                                Div commandAnswerDiv = new Div();
                                commandAnswerDiv.appendChild(commandAnswer);

                                IndexComposer.this.consoleOutput.appendChild(commandDiv);
                                IndexComposer.this.consoleOutput.appendChild(commandAnswerDiv);
                                Clients.scrollIntoView(commandAnswerDiv);
                                IndexComposer.this.consoleInput.setValue("");
                            } catch (vmscript.exception.ResultException e) {
                                // System.out.println("result exeception catched");
                                Label commandAnswer = new Label(e.toString());
                                Div commandAnswerDiv = new Div();
                                commandAnswerDiv.appendChild(commandAnswer);
                                IndexComposer.this.consoleOutput.appendChild(commandAnswerDiv);
                                Clients.scrollIntoView(commandAnswerDiv);
                                IndexComposer.this.consoleInput.setValue("");
                            }
                        }
                    }
                });

        this.consoleInput.addEventListener("onCtrlKey",
                new EventListener() {

                    public void onEvent(Event event) throws Exception {
                        KeyEvent ke = (KeyEvent) event;
                        int kc = ke.getKeyCode();
                        switch (kc) {
                            case KeyEvent.DOWN:
                                if (IndexComposer.this.commandNumber > 0) {
                                    IndexComposer.this.commandNumber--;
                                    IndexComposer.this.consoleInput.setValue(IndexComposer.this.commandHistory.get(IndexComposer.this.commandNumber));
                                } else if (IndexComposer.this.commandNumber == 0) {
                                    IndexComposer.this.consoleInput.setValue("");
                                }
                                break;
                            case KeyEvent.UP:
                                if (IndexComposer.this.commandNumber < IndexComposer.this.commandHistory.size()) {
                                    IndexComposer.this.consoleInput.setValue(IndexComposer.this.commandHistory.get(IndexComposer.this.commandNumber));
                                    IndexComposer.this.commandNumber++;
                                }
                                break;
                        }
                    }
                });

        // this.stopRadio.addEventListener("onCheck", new EventListener() {
        // public void onEvent(Event arg0) throws Exception {
        // IndexComposer.this.applyBtn.setDisabled(true);
        // IndexComposer.this.notApplyBtn.setDisabled(true);
        // try {
        // IndexComposer.this.registry = LocateRegistry.getRegistry(48888);
        // ControlerInterface stub = (ControlerInterface)
        // IndexComposer.this.registry.lookup("myRMI");
        // stub.setEntropyMode("stop");
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // }
        // });
        //
        // this.semiRadio.addEventListener("onCheck", new EventListener() {
        // public void onEvent(Event arg0) throws Exception {
        // IndexComposer.this.applyBtn.setDisabled(false);
        // IndexComposer.this.notApplyBtn.setDisabled(false);
        // try {
        // IndexComposer.this.registry = LocateRegistry.getRegistry(48888);
        // ControlerInterface stub = (ControlerInterface)
        // IndexComposer.this.registry.lookup("myRMI");
        // stub.setEntropyMode("semi");
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // }
        // });
        //
        // this.autoRadio.addEventListener("onCheck", new EventListener() {
        // public void onEvent(Event arg0) throws Exception {
        // IndexComposer.this.applyBtn.setDisabled(true);
        // IndexComposer.this.notApplyBtn.setDisabled(true);
        // try {
        // IndexComposer.this.registry = LocateRegistry.getRegistry(48888);
        // ControlerInterface stub = (ControlerInterface)
        // IndexComposer.this.registry.lookup("myRMI");
        // stub.setEntropyMode("auto");
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // }
        // });
        //
        // this.applyBtn.setDisabled(true);
        // this.applyBtn.addEventListener("onClick", new EventListener() {
        // public void onEvent(Event arg0) throws Exception {
        // try {
        // IndexComposer.this.registry = LocateRegistry.getRegistry(48888);
        // ControlerInterface stub = (ControlerInterface)
        // IndexComposer.this.registry.lookup("myRMI");
        // stub.setAction("continue");
        // stub.setPlan(null);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // }
        // });
        //
        // this.notApplyBtn.setDisabled(true);
        // this.notApplyBtn.addEventListener("onClick", new EventListener() {
        // public void onEvent(Event arg0) throws Exception {
        // try {
        // IndexComposer.this.registry = LocateRegistry.getRegistry(48888);
        // ControlerInterface stub = (ControlerInterface)
        // IndexComposer.this.registry.lookup("myRMI");
        // stub.setAction("recompute");
        // stub.setPlan(null);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // }
        // });
        //
        // this.timer.addEventListener("onTimer", new EventListener() {
        // public void onEvent(Event arg0) throws Exception {
        // //System.out.println("timer on 1");
        // IndexComposer.this.planLabel.setValue(null);
        // try {
        // //System.out.println("timer on 2");
        // IndexComposer.this.registry = LocateRegistry.getRegistry(48888);
        // ControlerInterface stub = (ControlerInterface)
        // IndexComposer.this.registry.lookup("myRMI");
        //
        // if(stub.getEntropyMode().equals("auto")){
        // //System.out.println("auto");
        // if(stub.getPlan()!=null){
        // IndexComposer.this.planLabel.setValue(stub.getPlan());
        // stub.setPlan(null);
        // }
        // stub.setAction("continue");
        // }else if(stub.getEntropyMode().equals("semi")){
        // //System.out.println("semi");
        // if (stub.getPlan() != null) {
        // IndexComposer.this.planLabel.setValue(stub.getPlan());
        //
        // }
        // } else if(stub.getEntropyMode().equals("stop")){
        // //System.out.println("stop");
        // stub.setAction("undefined");
        // }
        // } catch (Exception e) {
        // //e.printStackTrace();
        // }
        // }
        // });

    }

    public void onClick$VNCTab(Event event) {
        if (this.selectedItem instanceof Machine) {
            String ip = ((Machine) this.selectedItem).getIp();
            ((ServletContext) this.application.getNativeContext()).getContext("/guacadev").setAttribute("host", ip);
            this.guacamole.setSrc("http://localhost:8080/guacadev");
        } else {
            this.guacamole.setSrc(null);
        }
        this.guacamole.invalidate();
    }

    public void addToHistory(String command) {
        this.commandHistory.add(0, command);
        if (this.commandHistory.size() > this.COMMANDLINES) {
            this.commandHistory.remove(this.commandHistory.size());
        }
    }

    public void setPageToDisplay(String page) {
        this.include.setSrc(page);
    }

    public void onSelect() {

        this.selectedItem = this.tree.getSelectedItem().getValue();
        if (((GridElement) this.selectedItem).getType().equals(
                AllFactories.getFactory().getLastLogicalType())) {

            setPageToDisplay("./VM/vm.zul");
            this.monitoredItem.setLabel(((GridElement) this.selectedItem).getName());
            ComposerEventHandler.fireOnSelectNotification(
                    ComposerEventHandler.getVmComposerListerner(),
                    this.selectedItem);


        } else if (((GridElement) this.selectedItem).getType().equals(
                AllFactories.getFactory().getLastPhysicalType())) {

            setPageToDisplay("./host/host.zul");
            this.monitoredItem.setLabel(((GridElement) this.selectedItem).getName());
            ComposerEventHandler.fireOnSelectNotification(ComposerEventHandler.getHostComposerListerner(), this.selectedItem);

        } else {
            setPageToDisplay("error404.zul");
        }

    }
}
