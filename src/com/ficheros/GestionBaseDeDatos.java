package com.ficheros;

import com.modelos.Empleado;

import java.sql.*;
import java.util.ArrayList;

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

    public static ArrayList<Empleado> cargarFilaBaseDeDatos(String nombreTabla, ArrayList<Empleado> empleados) {
        Empleado variableEmpleado;
        Connection conexion = cargarBaseDeDatos();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        if (conexion != null) {
            int cantidadAfectada = 0;
            try {
                stmt = conexion.prepareStatement("select * from " + nombreTabla);
                rs = stmt.executeQuery("select * from " + nombreTabla);
                while (rs.next()) {
                    variableEmpleado = new Empleado();
                    variableEmpleado.setCodigo(rs.getString(1));
                    variableEmpleado.setNombre(rs.getString(2));
                    variableEmpleado.setPrimerApellido(rs.getString(3));
                    variableEmpleado.setSegundoApellido(rs.getString(4));
                    variableEmpleado.setDNI(rs.getString(5));
                    variableEmpleado.setFechaNacimiento( null /* rs.getDate(6) */);
                    variableEmpleado.setNacionalidad(rs.getString(7));
                    variableEmpleado.setDireccion( null /* rs.getString(8) */);
                    variableEmpleado.setEstado( null /* rs.getString(9) */);
                    variableEmpleado.setFechaAlta(rs.getDate(10));
                    variableEmpleado.setContratos( null /* rs.getString(9) */);
                    empleados.add(variableEmpleado);
                }
            } catch (SQLException exception) {
                System.out.println("Error cargando en la tabla " + nombreTabla);
            } finally {
                try {
                    stmt.close();
                    rs.close();
                    conexion.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return empleados;
    }

    public static void guardarDatosBaseDeDato(String nombreTabla, Empleado empleado){
        Connection conexion = cargarBaseDeDatos();
        PreparedStatement stmt = null;

        if (conexion != null) {
            try {
                stmt = conexion.prepareStatement("insert into " + nombreTabla + " values(?,?,?,?,?,?,?,?,?,?,?)");

                stmt.setString(1, empleado.getCodigo());                      // Codigo
                stmt.setString(2, empleado.getNombre());                      // Nombre
                stmt.setString(3, empleado.getPrimerApellido());              // Primer apellido
                stmt.setString(4, empleado.getSegundoApellido());             // Segundo apellido
                stmt.setString(5, empleado.getDNI());                         // DNI
                stmt.setDate(6, null /* empleado.getFechaNacimiento() */); // Fecha nacimiento
                stmt.setString(7, empleado.getNacionalidad());                // Nacionalidad
                stmt.setString(8, null);                                   // Direccion
                stmt.setString(9, null /* empleado.getEstado() */);        // Estado
                stmt.setDate(10, null /* empleado.getFechaAlta() */);      // Fecha nacimiento
                stmt.setString(11, null /* empleado.getContratos() */);    // Contratos

                int cantidadAfectada = stmt.executeUpdate();
                System.out.println("Guardado con exito, " + cantidadAfectada + " fila/s afectada/s");
            } catch (SQLIntegrityConstraintViolationException e){
                System.out.println("La clave primaría asignada a este usuario ya existe. Reintente cambiando su codigo");
            } catch (SQLException exception) {
                System.out.println("Error guardando una fila en la tabla " + nombreTabla);
            } finally {
                try {
                    stmt.close();
                    conexion.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    public static void borrarFilaBaseDeDatos(String nombreTabla){
        Connection conexion = cargarBaseDeDatos();
        PreparedStatement stmt = null;
        try {
            stmt = conexion.prepareStatement("delete from " + nombreTabla + " where ID = ?" );
            stmt.setInt(1,10);
            int cantidadAfectada = stmt.executeUpdate();
            stmt.close();
            conexion.close();
            System.out.println("Borrado con exito, " + cantidadAfectada + " fila/s afectada/s");
        } catch (SQLException throwables) {
            System.out.println("Error borrando una fila en la tabla " + nombreTabla);
        } finally {
            try {
                stmt.close();
                conexion.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
