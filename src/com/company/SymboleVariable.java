package com.company;

public class SymboleVariable extends Symbole{

    private String nom;
    private double valeur;

    public SymboleVariable(String nom, double valeur){
        this.nom = nom;
        this.valeur = valeur;
    }

    public double getValeur() {
        return valeur;
    }
}
