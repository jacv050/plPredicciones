/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jacv050;

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
public class ObtencionSiguientesTest {
    
    Prediccion mPrediccion;
    
    public ObtencionSiguientesTest() {
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
        ConjuntosSiguientes real = mPrediccion.getConjuntosSiguientes();
        
        List<String> esperadoA = Arrays.asList("$");
        List<String> esperadoB = Arrays.asList("cat", "cow");
        List<String> esperadoC = Arrays.asList("$", "boss");
        
        assertEqualsConjuntos(esperadoA, real.getSiguientes("A"));
        assertEqualsConjuntos(esperadoB, real.getSiguientes("B"));
        assertEqualsConjuntos(esperadoC, real.getSiguientes("C"));
        //assertEquals(esperadoA, real.getPrimeros(ficheroEntrada));
    }
    
    
    @Test
    public void C2Reglas2Test(){
        String ficheroEntrada = "reglas2.txt";
        mPrediccion = new Prediccion(ficheroEntrada);
        ConjuntosSiguientes real = mPrediccion.getConjuntosSiguientes();
        
        List<String> esperadoA = Arrays.asList("$", "all", "boss");
        List<String> esperadoB = Arrays.asList("cat", "cow");
        List<String> esperadoC = Arrays.asList("$", "all", "boss", "cat", "cow");
        
        assertEqualsConjuntos(esperadoA, real.getSiguientes("A"));
        assertEqualsConjuntos(esperadoB, real.getSiguientes("B"));
        assertEqualsConjuntos(esperadoC, real.getSiguientes("C"));
        //assertEquals(esperadoA, real.getPrimeros(ficheroEntrada));
    }
    
    @Test
    public void C3Reglas3Test(){
        //HAY QUE MIRAR EL RESTOS DE SIMBOLOS SI EL SIMBOLO INTERNO NO ES TERMINAL
        String ficheroEntrada = "reglas3.txt";
        mPrediccion = new Prediccion(ficheroEntrada);
        ConjuntosSiguientes real = mPrediccion.getConjuntosSiguientes();
        
        List<String> esperadoA = Arrays.asList("tres", "cuatro", "$", "cinco", "seis");
        List<String> esperadoB = Arrays.asList("seis", "cinco", "$", "tres", "cuatro");
        List<String> esperadoC = Arrays.asList("cinco", "$");
        List<String> esperadoD = Arrays.asList("tres");
        List<String> esperadoE = Arrays.asList("$", "tres");
        List<String> esperadoS = Arrays.asList("$");
        
        
        assertEqualsConjuntos(esperadoA, real.getSiguientes("A"));
        assertEqualsConjuntos(esperadoB, real.getSiguientes("B"));
        assertEqualsConjuntos(esperadoC, real.getSiguientes("C"));
        assertEqualsConjuntos(esperadoD, real.getSiguientes("D"));
        assertEqualsConjuntos(esperadoE, real.getSiguientes("E"));
        assertEqualsConjuntos(esperadoS, real.getSiguientes("S"));
        //assertEquals(esperadoA, real.getPrimeros(ficheroEntrada));
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
