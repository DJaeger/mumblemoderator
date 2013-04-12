/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mumblemoderator.ui;

import java.util.Comparator;
import javax.swing.tree.DefaultMutableTreeNode;
import mumblemoderator.service.model.User;

/**
 *
 * @author frank
 */
class UserTreeNode extends DefaultMutableTreeNode implements Comparable<UserTreeNode> {

	private static final long serialVersionUID = -4102779706790084833L;
	private final String _name;

	public UserTreeNode(Object userObject) {
		super(userObject);
		_name = userObject.toString();
	}

	public UserTreeNode(User userObject) {
		super(userObject);
		_name = userObject.name;
	}

	public String getName(){
		if (getUserObject() instanceof User) {
			User user = (User)getUserObject();
			return user.name;

		}
		return "*".concat(_name);

	}

	
	public long getStartTime() {
		if (getUserObject() instanceof User) {
			User user = (User)getUserObject();
			return user.getStartTime();

		}
		return 0;
	}

	
	
	protected Comparator nodeComparator = new Comparator () {
		@Override
		public int compare(Object o1, Object o2) {
			
			UserTreeNode tmp1;
			UserTreeNode tmp2;

			if (o1 instanceof UserTreeNode) {
				tmp1 = (UserTreeNode)o1;

			} else {
				return 0;

			}
			
			if (o2 instanceof UserTreeNode) {
				tmp2 = (UserTreeNode)o2;

			} else {
				return 0;

			}
			
			User tmp1a;
			User tmp2a;
			
			if (tmp1.getUserObject() instanceof User) {
				tmp1a = (User)tmp1.getUserObject();
				if (tmp2.getUserObject() instanceof User) {
					tmp2a = (User)tmp2.getUserObject();

				} else {
					return 0;

				}

			} else {
				return 0;
				
			}
			int result = tmp1a.name.compareToIgnoreCase(tmp2a.name);
			System.out.println("***" + tmp1a.name + "|" + tmp2a.name + "***");
			return result;
//			return 1;

		}

	};

	@Override
	public int compareTo(UserTreeNode o) {
		
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
