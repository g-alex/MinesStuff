package fr.chavrier.entropyWebManager.tree;

import org.zkoss.zul.SimpleTreeModel;
import org.zkoss.zul.SimpleTreeNode;
import org.zkoss.zul.event.TreeDataEvent;

public class IHMTreeModel extends SimpleTreeModel{
	
	public IHMTreeModel(SimpleTreeNode root) {
		super(root);
	}
	
	public void add(Object parent, Object node){
		SimpleTreeNode parentNode =(SimpleTreeNode) parent;
		int size = parentNode.getChildren().size();
		parentNode.getChildren().add(node);
		fireEvent(parent, size, size, TreeDataEvent.INTERVAL_ADDED);
	}
	
	public void remove(Object parent, Object node){
		SimpleTreeNode parentNode =(SimpleTreeNode) parent;
		int size = parentNode.getChildren().size();
		int nodeIndex=-1;
		for(int i=0;i<size;i++){
			if(parentNode.getChildren().get(i)==node){
				nodeIndex = i;
			}
		}
		parentNode.getChildren().remove(nodeIndex);
		fireEvent(parent, nodeIndex, nodeIndex, TreeDataEvent.INTERVAL_REMOVED);
	}
}


