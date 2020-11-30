package CLI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javax.swing.filechooser.FileSystemView;

public class Terminal {
	public static String currentDir = "D://";
	private final static String Root = "D://";

//----------------------------------------------------------------------------------------
	/**
	 * function that take standerd input from console and redirect it file
	 * 
	 * @param Filename
	 * @param b        true : represent is it redirect operator >> Or false:
	 *                 represent is it represent redirect operator >
	 * @throws IOException
	 */
	private static void reconsoletofile(String Filename, boolean b) throws IOException {
		String temp = currentDir;

		if (new File(Filename).isAbsolute())
			temp = new File(Filename).getParent();

		if (new File(Filename).isDirectory()) {
			System.out.println("it's n't file");
			return;
		}
		File adir = new File(temp);
		File file = new File(adir, new File(Filename).getName());
		if (!file.exists())
			file.createNewFile();
		if (b == false) {
			if (file.exists())
				file.delete();
			file.createNewFile();

		}

		Scanner input = new Scanner(System.in);
		List<String> multlines = new ArrayList<String>();
		String lineNew;

		while (input.hasNextLine()) {
			lineNew = input.nextLine();
			if (lineNew.isEmpty()) {
				break;
			}
			multlines.add(lineNew);
		}
		for (String string : multlines) {
			FileWriter fileWritter = new FileWriter(file, true);
			BufferedWriter writer = new BufferedWriter(fileWritter);
			writer.write(string + "\r\n");
			writer.close();
		}
	}

//----------------------------------------------------------------------------------------
	/**
	 * function represent take input from a file and put it in another file
	 * 
	 * @param filenamei input file
	 * @param filenameo output file
	 * @param b         true : represent is it redirect operator >> Or false:
	 *                  represent is it represent redirect operator >
	 * @throws IOException
	 */
	private static void FiletoFile(String filenamei, String filenameo, boolean b) throws IOException {
		String temp1 = currentDir;
		String temp2 = currentDir;
		if (new File(filenamei).isAbsolute()) {
			temp1 = new File(filenamei).getParent();
		}
		if (new File(filenamei).isDirectory()) {
			System.out.println("it's n't file");
			return;
		}
		if (new File(filenameo).isAbsolute()) {
			temp2 = new File(filenameo).getParent();
		}
		if (new File(filenameo).isDirectory()) {
			System.out.println("it's n't file");
			return;
		}
		File adir = new File(temp1);
		File file = new File(adir, new File(filenamei).getName());

		if (!file.exists()) {
			System.out.println(currentDir + "\\" + filenamei + " Not exist");
			return;
		}
		File adiro = new File(temp2);
		File fileo = new File(adiro, new File(filenameo).getName());
		if (!fileo.exists())
			fileo.createNewFile();
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file.getAbsolutePath()));
		String line = bufferedReader.readLine();
		if (b == false) {
			if (fileo.exists())
				fileo.delete();
			fileo.createNewFile();
		}
		while (line != null) {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileo.getAbsolutePath(), true));
			bufferedWriter.write(line + "\r\n");
			bufferedWriter.close();
			line = bufferedReader.readLine();
		}
	}

//----------------------------------------------------------------------------------------
	/**
	 * Utility function to help list files function in case of the result of list
	 * redirected on file
	 * 
	 * @param Filename
	 * @param b        true : represent is it redirect operator >> Or false:
	 *                 represent is it represent redirect operator >
	 * @throws IOException
	 */
	private static void refilelisttoFile(String Filename, boolean b) throws IOException {
		String temp = currentDir;

		if (new File(Filename).isAbsolute())
			temp = new File(Filename).getParent();

		if (new File(Filename).isDirectory()) {
			System.out.println("it's n't file");
			return;
		}
		File adir = new File(temp);
		File file = new File(adir, new File(Filename).getName());
		if (!file.exists())
			file.createNewFile();
		if (b == false) {
			if (file.exists())
				file.delete();
			file.createNewFile();
		}
		FileSystemView filesv = FileSystemView.getFileSystemView();
		File[] roots = filesv.getFiles(adir, false);
		for (int i = 0; i < roots.length; i++) {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file.getAbsolutePath(), true));
			bufferedWriter.write((i + 1) + ") " + roots[i].getName() + "\r\n");
			bufferedWriter.close();
		}
	}

