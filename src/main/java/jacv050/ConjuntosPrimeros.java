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

    public ConjuntosPrimeros(){
        mPrimeros = new HashMap<>();
    }
    
    /**
     * Devuleve el conjunto de primeros de un simbolo no terminal
     *
     * @param noTerminal
     * @return
     */
    public ArrayList<String> getPrimeros(String noTerminal) {
        
        ArrayList<String> salida = mPrimeros.get(noTerminal);
        
        if(salida == null)
            salida = new ArrayList<>();
        
        return salida;
    }
    
    
    /**
     * Devuelve el conjunto de primeros de un simbolo no terminal menos epsilon
     * @param noTerminal 
     * @return 
     */
    public ArrayList<String> getPrimerosSinEpsilon(String noTerminal){
        ArrayList<String> primeros = new ArrayList<>();
        ArrayList<String> primerosTotal = mPrimeros.get(noTerminal);
        
        if(primerosTotal == null)
            return null;
        
        for(String simbolo : primerosTotal){
            if(!simbolo.equals(Reglas.EPSILON))
                primeros.add(simbolo);
        }
        /*mPrimeros.get(noTerminal).stream().filter((simbolo) -> (!simbolo.equals(Reglas.EPSILON))).forEach((simbolo) -> {
            primeros.add(simbolo);
        });*/
        return primeros;
    }

    /**
     * Anyade nuevos primeros a un simbolo no terminal
     * @param noTerminal
     * @param newprimeros
     */
    public void addPrimeros(String noTerminal, ArrayList<String> newprimeros) {
        if (mPrimeros.containsKey(noTerminal)) {
            ArrayList<String> primeros = mPrimeros.get(noTerminal);
            for(String primero : newprimeros){
                if(!primeros.contains(primero))
                    primeros.add(primero);
            }
            //newprimeros.stream().forEach(primeros::add);
        } else {
            mPrimeros.put(noTerminal, new ArrayList(newprimeros));
        }
    }

    /**
     * Anyade nuevos primeros a un simbolo no terminal
     *
     * @param noTerminal
     * @param newprimeros
     */
    public void addPrimeros(String noTerminal, String newprimeros) {
        ArrayList<String> primeros;
        if (mPrimeros.containsKey(noTerminal)) {
            primeros = mPrimeros.get(noTerminal);
            if(!primeros.contains(newprimeros))
                primeros.add(newprimeros);
        } else {
            primeros = new ArrayList();
            primeros.add(newprimeros);
            mPrimeros.put(noTerminal, primeros);

        }
    }
    
    public boolean exists(String noTerminal){
        return mPrimeros.containsKey(noTerminal);
    }
    
    public void removeNoTerminal(String noTerminal){
        mPrimeros.remove(noTerminal);
    }
}
