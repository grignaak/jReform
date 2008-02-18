package test.org.jreform;

import static org.jreform.SelectableState.SELECTED;
import static org.jreform.SelectableState.UNSELECTED;

import org.jreform.HtmlForm;
import org.jreform.MultiSelect;
import org.jreform.Select;

public class SelectTest extends BaseTestCase
{
    private static final String COUNTRY = "country";
    private static final String HOTELS = "hotels";
    
    private TestForm form;
    
    protected void init()
    {
        form = new TestForm();
    }
    
    protected HtmlForm getForm()
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
    
    
    private static class TestForm extends HtmlForm
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