//----------------------------------------------------------------------------------------
	/**
	 * cd Function is to change current dir "working on dirctory" if it called with
	 * know args it return to Root if it has argument it check if dir is exist and
	 * make it "working dir" in case ".." it go back to its parent
	 * 
	 * @param parameters "arguments of command"
	 */
	public static void cd(String[] parameters) {
		boolean dirflag = false;
		if (parameters == null) {
			currentDir = "E://";
			return;
		} else if (parameters[0].equals("..")) {
			currentDir = new File(currentDir).getParent();
			return;
		} else if (!(new File(parameters[0]).isAbsolute())) {
			if (new File(currentDir + "\\" + parameters[0]).exists())
				currentDir = new File(currentDir + "\\" + parameters[0]).getAbsolutePath();
		} else if (new File(parameters[0]).isDirectory()) {

			dirflag = true;
			currentDir = new File(parameters[0]).getAbsolutePath();
			return;

		} else {
			if (!dirflag)
				System.out.println("bash: cd: " + parameters[0] + " not a directory");
			else
				System.out.println("bash: cd: " + parameters[0] + " no such file or directory");
			return;
		}
	}

//----------------------------------------------------------------------------------------
	/**
	 * ListFiles function implement ls command implemented 3 cases : no argument
	 * list file in current dir and output on console 1 arguments (>>) file name
	 * append result list on file 1 arguments (>) file name create file and redirect
	 * output to new file 1 arguments *.(file type ) list all files in current
	 * dirctory with the same extension
	 * 
	 * @param parameters
	 * @throws IOException
	 */
	public static void ListFiles(String[] parameters) throws IOException {
		if (parameters == null) {
			FileSystemView filesv = FileSystemView.getFileSystemView();
			File dir = new File(currentDir);
			File[] roots = filesv.getFiles(dir, false);
			for (int i = 0; i < roots.length; i++) {
				System.out.println(roots[i].getName());
			}
			return;
		} else {
			for (int i = 0; i < parameters.length; i++) {
				if (parameters[i].contains(">>")) {
					String outfile;
					if (i == 0) {
						outfile = parameters[i].substring(2, parameters[i].length());
						if (!outfile.equals("")) {
							Terminal.refilelisttoFile(outfile, true);
							return;
						} else if (i != (parameters.length - 1)) {
							outfile = parameters[i + 1];
							if (!outfile.equals("")) {
								Terminal.refilelisttoFile(outfile, true);
								return;
							} else {
								System.out.println("bash: syntax error near unexpected token 'newline'");
								return;
							}

						} else {
							System.out.println("bash: syntax error near unexpected token 'newline'");
							return;
						}
					}

				}
			}
			for (int i = 0; i < parameters.length; i++) {
				if (parameters[i].contains(">")) {
					String outfile;
					if (i == 0) {
						outfile = parameters[i].substring(1, parameters[i].length());
						if (!outfile.equals("")) {
							Terminal.refilelisttoFile(outfile, false);
							return;
						} else if (i != (parameters.length - 1)) {
							outfile = parameters[i + 1];
							if (!outfile.equals("")) {
								Terminal.refilelisttoFile(outfile, false);
								return;
							} else {
								System.out.println("bash: syntax error near unexpected token 'newline'");
								return;
							}

						} else {
							System.out.println("bash: syntax error near unexpected token 'newline'");
							return;
						}

					}
				}
			}
			if (parameters.length == 1) {
				if (parameters[0].contains("*")) {

					int index = parameters[0].lastIndexOf('.');
					if (index > 0) {
						String extension = parameters[0].substring(index + 1);
						FilenameFilter filter = new FilenameFilter() {

							public boolean accept(File f, String name) {
								return name.endsWith(extension);
							}
						};
						File f = new File(currentDir);
						File[] files = f.listFiles(filter);
						for (int i = 0; i < files.length; i++) {
							System.out.println(files[i].getName());
						}
					}
				}
				if (new File(parameters[0]).isFile())
					System.out.println(new File(parameters[0]).getName());
			}
		}
	}

