package com.ficheros;

import com.modelos.*;
import com.utilidades.Fecha;
import com.utilidades.Prints;

import java.net.ConnectException;
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
        ResultSet set = null;
        if (conexion != null) {
            int cantidadAfectada = 0;
            try {
                stmt = conexion.prepareStatement("select * from " + nombreTabla);
                set = stmt.executeQuery();
                while (set.next()) {
                    variableEmpleado = new Empleado();
                    variableEmpleado.setCodigo(set.getString(1));                                                        // Codigo
                    variableEmpleado.setNombre(set.getString(2));                                                        // Nombre
                    variableEmpleado.setPrimerApellido(set.getString(3));                                                // Primer apellido
                    variableEmpleado.setSegundoApellido(set.getString(4));                                               // Segundo apellido
                    variableEmpleado.setDNI(set.getString(5));                                                           // DNI
                    variableEmpleado.setFechaNacimiento(set.getDate(6));                                                 // Fecha nacimiento
                    variableEmpleado.setNacionalidad(set.getString(7));                                                  // Nacionalidad
                    variableEmpleado.setDireccion(cargarDireccion( "FPM_DIRECCION", set.getInt(8),conexion)); // Direccion
                    variableEmpleado.setEstado(Estado.values()[Servicios.estadoEleccion(set.getString(9))]);             // Estado
                    variableEmpleado.setFechaAlta(set.getDate(10));                                                      // Fecha alta
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
                    set.close();
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
        ResultSet set = null;
        if (conexion != null) {
            int cantidadAfectada = 0;
            try {
                stmt = conexion.prepareStatement("select * from " + nombreTabla + " where ID_DIRECCION = ?");
                stmt.setInt(1, codigoDireccicon);
                set = stmt.executeQuery();
                while (set.next()){
                    variableDireccion.setCodigo(set.getInt(1));                    // Codigo
                    variableDireccion.setCalle(set.getString(2));                  // Calle
                    variableDireccion.setNumero(set.getInt(3));                    // Numero
                    variableDireccion.setBloque(set.getString(4));                 // Bloque
                    variableDireccion.setPiso(set.getString(5));                   // Piso
                    variableDireccion.setPuerta(set.getString(6));                 // Puerta
                    variableDireccion.setCodigoPostal(set.getInt(7));              // Codigo postal
                    variableDireccion.setLocalidad(set.getString(8));              // Localidad
                    variableDireccion.setProvincia(set.getString(9));              // Provincia
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
        ResultSet set = null;
        if (conexion != null) {
            int cantidadAfectada = 0;
            try {
                stmt = conexion.prepareStatement("select * from " + nombreTabla + " where CODIGO_EMPLEADO = ?");
                stmt.setString(1, codigoEmpleado);
                set = stmt.executeQuery();
                while (set.next()){
                    variableContrato.setId(set.getInt(1));                           // Id Contrato
                    variableContrato.setFechaInicioContrato(set.getDate(2));         // Fecha inicio contrato
                    variableContrato.setFechaFinalContrato(set.getDate(3));          // Fecha final contrato
                    variableContrato.setFechaFinalizacionEstimada(set.getDate(4));   // Fecha finalizacion estimada
                    variableContrato.setSalario(set.getInt(5));                      // Salario
                    variableContrato.setPuesto(Puesto.values()[set.getInt(6)]);      // Puesto
                    variableContrato.setCodigoEmpleadoAsignado(set.getString(7));    // Codigo empleado
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
        String nombreTablaModificando = nombreTabla;
        if (conexion != null) {
            try {
                int cantidadAfectada =+ guardarDatosCamposPersonales(stmt,conexion,nombreTabla,empleado);
                for (int i = 0; i < empleado.getContratos().size(); i++) {
                    cantidadAfectada = guardarDatosContrato(nombreTabla, empleado, empleado.getContratos().get(i).getId(), stmt, conexion);
                }
                System.out.println("Guardado con exito, " + cantidadAfectada + " fila/s afectada/s en " + nombreTabla);
            } catch (SQLIntegrityConstraintViolationException e){
                System.out.println("La clave primaría asignada a este usuario ya existe. Reintente cambiando su codigo");
            } catch (SQLException exception) {
                System.out.println("Error guardando una fila en la tabla " + nombreTablaModificando);
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
    public static int guardarDatosCamposPersonales(PreparedStatement stmt, Connection conexion, String nombreTabla, Empleado empleado) throws SQLException {
        String nombreTablaModificando = nombreTabla;
        stmt = conexion.prepareStatement("insert into " + nombreTablaModificando + " values(?,?,?,?,?,?,?,?,?,?)");
        stmt.setString(1, empleado.getCodigo());                                   // Codigo
        stmt.setString(2, empleado.getNombre());                                   // Nombre
        stmt.setString(3, empleado.getPrimerApellido());                           // Primer apellido
        stmt.setString(4, empleado.getSegundoApellido());                          // Segundo apellido
        stmt.setString(5, empleado.getDNI());                                      // DNI
        stmt.setDate(6, Fecha.cambiarDateADateSQL(empleado.getFechaNacimiento())); // Fecha nacimiento
        stmt.setString(7, empleado.getNacionalidad());                             // Nacionalidad
        nombreTablaModificando = "FPM_DIRECCION";
        int cantidadAfectada =+ guardarDatosDireccionBaseDeDato(nombreTablaModificando, empleado, stmt, conexion);
        nombreTablaModificando = nombreTabla;
        stmt.setInt(8, empleado.getDireccion().getCodigo());                       // Direccion
        stmt.setString(9, empleado.getEstado().toString());                        // Estado
        stmt.setDate(10, Fecha.cambiarDateADateSQL(empleado.getFechaAlta()));      // Fecha alta
        cantidadAfectada =+ stmt.executeUpdate();
        return cantidadAfectada;
    }
    public static int guardarDatosDireccionBaseDeDato(String nombreTabla, Empleado empleado, PreparedStatement stmt, Connection conexion) throws SQLException {
        stmt = conexion.prepareStatement("insert into " + nombreTabla + " values(?,?,?,?,?,?,?,?,?)");
        stmt.setInt(1, empleado.getDireccion().getCodigo());         // Codigo
        stmt.setString(2, empleado.getDireccion().getCalle());       // Calle
        stmt.setInt(3, empleado.getDireccion().getNumero());         // Numero
        stmt.setString(4, empleado.getDireccion().getBloque());      // Bloque
        stmt.setString(5, empleado.getDireccion().getPiso());        // Piso
        stmt.setString(6, empleado.getDireccion().getPuerta());      // Puerta
        stmt.setInt(7, empleado.getDireccion().getCodigoPostal());   // Codigo Postal
        stmt.setString(8, empleado.getDireccion().getLocalidad());   // Localidad
        stmt.setString(9, empleado.getDireccion().getProvincia());   // Provincia
        int cantidadAfectada = stmt.executeUpdate();
        System.out.println("Guardado con exito, " + cantidadAfectada + " fila/s afectada/s en " + nombreTabla);
        return cantidadAfectada;
    }
    public static int guardarDatosContrato(String nombreTabla, Empleado empleado, int numeroDeContrato, PreparedStatement stmt, Connection conexion) throws SQLException {
        stmt = conexion.prepareStatement("insert into " + nombreTabla + " values(?,?,?,?,?,?,?)");
        stmt.setInt(1, empleado.getContratos().get(numeroDeContrato).getId());                                                     // Id contrato
        stmt.setDate(2,  Fecha.cambiarDateADateSQL(empleado.getContratos().get(numeroDeContrato).getFechaInicioContrato()));       // Fecha inicio
        stmt.setDate(3, Fecha.cambiarDateADateSQL(empleado.getContratos().get(numeroDeContrato).getFechaFinalContrato()));         // Fecha finalizacion
        stmt.setDate(4, Fecha.cambiarDateADateSQL(empleado.getContratos().get(numeroDeContrato).getFechaFinalizacionEstimada()));  // Fecha finalizacion estimada
        stmt.setInt(5, (int) empleado.getContratos().get(numeroDeContrato).getSalario());                                          // Salario
        stmt.setString(6, String.valueOf(empleado.getContratos().get(numeroDeContrato).getPuesto()));                              // Puesto
        stmt.setString(7, empleado.getContratos().get(numeroDeContrato).getCodigoEmpleadoAsignado());                              // Codigo empleado
        int cantidadAfectada = stmt.executeUpdate();
        System.out.println("Guardado con exito, " + cantidadAfectada + " fila/s afectada/s en " + nombreTabla);
        return cantidadAfectada;
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


    public static void updateFilaBaseDeDatos(String nombreTabla, Empleado empleado){ // TODO
        Connection conexion = cargarBaseDeDatos();
        PreparedStatement stmt = null;
        try {
            int cantidadAfectada = updateCamposPersonales(stmt,conexion,nombreTabla,empleado);
            cantidadAfectada += updateCamposDireccion(stmt,conexion,empleado);
            if (empleado.getContratos() == null){
                System.out.println("Este empleado no tiene contratos asignados");
            } else {
                cantidadAfectada += updateCamposContrato(stmt,conexion,empleado);
            }
            System.out.println("Campos modificados con exito, " + cantidadAfectada + " fila/s afectada/s");
        } catch (SQLException throwables) {
            System.out.println("Error actualizando las tablas " + nombreTabla);
        } finally {
            Prints.limpiar(1);
            try {
                stmt.close();
                conexion.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    public static int updateCamposPersonales(PreparedStatement stmt, Connection conexion, String nombreTabla, Empleado empleado) throws SQLException {
        stmt = conexion.prepareStatement("update " + nombreTabla + " set NOMBRE = ?, PRIMER_APELLIDO = ?, " +
                "SEGUNDO_APELLIDO = ?, DNI = ?, FECHA_NACIMIENTO = ?, NACIONALIDAD = ?, ESTADO = ?" );
        stmt.setString(1, empleado.getNombre());                                    // Nombre
        stmt.setString(2, empleado.getPrimerApellido());                            // Primer apellido
        stmt.setString(3, empleado.getSegundoApellido());                           // Segundo apellido
        stmt.setString(4, empleado.getDNI());                                       // DNI
        stmt.setDate(5, Fecha.cambiarDateADateSQL(empleado.getFechaNacimiento()));  // Fecha nacimiento
        stmt.setString(6, empleado.getNacionalidad());                              // Nacionalidad
        stmt.setString(7, empleado.getEstado().toString());                         // Estado
        return stmt.executeUpdate();
    }
    public static int updateCamposDireccion(PreparedStatement stmt, Connection conexion, Empleado empleado) throws SQLException {
        stmt = conexion.prepareStatement("update FPM_DIRECCION set CALLE = ?, NUMERO = ?," +
                " BLOQUE = ?, PISO = ?, PUERTA = ?, CODIGO_POSTAL = ?, LOCALIDAD = ?, PROVINCIA = ? where ID_DIRECCION = ?" );
        stmt.setString(1, empleado.getDireccion().getCalle());
        stmt.setInt(2, empleado.getDireccion().getNumero());
        stmt.setString(3, empleado.getDireccion().getBloque());
        stmt.setString(4, empleado.getDireccion().getPiso());
        stmt.setString(5, empleado.getDireccion().getPuerta());
        stmt.setInt(6, empleado.getDireccion().getCodigoPostal());
        stmt.setString(7, empleado.getDireccion().getLocalidad());
        stmt.setString(8, empleado.getDireccion().getProvincia());
        stmt.setInt(9, empleado.getDireccion().getCodigo());
        return stmt.executeUpdate();
    }
    public static int updateCamposContrato(PreparedStatement stmt, Connection conexion, Empleado empleado) throws SQLException {
        stmt = conexion.prepareStatement("update FPM_CONTRATOS set FECHA_INICIO_CONTRATO = ?, FECHA_FINAL_CONTRATO = ?," +
                " FECHA_FINALIZACION_ESTIMADA = ?, SALARIO = ?, PUESTO = ? where CODIGO_EMPLEADO = ?");
        stmt.setDate(1, Fecha.cambiarDateADateSQL(empleado.getContratos().get(empleado.getContratos().size() - 1).getFechaInicioContrato()));
        stmt.setDate(2, Fecha.cambiarDateADateSQL(empleado.getContratos().get(empleado.getContratos().size() - 1).getFechaFinalContrato()));
        stmt.setDate(3, Fecha.cambiarDateADateSQL(empleado.getContratos().get(empleado.getContratos().size() - 1).getFechaFinalizacionEstimada()));
        stmt.setInt(4, (int) empleado.getContratos().get(empleado.getContratos().size() - 1).getSalario());
        stmt.setString(5, String.valueOf(empleado.getContratos().get(empleado.getContratos().size() - 1).getPuesto()));
        stmt.setString(6, empleado.getCodigo());
        return stmt.executeUpdate();
    }
}