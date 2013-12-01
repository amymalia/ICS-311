package code;

import java.util.Scanner;

public class Driver {

	/**
	 * @param args
	 */
	static Scanner inputReader = new Scanner(System.in);
	
	public static void main(String[] args) {
		while (true) {
			System.out.println("What would you like to do?");
			System.out.println("Options available: analyzeGraph, runSCC quit");
			String choice = inputReader.nextLine();
			if (!Utils.isEnum(choice)) {
				System.out.println("That command is not recognized\n");
				continue;
			}
			if (Utils.Command.valueOf(choice).equals(Utils.Command.quit)) {
				System.out.println("Goodbye!");
				break;
			}
			else {
				System.out.println("Type in the name of the graph:  ");
				String fileName = inputReader.nextLine();
				DirectedGraph graph = VNAParser.generateGraph(fileName);
				if (graph == null) {
					System.out.println("File not found!  Please try again\n");
					continue;
				}
				if (Utils.Command.valueOf(choice).equals(Utils.Command.analyzeGraph)) {
					Utils.printTable(graph, fileName);
				}
				if (Utils.Command.valueOf(choice).equals(Utils.Command.runSCC)) {
					Utils.printSCCTable(graph, fileName);
				}
			}
		}
	}
}
