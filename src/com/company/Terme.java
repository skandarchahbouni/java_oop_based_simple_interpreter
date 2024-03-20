package com.company;

public class Terme implements Evaluer{
    private String valeur;

    public Terme(String valeur){
        this.valeur = valeur;
    }

    @Override
    public double evaluer() throws LogNegatifException, SqrtNegatifException, NonDeclaredVariableException, DivisionByZeroException, ErreurSyntaxiqueException {

        char[] caracteres = valeur.toCharArray();
        StringBuffer fact = new StringBuffer();
        double result = 1;
        char c  ;
        char c2 ='*';

        for (int i = 0; i < caracteres.length ; i++) {
            c = caracteres[i];
            if (c == '*' || c == '/') {
                if ((i==0)||(caracteres[i+1]=='*')||(caracteres[i+1]=='/')){
                    throw new ErreurSyntaxiqueException("Error : Invalid expression go go go ");
                }
                Facteur facteur = new Facteur(fact.toString());
                fact.delete(0, fact.length());


                if (c2 == '*') {
                    result *= facteur.evaluer();
                } else {
                    if (facteur.evaluer()==0){
                        throw new DivisionByZeroException("Error : Division by zero ");
                    }else{
                        result /= facteur.evaluer();
                    }
                }
                c2 = c;
            }
            else {
                fact.append(c);
            }
        }

        Facteur facteur = new Facteur(fact.toString());
        if (c2 == '*') {
            result *= facteur.evaluer();
        } else {
            if (facteur.evaluer()==0){
                throw new DivisionByZeroException("Error : Division by zero ");

            }else{
                result /= facteur.evaluer();
            }
        }
        return result;
    }
}
