/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anlugifa.editor.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Suporte
 */
public class ExecComando {
 
public static String executar(java.lang.String scriptExec, java.lang.String typeCommand, java.lang.String command){
String[] finalCommand;
String retorno = null; 
finalCommand = new String[3];
finalCommand[0] = scriptExec;
finalCommand[1] = typeCommand;
finalCommand[2] = command;
Process proc;
String sucesso = "", erro = "", escuta;
    try{
        proc = Runtime.getRuntime().exec(finalCommand);
        //proc.waitFor();
        BufferedReader inputSucesso = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        BufferedReader inputErro = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
        while ((escuta = inputSucesso.readLine()) != null) {
            sucesso += escuta + "";
        }
        inputSucesso.close();
        while ((escuta = inputErro.readLine()) != null) {
            erro += escuta + "";
            
        }
        inputErro.close();
        if(!sucesso.equals("")){
            System.out.println("");
            retorno= sucesso;
        }else if(!erro.equals("")){
            System.out.println("");
            retorno=erro;
        }
    }catch (IOException ex) {
        System.err.println("Mensagem de Erro: "+ex.getMessage());
    }
 return retorno;
}
 
}

/**
import java.io.IOException;  
import java.util.Scanner;  
  
public class ExecComando{  
    
    public static String executar(java.lang.String scriptExec, java.lang.String typeCommand, java.lang.String command) {  
        String[] finalCommand = new String[3];
        finalCommand[0] = scriptExec;
        finalCommand[1] = typeCommand;
        finalCommand[2] = command;
        String ret = "";
        try {  
            Process process = Runtime.getRuntime().exec(finalCommand);  
            Scanner leitor = new Scanner(process.getInputStream());  
            while (leitor.hasNextLine()) {  
                ret += leitor.nextLine();  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return ret;
    }
}
*/