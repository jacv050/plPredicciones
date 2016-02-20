/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jacv050;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author jacv050
 */
public class ConjuntosPrimeros {
    HashMap<String, ArrayList<String>> mPrimeros;
    
    /**
     * Devuleve el conjunto de primeros de un simbolo no terminal
     * @param noTerminal
     * @return 
     */
    public ArrayList<String> getPrimeros(String noTerminal){
        return mPrimeros.get(noTerminal);
    }
    
    /**
     * Anyade nuevos primeros a un simbolo no terminal
     * @param noTerminal
     * @param newprimeros 
     */
    public void addPrimeros(String noTerminal, ArrayList<String> newprimeros){
        if(mPrimeros.containsKey(noTerminal)){
            ArrayList<String> primeros = mPrimeros.get(noTerminal);
            newprimeros.stream().forEach(primeros::add);
        }else
            mPrimeros.put(noTerminal, newprimeros);
    }
}
