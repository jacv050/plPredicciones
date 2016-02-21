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

    ArrayList<Regla> mReglas;
    ArrayList<String> mNoTerminales;
    //ArrayList<String> terminales;

    public final static String EPSILON = "epsilon";
    public final static String INICIAL = "$";
    
    public Reglas() {
        mReglas = new ArrayList<>();
        mNoTerminales = new ArrayList<>();
        //terminales = new ArrayList<String>();

    }

    public void addRegla(String regla) {
        String[] splitted = regla.split(" ");

        if (!mNoTerminales.contains(splitted[0])) {
            mNoTerminales.add(splitted[0]);
        }

        ArrayList<String> parteDerecha = new ArrayList<>();
        for (int i = 2; i < splitted.length; ++i) {
            parteDerecha.add(splitted[i]);
        }

        mReglas.add(new Regla(splitted[0], parteDerecha));
    }

    public ArrayList<String> getListaSimbolosNoTerminales() {
        return mNoTerminales;
    }

    /**
     * Devuelve las reglas con simbolos en la parte izquierda igual al pasado
     * por parametro
     *
     * @param simboloParteIzquierda
     * @return
     */
    public ArrayList<Regla> getReglas(String simboloParteIzquierda) {
        ArrayList<Regla> salida = new ArrayList<>();

        //mReglas.stream().filter((regla) -> (regla.getParteIzquierda().equals(simboloParteIzquierda))).forEach(salida::add);
        //codigo equivalente
        for (Regla regla : mReglas) {
            if (regla.getParteIzquierda().equals(simboloParteIzquierda)) {
                salida.add(regla);
            }
        }

        return salida;
    }

    public ArrayList<Regla> getReglasSimboloDerecha(String simboloParteDerecha){
        ArrayList<Regla> salida = new ArrayList<>();
        
        for(Regla regla : mReglas){
            if(regla.getParteDerecha().contains(simboloParteDerecha))
                salida.add(regla);
        }
        
        return salida;
    }
    
    /**
     * Devuelve true si es un simbolo terminal
     * @param simbolo
     * @return 
     */
    public boolean esTerminal(String simbolo){
        return !mNoTerminales.contains(simbolo);
    }
    
    /**
     * Devuelve true si el simbolo es epsilon
     * @param simbolo
     * @return 
     */
    public boolean esEpsilon(String simbolo){
        return simbolo.equals(EPSILON);
    }
    
    public class Regla {

        private final String parteIzquierda;
        private final ArrayList<String> parteDerecha;

        public Regla(Regla regla) {
            parteIzquierda = regla.getParteIzquierda();
            parteDerecha = new ArrayList<>(regla.getParteDerecha());
        }

        public Regla(String parteIzquierda, ArrayList<String> parteDerecha) {
            this.parteIzquierda = parteIzquierda;
            this.parteDerecha = parteDerecha;
        }

        public String getString() {
            return parteIzquierda;
        }

        public String getParteIzquierda() {
            return parteIzquierda;
        }

        public ArrayList<String> getParteDerecha() {
            return parteDerecha;
        }
    }
}