//----------------------------------------------------------------------------------------
	/**
	 * function to move dir to dir
	 * 
	 * @param sourcePath
	 * @param destinationPath
	 * @return
	 */
	public static boolean mDir(Path sourcePath, Path destinationPath) {
		if (sourcePath.toFile().isDirectory()) {
			for (File file : sourcePath.toFile().listFiles()) {
				mDir(file.toPath(), destinationPath.resolve(sourcePath.relativize(file.toPath())));
			}
		}

		try {
			Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * function that move file to directory
	 * 
	 * @param src
	 * @param dest
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void mv(String src, String dest) throws FileNotFoundException, IOException {
		String temp1 = currentDir;
		File sf;
		if (new File(src).isAbsolute()) {
			temp1 = new File(src).getParent();
		}
		sf = new File(temp1, new File(src).getName());
		File destf = new File(dest);
		if (destf.isDirectory()) {
			if (sf.isFile()) {
				if (sf.renameTo(new File(dest + "//" + sf.getName()))) {
					// if file copied successfully then delete the original file
					sf.delete();
					System.out.println("File moved successfully");
				} else {
					System.out.println("Failed to move the file");
				}
			}
		}
	}

//----------------------------------------------------------------------------------------
	/**
	 * rm function is implement rm command that delete file
	 * 
	 * @param sourcePath
	 */
	public static void rm(String sourcePath) {
		String temp = currentDir;
		File remove;
		if (new File(sourcePath).isAbsolute()) {
			temp = new File(sourcePath).getParent();
		}
		remove = new File(temp, sourcePath);
		if (remove.delete()) {
			System.out.println("File deleted ");
		} else {
			System.out.println("Failed");
		}
	}

//----------------------------------------------------------------------------------------
	/**
	 * function implement pwd command that show current working dir
	 */
	public static void pwd() {
		System.out.println(">The current working directory is " + currentDir);
	}

//----------------------------------------------------------------------------------------
	public static void cp(String sourcePath, String destinationPath) throws FileNotFoundException, IOException {
		// copy text file to text file
		File file = new File(sourcePath);
		File file2 = new File(destinationPath);
		if (!file2.isDirectory())
			Terminal.FiletoFile(sourcePath, destinationPath, false);
		else
			Terminal.FiletoFile(sourcePath, (destinationPath + "//" + file.getName()), false);
	}

//----------------------------------------------------------------------------------------
	public static void mkdir(String[] place) {
		String temp = currentDir;
		if (new File(place[0]).isAbsolute())
			temp = new File(place[0]).getParent();
		if (place[0].equals("-p")) {
			File dir = new File(temp, place[1]);
			if (!dir.exists()) {
				if (dir.mkdir()) {
					File dir1 = new File(temp, place[1] + "\\" + place[2]);
					if (!dir1.exists()) {
						if (dir1.mkdir()) {
							System.out.println("Directory is created!");

						} else {
							System.out.println("Directory is Not created!");
						}
					} else {
						System.out.println("Directory is existed already!");
					}

				} else {
					System.out.println("Directory is Not created!");
				}
			} else {
				System.out.println("Directory is existed already!");
			}
		} else {
			File dir = new File(temp, place[0]);
			if (!dir.exists()) {
				if (dir.mkdir()) {
					System.out.println("Directory is created!");

				} else {
					System.out.println("Directory is Not created!");
				}
			} else {
				System.out.println("Directory is existed already!");
			}
		}
	}

//----------------------------------------------------------------------------------------
	/**
	 * rmdir function
	 * 
	 * @param place
	 */
	public static void rmdir(String[] place) {
		String temp = currentDir;
		if (new File(place[0]).isAbsolute()) {
			temp = new File(place[0]).getParent();
		}
		File dir = new File(temp, new File(place[0]).getName());
		if (!dir.exists()) {
			System.out.println("Directory Not existed already!");
		} else {
			FileSystemView filesv = FileSystemView.getFileSystemView();
			File[] roots = filesv.getFiles(dir, false);
			if (roots != null) {
				for (int i = 0; i < roots.length; i++) {
					roots[i].delete();
				}
			}
			dir.delete();
			System.out.println("Directory is deleted!");
		}
	}

//----------------------------------------------------------------------------
	public static void cat(String[] parameter) throws IOException {

		Scanner input = new Scanner(System.in);
		List<String> multlines = new ArrayList<String>();
		String lineNew;
		/**
		 * first case that cat don't have any parameter so it use stander input( read
		 * from console) stander output (write on console)
		 */
		if (parameter == null) {
			while (input.hasNextLine()) {
				lineNew = input.nextLine();
				if (lineNew.isEmpty()) {
					break;
				}
				System.out.println(lineNew);
				multlines.add(lineNew);
			}
			return;
		}
		/**
		 * Second case if it has arguments so we have 3 cases in it cat have one arg
		 * represent file name show file cat >file name it create file and remove any
		 * othe version cat >>file name append to file an input get from console cat
		 * file >>file appand file to file cat file>file like copy containt of file to
		 * another file
		 */
		for (int i = 0; i < parameter.length; i++) {
			if (parameter[i].contains(">>")) {
				String outfile;
				if (i == 0) {
					outfile = parameter[i].substring(2, parameter[i].length());
					if (!outfile.equals("")) {
						Terminal.reconsoletofile(outfile, true);
						return;
					} else if (i != (parameter.length - 1)) {
						outfile = parameter[i + 1];
						if (!outfile.equals("")) {
							Terminal.reconsoletofile(outfile, true);
							return;
						} else {
							System.out.println("bash: syntax error near unexpected token 'newline'");
							return;
						}

					} else {
						System.out.println("bash: syntax error near unexpected token 'newline'");
						return;
					}

				} else if (i == 1) {
					/**
					 * read previous index (input file name) open input file name from current dir
					 * get out file open out file call combine 2 files
					 */
					String inputfile = parameter[i - 1];
					/* check if output file in same index or next index */
					outfile = parameter[i].substring(2, parameter[i].length());
					if (!outfile.equals("")) {
						Terminal.FiletoFile(inputfile, outfile, true);
						return;
					} else if (i != (parameter.length - 1)) {
						outfile = parameter[i + 1];
						if (!outfile.equals("")) {
							Terminal.FiletoFile(inputfile, outfile, true);
							return;
						} else {
							System.out.println("bash: syntax error near unexpected token 'newline'");
							return;
						}
					}
				} else {
					System.out.println("bash: syntax error near unexpected token 'newline'");
					return;
				}
			}
		}
		for (int i = 0; i < parameter.length; i++) {
			if (parameter[i].contains(">")) {
				String outfile;
				if (i == 0) {
					outfile = parameter[i].substring(1, parameter[i].length());
					if (!outfile.equals("")) {
						Terminal.reconsoletofile(outfile, false);
						return;
					} else if (i != (parameter.length - 1)) {
						outfile = parameter[i + 1];
						if (!outfile.equals("")) {
							Terminal.reconsoletofile(outfile, false);
							return;
						} else {
							System.out.println("bash: syntax error near unexpected token 'newline'");
							return;
						}

					} else {
						System.out.println("bash: syntax error near unexpected token 'newline'");
						return;
					}

				} else if (i == 1) {
					/**
					 * read previous index (input file name) open input file name from current dir
					 * get out file open out file call combine 2 files
					 */
					String inputfile = parameter[i - 1];
					/* check if output file in same index or next index */
					outfile = parameter[i].substring(1, parameter[i].length());
					if (!outfile.equals("")) {
						Terminal.FiletoFile(inputfile, outfile, false);
						return;
					} else if (i != (parameter.length - 1)) {
						outfile = parameter[i + 1];
						if (!outfile.equals("")) {
							Terminal.FiletoFile(inputfile, outfile, false);
							return;
						} else {
							System.out.println("bash: syntax error near unexpected token 'newline'");
							return;
						}
					}
				} else {
					System.out.println("bash: syntax error near unexpected token 'newline'");
					return;
				}
			}
		}
		if (parameter.length == 1) {
			String temp = currentDir;
			if (new File(parameter[0]).isAbsolute())
				temp = new File(parameter[0]).getParent();

			if (new File(parameter[0]).isDirectory()) {
				System.out.println("it's n't file");
				return;
			}
			File adir = new File(temp);
			File file = new File(adir, new File(parameter[0]).getName());

			if (!file.exists()) {
				System.out.println(parameter[0] + " Not exist");
				return;
			}
			BufferedReader r = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = r.readLine()) != null) {
				System.out.println(line);
			}

		}
	}

