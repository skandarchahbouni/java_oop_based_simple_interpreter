package com.company;

public class Expression implements Evaluer{

    private String valeur;

    public Expression(String valeur){
        this.valeur = valeur.replaceAll(" ", "");
    }

    @Override
    public double evaluer() throws LogNegatifException, SqrtNegatifException, NonDeclaredVariableException, DivisionByZeroException, ErreurSyntaxiqueException {
        char[] caracteres = valeur.toCharArray();

        StringBuffer term = new StringBuffer();
        double result = 0;
        char c;
        char c2 ;
        if (caracteres[0]!='-')
            c2='+';
        else
            c2='-';


        for (int i = 0; i < caracteres.length; i++) {
            // if operator
            c = caracteres[i];
            if (c == '+' || c == '-') {
                if (i>0){
                    if (caracteres[i-1]== '*' || caracteres[i-1]== '/' || caracteres[i-1]== '-' || caracteres[i-1] == '+' || caracteres[i-1]=='^' || caracteres[i-1]=='n' || caracteres[i-1]=='s' || caracteres[i-1]=='t' || caracteres[i-1]=='g'){
                        term.append(c);
                    }else {

                        Terme terme = new Terme(term.toString());
                        term.delete(0, term.length());
                        if (c2 == '+') {
                            result += terme.evaluer();
                        } else {
                            result -= terme.evaluer();
                        }
                        c2 = c;
                    }
                }
                else {

                    Terme terme = new Terme(term.toString());
                    term.delete(0, term.length());
                    if (c2 == '+') {
                        result += terme.evaluer();
                    } else {
                        result -= terme.evaluer();
                    }
                    c2 = c;
                }
            }
            else {
                term.append(c);
            }
        }

        Terme terme = new Terme(term.toString());
        if (c2 == '+') {
            result += terme.evaluer();
        } else {
            result -= terme.evaluer();
        }
        return result;
    }
}
