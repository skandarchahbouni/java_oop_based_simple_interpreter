package com.company;

public class Facteur implements Evaluer{

    private String valeur;

    public Facteur(String valeur){
        this.valeur = valeur;
    }
    @Override
    public double evaluer() throws LogNegatifException, SqrtNegatifException, NonDeclaredVariableException, DivisionByZeroException, ErreurSyntaxiqueException {
        String[] operands = valeur.split("\\^");
        Element e1 = new Element(operands[operands.length-1]);
        double result = e1.evaluer();
        for (int i = operands.length - 1; i > 0; i--) {
            Element e2 = new Element(operands[i-1]);
            result = Math.pow(e2.evaluer(), result);
        }
        return result;
    }
}
