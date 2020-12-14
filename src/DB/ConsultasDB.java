
package DB;

import java.awt.Color;
import java.awt.Component;
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
    
    public static void traerTitulo(JList lista, JList completadas){
        DefaultListModel modelo = new DefaultListModel();
        DefaultListModel modeloDos = new DefaultListModel();
        ResultSet resultado = null;
        lista.setModel(modelo);
        completadas.setModel(modeloDos);
        try {
            PreparedStatement pst = cn.prepareStatement("SELECT Titulo, Estado FROM Tareas");
            resultado = pst.executeQuery();            
            while(resultado.next()){
                if(resultado.getInt("Estado") == 1){ // 1 para tarea lista
                    modelo.addElement(resultado.getString("Titulo"));
                    lista.setForeground(Color.GRAY);    
                }else{
                    modeloDos.addElement(resultado.getString("Titulo"));
                    //completadas.setForeground(Color.GREEN);
                }
            }  
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //return modelo, modeloDos;
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
