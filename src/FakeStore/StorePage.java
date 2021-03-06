package FakeStore;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import FakeStore.inventoryEntry;


@WebServlet("/Store/StorePage")
public class StorePage extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public StorePage() {
		super();

	}


	public void init( ServletConfig config ) throws ServletException
	{
		super.init( config );

		try
		{
			Class.forName( "com.mysql.jdbc.Driver" );
		}
		catch( ClassNotFoundException e )
		{
			throw new ServletException( e );
		}

	}

	protected void doGet( HttpServletRequest request,
			HttpServletResponse response ) throws ServletException, IOException
	{
		List<inventoryEntry> inventory = new ArrayList<inventoryEntry>();
	
		Connection c = null;
		try
		{
			String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu70";
			String username = "cs3220stu70";
			String password = "812!xL1B";

			c = DriverManager.getConnection( url, username, password );
			Statement stmt1 = c.createStatement();
			ResultSet rs1 = stmt1.executeQuery( "select * from inventory" );

			while( rs1.next() )
			{

				inventoryEntry entry = new inventoryEntry( rs1.getString( "name" ),rs1.getString( "desciption" ), rs1.getInt( "quantity" ),rs1.getDouble("price"),rs1.getInt("id") );

				inventory.add( entry );

			}
			
			
		}

		catch( SQLException e )
		{
			throw new ServletException( e );
		}
		finally
		{
			try
			{
				if( c != null ) c.close();
			}
			catch( SQLException e )
			{
				throw new ServletException( e );
			}
		}


		request.setAttribute( "inventory", inventory );
		
		request.getRequestDispatcher( "/WEB-INF/store.jsp" ).forward(request, response );
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		doGet(request, response);
	}

}
