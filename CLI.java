package CLI;

/**
 * Esraa Abo-bakr Mubarak 20180040
 * Thoraya Atef Anwar 20180078
 * Salma Ahmed Anees 20180122
 * Abdelrahman Ashraf Mohamed 20180150
 */
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class CLI {
// is syntax

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String input;
		Scanner sc = new Scanner(System.in);
		do {
			System.out.print(Terminal.currentDir + " > ");
			input = sc.nextLine();
			Parser command = new Parser();
			if (!command.pipecheck(input)) {
				if (command.parse(input)) {
					String[] par = null;
					par = command.getArgs();
					switch (command.getCmd()) {
					case "ls":
						Terminal.ListFiles(par);
						break;
					case "cd":
						Terminal.cd(par);
						break;
					case "cp":
						/* D:// > cp file4.txt D:\\Root */
						Terminal.cp(par[0], par[1]);
						break;
					case "cat":
						Terminal.cat(par);
						break;
					case "more":
						Terminal.more(par);
						break;
					case "mkdir":
						Terminal.mkdir(par);
						break;
					case "rmdir":
						Terminal.rmdir(par);
						break;
					case "mv": {
						// Enter full path
						// move files inside src dir to des dir
						if (new File(par[0]).isDirectory() && new File(par[0]).exists()) {
							Terminal.mDir(new File(par[0]).toPath(), new File(par[1]).toPath());
							System.out.println("File moved successfully");
						}
						Terminal.mv(par[0], par[1]);
						break;
					}
					case "rm":
						Terminal.rm(par[0]);
						break;
					case "args":
						Terminal.args(par[0]);
						break;
					case "date":
						Terminal.date();
						break;
					case "help":
						Terminal.help();
						break;
					case "pwd":
						Terminal.pwd();
						break;
					case "clear":
						Terminal.clear();
						break;
					case "exit":
						System.exit(0);
					}
				}
			}

		} while (true);

	}
}
