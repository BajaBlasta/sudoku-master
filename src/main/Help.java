import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

public class Help extends JPanel implements TreeSelectionListener {

	private static final long serialVersionUID = 1L;
	private JEditorPane htmlPane;
	private JTree tree;
	private URL helpURL;

	public Help() {
		super(new GridLayout(1,0));

		//Create the nodes.
		DefaultMutableTreeNode top = createNodes();

		//Create a tree that allows one selection at a time.
		tree = new JTree(top);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		//Listen for when the selection changes.
		tree.addTreeSelectionListener(this);

		//Create the scroll pane and add the tree to it. 
		JScrollPane treeView = new JScrollPane(tree);

		//Create the HTML viewing pane.
		htmlPane = new JEditorPane();
		htmlPane.setEditable(false);
		initHelp();
		JScrollPane htmlView = new JScrollPane(htmlPane);

		//Add the scroll panes to a split pane.
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setTopComponent(treeView);
		splitPane.setBottomComponent(htmlView);

		Dimension minimumSize = new Dimension(100, 50);
		htmlView.setMinimumSize(minimumSize);
		treeView.setMinimumSize(minimumSize);
		splitPane.setDividerLocation(200); 
		splitPane.setPreferredSize(new Dimension(700, 500));

		//Add the split pane to this panel.
		add(splitPane);
	}

	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

		if (node == null)
			return;

		Object nodeInfo = node.getUserObject();
		if (node.isLeaf()) {
			HelpItem item = (HelpItem)nodeInfo;
			displayURL(item.url);
		} else {
			displayURL(helpURL); 
		}
	}

	private class HelpItem {
		public String title;
		public URL url;

		public HelpItem(String title, String filename) {
			this.title = title;
			url = getClass().getResource(filename);
			if (url == null) {
				System.err.println("Couldn't find file: " + filename);
			}
		}

		public String toString() {
			return title;
		}
	}

	private void initHelp() {
		String s = "help.md.html";
		helpURL = getClass().getResource(s);
		if (helpURL == null) {
			System.err.println("Couldn't open help file: " + s);
		}

		displayURL(helpURL);
	}

	private void displayURL(URL url) {
		try {
			if (url != null) {
				htmlPane.setPage(url);
			} else { //null url
				htmlPane.setText("File Not Found");
			}
		} catch (IOException e) {
			System.err.println("Attempted to read a bad URL: " + url);
		}
	}

	private DefaultMutableTreeNode createNodes() {
		DefaultMutableTreeNode top = new DefaultMutableTreeNode(new HelpItem("Help", "help.md.html"));
		
		DefaultMutableTreeNode rules = new DefaultMutableTreeNode(new HelpItem("Rules", "rules.md.html"));
		top.add(rules);
		
		DefaultMutableTreeNode howto = new DefaultMutableTreeNode(new HelpItem("How To Play", "howto.md.html"));
		top.add(howto);
		
		return top;
	}

	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Help");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(new Help());
		frame.pack();
		frame.setVisible(true);
	}

	public static void showHelp() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
