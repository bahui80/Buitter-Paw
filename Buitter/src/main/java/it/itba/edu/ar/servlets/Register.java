package it.itba.edu.ar.servlets;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

@SuppressWarnings("serial")
public class Register extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		DiskFileUpload fu = new DiskFileUpload();
        // If file size exceeds, a FileUploadException will be thrown
//        fu.setSizeMax(100000000);

        List fileItems;
		try {
			fileItems = fu.parseRequest(request);
			
			Iterator itr = fileItems.iterator();

	        while(itr.hasNext()) {
	          FileItem fi = (FileItem)itr.next();

	          //Check if not form field so as to only handle the file inputs
	          //else condition handles the submit button input
	          if(!fi.isFormField()) {
	            System.out.println("nNAME: "+fi.getName());
	            System.out.println("SIZE: "+fi.getSize());
	            //System.out.println(fi.getOutputStream().toString());
	            File fNew= new File(fi.getName());

	            System.out.println(fNew.getAbsolutePath());
	            fi.write(fNew);
	          }
	          else {
	            System.out.println("Field ="+fi.getFieldName());
	          }
		} }catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
      }
}
