/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jacv050;

import java.util.ArrayList;

/**
 *
 * @author jacv050
 */
public class Reglas {
    
    ArrayList<Regla> reglas;
    ArrayList<String> noTerminales;
    //ArrayList<String> terminales;
    
    public Reglas(){
        reglas = new ArrayList<>();
        noTerminales = new ArrayList<>();
        //terminales = new ArrayList<String>();
        
    }
            
    public void addRegla(String regla){
        String[] splitted = regla.split(" ");
        
        if(!noTerminales.contains(splitted[0]))
            noTerminales.add(splitted[0]);
        
        ArrayList<String> parteDerecha = new ArrayList<>();
        for(int i =2; i<splitted.length; ++i){
            parteDerecha.add(splitted[i]);
        }
        
        reglas.add(new Regla(splitted[0], parteDerecha));
    }
    
}


class Regla{
    private String parteIzquierda;
    private ArrayList<String> parteDerecha;
    
    public Regla(String parteIzquierda, ArrayList<String> parteDerecha){
        this.parteIzquierda = parteIzquierda;
        this.parteDerecha = parteDerecha;
    }
    
    public String getString(){
        return parteIzquierda;
    }
    
    public ArrayList<String> getParteDerecha(){
        return parteDerecha;
    }
}