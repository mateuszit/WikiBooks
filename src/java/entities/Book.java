/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mateusz
 */
@Entity
@Table(name = "book")
@XmlRootElement

@NamedQueries({
    @NamedQuery(name = "Book.findByCategory", query = "SELECT b FROM Book b WHERE b.categoryId.idCategory = :idCategory ORDER BY b.title"),
    @NamedQuery(name = "Book.findByUser", query = "SELECT b FROM Book b WHERE b.userId.idUser = :idUser"),
    @NamedQuery(name = "Book.findAll", query = "SELECT b FROM Book b ORDER BY b.addDate DESC"),
    @NamedQuery(name = "Book.findByIdBook", query = "SELECT b FROM Book b WHERE b.idBook = :idBook"),
    @NamedQuery(name = "Book.findByAddDate", query = "SELECT b FROM Book b WHERE b.addDate = :addDate"),
    @NamedQuery(name = "Book.findByTitle", query = "SELECT b FROM Book b WHERE b.title = :title")})
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idBook")
    private Integer idBook;
    @Column(name = "addDate")
    @Temporal(TemporalType.DATE)
    private Date addDate;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "text")
    private String text;
    @Size(max = 255)
    @Column(name = "title")
    private String title;
    @JoinColumn(name = "userId", referencedColumnName = "idUser")
    @ManyToOne
    private User userId;
    @JoinColumn(name = "categoryId", referencedColumnName = "idCategory")
    @ManyToOne
    private Category categoryId;

    public Book() {
    }

    public Book(Integer idBook) {
        this.idBook = idBook;
    }
    
    public Book(int idBook, Date addDate, String text, String title, User userId, Category categoryId) {
        this.idBook = idBook;
        this.addDate = addDate;
        this.text = text;
        this.title = title;
        this.userId = userId;
        this.categoryId = categoryId;
    }

    public Integer getIdBook() {
        return idBook;
    }

    public void setIdBook(Integer idBook) {
        this.idBook = idBook;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBook != null ? idBook.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Book)) {
            return false;
        }
        Book other = (Book) object;
        if ((this.idBook == null && other.idBook != null) || (this.idBook != null && !this.idBook.equals(other.idBook))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Book[ idBook=" + idBook + " ]";
    }
    
}
