/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mumblemoderator.ui;

import mumblemoderator.service.Controller;

/**
 *
 * @author frank
 */
public class UIDataHolder {
	private static Controller  _controller;
	private static ChannelUser  _channeluser;
	
	
	private static String _comment = "".
//			concat("<span ").
//			concat("style=\"").
//			concat("color:#000000; background-color:#cccccc; ").
//			concat("font-weight:bold; ").
//			concat("font-family:Georgia, 'Times New Roman', Times, serif; ").
//			concat("display: block; ").
//			concat("\"> ").
//			concat("Ich bin ein ComputerProgramm. Ich kann auf Audioanfragen ").
//			concat("nicht reagieren. Für weitere Fragen sprich meinen ").
//			concat("Programmierer an: ").
//			concat("<a href=\"http://wiki.piratenpartei.de/Benutzer:Pacman\"></a>").
// 			concat("</span>");	

	
//			concat("<span> ").
//			concat("Ich bin ein ComputerProgramm. Ich kann auf Audioanfragen ").
//			concat("nicht reagieren. Für weitere Fragen sprich meinen ").
//			concat("Programmierer an: ").
//			concat("<a href=\"http://wiki.piratenpartei.de/Benutzer:Pacman\"></a>").
// 			concat("</span>");	


			concat("<span> ").
			concat("<table width=\"400px\" border=\"0\" style=\"color:#000000; background-color:#cccccc; font-weight:bold; font-family:Georgia, 'Times New Roman', Times, serif;\"> ").
			concat("<tr> ").
			concat("<td> ").
			concat("Ich bin ein ComputerProgramm. Ich kann auf Audioanfragen nicht reagieren. F�r weitere Fragen sprich meinen ").
			concat("Programmierer an: <a href=\"http://wiki.piratenpartei.de/Benutzer:Pacman\">Pacman</a>").
			concat("</td> ").
			concat("</tr> ").
			concat("</table> ").
			concat("</span> ");
	

	
	
	
	public static void setController(Controller controller) {
		_controller = controller;

	}
	
	public static Controller getController(){
		return _controller;

	}

	public static void setComment(String text) {
		_comment = text;

	}
	
	public static String getComment() {
		if (_comment != null)
			return _comment;
		return ""; 
	}
	
	public static ChannelUser getchanneluser() {
		return _channeluser;
	}

	public static void setchanneluser(ChannelUser channeluser) {
		UIDataHolder._channeluser = channeluser;
	}

	

	
	
	
	

}
