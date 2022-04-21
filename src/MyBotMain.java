import org.jibble.pircbot.*;

public class MyBotMain {
   public static void main(String[] args) throws Exception
   {
//you can read more about what these lines do in the documentation
       MyBot ChatBot = new MyBot();
       ChatBot.setVerbose(true);
       ChatBot.connect("irc.freenode.net"); //tells it where to connect to - this is the same as the web interface I linked in the last slide
       ChatBot.joinChannel("#NhatPhamChatBox"); // Name of channel is you want to connect to - in this case it’s called “#testChannel” 
//this is the default message it will send when your pircbot first goes live 
       ChatBot.sendMessage("#NhatPhamChatBox", "Hello! Welcome to Nhat Pham's chat box! Type \"syntax\" for the list of funtions and how to use them!");
	//That’s it for setting up you bot! After this, you can implemented custom logic that will look similar to the next slide
   }
}

