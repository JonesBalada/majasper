package zclh.majasper;

import java.io.IOException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{

	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     * @throws IOException 
     * @throws InterruptedException 
     */
    public void testApp() throws IOException, InterruptedException{
    		String nomeArquivo = "Voos";
    		String camAtehPasta = "/home/coelho/workspace/neon/edbv/edbv-parent/edbv-dominio/src/main/resources/";
String pasta = "jasperreports";
    	String args[] = new String[3];
    	args[0] = camAtehPasta;
    	args[1] = pasta;
    	args[2] = nomeArquivo;
    	// App.main(args);

//    	String args[] = new String[1];
////    	args[0] = "fill";
////    	args[0] = "pdf";
//    	args[0] = "html";
//    	StretchApp.main(args);
//        assertTrue( true );
    }
}
