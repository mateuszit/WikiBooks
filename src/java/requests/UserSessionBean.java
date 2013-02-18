package requests;

import entities.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Mateusz
 */
@Stateless
public class UserSessionBean {

    @PersistenceContext
    private EntityManager em;

    public User createUser(User user) {
        em.persist(user);
        return user;
    }

    public User createUser(int idUser, String name, String surname, String mail, String password, int rank) {
        User user = new User(1, name, surname, mail, password, rank);
        em.persist(user);
        return user;
    }

    public void removeUser(User user) {
        em.remove(em.merge(user));
    }

    public void mergeUser(User user) {
        em.merge(user);
    }

    public User findByMail(String mail) {
        try {
            Query q = em.createNamedQuery("User.findByMail").setParameter("mail", mail);
            return (User) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<User> findAll() {
        try {
            Query q = em.createNamedQuery("User.findAll");
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public User findById(int idUser) {
        try {
            Query q = em.createNamedQuery("User.findByIdUser").setParameter("idUser", idUser);
            return (User) q.getSingleResult();
        } catch(NoResultException e) {
            return null;
        }
    }
    
    public List<User> findAdmins() {
        try {
            Query q = em.createQuery("SELECT u FROM User u WHERE u.rank = 1");
            return q.getResultList();
        } catch(NoResultException e) {
            return null;
        }
    }
}
