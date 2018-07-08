import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQL
{
    private Connection conn = null;
    private int id = 0;
    private Model model;
    public SQL(Model model)
    {
        this.model = model;
        try 
        {
            // create a connection to the database
            conn = DriverManager.getConnection("jdbc:sqlite:Sudoku.db");
            id = conn.createStatement().executeQuery("SELECT last_insert_rowid() FROM sudoku").getInt("id");
            System.out.println("Connection to SQLite has been established.");
            
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }        
        newtable();
        
    }

    

    public void newtable() {        
        try 
        {
            Statement stmt = conn.createStatement();
        
            // create a new table
            stmt.execute("CREATE TABLE IF NOT EXISTS sudoku (id int PRIMARY KEY, x int, y int, wert int);");
            stmt.execute("CREATE TABLE IF NOT EXISTS high (id int PRIMARY KEY, name String, wert double);");
        } 
        catch (SQLException ex) 
        {
            System.out.println(ex.getMessage());
        }
    }
    
    public void insert() {
        PreparedStatement pstmt;
        String sql;
        try
        {
            for(int y=0;y<9;y++)
            {
                for(int x=0;x<9;x++)
                {
                    sql = "INSERT INTO sudoku(id,x,y,wert) VALUES(?,?,?,?)";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, id);                    
                    pstmt.setInt(2, x);
                    pstmt.setInt(3, y);
                    pstmt.setInt(4, model.spielfeld[x][y].getInhalt());
                    pstmt.executeUpdate();
                    id++;
                }
            }
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }        
    }
    
    public void delete() {
        String sql = "DELETE FROM sudoku WHERE id = ?";
        try 
        {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            int idtmp = id-81;
            for(;id>=idtmp;id--){
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }
    }    
    
    public void selectAll(){
        String sql = "SELECT id, name, capacity FROM sudoku";
        
        try 
        {
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" + 
                                   rs.getString("x") + "\t" +
                                   rs.getDouble("y"));
            }
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }
    }
}
