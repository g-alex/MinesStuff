package fr.chavrier.entropyWebManager.tree;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.zkoss.zul.SimpleTreeNode;

import vmscript.AllFactories;
import vmscript.VMScriptScheduler;
import vmscript.api.GridElement;
import vmscript.exception.ConfigurationException;
import vmscript.exception.PropertyException;
import vmscript.parser.GridParser;

public class TreeFactory {

	//ArrayList<SimpleTreeNode> invisibleRootChildren;
	public TreeFactory() {
		//nothing to do
	}

	public SimpleTreeNode computeVMSTree() {

		try {

			// new VMScriptProperties("vmscript.properties");

			new AllFactories("vmscript.properties");

			// AllFactories.initialize();
			if (AllFactories.getFactory().getPhysicalTypes().isEmpty()) {
				new GridParser().parse(AllFactories.getProperties()
						.getGridToLoad());
			}

			new Thread(new VMScriptScheduler()).start();

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PropertyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println("test 5");

		// System.out.println(AllFactories.getFactory());
		// System.out.println(AllFactories.getFactory().getPhysicalTypes());
		Set<? extends GridElement> firstTypeSet = AllFactories.getFactory().getFromType(
				AllFactories.getFactory().getPhysicalTypes().get(0));

		List<SimpleTreeNode> invisibleRootChildren = new ArrayList<SimpleTreeNode>();
		SimpleTreeNode invisibleRoot = new SimpleTreeNode("invisible root", invisibleRootChildren);

		for (GridElement element : firstTypeSet) {
			invisibleRootChildren.add(VMSRecursiveCompute(element));
		}

		// System.out.println("test 6");

		return invisibleRoot;

	}

	public SimpleTreeNode VMSRecursiveCompute(GridElement father) {

		List<SimpleTreeNode> fatherChildren = new ArrayList<SimpleTreeNode>();
		SimpleTreeNode fatherNode = new SimpleTreeNode(father, fatherChildren);

		for (GridElement element : father.getLowerElements()) {
			if (!element.getType().equals(
					AllFactories.getFactory().getLastLogicalType())) {
				SimpleTreeNode elementNode = VMSRecursiveCompute(element);
				fatherChildren.add(elementNode);
			} else {
				SimpleTreeNode elementNode = new SimpleTreeNode(element,
						new ArrayList<SimpleTreeNode>());
				fatherChildren.add(elementNode);
			}
		}

		return fatherNode;
	}
}
