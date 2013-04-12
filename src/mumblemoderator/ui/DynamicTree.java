package mumblemoderator.ui;

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * - Neither the name of Oracle or the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior
 * written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
//package components;

/*
 * This code is based on an example provided by Richard Stanford, a tutorial
 * reader.
 */
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import mumblemoderator.sample.Observable;
import mumblemoderator.sample.Observer;
import mumblemoderator.service.audio.AudioOutputHost;
import mumblemoderator.service.model.Channel;
import mumblemoderator.service.model.Message;
import mumblemoderator.service.model.User;

public class DynamicTree extends JPanel implements Observer {

	private static final long serialVersionUID = 8520428063747882052L;
	protected ChannelTreeNode rootNode;
	protected SortTreeModel treeModel;
	protected JTree tree;

	public DynamicTree() {
		super(new GridLayout(1, 0));

		NodeRenderer renderer = new NodeRenderer();

		Channel rootChannel = new Channel("MumbleModerator not connected");
		rootChannel.id = 0;
		
		rootNode = new ChannelTreeNode(rootChannel);
		treeModel = new SortTreeModel(rootNode);
		treeModel.addTreeModelListener(new MyTreeModelListener());
		tree = new JTree(treeModel);
		tree.setCellRenderer(renderer);

		tree.setEditable(true);
		tree.addTreeSelectionListener(new MyTreeSelektionListener());;
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setShowsRootHandles(true);
		tree.setToggleClickCount(3);


		tree.addMouseListener(new MouseAdapter() {
			
			public void mousePressed(MouseEvent e) {
				int selRow = tree.getRowForLocation(e.getX(), e.getY());
				TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
				if(selRow != -1) {
					if(e.getClickCount() == 1) {
//						mySingleClick(selRow, selPath);
					} else if(e.getClickCount() == 2) {
						if (selPath.getLastPathComponent() instanceof ChannelTreeNode) {
							ChannelTreeNode treeNode = (ChannelTreeNode)selPath.getLastPathComponent();
							
							System.out.println("****" + selPath.getLastPathComponent());
							
							if (treeNode.getUserObject() instanceof Channel) {
								Channel channel = (Channel)treeNode.getUserObject();
								UIDataHolder.getController().joinChannel(channel.id);
							
							}
							
						}
						
					}

				}

			}

//			public void mouseClicked(MouseEvent me) {
//				TreePath tp = tree.getPathForLocation(me.getX(), me.getY());
//				System.out.println(tp.getLastPathComponent());
//			}
		});

		
		JScrollPane scrollPane = new JScrollPane(tree);
		add(scrollPane);

		Observable.addObserver(this);

	}

	
	DefaultMutableTreeNode addChannel(Channel channel) {
		ChannelTreeNode treeNode = new ChannelTreeNode(channel);
		ChannelTreeNode parent = searchchannel(channel.pid);
		if (channel.id == 0) {
			rootNode.setUserObject(channel);

		} else {
			if (channel.pid == 0) {
				parent = rootNode;

			}
			treeModel.insertNodeInto(treeNode, parent,parent.getChildCount());

		}
		return treeNode;

	};
	
	void removeChannel(int channelId) {
		ChannelTreeNode treeNode = searchchannel(channelId);
		treeModel.removeNodeFromParent(treeNode);

	};
	
	DefaultMutableTreeNode changeChannel(Channel channel) {
		ChannelTreeNode treeNode = new ChannelTreeNode(channel);

		return treeNode;

	};
	
	DefaultMutableTreeNode addUser(User user) {
		UserTreeNode treeNode = new UserTreeNode(user);
		ChannelTreeNode parent = searchchannel(user.getChannel().id);
//		parent.add(treeNode);
		treeModel.insertNodeInto(treeNode, parent,parent.getChildCount());

//		tree.scrollPathToVisible(new TreePath(treeNode.getPath()));
		
		return treeNode;

	}
	
	void removeUser(int userId) {
		UserTreeNode treeNode = searchuser(userId);
		treeModel.removeNodeFromParent(treeNode);
	
	};
	

