package org.jreform;

import static junit.framework.Assert.assertTrue;

import java.util.Collection;

import org.jreform.util.HttpServletRequestStub;
import org.junit.Before;


public abstract class BaseTestCase<T extends Form>
{
    private HttpServletRequestStub req;
    protected T form = createForm();
    
    protected abstract T createForm();
    
    @Before
    public final void setUp() throws Exception
    {
        req = new HttpServletRequestStub();
    }
    
    protected boolean validateForm()
    {
        return form.validateRequest(req);
    }
    
    protected void setParameter(String key, String value)
    {
        req.setParameter(key, value);
    }
    
    protected void setParameters(String key, String[] values)
    {
        req.setParameterValues(key, values);
    }
    
    protected static <T> void assertContains(T thing, Collection<? super T> things)
    {
        assertTrue(things + " does not contain " + thing,
                things.contains(thing));
    }
    
}
