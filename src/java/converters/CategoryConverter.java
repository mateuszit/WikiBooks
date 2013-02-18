package converters;

import entities.Category;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import requests.CategorySessionBean;

/**
 *
 * @author Mateusz
 */
@ManagedBean(name="CategoryConverter")
@RequestScoped
public class CategoryConverter implements Converter {
    
    @EJB
    private CategorySessionBean csb;
    private Category category = new Category();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        
        this.category = csb.findByTitle(value);
        
        
        return this.category;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
    }
    
    
}