	DefaultMutableTreeNode changeUser(User user) {
		UserTreeNode treeNode = new UserTreeNode(user);
		UserTreeNode oldtreeNode = searchuser(user.hashCode());
		ChannelTreeNode parent = searchchannel(user.getChannel().id);
		treeModel.removeNodeFromParent(oldtreeNode);
		treeModel.insertNodeInto(treeNode, parent, parent.getChildCount());
		
		if (user.name.equalsIgnoreCase(UIDataHolder.getController().getLogin())) {
			tree.scrollPathToVisible(new TreePath(treeNode.getPath()));
		}

		
		return treeNode;

	};
	
	
	@Override
	public void channelupdate(Channel channel) {
		changeChannel(channel);

	}

	@Override
	public void channeladd(Channel channel) {
		addChannel(channel);

	}

	@Override
	public void channelremove(int channelId) {
		removeChannel(channelId);

	}

	@Override
	public void userupdate(User user) {
		changeUser(user);

	}

	@Override
	public void useradd(User user) {
		addUser(user);

	}

	@Override
	public void userremove(int userId) {
		removeUser(userId);

	}

	@Override
	public void usertalkstate(User user, int state) {
		if (state == AudioOutputHost.STATE_TALKING){
			UserTreeNode treenode = searchuser(user.hashCode());
			treenode = searchuser(user.hashCode());
			changeUser(user);
			treeModel.nodeChanged(treenode);

		}

	}


	public void sort(){
		
	}

	@Override
	public void serverSychronized() {
		UserTreeNode user = searchUser(UIDataHolder.getController().getLogin());
		if (user != null)
			tree.scrollPathToVisible(new TreePath(user.getPath()));

	}

	@Override
	public void channelJoin(int channelId) {
		//Not supported yet.
	}

	@Override
	public void commentSet(String comment) {
		//Not supported yet.
	}


	class MyTreeModelListener implements TreeModelListener {

		@Override
		public void treeNodesChanged(TreeModelEvent e) {
		}

		@Override
		public void treeNodesInserted(TreeModelEvent e) {
		}

		@Override
		public void treeNodesRemoved(TreeModelEvent e) {
		}

		@Override
		public void treeStructureChanged(TreeModelEvent e) {
		}
	}
	
	class MyTreeSelektionListener implements TreeSelectionListener {

		@Override
		public void valueChanged(TreeSelectionEvent e) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                           tree.getLastSelectedPathComponent();
			
			if (node == null) return;

			if (node instanceof ChannelTreeNode){
				ChannelTreeNode tmp = (ChannelTreeNode)node;
				System.out.println(((Channel)tmp.getUserObject()));
			}
			
		}
		
	}

	private ChannelTreeNode searchchannel(int id) {
		Enumeration enumeration = rootNode.breadthFirstEnumeration();
		ChannelTreeNode channelnode = null;
		DefaultMutableTreeNode treenode = null;
		
		while (enumeration.hasMoreElements()) {
			treenode = (DefaultMutableTreeNode)enumeration.nextElement();

			if (treenode instanceof ChannelTreeNode) {
				channelnode =(ChannelTreeNode)treenode;
				if (channelnode.getUserObject() instanceof Channel) {
					if (((Channel)channelnode.getUserObject()).id == id) {
						return channelnode;

					}
				
				}

			}
			
		}
		return null;

	}
	
	private UserTreeNode searchuser(int id) {
		Enumeration enumeration = rootNode.breadthFirstEnumeration();
		DefaultMutableTreeNode node = null;
		UserTreeNode usernode = null;
		
		while (enumeration.hasMoreElements()) {
			node = (DefaultMutableTreeNode) enumeration.nextElement();
			
			if (node instanceof UserTreeNode) {
				usernode = (UserTreeNode) node;
				
				if (((User)usernode.getUserObject()).hashCode() == id) {
					return usernode;
				}
				
			}

		}
		return null;

	}

	private UserTreeNode searchUser(String username) {
		int userid = UIDataHolder.getController().getUserId(username);
		return searchuser(userid);

	}


	@Override
	public void messageReceived(Message msg) {
		// TODO Auto-generated method stub
		
	}

}