// ----------------------------------------------------------------------------
	private static void execute(Parser cmd) throws IOException {
		String[] par = cmd.getArgs();
		switch (cmd.getCmd()) {
		case "ls":
			ListFiles(par);
			break;
		case "cd": {

			cd(par);
			break;
		}
		case "cp":
			cp(par[0], par[1]);
			break;
		case "cat":
			cat(par);
			break;
		case "more":
			more(par);
			break;
		case "mkdir":
			mkdir(par);
			break;
		case "rmdir":
			rmdir(par);
			break;
		case "mv": {
			// Enter full path
			// move files inside src dir to des dir
			if (new File(par[0]).isDirectory() && new File(par[0]).exists()) {
				mDir(new File(par[0]).toPath(), new File(par[1]).toPath());
				System.out.println("File moved successfully");
			}
			mv(par[0], par[1]);
			break;
		}
		case "rm":
			rm(par[0]);
			break;
		case "args":
			args(par[0]);
			break;
		case "date":
			date();
			break;
		case "help":
			help();
			break;
		case "pwd":
			pwd();
			break;
		case "clear":
			clear();
			break;
		}
	}

	public static void pipe(String cmd1, String cmd2) throws IOException {
		Parser commandline1 = new Parser();
		Parser commandline2 = new Parser();

		if (commandline1.parse(cmd1)) {
			if (cmd2.equals("more")) {
			}
			execute(commandline1);
		}
		if (commandline2.parse(cmd2)) {
			execute(commandline2);
		}
	}

