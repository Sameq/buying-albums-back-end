package br.com.sysmap.bootcamp.infra.exceptions;

public class ExistingWallet extends RuntimeException{
    public ExistingWallet(){
        super("User existing");
    }

    public ExistingWallet(String message){
        super(message);
    }
}
