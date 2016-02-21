/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jacv050;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
//import jacv050.Reglas.*;

/**
 *
 * @author jacv050
 */
public class Prediccion {

    private Reglas mReglas;
    private ConjuntosPrimeros mPrimeros;
    private ConjuntosSiguientes mSiguientes;
    private String nombreFichero;

    public Prediccion(String nombreFichero) {
        this.nombreFichero = nombreFichero;
        mReglas = new Reglas();
        mPrimeros = new ConjuntosPrimeros();
        
        try {
            FileReader fr = new FileReader(nombreFichero);
            BufferedReader br = new BufferedReader(fr);
            String cadena = "";

            while ((cadena = br.readLine()) != null) {
                mReglas.addRegla(cadena);
            }
            
            obtainPrimeros();
            generarSalida();

            br.close();
            fr.close();

        } catch (FileNotFoundException ex) {
            System.err.println("No se ha encontrado el archivo.");
        } catch (IOException ex) {
            Logger.getLogger(Prediccion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public final void generarSalida(){
        
        try {
            FileWriter fw = new FileWriter(nombreFichero + ".salida");
            PrintWriter pw = new PrintWriter(fw);
            
            ArrayList<String> noTerminales = mReglas.getListaSimbolosNoTerminales();
            for(String noTerminal : noTerminales){
                pw.print(noTerminal + " -> { ");
                ArrayList<String> primeros = mPrimeros.getPrimeros(noTerminal);
                for(String primero : primeros){
                    pw.print(primero + " ");
                }
                pw.print("}");
                
                pw.println();
            }
            
            pw.close();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Prediccion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public ConjuntosPrimeros getConjuntosPrimeros(){
        return mPrimeros;
    }
    
    public boolean obtainSiguientesAux(String noTerminal){
        
        return true;
    }
    
    public void obtainSiguientes(){
                //ArrayList<String> salida = new ArrayList<>();
        boolean[] simboloProcesados = new boolean[mReglas.getListaSimbolosNoTerminales().size()];
        boolean procesados = true;
        
        ArrayList<String> noTerminales = mReglas.getListaSimbolosNoTerminales();
        
        //Anyadimos el simbolo inicial
        
        
        do{
            procesados = true;
            for (int i=0; i<noTerminales.size(); ++i) {
                if(!simboloProcesados[i])
                    simboloProcesados[i] = obtainSiguientesAux(noTerminales.get(i));
            }
            
            //Comprobamos si hemos encontrado todos los primeros
            for(boolean sp : simboloProcesados){
                procesados &= sp;
            }
        }while(!procesados);
    }
    
    /**
     * Devuelve el conjunto de primeros pertenecientes a un simbolo no terminal
     *
     * @param simboloNoTerminal El simbolo no terminal del que queremos el
     * conjunto de primeros
     * @return Devuelve si ha sido capaz de obtener los primeros del simbolo
     */
    private boolean obtainPrimerosAux(String noTerminal) {
        //Obtenemos las reglas en las que aparece el simbolo terminal en la
        //parte izquierda.
        ArrayList<Reglas.Regla> reglas = mReglas.getReglas(noTerminal);

        //Obtenemos las reglas que empiezans con el simbolo "noTerminal"
        for (Reglas.Regla regla : reglas) {
            ArrayList<String> parteDerecha = regla.getParteDerecha();

            //Analizamos la parte derecha y obtenemos los primeros
            
            //Si es epsilon
            String simbolo = parteDerecha.get(0);
            if (mReglas.esEpsilon(simbolo)) {
                mPrimeros.addPrimeros(regla.getParteIzquierda(), simbolo);
            } //Si es terminal
            else if (mReglas.esTerminal(simbolo)) {
                mPrimeros.addPrimeros(regla.getParteIzquierda(), simbolo);
            } //Si es no terminal
            else {
                //Si epsilon pertenece a primeros(simbolo) 
                ArrayList<String> primerosConEpsilon = mPrimeros.getPrimeros(simbolo); //Con epsilon si tuviese
                
                //Si aun no se han calculado los primeros
                if(primerosConEpsilon.isEmpty())
                    return false;
                
                //Obtenemos los primeros de simbolo -> primeros(simbolo)
                ArrayList<String> primerosSinEpsilon = mPrimeros.getPrimerosSinEpsilon(simbolo);
                //Anyadimos el conjunto de primeros(simbolo) a los primeros(noTerminal)
                for (String primero : primerosSinEpsilon) {
                    mPrimeros.addPrimeros(noTerminal, primero);
                }
                
                if (primerosSinEpsilon.size() != primerosConEpsilon.size()) {
                    //Si solo tiene epsilon entonces anyadimos epsilon a primeros(noTerminal)
                    if (primerosConEpsilon.size() == 1) {
                        mPrimeros.addPrimeros(noTerminal, Reglas.EPSILON);
                    } else {
                        //Hay que añadir los primeros del resto de simbolos
                        //de la parte derecha "noTerminal"
                        for (int i = 1; i < parteDerecha.size(); ++i) {
                            boolean esTerminal = mReglas.esTerminal(parteDerecha.get(i));
                            if(!mPrimeros.exists(parteDerecha.get(i)) && !esTerminal)
                                return false;
                            
                            if(esTerminal)
                                mPrimeros.addPrimeros(noTerminal, parteDerecha.get(i));
                            else
                                mPrimeros.addPrimeros(noTerminal, mPrimeros.getPrimeros(parteDerecha.get(i)));
                        }
                    }
                }

                //primeros(simbolo) - {epsilon}
                //if()
            }

        }
        
        return true;

    }

    private void obtainPrimeros() {
        //ArrayList<String> salida = new ArrayList<>();
        boolean[] simboloProcesados = new boolean[mReglas.getListaSimbolosNoTerminales().size()];
        boolean procesados = true;
        
        ArrayList<String> noTerminales = mReglas.getListaSimbolosNoTerminales();
        
        do{
            procesados = true;
            for (int i=0; i<noTerminales.size(); ++i) {
                if(!simboloProcesados[i])
                    simboloProcesados[i] = obtainPrimerosAux(noTerminales.get(i));
            }
            
            //Comprobamos si hemos encontrado todos los primeros
            for(boolean sp : simboloProcesados){
                procesados &= sp;
            }
        }while(!procesados);
    }

}

