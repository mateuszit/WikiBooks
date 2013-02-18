package controllers;

import entities.User;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import requests.UserSessionBean;

/**
 *
 * @author Mateusz
 */
@ManagedBean(name = "user")
@ViewScoped
public class UserController implements Serializable {
    
    @EJB
    private UserSessionBean userBean;
    private User user = new User();
    
    private boolean edit;
    
    public User getUser() {
        return this.user;
    }
    
    public List<User> getUserList() {
        List<User> userList = userBean.findAll();
        return userList;
    }
    
    public List<User> getAdminList() {
        return userBean.findAdmins();
    }
    
    public int getCount() {
        return userBean.findAll().size();
    }
    
    public boolean isEdit() {
        return this.edit;
    }
    
    public String add() {
        FacesContext context = FacesContext.getCurrentInstance();
        User u;
        u = userBean.findByMail(user.getMail());
        if(u == null) {
            if(getCount() == 0) {
                user.setRank(1);
            } else {
                user.setRank(2);
            }
            userBean.createUser(user);
            user = new User();
            context.addMessage("registerUserForm", new FacesMessage("Rejestracja zakończona pomyślnie!", "Możesz zalogować się w serwisie"));
            return null;
        } else {
            context.addMessage("registerUserForm:mail", new FacesMessage("Błąd", "Podany adres e-mail już istnieje w naszej bazie!"));
            return null;
        }
    }
    
    public void save() {
        userBean.mergeUser(user);
        user = new User();
        this.edit = false;
    }
    
    public void edit(User user) {
        this.user = user;
        this.edit = true;
    }
    
    public void delete(User user) {
        userBean.removeUser(user);
    }
    
    public User singleUser(int idUser) {
        return userBean.findById(idUser);
    }
}
