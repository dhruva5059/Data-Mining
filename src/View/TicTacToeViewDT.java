package View;
import java.util.ArrayList;
import java.util.Scanner;

import Controller.TicTacToeControllerDT;
import Model.TicTacToe;

public class TicTacToeViewDT {
public static void main(String args[]){
		
		TicTacToeControllerDT ticTacToeControllerDT = new TicTacToeControllerDT();
		TicTacToe ticTacToe = new TicTacToe();
		Boolean conn = ticTacToeControllerDT.setupDBConnection();
		if (!conn) {
			System.out.println("Can't connect to the database");
			return;
		}
		
		ticTacToeControllerDT.generateDecisionTree();			//To generate decision tree
		
		Scanner sc = new Scanner(System.in);
		
		//Get Top values
		System.out.println("Enter Top Left Value (x, o, b) : \n");
		String var = sc.next();
		while (!(var.toLowerCase().equals("x")) && !(var.toLowerCase().equals("o"))  && !(var.toLowerCase().equals("b"))) {
			System.out.println("Enter Top Left Value (x, o, b) : \n");
			var = sc.next();
		}
		ticTacToe.setTL(var);
		System.out.println("Enter Top Middle Value (x, o, b) : \n");
		var = sc.next();
		while (!(var.toLowerCase().equals("x")) && !(var.toLowerCase().equals("o"))  && !(var.toLowerCase().equals("b"))) {
			System.out.println("Enter Top Middle Value (x, o, b) : \n");
			var = sc.next();
		}
		ticTacToe.setTM(var);
		System.out.println("Enter Top Right Value (x, o, b) : \n");
		var = sc.next();
		while (!(var.toLowerCase().equals("x")) && !(var.toLowerCase().equals("o"))  && !(var.toLowerCase().equals("b"))) {
			System.out.println("Enter Top Right Value (x, o, b) : \n");
			var = sc.next();
		}
		ticTacToe.setTR(var);
		
		//Get Middle values
		System.out.println("Enter Middle Left Value (x, o, b) : \n");
		var = sc.next();
		while (!(var.toLowerCase().equals("x")) && !(var.toLowerCase().equals("o"))  && !(var.toLowerCase().equals("b"))) {
			System.out.println("Enter Middle Left Value (x, o, b) : \n");
			var = sc.next();
		}
		ticTacToe.setML(var);
		System.out.println("Enter Middle Middle Value (x, o, b) : \n");
		var = sc.next();
		while (!(var.toLowerCase().equals("x")) && !(var.toLowerCase().equals("o"))  && !(var.toLowerCase().equals("b"))) {
			System.out.println("Enter Middle Middle Value (x, o, b) : \n");
			var = sc.next();
		}
		ticTacToe.setMM(var);
		System.out.println("Enter Middle Right Value (x, o, b) : \n");
		var = sc.next();
		while (!(var.toLowerCase().equals("x")) && !(var.toLowerCase().equals("o"))  && !(var.toLowerCase().equals("b"))) {
			System.out.println("Enter Middle Right Value (x, o, b) : \n");
			var = sc.next();
		}
		ticTacToe.setMR(var);
		
		//Get Bottom Values
		System.out.println("Enter Bottom Left Value (x, o, b) : \n");
		var = sc.next();
		while (!(var.toLowerCase().equals("x")) && !(var.toLowerCase().equals("o"))  && !(var.toLowerCase().equals("b"))) {
			System.out.println("Enter Bottom Left Value (x, o, b) : \n");
			var = sc.next();
		}
		ticTacToe.setBL(var);
		System.out.println("Enter Bottom Middle Value (x, o, b) : \n");
		var = sc.next();
		while (!(var.toLowerCase().equals("x")) && !(var.toLowerCase().equals("o"))  && !(var.toLowerCase().equals("b"))) {
			System.out.println("Enter Bottom Middle Value (x, o, b) : \n");
			var = sc.next();
		}
		ticTacToe.setBM(var);
		System.out.println("Enter Bottom Right Value (x, o, b) : \n");
		var = sc.next();
		while (!(var.toLowerCase().equals("x")) && !(var.toLowerCase().equals("o"))  && !(var.toLowerCase().equals("b"))) {
			System.out.println("Enter Bottom Right Value (x, o, b) : \n");
			var = sc.next();
		}
		ticTacToe.setBR(var);
		
		//Generate result
		ticTacToeControllerDT.getResult(ticTacToe);
		System.out.println(ticTacToe.toString());
	}
}
