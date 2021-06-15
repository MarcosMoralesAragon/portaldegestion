package com.servicios;

import com.modelos.*;
import com.utilidades.Fecha;
import com.utilidades.Prints;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;

public class ServiciosBaseDeDatos {
    private final Prints prints = new Prints();

    public Connection cargarBaseDeDatos(String palabra){
        String frase = "";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            prints.escribir("Fallo en la declaracion del driver");
        }
        Connection conexion = null;
        try  {
            conexion = DriverManager.getConnection(
                    "jdbc:oracle:thin:@//10.206.110.3:1521/pdbcmdb.at4wireless.com", "TD_CMDB_V1", "bg4ve8l8");
            if (conexion != null) {
                frase = "¡ Conexion exitosa !";
            }
            if (palabra.equals("")){
                prints.escribir(frase);
            }
        } catch (Exception e) {
            prints.escribir("Error al realizar la conexion");
        }
        return conexion;
    }

    public ArrayList<Empleado> cargarFilaBaseDeDatos(String nombreTabla, ArrayList<Empleado> empleados) {
        Empleado variableEmpleado;
        Connection conexion = cargarBaseDeDatos("");
        PreparedStatement stmt = null;
        ResultSet set = null;
        if (conexion != null) {
            int cantidadAfectada = 0;
            try {
                stmt = conexion.prepareStatement("select * from " + nombreTabla);
                set = stmt.executeQuery();
                while (set.next()) {
                    variableEmpleado = new Empleado();
                    ServiciosGeneral Servicios = new ServiciosGeneral();
                    Servicios.datosEmpleados(null,variableEmpleado,"bbdd",set,null,null);
                    variableEmpleado.setContratos(cargarContrato("FPM_CONTRATOS",variableEmpleado.getCodigo(),conexion));
                    empleados.add(variableEmpleado);
                    cantidadAfectada ++;
                }
                prints.escribir("Carga correcta, " + cantidadAfectada + " empleado/s cargadado/s");
            } catch (SQLException exception) {
                prints.escribir("Error cargando en la tabla " + nombreTabla);
            } catch (NullPointerException e){
                prints.escribir("Uno de los campos se encuentra vacio o con un dato erroneo, revise la BBDD");
            } catch (ParseException e) {
                prints.escribir("Formato de fecha erroneo");
            } finally {
                try {
                    assert stmt != null;
                    stmt.close();
                    assert set != null;
                    set.close();
                    conexion.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return empleados;
    }

    public Direccion cargarDireccion(String nombreTabla, int codigoDireccicon) {
        Direccion variableDireccion = new Direccion();
        Connection conexion = cargarBaseDeDatos("no");
        if (conexion != null) {
            try {
                PreparedStatement stmt = conexion.prepareStatement("select * from " + nombreTabla + " where ID_DIRECCION = " + codigoDireccicon);
                ResultSet set = stmt.executeQuery();
                while (set.next()){
                    ServiciosGeneral Servicios = new ServiciosGeneral();
                    variableDireccion = Servicios.datosDireccion(null, "bbdd", set, null, null);
                }
            } catch (SQLException exception) {
                prints.escribir("Error cargando en la tabla " + nombreTabla);
            } catch (NullPointerException e){
                prints.escribir("Uno de los campos se encuentra vacio o con un dato erroneo, revise la BBDD");
            }
        }
        return variableDireccion;
    }
    public ArrayList<Contrato> cargarContrato(String nombreTabla, String codigoEmpleado, Connection conexion) {
        ArrayList <Contrato> contratoArrayList = new ArrayList<>();
        if (conexion != null) {
            try {
                PreparedStatement stmt = conexion.prepareStatement("select * from " + nombreTabla + " where CODIGO_EMPLEADO = " + "'" + codigoEmpleado + "'");
                ResultSet set = stmt.executeQuery();
                while (set.next()){
                    Contrato variableContrato = new Contrato();
                    variableContrato.setId(set.getInt(1));                           // Id Contrato
                    variableContrato.setFechaInicioContrato(set.getDate(2));         // Fecha inicio contrato
                    variableContrato.setFechaFinalContrato(set.getDate(3));          // Fecha final contrato
                    variableContrato.setFechaFinalizacionEstimada(set.getDate(4));   // Fecha finalizacion estimada
                    variableContrato.setSalario(set.getInt(5));                      // Salario
                    ServiciosGeneral Servicios = new ServiciosGeneral();
                    variableContrato.setPuesto(Puesto.values()[Servicios.puestoEleccion(set.getString(6))]);      // Puesto
                    variableContrato.setCodigoEmpleadoAsignado(set.getString(7));    // Codigo empleado
                    contratoArrayList.add(variableContrato);
                    ServiciosGeneral.contratos.add(variableContrato);
                }
            } catch (SQLException exception) {
                prints.escribir("Error cargando en la tabla " + nombreTabla);
            } catch (NullPointerException e){
                prints.escribir("Uno de los campos en " + nombreTabla + " se encuentra vacio o con un dato erroneo, revise la BBDD");
            }
        }
        return contratoArrayList;
    }


    public void guardarDatosEmpleadosBaseDeDato(String nombreTabla, Empleado empleado, Contrato contratos){
        Connection conexion = cargarBaseDeDatos("");
        if (conexion != null) {
            try {
                int cantidadAfectada = 0;
                if (nombreTabla.equals("FPM_EMPLEADOS")){
                    cantidadAfectada =+ guardarDatosCamposPersonales(conexion,nombreTabla,empleado);
                } else if (nombreTabla.equals("FPM_CONTRATOS")){
                    cantidadAfectada =+ guardarDatosContrato(nombreTabla, contratos , conexion);
                }
                prints.escribir("Guardado con exito, " + cantidadAfectada + " fila/s afectada/s en " + nombreTabla);
            } catch (SQLIntegrityConstraintViolationException e){
                prints.escribir("La clave primaría asignada a este usuario ya existe. Reintente cambiando su codigo");
            } catch (SQLException exception) {
                prints.escribir("Error guardando una fila en la tabla " + nombreTabla);
            } finally {
                try {
                    conexion.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
    public int guardarDatosCamposPersonales(Connection conexion, String nombreTabla, Empleado empleado) throws SQLException {
        String nombreTablaModificando = nombreTabla;
        PreparedStatement stmt = conexion.prepareStatement("insert into " + nombreTablaModificando + " values(?,?,?,?,?,?,?,?,?,?)");
        stmt.setString(1, empleado.getCodigo());                                   // Codigo
        stmt.setString(2, empleado.getNombre());                                   // Nombre
        stmt.setString(3, empleado.getPrimerApellido());                           // Primer apellido
        stmt.setString(4, empleado.getSegundoApellido());                          // Segundo apellido
        stmt.setString(5, empleado.getDNI());                                      // DNI
        stmt.setDate(6, Fecha.cambiarDateADateSQL(empleado.getFechaNacimiento())); // Fecha nacimiento
        stmt.setString(7, empleado.getNacionalidad());                             // Nacionalidad
        nombreTablaModificando = "FPM_DIRECCION";
        guardarDatosDireccionBaseDeDato(nombreTablaModificando, empleado, conexion);
        stmt.setDate(8, Fecha.cambiarDateADateSQL(empleado.getFechaAlta()));      // Fecha alta
        stmt.setString(9, empleado.getEstado().toString());                        // Estado
        stmt.setInt(10, empleado.getDireccion().getCodigo());                       // Direccion
        return stmt.executeUpdate();
    }
    public void guardarDatosDireccionBaseDeDato(String nombreTabla, Empleado empleado, Connection conexion) throws SQLException {
        PreparedStatement stmt = conexion.prepareStatement("insert into " + nombreTabla + " values(?,?,?,?,?,?,?,?,?)");
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
        prints.escribir("Guardado con exito, " + cantidadAfectada + " fila/s afectada/s en " + nombreTabla);
    }
    public int guardarDatosContrato(String nombreTabla,Contrato contratos, Connection conexion) throws SQLException {
        PreparedStatement stmt = conexion.prepareStatement("insert into " + nombreTabla + " values(?,?,?,?,?,?,?)");
        stmt.setInt(1, contratos.getId());                                                     // Id contrato
        stmt.setDate(2,  Fecha.cambiarDateADateSQL(contratos.getFechaInicioContrato()));       // Fecha inicio
        stmt.setDate(3, Fecha.cambiarDateADateSQL(contratos.getFechaFinalContrato()));         // Fecha finalizacion
        stmt.setDate(4, Fecha.cambiarDateADateSQL(contratos.getFechaFinalizacionEstimada()));  // Fecha finalizacion estimada
        stmt.setInt(5, (int) contratos.getSalario());                                          // Salario
        stmt.setString(6, String.valueOf(contratos.getPuesto()));                              // Puesto
        stmt.setString(7, contratos.getCodigoEmpleadoAsignado());                              // Codigo empleado
        int cantidadAfectada = stmt.executeUpdate();
        System.out.println("Guardado con exito, " + cantidadAfectada + " fila/s afectada/s en " + nombreTabla);
        return cantidadAfectada;
    }


    public void borrarFilaBaseDeDatos(Empleado empleado){
        Connection conexion = cargarBaseDeDatos("");
        PreparedStatement stmt = null;
        try {
            stmt = conexion.prepareStatement("delete from FPM_EMPLEADOS where ID = '" + empleado.getCodigo() + "'");
            int cantidadAfectada = stmt.executeUpdate();
            stmt = conexion.prepareStatement("delete from FPM_DIRECCION where ID_DIRECCION = " + empleado.getDireccion().getCodigo());
            cantidadAfectada =+ stmt.executeUpdate();
            prints.escribir("Borrado con exito, " + cantidadAfectada + " fila/s afectada/s");
        } catch (SQLException throwables) {
            prints.escribir("Error borrando una fila en la tabla");
        } finally {
            try {
                assert stmt != null;
                stmt.close();
                conexion.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }


    public void updateTodoBaseDeDatos(Empleado empleado) throws SQLException { // TODO
        int cantidadAfectada = updateCamposPersonales(empleado);
        cantidadAfectada += updateCamposDireccion(empleado);
        if (empleado.getContratos() == null){
            prints.escribir("Este empleado no tiene contratos asignados");
        } else {
            cantidadAfectada += updateCamposContrato(empleado);
        }
        prints.escribir("Campos modificados con exito, " + cantidadAfectada + " fila/s afectada/s");

    }
    public int updateCamposPersonales(Empleado empleado) throws SQLException {
        Connection conexion = cargarBaseDeDatos("no");
        PreparedStatement stmt = conexion.prepareStatement("update FPM_PRUEBA set NOMBRE = ?, PRIMER_APELLIDO = ?, " +
                "SEGUNDO_APELLIDO = ?, DNI = ?, FECHA_NACIMIENTO = ?, NACIONALIDAD = ?, ESTADO = ? where ID = ?" );
        stmt.setString(1, empleado.getNombre());                                    // Nombre
        stmt.setString(2, empleado.getPrimerApellido());                            // Primer apellido
        stmt.setString(3, empleado.getSegundoApellido());                           // Segundo apellido
        stmt.setString(4, empleado.getDNI());                                       // DNI
        stmt.setDate(5, Fecha.cambiarDateADateSQL(empleado.getFechaNacimiento()));  // Fecha nacimiento
        stmt.setString(6, empleado.getNacionalidad());                              // Nacionalidad
        stmt.setString(7, empleado.getEstado().toString());                         // Estado
        stmt.setString(8, empleado.getCodigo());                                    // Codigo

        return stmt.executeUpdate();
    }
    public int updateCamposDireccion(Empleado empleado) throws SQLException {
        Connection conexion = cargarBaseDeDatos("no");
        PreparedStatement stmt = conexion.prepareStatement("update FPM_DIRECCION set CALLE = ?, NUMERO = ?," +
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
    public int updateCamposContrato(Empleado empleado) throws SQLException {
        Connection conexion = cargarBaseDeDatos("no");
        PreparedStatement stmt = conexion.prepareStatement("update FPM_CONTRATOS set FECHA_INICIO_CONTRATO = ?, FECHA_FINAL_CONTRATO = ?," +
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