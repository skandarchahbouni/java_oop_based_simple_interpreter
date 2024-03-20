package com.company;

public interface Evaluer {
    double evaluer() throws LogNegatifException, SqrtNegatifException, NonDeclaredVariableException, DivisionByZeroException, ErreurSyntaxiqueException;
}
