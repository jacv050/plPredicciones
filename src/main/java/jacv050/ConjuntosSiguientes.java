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
public class ConjuntosSiguientes {
    HashMap<String, ArrayList<String>> mSiguientes;
    
    public ConjuntosSiguientes(){
        mSiguientes = new HashMap<>();
    }
    
    /**
     * Devuleve el conjunto de primeros de un simbolo no terminal
     * @param noTerminal
     * @return 
     */
    public ArrayList<String> getSiguientes(String noTerminal){
        return mSiguientes.get(noTerminal);
    }
    
    /**
     * Anyade nuevos siguientes a un simbolo no terminal
     * @param noTerminal
     * @param newsiguientes
     */
    public void addSiguientes(String noTerminal, ArrayList<String> newsiguientes) {
        if (mSiguientes.containsKey(noTerminal)) {
            ArrayList<String> primeros = mSiguientes.get(noTerminal);
            for(String primero : newsiguientes){
                if(!primeros.contains(primero))
                    primeros.add(primero);
            }
            //newprimeros.stream().forEach(primeros::add);
        } else {
            mSiguientes.put(noTerminal, new ArrayList(newsiguientes));
        }
    }
    
        /**
     * Anyade nuevos primeros a un simbolo no terminal
     *
     * @param noTerminal
     * @param newsiguientes
     */
    public void addSiguientes(String noTerminal, String newsiguientes) {
        ArrayList<String> primeros;
        if (mSiguientes.containsKey(noTerminal)) {
            primeros = mSiguientes.get(noTerminal);
            if(!primeros.contains(newsiguientes))
                primeros.add(newsiguientes);
        } else {
            primeros = new ArrayList();
            primeros.add(newsiguientes);
            mSiguientes.put(noTerminal, primeros);

        }
    }
    
    public void removeNoTerminal(String noTerminal){
        mSiguientes.remove(noTerminal);
    }
}
