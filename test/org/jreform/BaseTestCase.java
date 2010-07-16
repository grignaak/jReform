package org.jreform;

import java.util.Collection;

import junit.framework.TestCase;

import org.jreform.util.HttpServletRequestStub;


abstract class BaseTestCase extends TestCase
{
    private HttpServletRequestStub req;
    
    protected final void setUp() throws Exception
    {
        req = new HttpServletRequestStub();
        init();
    }
    
    protected abstract Form getForm();
    protected void init() {}
    protected void destroy() {}
    
    protected boolean validateForm()
    {
        return getForm().validateRequest(req) && getForm().isValid();
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
