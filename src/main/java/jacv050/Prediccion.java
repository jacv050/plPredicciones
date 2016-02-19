/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jacv050;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jacv050
 */
public class Prediccion {
 
    private static ArrayList<String> getPrimeros(String[] regla){
        ArrayList<String> salida = new ArrayList<>();
        
        //salida.add(e)
        
        return salida;
    }
    
    public Prediccion(String nombreFichero){
        Reglas reglas = new Reglas();
        try {
            FileReader fr = new FileReader(nombreFichero);        
            BufferedReader br = new BufferedReader(fr);
            String cadena = "";
        
            while((cadena = br.readLine()) != null){
                reglas.addRegla(cadena);
            }
            
            br.close();
            fr.close();
            
        } catch (FileNotFoundException ex) {
            System.err.println("No se ha encontrado el archivo.");
        } catch (IOException ex) {
            Logger.getLogger(Prediccion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}
