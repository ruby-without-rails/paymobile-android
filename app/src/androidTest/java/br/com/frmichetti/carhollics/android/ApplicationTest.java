/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.frmichetti.carhollics.android;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ApplicationTest {

    boolean b;

    @Before
    public void init(){
         b = true;
    }

    @Test
    public void firstTest(){

        assertTrue(b);
    }


}