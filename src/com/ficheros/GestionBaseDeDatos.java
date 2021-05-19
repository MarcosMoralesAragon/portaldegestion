package com.ficheros;

import com.modelos.*;
import com.utilidades.Fecha;

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
                System.out.println("¡ Conexion exitosa !");
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
                rs = stmt.executeQuery();
                while (rs.next()) {
                    variableEmpleado = new Empleado();
                    variableEmpleado.setCodigo(rs.getString(1));                                                        // Codigo
                    variableEmpleado.setNombre(rs.getString(2));                                                        // Nombre
                    variableEmpleado.setPrimerApellido(rs.getString(3));                                                // Primer apellido
                    variableEmpleado.setSegundoApellido(rs.getString(4));                                               // Segundo apellido
                    variableEmpleado.setDNI(rs.getString(5));                                                           // DNI
                    variableEmpleado.setFechaNacimiento(rs.getDate(6));                                                 // Fecha nacimiento
                    variableEmpleado.setNacionalidad(rs.getString(7));                                                  // Nacionalidad
                    variableEmpleado.setDireccion(cargarDireccion( "FPM_DIRECCION", rs.getInt(8),conexion)); // Direccion
                    variableEmpleado.setEstado(Estado.values()[Servicios.estadoEleccion(rs.getString(9))]);             // Estado
                    variableEmpleado.setFechaAlta(rs.getDate(10));                                                      // Fecha alta
                    variableEmpleado.getContratos().add(cargarContrato("FPM_CONTRATOS", variableEmpleado.getCodigo(), conexion));
                    empleados.add(variableEmpleado);
                    cantidadAfectada ++;
                }
                System.out.println("Carga correcta, " + cantidadAfectada + " empleado/s cargadado/s");
            } catch (SQLException exception) {
                System.out.println("Error cargando en la tabla " + nombreTabla);
            } catch (NullPointerException e){
                System.out.println("Uno de los campos se encuentra vacio o con un dato erroneo, revise la BBDD");
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

    public static Direccion cargarDireccion(String nombreTabla, int codigoDireccicon, Connection conexion) {
        Direccion variableDireccion = new Direccion();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        if (conexion != null) {
            int cantidadAfectada = 0;
            try {
                stmt = conexion.prepareStatement("select * from " + nombreTabla + " where ID_DIRECCION = ?");
                stmt.setInt(1, codigoDireccicon);
                rs = stmt.executeQuery();
                while (rs.next()){
                    variableDireccion.setCodigo(rs.getInt(1));                    // Codigo
                    variableDireccion.setCalle(rs.getString(2));                  // Calle
                    variableDireccion.setNumero(rs.getInt(3));                    // Numero
                    variableDireccion.setBloque(rs.getString(4));                 // Bloque
                    variableDireccion.setPiso(rs.getString(5));                   // Piso
                    variableDireccion.setPuerta(rs.getString(6));                 // Puerta
                    variableDireccion.setCodigoPostal(rs.getInt(7));              // Codigo postal
                    variableDireccion.setLocalidad(rs.getString(8));              // Localidad
                    variableDireccion.setProvincia(rs.getString(9));              // Provincia
                }
            } catch (SQLException exception) {
                System.out.println("Error cargando en la tabla " + nombreTabla);
            } catch (NullPointerException e){
                System.out.println("Uno de los campos se encuentra vacio o con un dato erroneo, revise la BBDD");
            }
        }
        return variableDireccion;
    }

    public static Contrato cargarContrato(String nombreTabla, String codigoEmpleado, Connection conexion) {
        Contrato variableContrato = new Contrato();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        if (conexion != null) {
            int cantidadAfectada = 0;
            try {
                stmt = conexion.prepareStatement("select * from " + nombreTabla + " where CODIGO_EMPLEADO = ?");
                stmt.setString(1, codigoEmpleado);
                rs = stmt.executeQuery();
                while (rs.next()){
                    variableContrato.setId(rs.getInt(1));                           // Id Contrato
                    variableContrato.setFechaInicioContrato(rs.getDate(2));         // Fecha inicio contrato
                    variableContrato.setFechaFinalContrato(rs.getDate(3));          // Fecha final contrato
                    variableContrato.setFechaFinalizacionEstimada(rs.getDate(4));   // Fecha finalizacion estimada
                    variableContrato.setSalario(rs.getInt(5));                      // Salario
                    variableContrato.setPuesto(Puesto.values()[rs.getInt(6)]);      // Puesto
                    variableContrato.setCodigoEmpleadoAsignado(rs.getString(7));    // Codigo empleado
                }
            } catch (SQLException exception) {
                System.out.println("Error cargando en la tabla " + nombreTabla);
            } catch (NullPointerException e){
                System.out.println("Uno de los campos se encuentra vacio o con un dato erroneo, revise la BBDD");
            }
        }
        return variableContrato;
    }

    public static void guardarDatosEmpleadosBaseDeDato(String nombreTabla, Empleado empleado){
        Connection conexion = cargarBaseDeDatos();
        PreparedStatement stmt = null;

        if (conexion != null) {
            try {
                stmt = conexion.prepareStatement("insert into " + nombreTabla + " values(?,?,?,?,?,?,?,?,?,?)");

                stmt.setString(1, empleado.getCodigo());                                   // Codigo
                stmt.setString(2, empleado.getNombre());                                   // Nombre
                stmt.setString(3, empleado.getPrimerApellido());                           // Primer apellido
                stmt.setString(4, empleado.getSegundoApellido());                          // Segundo apellido
                stmt.setString(5, empleado.getDNI());                                      // DNI
                stmt.setDate(6, Fecha.cambiarDateADateSQL(empleado.getFechaNacimiento())); // Fecha nacimiento
                stmt.setString(7, empleado.getNacionalidad());                             // Nacionalidad
                stmt.setInt(8, empleado.getDireccion().getCodigo());
                guardarDatosDireccionBaseDeDato("FPM_DIRECCION", empleado);                  // Direccion
                stmt.setString(9, empleado.getEstado().toString());                        // Estado
                stmt.setDate(10, Fecha.cambiarDateADateSQL(empleado.getFechaAlta()));      // Fecha alta

                int cantidadAfectada = stmt.executeUpdate();
                System.out.println("Guardado con exito, " + cantidadAfectada + " fila/s afectada/s en " + nombreTabla);
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

    public static void guardarDatosDireccionBaseDeDato(String nombreTabla, Empleado empleado){
        Connection conexion = cargarBaseDeDatos();
        PreparedStatement stmt = null;

        if (conexion != null) {
            try {
                stmt = conexion.prepareStatement("insert into " + nombreTabla + " values(?,?,?,?,?,?,?,?,?)");

                stmt.setInt(1, empleado.getDireccion().getCodigo());        // Codigo
                stmt.setString(2, empleado.getDireccion().getCalle());      // Calle
                stmt.setInt(3, empleado.getDireccion().getNumero());        // Numero
                stmt.setString(4, empleado.getDireccion().getBloque());     // Bloque
                stmt.setString(5, empleado.getDireccion().getPiso());       // Piso
                stmt.setString(6, empleado.getDireccion().getPuerta());     // Puerta
                stmt.setInt(7, empleado.getDireccion().getCodigoPostal());  // Codigo Postal
                stmt.setString(8, empleado.getDireccion().getLocalidad());  // Localidad
                stmt.setString(9, empleado.getDireccion().getProvincia());  // Provincia

                int cantidadAfectada = stmt.executeUpdate();
                System.out.println("Guardado con exito, " + cantidadAfectada + " fila/s afectada/s en " + nombreTabla);
            } catch (SQLIntegrityConstraintViolationException e){
                System.out.println("La clave primaría asignada a esta direccion ya existe. Reintente cambiando su codigo");
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

    public static void guardarDatosContrato(String nombreTabla, Empleado empleado, int numeroDeContrato){
        Connection conexion = cargarBaseDeDatos();
        PreparedStatement stmt = null;

        if (conexion != null) {
            try {
                stmt = conexion.prepareStatement("insert into " + nombreTabla + " values(?,?,?,?,?,?,?)");

                stmt.setInt(1, empleado.getContratos().get(numeroDeContrato).getId());        // Codigo
                stmt.setDate(2,  Fecha.cambiarDateADateSQL(empleado.getContratos().get(numeroDeContrato).getFechaInicioContrato()));      // Calle
                stmt.setDate(3, Fecha.cambiarDateADateSQL(empleado.getContratos().get(numeroDeContrato).getFechaFinalContrato()));        // Numero
                stmt.setDate(4, Fecha.cambiarDateADateSQL(empleado.getContratos().get(numeroDeContrato).getFechaFinalizacionEstimada()));     // Bloque
                stmt.setInt(5, (int) empleado.getContratos().get(numeroDeContrato).getSalario());       // Piso
                stmt.setString(6, String.valueOf(empleado.getContratos().get(numeroDeContrato).getPuesto()));     // Puerta
                stmt.setString(7, empleado.getContratos().get(numeroDeContrato).getCodigoEmpleadoAsignado());  // Codigo Postal

                int cantidadAfectada = stmt.executeUpdate();
                System.out.println("Guardado con exito, " + cantidadAfectada + " fila/s afectada/s en " + nombreTabla);
            } catch (SQLIntegrityConstraintViolationException e){
                System.out.println("La clave primaría asignada a esta direccion ya existe. Reintente cambiando su codigo");
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
            int cantidadAfectada = stmt.executeUpdate();
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

    public static void updateFilaBaseDeDatos(String nombreTabla, Empleado empleado){
        Connection conexion = cargarBaseDeDatos();
        PreparedStatement stmt = null;
        try {
            stmt = conexion.prepareStatement("update " + nombreTabla + " set NOMBRE = ?, PRIMER_APELLIDO = ?, " +
                    "SEGUNDO_APELLIDO = ?, DNI = ?, FECHA_NACIMIENTO = ?, NACIONALIDAD = ?, ESTADO = ? where ID = ?" );
            stmt.setString(1, empleado.getNombre());                                   // Nombre
            stmt.setString(2, empleado.getPrimerApellido());                           // Primer apellido
            stmt.setString(3, empleado.getSegundoApellido());                          // Segundo apellido
            stmt.setString(4, empleado.getDNI());                                      // DNI
            stmt.setDate(6, Fecha.cambiarDateADateSQL(empleado.getFechaNacimiento())); // Fecha nacimiento
            stmt.setString(9, empleado.getEstado().toString());                        // Estado
            stmt.setString(5, empleado.getCodigo());                                   // Codigo
            int cantidadAfectada = stmt.executeUpdate();
            stmt.close();
            conexion.close();
            System.out.println("Campos modificados guardados con exito, " + cantidadAfectada + " fila/s afectada/s");
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