//----------------------------------------------------------------------------
	/**
	 * it read file contain command and description of its argument
	 * 
	 * @throws FileNotFoundException
	 */
	public static void help() throws FileNotFoundException {
		try {
			File help = new File("help.txt");
			Scanner Read = new Scanner(help);
			while (Read.hasNextLine()) {
				System.out.println(Read.nextLine());
			}
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
		}
	}

//----------------------------------------------------------------------------
	/**
	 * date function get time and date of system in format day/month/number of day
	 * // time of pc // year
	 */
	public static void date() {
		Date date = new Date();
		System.out.println(date.toString());
	}

//----------------------------------------------------------------------------
	/**
	 * clear function simulate option clear that cls console
	 */
	public static void clear() {
		String n = "\n";
		//n = n.repeat(100);
		System.out.println(n);
	}

//----------------------------------------------------------------------------
	/**
	 * Args function is a function that take command as input and return argument
	 * information about it
	 * 
	 * @param comm represent command that want to know its arg
	 */
	public static void args(String comm) {
		ArrayList<String> commands = new ArrayList<String>(18);
		ArrayList<String> argsOfcommands = new ArrayList<String>(18);
		boolean find = false;
		/**
		 * find is a variable that validates the command in commandslist
		 */
		commands.add("cd");
		commands.add("ls");
		commands.add("cp");
		commands.add("cat");
		commands.add("more");
		commands.add("pwd");
		commands.add("date");
		commands.add("clear");
		commands.add("args");
		commands.add("mkdir");
		commands.add("rmdir");
		commands.add("mv");
		commands.add("rm");
		commands.add("echo");
		commands.add("less");
		commands.add("uname");
		commands.add("users");
		commands.add("exit");
		///////////////////////////////////////////////////////////////////////
		argsOfcommands.add("[Optional]..\nHas One argument or no one..\n (arg:name of directory/)");
		argsOfcommands
				.add("[Optional]..\n1-just(ls).\n2-(ls -a )to display the manual for ls to know another options. ");
		argsOfcommands.add("Has two arguments (arg1:SourcePath, arg2:DestinationPath");
		argsOfcommands.add("[Optional]..Has from One to Three arguments (arg1:FileName1 arg2:FileName2 arg..)");
		argsOfcommands.add("Has One or Two arguments.(arg:FileName.txt)");
		argsOfcommands.add("It has NO arguments..");
		argsOfcommands.add("It has NO arguments..");
		argsOfcommands.add(" Accepts neither options nor arguments.Not has arguments...");
		argsOfcommands.add("Has One arguments (arg:CommandName)");
		argsOfcommands.add(
				"Has from One to Three arguments (arg:The name of directory)or (-p directoryname1 directoryname2).");
		argsOfcommands.add("Has one argument (arg:The name of directory.)");
		argsOfcommands.add("Has Two arguments (arg1:source, arg2:destination)");
		argsOfcommands.add("Has one argument (arg:The name of file.)");
		argsOfcommands.add("Has One or more arguments (arg:anything you want to be printed)");
		argsOfcommands.add("Has One argument.(arg:FileName.txt)");
		argsOfcommands.add("It has NO arguments..");
		argsOfcommands.add("It has NO arguments..");
		argsOfcommands.add("It has NO arguments..");
		///////////////////////////////////////////////////////////////////////
		for (int i = 0; i < commands.size(); i++) {
			if (comm.equals(commands.get(i))) {
				System.out.println(argsOfcommands.get(i));
				find = true;
			}
		}
		if (find == false)
			System.out.println("This command Not found");

	}

