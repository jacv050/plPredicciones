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
public class ConjuntosPrediccion {
    HashMap<Reglas.Regla, ArrayList<String>> mPrediccion;
    
    public ConjuntosPrediccion(){
        mPrediccion = new HashMap<>();
    }
    
    public ArrayList<String> getPrediccion(String noTerminal){
        return mPrediccion.get(noTerminal);
    }
    
    public void addPrediccion(Reglas.Regla noTerminal, ArrayList<String> newprediccion) {
        if (mPrediccion.containsKey(noTerminal)) {
            ArrayList<String> primeros = mPrediccion.get(noTerminal);
            for(String primero : newprediccion){
                if(!primeros.contains(primero))
                    primeros.add(primero);
            }
            //newprimeros.stream().forEach(primeros::add);
        } else {
            mPrediccion.put(noTerminal, new ArrayList(newprediccion));
        }
    }

    public void addPrediccion(Reglas.Regla noTerminal, String newprediccion) {
        ArrayList<String> primeros;
        if (mPrediccion.containsKey(noTerminal)) {
            primeros = mPrediccion.get(noTerminal);
            if(!primeros.contains(newprediccion))
                primeros.add(newprediccion);
        } else {
            primeros = new ArrayList();
            primeros.add(newprediccion);
            mPrediccion.put(noTerminal, primeros);

        }
    }
    
    public ArrayList<String> getPrediccion(Reglas.Regla noTerminal){
        return mPrediccion.get(noTerminal);
    }
}
