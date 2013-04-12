/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mumblemoderator.ui;

import java.util.Comparator;
import java.util.logging.Level;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import mumblemoderator.util.Util;

/**
 *
 * @author ffitzke
 */
class SortTreeModel extends DefaultTreeModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8621764245203005349L;
	private Comparator comparator;
	private final long starttime = System.currentTimeMillis();
	
	class DefaultComparator implements Comparator {
		@Override
		public int compare(Object o1, Object o2) {
			if ((o1 instanceof UserTreeNode) && (o2 instanceof UserTreeNode)) {
				UserTreeNode tmp_o1 = (UserTreeNode)o1;
				UserTreeNode tmp_o2 = (UserTreeNode)o2;
				return compare(tmp_o1, tmp_o2);
				
			}

			if ((o1 instanceof ChannelTreeNode) && (o2 instanceof ChannelTreeNode)) {
				ChannelTreeNode tmp_o1 = (ChannelTreeNode)o1;
				ChannelTreeNode tmp_o2 = (ChannelTreeNode)o2;
				return compare(tmp_o1, tmp_o2);
				
			}
			
//			return o1.toString().compareToIgnoreCase(o2.toString()); // Benutzer und Channel gleichbrechtigt
			return 1; // Benutzer unter Channel
//			return 0; // Benutzer Ã¼ber Channel

		}

		private int compare(UserTreeNode u1, UserTreeNode u2) {
//			return u1.getName().compareToIgnoreCase(u2.getName());
			Util.getLogger(SortTreeModel.class).log(Level.FINE, "\n" +
					u1.getName() + ":\t" + (u1.getStartTime() - starttime) + "\n" + 
					u2.getName() + ":\t" + (u2.getStartTime() - starttime) + "\n" + 
					(u1.getName().compareToIgnoreCase(u2.getName())) + "\t" + (u1.getStartTime() - u2.getStartTime()));

			if (u2.getStartTime() == u1.getStartTime()) {
				return 0;

			}
//			Util.getLogger(SortTreeModel.class).log(Level.INFO, "\n" + u1.getName() + ":\t");

			return (int)(u2.getStartTime() - u1.getStartTime());
	
		}

		private int compare(ChannelTreeNode u1, ChannelTreeNode u2) {
			return u1.getName().compareToIgnoreCase(u2.getName());
//			return u1.getId() - u2.getId();
//			return u2.getId() - u1.getId();

		}

	}
	

	public SortTreeModel(TreeNode root, boolean asksAllowsChildren) {
		super(root, asksAllowsChildren);
		comparator = new DefaultComparator();

	}

	
	public SortTreeModel(TreeNode root) {
		super(root);
		comparator = new DefaultComparator();

	}

	
	public SortTreeModel(TreeNode node, Comparator c) {
		super(node);
		comparator = c;

	}

	
	public SortTreeModel(TreeNode node, boolean asksAllowsChildren, Comparator c) {
		super(node, asksAllowsChildren);
		comparator = c;

	}

	
	public void insertNodeInto(MutableTreeNode child, MutableTreeNode parent) {
		int index = findIndexFor(child, parent);
		super.insertNodeInto(child, parent, index);

	}


	@Override
	public void insertNodeInto(MutableTreeNode child, MutableTreeNode par, int i) {
		// The index is useless in this model, so just ignore it.
		insertNodeInto(child, par);

	}
	
	
	private int findIndexFor(MutableTreeNode child, MutableTreeNode parent) {
		int cc = parent.getChildCount();
		if (cc == 0) {
			return 0;

		}
		if (cc == 1) {
			return comparator.compare(child, parent.getChildAt(0)) <= 0 ? 0 : 1;

		}
		return findIndexFor(child, parent, 0, cc - 1);

	}


	private int findIndexFor(MutableTreeNode child, MutableTreeNode parent, int i1, int i2) {
		if (i1 == i2) {
			return comparator.compare(child, parent.getChildAt(i1)) <= 0 ? i1 : i1 + 1;

		}
		int half = (i1 + i2) / 2;
		if (comparator.compare(child, parent.getChildAt(half)) <= 0) {
			return findIndexFor(child, parent, i1, half);

		}
		return findIndexFor(child, parent, half + 1, i2);

	}
	
}


