package com.ficheros;


import java.sql.*;

public class GestionBaseDeDatos {
    public static Connection cargarBaseDeDatos(){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Fallo en la declaracion del driver");
        }
        Connection conexion = null;
        try  {
            conexion = DriverManager.getConnection(
                    "jdbc:oracle:thin:@//10.206.110.3:1521/pdbcmdb.at4wireless.com", "TD_CMDB_V1", "bg4ve8l8");
            if (conexion != null) {
                System.out.println("Conexion exitosa");
            } else {
                System.out.println("Error al realizar la conexion");
            }
        } catch (Exception e) {
            System.out.println("Error al realizar la conexion");
        }
        return conexion;
    }
    public static void pintarTablaBaseDeDatos(Connection connection, String tableName) {
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("select * from " + tableName);

                while (rs.next()) {
                    System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  "
                            + rs.getString(3) + " " + rs.getString(4)+ " " + rs.getString(5));
                }
                connection.close();
            } catch (SQLException exception) {
                System.out.println("Error pintando tabla con nombre " + tableName);
            }
        }
    }

    public static void guardarDatosBaseDeDato(Connection connection, String tableName, int id, String nombre, String primerApellido, String segundoApellido, String DNI){
        if (connection != null) {
            try {
                PreparedStatement stmt = connection.prepareStatement("insert into FPM_PRUEBA values(?,?,?,?,?)");
                stmt.setInt(1,2);
                stmt.setString(2,"Sergio");
                stmt.setString(3,"Garcia");
                stmt.setString(4, "Nomelose");
                stmt.setString(5, "12345L");
                int i = stmt.executeUpdate();
                System.out.println("Guardado con exito");
                connection.close();
            } catch (SQLException exception) {
                System.out.println("Error escribiendo los datos nuevos en la tabla " + tableName);
            }
        }
    }
}
