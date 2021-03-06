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
    private ConjuntosPrediccion mPrediccion;
    private String nombreFichero;

    public Prediccion(String nombreFichero) {
        this.nombreFichero = nombreFichero;
        mReglas = new Reglas();
        mPrimeros = new ConjuntosPrimeros();
        mSiguientes = new ConjuntosSiguientes();
        mPrediccion = new ConjuntosPrediccion();
        
        try {
            FileReader fr = new FileReader(nombreFichero);
            BufferedReader br = new BufferedReader(fr);
            String cadena = "";

            while ((cadena = br.readLine()) != null) {
                mReglas.addRegla(cadena);
            }
            
            if(obtainPrimeros() && obtainSiguientes() && obtainPrediccion()){
                generarSalida();
            }

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
            
            pw.println();
            //Salida de los siguientes
            for(String noTerminal : noTerminales){
                pw.print(noTerminal + " -> { ");
                ArrayList<String> siguientes = mSiguientes.getSiguientes(noTerminal);
                for(String siguiente : siguientes){
                    pw.print(siguiente + " ");
                }
                pw.print("}");
                
                pw.println();
            }
            
            //Salida de predicciones
            pw.println();
            ArrayList<Reglas.Regla> reglas = mReglas.getReglas();
            for(Reglas.Regla regla : reglas){
                pw.print(regla.getParteIzquierda());
                pw.print(" -> ");
                for(String s : regla.getParteDerecha())
                    pw.print(s + " ");
                
                pw.print("\t\t\t");
                
                pw.print("{ ");
                for(String s : mPrediccion.getPrediccion(regla)){
                    pw.print(s + " ");
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
    
    public ConjuntosSiguientes getConjuntosSiguientes(){
        return mSiguientes;
    }
    
    public ConjuntosPrediccion getConjuntosPrediccion(){
        return mPrediccion;
    }
    
    public boolean obtainPrediccionAux(Reglas.Regla regla){
        
        
        //Si tiene epsilon
        boolean conEpsilon = true;
        ArrayList<String> parteDerecha = regla.getParteDerecha();
        for(int i=0; conEpsilon && i<parteDerecha.size(); ++i){
            String simbolo = parteDerecha.get(i);
            ArrayList<String> primerosSinEpsilon = null;
            ArrayList<String> primerosConEpsilon = null;
            boolean esTerminal = mReglas.esTerminal(simbolo);
            if(!esTerminal){
                primerosSinEpsilon = mPrimeros.getPrimerosSinEpsilon(simbolo);
                primerosConEpsilon = mPrimeros.getPrimeros(simbolo);
                
                mPrediccion.addPrediccion(regla, primerosSinEpsilon);
                if(primerosConEpsilon.size() == primerosSinEpsilon.size()){
                    conEpsilon = false;
                }
            }else if(!mReglas.esEpsilon(simbolo)){
                mPrediccion.addPrediccion(regla, simbolo);
                conEpsilon = false;
            //Si es epsilon
            }else{
                break;
            }
        }
        
        if(conEpsilon)
            mPrediccion.addPrediccion(regla, mSiguientes.getSiguientes(regla.getParteIzquierda()));
        
        return true;
    }
    
    public boolean obtainPrediccion(){
                //ArrayList<String> salida = new ArrayList<>();
        //boolean[] simboloProcesados = new boolean[mReglas.getListaSimbolosNoTerminales().size()];
        
        ArrayList<Reglas.Regla> reglas = mReglas.getReglas();
        
        for(Reglas.Regla regla : reglas){
            obtainPrediccionAux(regla);
        }
        
        return true;
    }
    
    public boolean obtainSiguientesAux(String noTerminal){
        
        ArrayList<Reglas.Regla> reglas = mReglas.getReglasSimboloDerecha(noTerminal);
        
        for(Reglas.Regla regla : reglas){
            ArrayList<String> parteDerecha = regla.getParteDerecha();
            int posicionSimbolo = parteDerecha.indexOf(noTerminal);
            
            //Si hay simbolos a la derecha del simbolo no terminal
            if(posicionSimbolo < parteDerecha.size()-1){
                if(!mReglas.esTerminal(parteDerecha.get(posicionSimbolo+1))){
                    ArrayList<String> primerosSinEpsilon = mPrimeros.getPrimerosSinEpsilon(parteDerecha.get(posicionSimbolo+1));
                    ArrayList<String> primerosConEpsilon = mPrimeros.getPrimeros(parteDerecha.get(posicionSimbolo+1));
                    mSiguientes.addSiguientes(noTerminal, primerosSinEpsilon);
                    
                    //Si epsilon pertence a siguientes(posicion+1)
                    if(primerosSinEpsilon.size() != primerosConEpsilon.size()){
                        //TODO ArrayList<String> siguientes = mSiguientes.getSiguientes(regla.getParteIzquierda());
                        ArrayList<String> siguientes = mSiguientes.getSiguientes(parteDerecha.get(posicionSimbolo+1));
                        
                        //Si aun no se han calculado siguientes devolver false
                        if(siguientes == null){
                            mSiguientes.removeNoTerminal(noTerminal);
                            return false;
                        }
                        
                        //Anyadimos los siguientes del simbolo no terminal de la parte izquierda de la regla
                        mSiguientes.addSiguientes(noTerminal, siguientes);
                    }
                }else if(mReglas.esEpsilon(parteDerecha.get(posicionSimbolo+1))){
                    ArrayList<String> siguientes = mSiguientes.getSiguientes(regla.getParteIzquierda());
                        
                    //Si aun no se han calculado siguientes devolver false
                    if(siguientes == null){
                        mSiguientes.removeNoTerminal(noTerminal);
                        return false;
                    }
                        
                    //Anyadimos los siguientes del simbolo no terminal de la parte izquierda de la regla
                    mSiguientes.addSiguientes(noTerminal, siguientes);
                }else//Si es terminal anyadimos el simbolo como siguiente de noterminal
                    mSiguientes.addSiguientes(noTerminal, parteDerecha.get(posicionSimbolo+1));
            }else{
                //Comprobar si realmente debo anyadir los siguientes de la parteizquierda cuando el
                //sombolo no terminal de la parte derecha no tiene simbolos a su derecha
                ArrayList<String> siguientes = mSiguientes.getSiguientes(regla.getParteIzquierda());
                
                //Si aun no se han calculado siguientes devolver false
                if(siguientes == null){
                    mSiguientes.removeNoTerminal(noTerminal);
                    return false;
                }
                
                //Anyadimos los siguientes del simbolo no terminal de la parte izquierda de la regla
                mSiguientes.addSiguientes(noTerminal, siguientes);
            }
        }
        
        return true;
    }
    
    public boolean obtainSiguientes(){
                //ArrayList<String> salida = new ArrayList<>();
        boolean[] simboloProcesados = new boolean[mReglas.getListaSimbolosNoTerminales().size()];
        boolean procesados = true;
        
        ArrayList<String> noTerminales = mReglas.getListaSimbolosNoTerminales();
        
        //Anyadimos el simbolo inicial
        mSiguientes.addSiguientes(noTerminales.get(0), Reglas.INICIAL);
        int iteraciones = 0;
        
        do{
            if(iteraciones > noTerminales.size()){
                System.err.println("Error: Puede haber entrado en un bucle infinito por un error en la gramatica");
                return false;
            }
            
            procesados = true;
            for (int i=0; i<noTerminales.size(); ++i) {
                if(!simboloProcesados[i])
                    simboloProcesados[i] = obtainSiguientesAux(noTerminales.get(i));
            }
            
            //Comprobamos si hemos encontrado todos los primerosSinEpsilon
            for(boolean sp : simboloProcesados){
                procesados &= sp;
            }
            
            iteraciones++;
        }while(!procesados);
        
        return true;
    }
    
    /**
     * Devuelve el conjunto de primerosSinEpsilon pertenecientes a un simbolo no terminal
     *
     * @param simboloNoTerminal El simbolo no terminal del que queremos el
 conjunto de primerosSinEpsilon
            ArrayList<String> parteDerecha = regla.getParteDerecha();
     * @return Devuelve si ha sido capaz de obtener los primerosSinEpsilon del simbolo
     */
    private boolean obtainPrimerosAux(String noTerminal) {
        //Obtenemos las reglas en las que aparece el simbolo terminal en la
        //parte izquierda.
        ArrayList<Reglas.Regla> reglas = mReglas.getReglas(noTerminal);
        //ArrayList<String> primerosAux = new ArrayList<>();
        
        //boolean salida = true;
        boolean esSegundoRecorrido = false;

        //Obtenemos las reglas que empiezans con el simbolo "noTerminal"
        //for (Reglas.Regla regla : reglas) {
        for(int i=0; i < reglas.size(); ++i){
            Reglas.Regla regla = reglas.get(i);
            ArrayList<String> parteDerecha = regla.getParteDerecha();

            //Analizamos la parte derecha y obtenemos los primerosSinEpsilon
            
            //Si es epsilon
            String simbolo = parteDerecha.get(0);
            if (mReglas.esEpsilon(simbolo)) {
                mPrimeros.addPrimeros(regla.getParteIzquierda(), simbolo);
            } //Si es terminal
            else if (mReglas.esTerminal(simbolo)) {
                mPrimeros.addPrimeros(regla.getParteIzquierda(), simbolo);
            } //Si es no terminal
            else {
                //Si epsilon pertenece a primerosSinEpsilon(simbolo) 
                ArrayList<String> primerosConEpsilon = mPrimeros.getPrimeros(simbolo); //Con epsilon si tuviese
                
                if(simbolo.equals(regla.getParteIzquierda()) && !esSegundoRecorrido){
                    esSegundoRecorrido = true;
                    reglas.add(regla);
                    //salida = false;
                    continue;
                }
                
                //Si aun no se han calculado los primerosSinEpsilon
                if(primerosConEpsilon.isEmpty()){
                    mPrimeros.removeNoTerminal(noTerminal);
                    return false;
                }
                
                //Obtenemos los primerosSinEpsilon de simbolo -> primerosSinEpsilon(simbolo)
                ArrayList<String> primerosSinEpsilon = mPrimeros.getPrimerosSinEpsilon(simbolo);
                //Anyadimos el conjunto de primerosSinEpsilon(simbolo) a los primerosSinEpsilon(noTerminal)
                for (String primero : primerosSinEpsilon) {
                    mPrimeros.addPrimeros(noTerminal, primero);
                    //primerosAux.add(primero);
                }
                
                if (primerosSinEpsilon.size() != primerosConEpsilon.size()) {
                    //Si solo tiene epsilon entonces anyadimos epsilon a primerosSinEpsilon(noTerminal)
                    //if (primerosConEpsilon.size() == 1 && !simbolo.equals(regla.getParteIzquierda())) {
                    if (regla.getParteDerecha().size() == 1 && !simbolo.equals(regla.getParteIzquierda())) {
                    //if (i == regla.getParteDerecha().size()-1 && !simbolo.equals(regla.getParteIzquierda())) {
                        mPrimeros.addPrimeros(noTerminal, Reglas.EPSILON);
                        //primerosAux.add(Reglas.EPSILON);
                    } else {
                        //Hay que añadir los primerosSinEpsilon del resto de simbolos
                        //de la parte derecha "noTerminal"
                        
                        boolean salir = false;
                        for (int j = 1; j < parteDerecha.size() && !salir; ++j) {
                        //for (int j = 1; j < parteDerecha.size() && j < 2; ++j) {
                            boolean esTerminal = mReglas.esTerminal(parteDerecha.get(j));
                            if(!mPrimeros.exists(parteDerecha.get(j)) && !esTerminal){
                                mPrimeros.removeNoTerminal(noTerminal);
                                return false;
                            }
                            
                            if(esTerminal){
                                mPrimeros.addPrimeros(noTerminal, parteDerecha.get(j));
                                salir = true;
                                //primerosAux.add(parteDerecha.get(j));
                            }else{
                                ArrayList<String> pd = mPrimeros.getPrimeros(parteDerecha.get(j));
                                ArrayList<String> pdsinep = mPrimeros.getPrimerosSinEpsilon(parteDerecha.get(j));
                                mPrimeros.addPrimeros(noTerminal, pdsinep);
                                //Si no tiene epsilon, salimos del bucle
                                if(pd.size() == pdsinep.size())
                                    salir = true;
                                else if(j == parteDerecha.size()-1)
                                    mPrimeros.addPrimeros(noTerminal, Reglas.EPSILON);
                                    
                            }
                                //primerosAux.addAll(mPrimeros.getPrimeros(parteDerecha.get(j)));
                        }
                    }
                }

                //primeros(simbolo) - {epsilon}
                //if()
            }

        }
        
        //if(!salida)
        //    mPrimeros.removeNoTerminal(noTerminal);
            //Borrar el noTerminal
            //mPrimeros.addPrimeros(noTerminal, primerosAux);
            
        return true;

    }

    private boolean obtainPrimeros() {
        //ArrayList<String> salida = new ArrayList<>();
        boolean[] simboloProcesados = new boolean[mReglas.getListaSimbolosNoTerminales().size()];
        boolean procesados = true;
        
        ArrayList<String> noTerminales = mReglas.getListaSimbolosNoTerminales();
        int iteraciones = 0;
        
        do{
            if(iteraciones > noTerminales.size()){
                System.err.println("Error: Puede haber entrado en un bucle infinito por un error en la gramatica");
                return false;
            }
            
            procesados = true;
            for (int i=0; i<noTerminales.size(); ++i) {
                if(!simboloProcesados[i])
                    simboloProcesados[i] = obtainPrimerosAux(noTerminales.get(i));
            }
            
            //Comprobamos si hemos encontrado todos los primerosSinEpsilon
            for(boolean sp : simboloProcesados){
                procesados &= sp;
            }
            
            iteraciones++;
        }while(!procesados);
        
        return true;
    }

}

//Como actuar si primer del noterminal es recursivo
//Duda respecto siguiente en caso de que contenga epsilon el de la posicion +1