package milindparikh;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class BasicEventingTest 
    extends TestCase
{

    public BasicEventingTest( String testName )
    {
        super( testName );
    }


    public static Test suite()
    {
        return new TestSuite( BasicEventingTest.class );
    }

    public void testBasicEventing()
    {
        assertTrue( true );
    }
}
