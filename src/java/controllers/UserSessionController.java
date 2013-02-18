package controllers;

import entities.User;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import requests.UserSessionBean;

/**
 *
 * @author Mateusz
 */
@ManagedBean(name = "userSession")
@SessionScoped
public class UserSessionController implements Serializable {
    
    @EJB
    private UserSessionBean userBean;
    
    private User user;    
    private boolean logged;
    
    private static String url = "http://localhost:8080/WikiBooks/";
    
    @PostConstruct
    protected void initUser() {
        this.user = new User();
    }
    
    public UserSessionController() {
        
    }
    
    public User getUser() {
        return this.user;
    }
    
    public boolean isLogged() {
        return this.logged;
    }
    
    public boolean isAdmin() {
        try {
            if (user.getRank().equals(1)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getUrl() {
        return UserSessionController.url;
    }
    
    public String loginUser() {
        User u;
        u = userBean.findByMail(user.getMail());
        if(u != null && u.getPassword().equals(user.getPassword())) {
            this.logged = true;
            this.user = u;
            return "index";
        } else {
            FacesContext.getCurrentInstance().addMessage("loginForm:mail", new FacesMessage("Błąd", "Podany adres e-mail i/lub hasło są nieprawidłowe"));
            return null;
        }
    }
    
    public String logoutUser() {
        this.logged = false;
        this.user = new User();
        return null;
    }
}
