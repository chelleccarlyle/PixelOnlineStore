package Store;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.imageio.stream.FileImageInputStream;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DisplayImage")
public class DisplayImage extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	Connection c = null;
	
	List<Integer> image_ids;
	List<byte[]> image_data;
	List<String> image_types;
       
	public void init(ServletConfig config) throws ServletException {
		
		super.init(config);
		
//		String url = "jdbc:mysql://cs3.calstatela.edu:3306/cs3220stu57";
//		String username = "cs3220stu57";
//		String password = "ezT*7o7x";
//		
//		try {
//			c = DriverManager.getConnection(url, username, password);
//		} catch (SQLException e) {
//			throw new ServletException(e);
//		}
//		finally {
//			if (c != null) {
//				getServletContext().setAttribute("connection", c);
//			}
//		}
		
	}
	
	// must upload in order to see photos (they are fetched in a location that's "local" to the servlet)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		File f = new File("webapps/cs3220stu69/images/img_" + id + ".jpg");
		String imageType = "image/jpeg";
		
		if (!f.exists()) {// if it's not a jpeg, then it's a png
			f = new File("webapps/cs3220stu69/images/img_" + id + ".png");
			imageType = "image/png";
		}
		
		if (!f.exists()) {// if it's not even a jpg, use stock img
			f = new File("webapps/cs3220stu69/images/img_default.png");
			imageType = "image/png";
		}
		
		if (!f.exists())
			return; // we ain't got nothing! (Oh, NO!)
		
		FileImageInputStream fs = new FileImageInputStream(f);
		
		byte[] imageData = new byte[(int) fs.length()];
		fs.read(imageData);
		
		response.setContentType(imageType);
		response.setContentLength(imageData.length);
		response.getOutputStream().write(imageData);
		
		fs.close();
		
	}
	
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		
//		//Get id parameter
//		int id = Integer.parseInt(request.getParameter("id"));
//		
////		Blob blob = null;
////		ServletOutputStream output = response.getOutputStream();
//		String imageType = null;
////		InputStream input = null;
//		ResultSet rs = null;
//		PreparedStatement pstmt = null;
//		//Connection c = null;
//		
//		System.out.println("Before: " + c);
//		
//		//Get input stream (blob) from item based on id
//		try {
//			
//			String url = "jdbc:mysql://cs3.calstatela.edu:3306/cs3220stu57";
//			String username = "cs3220stu57";
//			String password = "ezT*7o7x";
//			
//			//c = DriverManager.getConnection(url, username, password);
//			if (getServletContext().getAttribute("connection") != null) {
//				c = (Connection)getServletContext().getAttribute("connection");
//			}
//			else {
//				c = DriverManager.getConnection(url, username, password);
//			}
//			
//			System.out.println("After: " + c);
//			
//			pstmt = c.prepareStatement("select * from items_images where id = ?");
//			
//			pstmt.setInt(1, id);
//			rs = pstmt.executeQuery();
//			
//			
//			
//			if (rs.next()) {
//				
//				//Get blob image
//				byte[] content = rs.getBytes("image");
//				
//				//Get image type
//				imageType = rs.getString("image_type");
//				response.setContentType(imageType);
//				response.setContentLength(content.length);
//				response.getOutputStream().write(content);
//		
//				
//			}
//			else {
//				
//				//Display default image
//				String img_default_path = getServletContext().getRealPath("images/img_default.png");
//				File f = new File(img_default_path);
//				imageType = "image/png";
//				
//				FileImageInputStream fs = new FileImageInputStream(f);
//				
//				byte[] imageData = new byte[(int) fs.length()];
//				fs.read(imageData);
//				
//				response.setContentType(imageType);
//				response.setContentLength(imageData.length);
//				response.getOutputStream().write(imageData);
//				
//				fs.close();
//				
//			}
//			
////			input = blob.getBinaryStream();
////			int length = (int)blob.length();
////			byte[] bytes = new byte[1024];
////			response.setContentType(imageType);
////			
////			while((length = input.read(bytes)) != -1) {
////				output.write(bytes, 0, length);
////			}
//			
////			int bytesRead = input.read(bytes);
////			
////			while (bytesRead != -1) {
////				output.write(bytes, 0, bytesRead);
////				bytesRead = input.read(bytes);
////			}
//			
////			input.close();
////			output.flush();
////			output.close();
//			
//		}	
//		catch (SQLException e) {
//			throw new ServletException(e);
//		}
////		finally {
////			
////			//Close connection
////			try {
////				if (c != null) {
////					c.close();
////				}	
////				System.out.println("After close: " + c);
////		      } catch (SQLException e) {
////		    	  throw new ServletException(e);
////		      }
////			
////		}
//		
//	}

}