//----------------------------------------------------------------------------
	/**
	 * more function implement more command that control on view the file by
	 * different option -d option represent z line frome file and to continue file
	 * to next z line click c -p clear console before read file -numline specfiy how
	 * many line to read file name read all file
	 * 
	 * @param file
	 * @throws FileNotFoundException
	 */
	public static void more(String[] file) throws FileNotFoundException {

		char input = 'c';
		int k = 0, z = 30;
		Scanner scan = new Scanner(System.in);

		String temp = currentDir;
		if (new File(file[0]).isAbsolute())
			temp = new File(file[0]).getParent();

		if (file[0].equals("-d")) {
			File txt = new File(temp, new File(file[1]).getName());
			Scanner Read = new Scanner(txt);
			while (input == 'c') {

				for (int i = k; i < z; i++) {

					if (Read.hasNextLine()) {
						System.out.println(Read.nextLine());
					}
				}
				System.out.println("--More-- [Press 'c' to continue ,'q' to quit].");
				// scan.nextLine();
				input = scan.next().charAt(0);

				if (input == 'c') {
					k = z;
					z = z + 30;
				} else {
					input = 'q';
					break;
				}
			}
		} else if (file[0].equals("-p")) {
			clear();
			File txt = new File(temp, new File(file[1]).getName());
			Scanner Read = new Scanner(txt);
			while (Read.hasNext()) {
				if (Read.hasNextLine()) {
					System.out.println(Read.nextLine());
				}
			}
		} else if (file[0].startsWith("-")) {
			z = Integer.parseInt(file[0].substring(1));
			File txt = new File(temp, new File(file[1]).getName());
			Scanner Read = new Scanner(txt);
			for (int i = k; i < z; i++) {

				if (Read.hasNextLine()) {
					System.out.println(Read.nextLine());
				}
			}
		} else {
			File txt = new File(temp, new File(file[0]).getName());
			Scanner Read = new Scanner(txt);
			while (Read.hasNext()) {
				if (Read.hasNextLine()) {
					System.out.println(Read.nextLine());
				}
			}
		}
	}
}
