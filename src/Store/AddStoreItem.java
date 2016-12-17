package Store;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/AddStoreItem")
@javax.servlet.annotation.MultipartConfig
public class AddStoreItem extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//Read the inputs if they exist
		String name = request.getParameter("name") == null ? "" : request.getParameter("name");
		String description = request.getParameter("description") == null ? "" : request.getParameter("description");
		String quantity = request.getParameter("quantity") == null ? "" : request.getParameter("quantity");
		String price = request.getParameter("price") == null ? "" : request.getParameter("price");
		
		request.setAttribute("name", name);
		request.setAttribute("description", description);
		request.setAttribute("quantity", quantity);
		request.setAttribute("price", price);
	
		request.getRequestDispatcher("/WEB-INF/Store/AddStoreItem.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Get user input
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String quantity = request.getParameter("quantity");
		String price = request.getParameter("price");
		
		//Get image from file input
		Part filePart = request.getPart("image");
		
		//Check for invalid input
		boolean isValid = true;
		
		if (name == null || name.trim().length() <= 0) {
			request.setAttribute("errorName", "Please enter a name for the item.");
			isValid = false;
		}
		if (description == null || description.trim().length() <= 0) {
			request.setAttribute("errorDescription", "Please enter a description of the item.");
			isValid = false;
		}
		if (quantity == null || quantity.trim().length() <= 0 || !isPositiveInteger(quantity)) {
			request.setAttribute("errorQuantity", "Please enter a valid quantity of the item.");
			isValid = false;
		}
		if (price == null || price.trim().length() <= 0 || !isPositiveFloat(price)) {
			request.setAttribute("errorPrice", "Please enter a valid price for the item.");
			isValid = false;
		}
		if (filePart == null || !isValidImageType(filePart.getContentType())) {
			request.setAttribute("errorImage", "Please upload a valid image (only jpeg or png)");
			isValid = false;
		}
		
		if (!isValid) {
			doGet(request, response);
			return;
		}
		
		//Inputs are valid; add to database and upload image file
		try {
			String[] credentials = (String[])getServletContext().getAttribute("credentials");
			Connection c = DriverManager.getConnection(credentials[0], credentials[1], credentials[2]);
			PreparedStatement pstmt = c.prepareStatement("insert into items (name, description, quantity, price) values (?, ?, ?, ?)");
			
			pstmt.setString(1, name);
			pstmt.setString(2, description);
			pstmt.setInt(3, Integer.parseInt(quantity));
			pstmt.setFloat(4, Float.parseFloat(price));
			
			pstmt.executeUpdate();
			
			//Get id
			pstmt = c.prepareStatement("select * from items where name = ? and description = ? and quantity = ? and price = ?");
			
			pstmt.setString(1, name);
			pstmt.setString(2, description);
			pstmt.setInt(3, Integer.parseInt(quantity));
			pstmt.setFloat(4, Float.parseFloat(price));
			
			ResultSet rs = pstmt.executeQuery();
			
			int currentId = 1;
			
			while (rs.next()) {
				currentId = rs.getInt("id");
			}
			
			c.close();
			
			byte[] buffer = new byte[(int)filePart.getSize()];
			filePart.getInputStream().read(buffer);
			
			/*
			 * I have modified permissions on this folder so anyone can write/upload to it
			 * You must do the same to your server if you would like to upload files to yours.
			 * 
			 * Just SSH into your CS3 folder, navigate to the root of your 'www' folder,
			 * then chmod 777 a folder of your choosing.
			 * 
			 * For now, we all can just use my folder for all images since it just works.
			 * 
			 */
			String folder = "webapps/cs3220stu69/images/";
			
			String ext = filePart.getContentType().substring(6).toLowerCase();
			
			if (ext.equals("jpeg")) ext = "jpg"; // shortening jpg files
			
			FileOutputStream out = new FileOutputStream(new File(folder + "img_" + currentId + "." + ext));
			out.write(buffer);
			out.close();
		} 
		catch (Exception e) {
			throw new ServletException(e);
		}
		
		response.sendRedirect("Inventory");
		
	}
	
	//Checks if image type is valid
	public static boolean isValidImageType(String s) {
	
		if (s.equals("image/jpeg") || s.equals("image/png")) {
			return true;
		}
		else {
			return false;
		}
	
	}
	
	//Checks if input is an integer
	public static boolean isPositiveInteger(String s) {
	    try {
	        int num = Integer.parseInt(s);
	        
	        if (num >= 0) {
	        	return true;
	        }
	        else {
	        	return false;
	        }
	    } 
	    catch(NumberFormatException e) { 
	        return false; 
	    }
	}
	
	//Checks if input is a float
	public static boolean isPositiveFloat(String s) {
		try {
			float num = Float.parseFloat(s);
			
			if (num >= 0f) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(NumberFormatException e) {
			return false;
		}
	}

}
