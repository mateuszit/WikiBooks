/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Mateusz
 */
@ViewScoped
@ManagedBean(name="test")
public class TestController implements Serializable {
    
    private UploadedFile file;
    private static String dir = "C:\\Users\\Mateusz\\Documents\\NetBeansProjects\\WikiBooks\\web\\upload\\";

    public static void main(String[] args) {
        long filename = System.currentTimeMillis()/1000;
        
        String fn = String.valueOf(filename);
        
        System.out.println(fn);
    }
    
    
    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    public String getFileUrl() {
       if(file != null) {
           return "./upload/" + file.getFileName();
       } else {
           return "pusto";
       }
    }
    
    public void testowa() {
        System.out.println("testowa");
    }
    
    public void upload(FileUploadEvent event) {
        System.out.println("uruchomiłem");
        if(file != null) {
            try {
                copyFile(file.getFileName(), file.getInputstream());
            } catch(IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("nie działa");
        }
    }
    
    public void copyFile(String fileName, InputStream in) {
        try {
            OutputStream out = new FileOutputStream(new File(dir + fileName));
            
            int read = 0;
            byte[] bytes = new byte[1024];
            
            while((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            
            in.close();
            out.flush();
            out.close();
            
            System.out.println("Stworzony plik");
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
