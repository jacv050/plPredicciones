/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jacv050;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jacv050
 */
public class ObtencionPrimerosTest {
    
    Prediccion mPrediccion;
    
    public ObtencionPrimerosTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    public void assertEqualsConjuntos(List<String> esperados, List<String> reales){
        if(esperados.size() != reales.size())
            fail();
            
        for(String real : reales){
            if(!esperados.contains(real))
                fail();
        }
    }
    
    @Test
    public void C1Reglas1Test(){
        String ficheroEntrada = "reglas1.txt";
        mPrediccion = new Prediccion(ficheroEntrada);
        ConjuntosPrimeros real = mPrediccion.getConjuntosPrimeros();
        
        List<String> esperadoA = Arrays.asList("bad", "big", "cat", "cow");
        List<String> esperadoB = Arrays.asList("big", "epsilon");
        List<String> esperadoC = Arrays.asList("cat", "cow");
        
        assertEqualsConjuntos(esperadoA, real.getPrimeros("A"));
        assertEqualsConjuntos(esperadoB, real.getPrimeros("B"));
        assertEqualsConjuntos(esperadoC, real.getPrimeros("C"));
        //assertEquals(esperadoA, real.getPrimeros(ficheroEntrada));
    }
    
    
    @Test
    public void C2Reglas2Test(){
        String ficheroEntrada = "reglas2.txt";
        mPrediccion = new Prediccion(ficheroEntrada);
        ConjuntosPrimeros real = mPrediccion.getConjuntosPrimeros();
        
        List<String> esperadoA = Arrays.asList("ant", "big", "bus", "cat", "cow");
        List<String> esperadoB = Arrays.asList("big", "bus", "epsilon");
        List<String> esperadoC = Arrays.asList("cat", "cow");
        
        assertEqualsConjuntos(esperadoA, real.getPrimeros("A"));
        assertEqualsConjuntos(esperadoB, real.getPrimeros("B"));
        assertEqualsConjuntos(esperadoC, real.getPrimeros("C"));
        //assertEquals(esperadoA, real.getPrimeros(ficheroEntrada));
    }
    
    @Test
    public void C3Reglas3Test(){
        //HAY QUE MIRAR EL RESTOS DE SIMBOLOS SI EL SIMBOLO INTERNO NO ES TERMINAL
        String ficheroEntrada = "reglas3.txt";
        mPrediccion = new Prediccion(ficheroEntrada);
        ConjuntosPrimeros real = mPrediccion.getConjuntosPrimeros();
        
        List<String> esperadoA = Arrays.asList("dos", "epsilon");
        List<String> esperadoB = Arrays.asList("epsilon", "cuatro");
        List<String> esperadoC = Arrays.asList("seis", "epsilon");
        List<String> esperadoD = Arrays.asList("uno", "cuatro", "epsilon");
        List<String> esperadoE = Arrays.asList("tres");
        List<String> esperadoS = Arrays.asList("dos", "cuatro", "epsilon", "uno", "tres", "seis");
        
        
        assertEqualsConjuntos(esperadoA, real.getPrimeros("A"));
        assertEqualsConjuntos(esperadoB, real.getPrimeros("B"));
        assertEqualsConjuntos(esperadoC, real.getPrimeros("C"));
        assertEqualsConjuntos(esperadoD, real.getPrimeros("D"));
        assertEqualsConjuntos(esperadoE, real.getPrimeros("E"));
        assertEqualsConjuntos(esperadoS, real.getPrimeros("S"));
        //assertEquals(esperadoA, real.getPrimeros(ficheroEntrada));
    }
    
    
    @Test
    public void C4Reglas4Test(){
        //HAY QUE MIRAR EL RESTOS DE SIMBOLOS SI EL SIMBOLO INTERNO NO ES TERMINAL
        String ficheroEntrada = "reglas4.txt";
        mPrediccion = new Prediccion(ficheroEntrada);
        ConjuntosPrimeros real = mPrediccion.getConjuntosPrimeros();
        
        List<String> esperadoA = Arrays.asList("b", "c", "d", "e");
        List<String> esperadoB = Arrays.asList("b", "epsilon");
        List<String> esperadoC = Arrays.asList("c", "epsilon");
        List<String> esperadoD = Arrays.asList("d", "c", "e"); 
        
        assertEqualsConjuntos(esperadoA, real.getPrimeros("A"));
        assertEqualsConjuntos(esperadoB, real.getPrimeros("B"));
        assertEqualsConjuntos(esperadoC, real.getPrimeros("C"));
        assertEqualsConjuntos(esperadoD, real.getPrimeros("D"));
        //assertEquals(esperadoA, real.getPrimeros(ficheroEntrada));
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
