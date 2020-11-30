package CLI;

import java.io.IOException;
import java.util.ArrayList;

public class Parser {
	private String[] args ;
	private String cmd;

	public boolean pipecheck(String input) throws IOException
	{
		Terminal t = new Terminal();
		String  cmd1, cmd2;
		String[] tofindpipe;
		tofindpipe = input.split("\\s");
		for (int i = 0; i < tofindpipe.length; i++) {
			if (tofindpipe[i].equalsIgnoreCase("|")) {
				cmd1 = input.substring(0, input.indexOf("|"));
				cmd2 = input.substring(input.indexOf("|") + 1, input.length());
				t.pipe(cmd1, cmd2);
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param cmd       represent command name
	 * @param numOfargs represent the number of argument recevied from out
	 * @return if cmd in system and obtain at least the mininum number of operands
	 */

	private boolean validation(String cmd, int numOfargs) { // to check if the cmd is in the system or not

		boolean cmdflag = false;
		boolean numofargs = false;
		ArrayList<Command> cmdList = getCommands();
		for (int i = 0; i < cmdList.size(); i++) {
			if (cmdList.get(i).getCmdName().equals(cmd)) {
				cmdflag = true;
				if (cmdList.get(i).getMin_No_args() <= numOfargs && cmdList.get(i).getMax_No_args() >= numOfargs) {
					numofargs = true;
					return true;
				}
			}
		}
		if (!cmdflag)
			System.out.println(cmd + ": command not found");
		else if (!numofargs) {
			System.out.println(cmd + ": missing file oprand");
			System.out.println("Try ' args " + cmd + "' for more information");
		}
		return false;
	}

	/**
	 * @return the commands
	 */
	private ArrayList<Command> getCommands() {
		ArrayList<Command> commands = new ArrayList<Command>();
		Command cmd = new Command("cd", 0, 1);
		commands.add(cmd);
		Command cmd1 = new Command("ls", 0, 1);
		commands.add(cmd1);
		Command cmd2 = new Command("cp", 2, 2);
		commands.add(cmd2);
		Command cmd3 = new Command("cat", 0, 3);
		commands.add(cmd3);
		Command cmd4 = new Command("more", 1, 2);
		commands.add(cmd4);
		Command cmd5 = new Command("mkdir", 1, 3);
		commands.add(cmd5);
		Command cmd6 = new Command("rmdir", 1, 1);
		commands.add(cmd6);
		Command cmd7 = new Command("mv", 2, 2);
		commands.add(cmd7);
		Command cmd8 = new Command("rm", 1, 1);
		commands.add(cmd8);
		Command cmd9 = new Command("args", 1, 1);
		commands.add(cmd9);
		Command cmd10 = new Command("date", 0, 0);
		commands.add(cmd10);
		Command cmd11 = new Command("help", 0, 0);
		commands.add(cmd11);
		Command cmd12 = new Command("pwd", 0, 0);
		commands.add(cmd12);
		Command cmd13 = new Command("clear", 0, 0);
		commands.add(cmd13);
		Command cmd14 = new Command("exit", 0, 0);
		commands.add(cmd14);
		return commands;
	}

	/**
	 * 
	 * @param input represent stream input gotten from user
	 * @return true if it parsed and validates that command is in system and satisfy
	 *         operands
	 */
	public boolean parse(String input) {

		// first of all remove all un nessary white spaces from stream of string
		// + represent one or more space
		input = input.replaceAll(" +", " ");
		// remove all white space from beginning
		// ^ * represent from beging to first charater
		input = input.replaceFirst("^ *", "");
		// remove all white space from ending
		input = input.replaceAll(" +$", "");
		// extract command from input that supposed be first word in stream string
		String tempCmd;
		String[] paramters = null;
		int pa = 0;
		if (input.indexOf(" ") < 0) {
			tempCmd = input;

		} else {
			tempCmd = input.substring(0, input.indexOf(" "));
			// extract parameters in commend line
			// remove command from string
			input = input.substring(input.indexOf(" ") + 1, input.length());
			paramters = input.split("\\s");
			pa = paramters.length;
		}

		if (validation(tempCmd, pa)) {
			this.cmd = tempCmd;
			if (pa > 0)
				this.args = paramters;
			return true;
		} else
			return false;
	}

	/**
	 * @return the args
	 */
	public String[] getArgs() {
		return args;
	}

	/**
	 * @return the cmd
	 */
	public String getCmd() {
		return cmd;
	}

}
