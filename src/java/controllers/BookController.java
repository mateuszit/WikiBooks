package controllers;

import entities.Book;
import entities.User;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import requests.BookSessionBean;

/**
 *
 * @author Mateusz
 */
@ManagedBean(name = "book")
@ViewScoped
public class BookController implements Serializable {

    @EJB
    private BookSessionBean bookBean;
    private Book book = new Book();
    private boolean edit = false;
    
    private UploadedFile file;
    private static String dir = "C:\\Users\\Mateusz\\Documents\\NetBeansProjects\\WikiBooks\\web\\upload\\";
    
    @ManagedProperty(value = "#{userSession.user}")
    private User user;

    public Book getBook() {
        return this.book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isEdit() {
        return this.edit;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void save() {
        bookBean.mergeBook(book);
        book = new Book();
        this.edit = false;
    }

    public void edit(Book book) {
        this.book = book;
        this.edit = true;
    }

    public void delete(Book book) {
        bookBean.removeBook(book);
    }

    public String add() {
        Date now = new Date();
        Date timestamp = new java.sql.Timestamp(now.getTime());
        book.setAddDate(timestamp);
        book.setUserId(user);

        String htmlEnable = book.getText().replaceAll("&lt;", "&amp;lt;").replaceAll("&gt;", "&amp;gt;");
        book.setText(htmlEnable);

        bookBean.createBook(book);
        
        this.book = new Book();
        
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("addBook", new FacesMessage("Gratuluję!", "Książka została prawidłowo dodana!"));
        
        return "book?faces-redirect=true";

    }

    public void uploadPdfBook(FileUploadEvent event) {
        if (file != null) {
            try {
                book.setText("PDF:\\\\" + copyFile(file.getFileName(), file.getInputstream()));
                add();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Gratuluję!", "Książka została prawidłowo dodana!"));
            }
            catch(IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Błąd", "Książka nie dodana. Spróbuj ponownie."));
        }
    }

    public String copyFile(String fileName, InputStream in) {
        
        String fn = String.valueOf(System.currentTimeMillis()/1000) + "_" + fileName;       
        
        try {
            OutputStream out = new FileOutputStream(new File(dir + fn));
            
            int read = 0;
            byte[] bytes = new byte[1024];
            
            while((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            
            in.close();
            out.flush();
            out.close();
            return fn;
        } catch(IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public List<Book> bookListFromCategoryId(int idCategory) {
        return bookBean.getBookListFromCategoryId(idCategory);
    }

    public Book bookById(int id) {
        return bookBean.getBookById(id);
    }


    public List<Book> bookList() {
        return bookBean.findAll();
    }

    public List<Book> bookListFromUser(int idUser) {
        return bookBean.getBookListFromUser(idUser);
    }

    public List<Book> newestBookList(int limit) {
        return bookBean.getNewestBookList(limit);
    }

    public int getCount() {
        return bookBean.findAll().size();
    }
}
