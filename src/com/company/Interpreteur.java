package com.company;


import java.util.HashMap;
import java.util.Scanner;

public class Interpreteur {

    public static HashMap<String, Symbole> TABLESYMBOLE = new HashMap<>();

    public Interpreteur(){
        Scanner sc = new Scanner(System.in);
        System.out.println("");
        System.out.print("  >");
        String commande = sc.nextLine();
        Instruction instruction = new Instruction(commande);


        while(!commande.replaceAll(" ","").equalsIgnoreCase("end")){
            instruction.executer();
            System.out.println("");
            System.out.println("");
            System.out.print("  >");
            commande = sc.nextLine();
            instruction = new Instruction(commande);
        }
    }

}
