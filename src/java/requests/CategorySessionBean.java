package requests;

import entities.Category;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Mateusz
 */
@Stateless
public class CategorySessionBean {

    @PersistenceContext
    private EntityManager em;

    public Category createCategory(Category category) {
        em.persist(category);
        return category;
    }

    public Category createCategory(int idCategory, String title, String description) {
        Category category = new Category();
        category.setIdCategory(1);
        category.setTitle(title);
        category.setDescription(description);
        em.persist(category);
        return category;
    }

    public void removeCategory(Category category) {
        em.remove(em.merge(category));
    }

    public void mergeCategory(Category category) {
        em.merge(category);
    }
    
    public List<Category> findAll() {
        Query q = em.createNamedQuery("Category.findAll");
        return q.getResultList();
    }
    
    public Category findByTitle(String title) {
        Query q = em.createNamedQuery("Category.findByTitle").setParameter("title", title);
        return (Category) q.getSingleResult();
    }
    
    public Category findByIdCategory(int idCategory) {
        try {
            Query q = em.createNamedQuery("Category.findByIdCategory").setParameter("idCategory", idCategory);
            return (Category) q.getSingleResult();
        } catch(Exception e) {
            return new Category();
        }
        
    }
}
