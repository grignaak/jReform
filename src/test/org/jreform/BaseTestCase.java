package test.org.jreform;

import junit.framework.TestCase;

import org.jreform.internal.HtmlForm;

import test.util.HttpServletRequestStub;

abstract class BaseTestCase extends TestCase
{
    private HttpServletRequestStub req;
    
    protected final void setUp() throws Exception
    {
        req = new HttpServletRequestStub();
        init();
    }
    
    protected abstract HtmlForm getForm();
    protected void init() {}
    protected void destroy() {}
    
    protected boolean validateForm()
    {
        return getForm().validate(req) && getForm().isValid();
    }
    
    protected void setParameter(String key, String value)
    {
        req.setParameter(key, value);
    }
    
    protected void setParameters(String key, String[] values)
    {
        req.setParameterValues(key, values);
    }
    
}
