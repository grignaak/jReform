package org.jreform;

import static junit.framework.Assert.assertTrue;

import java.util.Collection;

import org.jreform.util.HttpServletRequestStub;
import org.junit.Before;


public abstract class BaseTestCase
{
    private HttpServletRequestStub req;
    
    @Before
    public final void setUp() throws Exception
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
