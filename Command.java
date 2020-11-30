package CLI;

public class Command {

	private String cmdName;
	private int min_No_args;
	private int max_No_args;

	Command(String cmd, int min, int max) {
		this.setCmdName(cmd);
		this.setMin_No_args(min);
		this.setMax_No_args(max);
	}

	/**
	 * @return the cmdName
	 */
	public String getCmdName() {
		return cmdName;
	}

	/**
	 * @param cmdName the cmdName to set
	 */
	public void setCmdName(String cmdName) {
		this.cmdName = cmdName;
	}

	/**
	 * @return the min_No_args
	 */
	public int getMin_No_args() {
		return min_No_args;
	}

	/**
	 * @param min_No_args the min_No_args to set
	 */
	public void setMin_No_args(int min_No_args) {
		this.min_No_args = min_No_args;
	}

	/**
	 * @return the max_No_args
	 */
	public int getMax_No_args() {
		return max_No_args;
	}

	/**
	 * @param max_No_args the max_No_args to set
	 */
	public void setMax_No_args(int max_No_args) {
		this.max_No_args = max_No_args;
	}

}
