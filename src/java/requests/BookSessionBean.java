package requests;

import entities.Book;
import entities.Category;
import entities.User;
import java.util.Date;
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
public class BookSessionBean {

    @PersistenceContext
    private EntityManager em;

    public Book createBook(Book book) {
        em.persist(book);
        return book;
    }

    public Book createBook(int idBook, Date addDate, String text, String title, User userId, Category categoryId) {
        Book book = new Book(idBook, addDate, text, title, userId, categoryId);
        em.persist(book);
        return book;
    }

    public List<Book> getBookListFromCategoryId(int idCategory) {
        try {
            Query q = em.createNamedQuery("Book.findByCategory").setParameter("idCategory", idCategory);
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Book> findAll() {
        try {
            Query q = em.createNamedQuery("Book.findAll");
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Book getBookById(int idBook) {
        try {
            Query q = em.createNamedQuery("Book.findByIdBook").setParameter("idBook", idBook);
            return (Book) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Book> getBookListFromUser(int idUser) {
        try {
            Query q = em.createNamedQuery("Book.findByUser").setParameter("idUser", idUser);
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Book> getNewestBookList(int limit) {
        try {
            Query q = em.createQuery("SELECT b FROM Book b ORDER BY b.addDate DESC").setMaxResults(limit);
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void mergeBook(Book book) {
        em.merge(book);
    }
    
    public void removeBook(Book book) {
        em.remove(em.merge(book));
    }
}
