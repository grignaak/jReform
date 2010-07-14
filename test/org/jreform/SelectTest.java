package org.jreform;

import static org.jreform.inputs.SelectableState.SELECTED;
import static org.jreform.inputs.SelectableState.UNSELECTED;

import org.jreform.impl.HtmlFormSupport;
import org.jreform.inputs.MultiSelect;
import org.jreform.inputs.Select;
import org.jreform.inputs.SelectableState;

public class SelectTest extends BaseTestCase
{
    private static final String COUNTRY = "country";
    private static final String HOTELS = "hotels";
    
    private TestForm form;
    
    protected void init()
    {
        form = new TestForm();
    }
    
    protected HtmlFormSupport getForm()
    {
        return form;
    }
    
    /** select multiple selections */
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
        
        assertEquals(form.country().getState().get(england), SELECTED);
        assertEquals(form.hotels().getState().get(claridges), SELECTED);
        assertEquals(form.hotels().getState().get(fawlty), SELECTED);
        
        assertEquals(form.country().getState().get("unknown"), UNSELECTED);
        assertEquals(form.hotels().getState().get("unknown"), UNSELECTED);
        
        assertEquals(form.country().getValue(), england);
        assertEquals(form.hotels().getValues().get(0), claridges);
        assertEquals(form.hotels().getValues().get(1), fawlty);
        
        assertTrue(form.hotels().getValues().size() == 2);
    }
    
    
    public void testSetValueAttributeSetsState()
    {
        form.country().setValueAttribute("country");
        assertEquals(SelectableState.SELECTED, form.country().getState().get("country"));
        
        form.hotels().setValueAttributes(new String[] {"one", "two"});
        assertEquals(SelectableState.SELECTED, form.hotels().getState().get("one"));
        assertEquals(SelectableState.SELECTED, form.hotels().getState().get("two"));
    }
    
    private static class TestForm extends HtmlFormSupport
    {
        public TestForm()
        {
            // required
            select(stringType(), COUNTRY);
            
            // optional multi select
            multiSelect(stringType(), HOTELS).optional();
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
