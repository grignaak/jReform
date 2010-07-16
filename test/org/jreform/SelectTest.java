package org.jreform;

import static junit.framework.Assert.*;

import java.util.Arrays;

import org.jreform.impl.GenericForm;
import org.jreform.inputs.MultiSelect;
import org.jreform.inputs.Select;
import org.jreform.types.Types;
import org.junit.Test;

public class SelectTest extends BaseTestCase
{
    private static final String COUNTRY = "country";
    private static final String HOTELS = "hotels";
    
    private TestForm form;
    
    protected void init()
    {
        form = new TestForm();
    }
    
    protected Form getForm()
    {
        return form;
    }
    
    /** select multiple selections */
    @Test
    public void testSelectingMultipleSelections()
    {
        final String england = "England";
        final String claridges = "Claridge's";
        final String fawlty = "Fawlty Towers";
        
        setParameter(COUNTRY, england);
        setParameters(HOTELS, new String[] {claridges, fawlty});
        
        assertTrue(validateForm());
        
        assertTrue(form.country().isRequired());
        assertTrue(form.country().isValid());
        
        assertFalse(form.hotels().isRequired());
        assertTrue(form.hotels().isValid());
        
        // TODO test that this parses things properly
        assertEquals(england, form.country().getValue());
        assertTrue(form.hotels().getSelectedKeys().contains(claridges));
        assertTrue(form.hotels().getSelectedKeys().contains(fawlty));
        
        assertFalse(form.hotels().getSelectedKeys().contains("unknown"));
        
        assertEquals(form.country().getValue(), england);
        assertEquals(form.hotels().getValues().get(0), claridges);
        assertEquals(form.hotels().getValues().get(1), fawlty);
        
        assertTrue(form.hotels().getValues().size() == 2);
    }
    
    @Test
    public void testSetValueAttributeSetsState()
    {
        form.country().setValueAttribute("country");
        assertEquals("country", form.country().getValue());
        
        form.hotels().setValueAttributes(Arrays.asList("one", "two"));
        assertTrue(form.hotels().getSelectedKeys().contains("one"));
        assertTrue(form.hotels().getSelectedKeys().contains("two"));
    }
    
    private static class TestForm extends GenericForm
    {
        public TestForm()
        {
            // required
            select(Types.stringType(), COUNTRY);
            
            // optional multi select
            multiSelect(Types.stringType(), HOTELS).optional();
        }
        
        public Select<String> country()
        {
            return getSelect(COUNTRY);
        }
        
        public MultiSelect<String> hotels()
        {
            return getMultiSelect(HOTELS);
        }
        
    }
    
}
