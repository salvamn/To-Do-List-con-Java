
package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultListModel;
import javax.swing.JList;


public class ConsultasDB {
    private static Conexion cc = new Conexion();
    private static Connection cn = cc.conectar();
    
    public static void agregarTarea(String titulo, String comentario, Integer estadoTarea){
        try {
            PreparedStatement pst = cn.prepareStatement("INSERT INTO Tareas VALUES (?,?,?)");
            pst.setString(1, titulo);
            pst.setString(2, comentario);
            pst.setInt(3, estadoTarea);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static DefaultListModel traerTitulo(JList lista){
        DefaultListModel modelo = new DefaultListModel();
        ResultSet resultado = null;
        //String resultadoFinal = null;
        lista.setModel(modelo);
        try {
            PreparedStatement pst = cn.prepareStatement("SELECT Titulo FROM Tareas");
            resultado = pst.executeQuery();
            
            while(resultado.next()){
                //resultadoFinal = resultado.getString("Titulo");
                modelo.addElement(resultado.getString("Titulo"));
            }  
        } catch (Exception e) {
        }
        return modelo;
    }
    
    public static String mostrarComentario(String titulo){
        ResultSet resultado = null;
        String resultadoFinal = null;
        try {
            PreparedStatement pst = cn.prepareStatement("SELECT Comentario FROM Tareas WHERE Titulo = ('"+titulo+"')");
            resultado = pst.executeQuery();
            resultadoFinal = resultado.getString("Comentario");
            System.out.println("Descripcion: " + resultado.getString("Comentario"));
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultadoFinal;
    }
    
        public static void eliminarTarea(JList listaItem, String seleccion){
        DefaultListModel modelo = new DefaultListModel();       
        listaItem.setModel(modelo);
        try {
            PreparedStatement pst = cn.prepareStatement("DELETE FROM Tareas WHERE Titulo = ?");
            pst.setString(1, seleccion);
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
        
    public static void actualizar(JList listaItem, String seleccion, String texto, Integer estadoTarea){
        DefaultListModel modelo = new DefaultListModel();       
        listaItem.setModel(modelo);

        try{
            PreparedStatement pst = cn.prepareStatement("UPDATE Tareas SET Titulo = ?, Estado = ? WHERE Titulo = ? AND Estado = 0");
            pst.setString(1, texto);
            pst.setInt(2, estadoTarea);
            pst.setString(3, seleccion);
            //pst.setInt(4, estadoActual);
            pst.executeUpdate();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
