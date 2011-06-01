package fr.chavrier.entropyWebManager.serverPush;

import java.util.ArrayList;
import java.util.List;

import javax.management.Notification;
import javax.management.NotificationListener;

import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.DesktopUnavailableException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.SimpleTreeNode;
import org.zkoss.zul.Tree;

import vmscript.api.VirtualMachine;
import vmscript.notifications.ModelNotification.notifType;
import fr.chavrier.entropyWebManager.controls.ActionNotification;
import fr.chavrier.entropyWebManager.controls.ErrorNotification;
import fr.chavrier.entropyWebManager.tree.IHMTreeModel;

public class TreeUpdateServerPush implements NotificationListener {

	private Tree tree;

	private Desktop desktop;

	public TreeUpdateServerPush(Tree tree, Desktop desktop) {
		this.tree = tree;
		this.desktop = desktop;
	}

	public Desktop getDesktop() {
		return this.desktop;
	}

	public void setDesktop(Desktop desktop) {
		this.desktop = desktop;
	}

	public Tree getTree() {
		return this.tree;
	}

	public void setTree(Tree tree) {
		//System.out.println("set TreeSP : "+tree);
		this.tree = tree;
	}

	public SimpleTreeNode getNode(SimpleTreeNode root, Object o) {
		SimpleTreeNode result = null;

		List<SimpleTreeNode> l = root.getChildren();

		for (SimpleTreeNode node:l){
			//System.out.println("node :"+node+" looking for:"+o);
			if (node.getData() == o){
				return node;
			} else {
				result = getNode(node, o);
				if (result!= null && result.getData() == o) {
					return result;
				}
			}
		}
		return result;
	}

	public void addVM(VirtualMachine vm) {
		//System.out.println("updateTree");

		SimpleTreeNode vmNode = new SimpleTreeNode(vm, new ArrayList<SimpleTreeNode>());
		SimpleTreeNode hostNode = getNode((SimpleTreeNode) this.tree.getModel()
				.getRoot(), vm.getPhysicalMachine());

		try {
			Executions.activate(this.desktop);
			try {
				((IHMTreeModel)this.tree.getModel()).add(hostNode,vmNode);
			} finally {
				Executions.deactivate(this.desktop);
			}
		} catch (DesktopUnavailableException ex) {
			System.out
			.println("The server push thread interrupted (desktop unavailable)");
		} catch (InterruptedException e) {
			System.out.println("The server push thread interrupted");
		}
	}

	public void deleteVM(VirtualMachine vm) {

		//TODO : passage à l'echelle ???
		SimpleTreeNode hostNode = getNode((SimpleTreeNode) this.tree.getModel()
				.getRoot(), vm.getPhysicalMachine());
		SimpleTreeNode vmNode = getNode((SimpleTreeNode)this.tree.getModel().getRoot(), vm);
		//System.out.println("vmNode :"+vmNode);
		try {
			Executions.activate(this.desktop);
			try {
				((IHMTreeModel)this.tree.getModel()).remove(hostNode,vmNode);
			} finally {
				Executions.deactivate(this.desktop);
			}
		} catch (DesktopUnavailableException ex) {
			System.out
			.println("The server push thread interrupted (desktop unavailable)");
		} catch (InterruptedException e) {
			System.out.println("The server push thread interrupted");
		}
	}

	public void handleNotification(Notification n, Object handback) {
		if (n instanceof ErrorNotification) {

		} else if (n instanceof ActionNotification) {

		} else {
			switch (notifType.valueOf(n.getType())) {
			case ADDED_VM:
				addVM((VirtualMachine) n.getSource());
				break;
			case MIGRATING_VM:
				deleteVM((VirtualMachine) n.getSource());
				break;
			}
		}
	}

}
