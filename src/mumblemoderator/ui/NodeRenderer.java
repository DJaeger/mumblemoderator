package mumblemoderator.ui;

import java.awt.Component;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import mumblemoderator.service.model.Channel;
import mumblemoderator.service.model.User;

public class NodeRenderer extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = -569140478218229434L;
	URL userimageURL = DynamicTree.class.getResource("user.gif");
	private ImageIcon userimage = new ImageIcon(userimageURL);
	URL channelimageURL = DynamicTree.class.getResource("channel.gif");
	private ImageIcon channelimage = new ImageIcon(channelimageURL);

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {

		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
				row, hasFocus);

		if (value instanceof DefaultMutableTreeNode) {
			DefaultMutableTreeNode tmp = (DefaultMutableTreeNode)value;
			if (tmp.getUserObject() instanceof Channel) {
				this.setIcon(channelimage);

			} else if (tmp.getUserObject() instanceof User) {
				this.setIcon(userimage);

			}

		}
			

		return this;

	}
}
