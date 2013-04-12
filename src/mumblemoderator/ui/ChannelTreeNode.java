/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mumblemoderator.ui;

import javax.swing.tree.DefaultMutableTreeNode;
import mumblemoderator.service.model.Channel;

/**
 *
 * @author frank
 */
class ChannelTreeNode extends DefaultMutableTreeNode{
	
	private String _name;
	private int _id;

	ChannelTreeNode(Channel child) {
		super(child);
		_name = child.name;
		_id = child.id;
		
	}

	public String getName(){
		return _name;
	}
	
	public int getId(){
		return _id;
	}
	
	public ChannelTreeNode(Object userObject) {
		super(userObject);
	}
	
}
