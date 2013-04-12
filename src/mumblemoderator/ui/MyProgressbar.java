package mumblemoderator.ui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JProgressBar;


/**
 *
 * @author frank
 */
public class MyProgressbar extends JProgressBar{

	private static final long serialVersionUID = -1861884526133170619L;
	private int limit1 = 5;
	private static int limit2;

	public int getLimit1() {
		return limit1;
	}

	public void setLimit1(int limit) {
		if (limit < super.getMinimum())
			limit = super.getMinimum();
		if (limit > super.getMaximum())
			limit = super.getMaximum();
		
		this.limit1 = limit;
	}

	public int getLimit2() {
		return limit2;
	}

	public void setLimit2(int limit) {
		if (limit < super.getMinimum())
			limit = super.getMinimum();
		if (limit > super.getMaximum())
			limit = super.getMaximum();
		
		MyProgressbar.limit2 = limit;
	}

	public MyProgressbar() {
		super();
		if(limit2 == 0){
			limit2 = 20;

		}

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int limit1position = (super.getMaximum()-super.getMinimum())*limit1/super.getMaximum()*super.getSize().width/super.getMaximum();
		int limit2position = (super.getMaximum()-super.getMinimum())*limit2/super.getMaximum()*super.getSize().width/super.getMaximum();
		
		g.setColor( Color.blue );
		g.drawRect(limit1position, 0, 1, (int)super.getSize().getHeight()-1);
		
		g.setColor( Color.red );
		g.drawRect(limit2position, 0, 1, (int)super.getSize().getHeight()-1);
		
	}
	
}

