/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jacv050;

/**
 *
 * @author jacv050
 */
public class Main {
    public static void main(String[] args){
        if(args.length == 1){
            System.err.println("Debe escribir el nombre de fichero como parametro.");
        }else{
            Prediccion prediccion = new Prediccion(args[1]);
        }
    }
}
