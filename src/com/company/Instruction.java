package com.company;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Instruction {
    private String commande;
    public Instruction(String commande){
        this.commande = commande;
    }

    private void verifierErreurSyntaxiqueLet(String variable, String expression) throws ErreurSyntaxiqueException {
        if (Objects.equals(variable, "let") || Objects.equals(variable, "print") || Objects.equals(variable, "log") || Objects.equals(variable, "sqrt") || Objects.equals(variable, "tan") || Objects.equals(variable, "sin") || Objects.equals(variable, "cos")){
            throw new ErreurSyntaxiqueException("Error : Variable name can't contain a function or command name" );
        }
        if (!(variable.matches("[_]?[a-zA-Z]+[0-9a-zA-Z_]*"))){
            throw new ErreurSyntaxiqueException("Error : Invalid variable name   "+variable );
        }
        verifierErreurSyntaxiquePrint(expression);
    }

    private void verifierErreurSyntaxiquePrint(String expression) throws ErreurSyntaxiqueException {
        expression = expression.replaceAll("[ \t]","");
        if     (expression.contains("++")||expression.contains("+/")||expression.contains("+-")||expression.contains("+*")||expression.contains("+^")||expression.contains("--")|| expression.contains("-+")|| expression.contains("-*")|| expression.contains("-/")||expression.contains("-^")||expression.contains("*^")||expression.contains("*+")||expression.contains("*-")||expression.contains("/^")||expression.contains("/+")||expression.contains("/-")||expression.contains("()")||expression.contains(")(")||expression.contains("+)")||expression.contains("-)")||expression.contains("*)")||expression.contains("/)")||expression.contains("^)")||expression.contains("(^")||expression.contains("..")||expression.endsWith("+")||expression.endsWith("-")||expression.endsWith("*")||expression.endsWith("/")||expression.endsWith("^")||expression.startsWith("^")||(expression.replaceAll("[a-zA-Z0-9_+/*^\\-(). \t]","")!="")){
            throw new ErreurSyntaxiqueException("Error : Invalid Expression gogo ");
        }
        char[] exp = expression.toCharArray();
        boolean val;
        for (int i = 0; i <exp.length ; i++) {
            val=true;
            if ((i>2)&&(exp[i]=='(')){
                if ((exp[i-1]!='s'&&exp[i-2]!='o'&&exp[i-3]!='c')||(exp[i-1]!='s'&&exp[i-2]!='b'&&exp[i-3]!='a')||(exp[i-1]!='n'&&exp[i-2]!='a'&&exp[i-3]!='t')||(exp[i-1]!='n'&&exp[i-2]!='i'&&exp[i-3]!='s')||(exp[i-1]!='g'&&exp[i-2]!='o'&&exp[i-3]!='l')){
                    val=false;
                }
            }
            if ((i>3)&&(exp[i]=='(')){
                if (exp[i-1]!='t'&&exp[i-2]!='r'&&exp[i-3]!='q'&&exp[i-3]!='s'){
                    val=false;
                }
            }
            if ((i>0)&&(exp[i]=='(')){
                if (val&&exp[i-1]!='+' && exp[i-1]!='-' && exp[i-1]!='*' && exp[i-1]!='/' && exp[i-1]!='^' && exp[i-1]!='('){
                    throw new ErreurSyntaxiqueException("Error : Invalid expression az,fka ");
                }else {
                    if ((i>1) && exp[i-2]=='(' && exp[i-1]!='('){

                        throw new ErreurSyntaxiqueException("Error : Invalid expression gogo rar ");
                    }
                }
            }
        }
        for (int i = 0; i <exp.length ; i++) {
            if ((i<exp.length-1)&&(exp[i]==')')){
                if (exp[i+1]!='+' && exp[i+1]!='-' && exp[i+1]!='*' && exp[i+1]!='/' && exp[i+1]!='^' && exp[i+1]!=')'){
                    throw new ErreurSyntaxiqueException("Error : Invalid expression so mon");
                }else {
                    if ((i<exp.length-2) && exp[i+2]==')' && exp[i+1]!=')'){
                        throw new ErreurSyntaxiqueException("Error : Invalid expression krgjlk");
                    }
                }
            }
        }
    }

    public void executer(){

        if (commande.trim().startsWith("let ")){
            executerLet();
        } else if (commande.trim().startsWith("print ")){
            executerPrint();
        }else {
            System.err.println("Error : Command not found ");
            System.out.println("");
        }
    }


    private void executerLet(){

        String[] parts = commande.split("=");
        if (parts.length == 2){
            String variablename = parts[0].trim().substring(3).trim();
            String expression = parts[1];
            try {
                verifierErreurSyntaxiqueLet(variablename,expression);
                double eval = evaluation(expression);
                SymboleVariable symvar = new SymboleVariable(variablename, eval);
                Interpreteur.TABLESYMBOLE.put(variablename, symvar);
                System.out.println("");
                System.out.println("Variable added");
            }catch (ParenthesesManquanteException | LogNegatifException | SqrtNegatifException | NonDeclaredVariableException | DivisionByZeroException | ErreurSyntaxiqueException e1){
                System.err.println(e1.getMessage());
            }
        }else if (parts.length >2){
            System.err.println("Error : Command can't contain more than one '=' ");
        }else if (commande.chars().filter(ch -> ch =='=').count() == 0){
            System.err.println("Error : the Command 'let' must contain '=' ");
        }else {
            System.err.println("Error : You need to affect an expression to your variable ");
        }
    }

    private void executerPrint(){
        String expression = commande.trim().substring(5);
        try {
            verifierErreurSyntaxiquePrint(expression);
            System.out.println("");
            System.out.println(">>>  "+evaluation(expression));

        }catch (ParenthesesManquanteException | LogNegatifException | SqrtNegatifException | NonDeclaredVariableException | DivisionByZeroException | ErreurSyntaxiqueException e1){
            System.err.println(e1.getMessage());
        }
    }

    public double evaluation (String command) throws ParenthesesManquanteException, LogNegatifException, SqrtNegatifException, NonDeclaredVariableException, DivisionByZeroException, ErreurSyntaxiqueException {
        Pattern pattern = Pattern.compile("\\([a-zA-Z0-9.+*^/\\- \t]*\\)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(command);
        while (matcher.find()) {
            double d = new Expression(matcher.group().replaceAll("\\(", "").replaceAll("\\)", "")).evaluer();
            command = command.replace(matcher.group(), Double.toString(d));
            matcher = pattern.matcher(command);
        }
        if (command.contains(")") || command.contains("(")){
            if (command.contains(")")) {
                throw new ParenthesesManquanteException("Error : you have  "+ command.chars().filter(ch -> ch == ')').count() +"  missing openning parentheses");
            }else{
                throw new ParenthesesManquanteException("Error : you have  "+ command.chars().filter(ch -> ch == '(').count() +"  missing closing parentheses");

            }
        }
        else {
            Expression e = new Expression(command);
            return e.evaluer();
        }
    }
}
