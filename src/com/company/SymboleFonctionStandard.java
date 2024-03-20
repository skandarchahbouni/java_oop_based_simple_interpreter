package com.company;

public class SymboleFonctionStandard extends Symbole{

    private String nom;
    private double valeur;

    public SymboleFonctionStandard(String nom, double valeur){
        this.nom = nom;
        this.valeur = valeur;
    }


    public double code_to_execute() throws LogNegatifException, SqrtNegatifException {

        double result = 0 ;
        switch (nom){
            case "sin":
                result = Math.sin(valeur);
                break;
            case "cos":
                result = Math.cos(valeur);
                break;
            case "tan":
                result = Math.tan(valeur);
                break;
            case "abs":
                result = Math.abs(valeur);
                break;
            case "sqrt":
                if (valeur<0) {
                    throw new SqrtNegatifException("Error : Square root can't contain a negatif number");
                }else {
                    result = Math.sqrt(valeur);

                }
                break;
            case "log":
                if (valeur<=0) {
                    throw new LogNegatifException("Error : log can't contain a null or negatif number");
                }else
                {
                    result = Math.log(valeur);
                }
                    break;

            default:
                System.out.println("not a valid function name");
                break;
        }
       return result;
    }
}
