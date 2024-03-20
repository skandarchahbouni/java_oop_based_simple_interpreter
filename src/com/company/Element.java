package com.company;

public class Element implements Evaluer{
    private String valeur;
    public Element(String valeur){
        this.valeur= valeur;
    }
    @Override
    public double evaluer() throws LogNegatifException, SqrtNegatifException, NonDeclaredVariableException, DivisionByZeroException, ErreurSyntaxiqueException {
        try {
            return Double.parseDouble(valeur);
        }catch (NumberFormatException e){
            if(Interpreteur.TABLESYMBOLE.containsKey(valeur)){
                        SymboleVariable symVar = (SymboleVariable) Interpreteur.TABLESYMBOLE.get(valeur);
                        return symVar.getValeur();
                     } else if ((valeur.trim().startsWith("sin")) || (valeur.trim().startsWith("cos")) || (valeur.trim().startsWith("tan")) || (valeur.trim().startsWith("log")) || (valeur.trim().startsWith("abs")) || (valeur.trim().startsWith("sqrt")) )
                        {

                        if (valeur.trim().startsWith("sqrt")) {
                            SymboleFonctionStandard f = new SymboleFonctionStandard(valeur.trim().substring(0, 4), new Expression(valeur.substring(4)).evaluer());
                            return f.code_to_execute();
                        } else {
                            SymboleFonctionStandard f = new SymboleFonctionStandard(valeur.trim().substring(0, 3), new Expression(valeur.substring(3)).evaluer());
                            return f.code_to_execute();
                        }
                    }else if ((!(Interpreteur.TABLESYMBOLE.containsKey(valeur)))&&(valeur.matches("[_0-9]?[a-zA-Z0-9_]+[0-9a-zA-Z_.]*"))){
                            if (valeur.matches("[_]?[a-zA-Z]+[0-9a-zA-Z_]*")) {
                                throw new NonDeclaredVariableException("Error : Variable  '" + valeur + "'  doesn't exist ");
                            }else {
                                throw new ErreurSyntaxiqueException("Error : Invalid Expression ");
                            }
                    } else if ((valeur !="")&&(valeur.replaceAll("[a-zA-Z0-9_\\-]","")==""))  {
                        Expression expression = new Expression(valeur);
                        return expression.evaluer();
                    } else if (valeur != ""){
                         throw new ErreurSyntaxiqueException("Error : Invalid expression  no no no ");
                     }
        }
        return 0;
    }
}
