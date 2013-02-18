package controllers;

import entities.Category;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import requests.CategorySessionBean;

/**
 *
 * @author Mateusz
 */
@ManagedBean(name = "category")
@ViewScoped
public class CategoryController implements Serializable {
    
    @EJB
    private CategorySessionBean categoryBean;
    private Category category = new Category();
    
    private boolean edit = false;
    
    //getters and setters:
    
    public Category getCategory() {
        return this.category;
    }
    
    public Category categoryById(int id) {
        return categoryBean.findByIdCategory(id);
    }
    
    public List<Category> getCategoryList() {
        List<Category> categoryList = categoryBean.findAll();
        return categoryList;
    }

    public void add() {
        categoryBean.createCategory(category);
        category = new Category();
    }
    
    public void save() {
        categoryBean.mergeCategory(category);
        category = new Category();
        this.edit = false;
    }
    
    public void edit(Category category) {
        this.category = category;
        this.edit = true;
    }
    
    public void delete(Category category) {
        categoryBean.removeCategory(category);
    }
    
    public boolean isEdit() {
        return this.edit;
    }
    
    public int getCount() {
        return categoryBean.findAll().size();
    }
}
