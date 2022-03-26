/*
 * Copyright (C) 2015 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.cvds.sampleprj.jdbc.example;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class JDBCExample {

    public static void main(String args[]){
        try {
            String url="jdbc:mysql://desarrollo.is.escuelaing.edu.co:3306/bdprueba";
            String driver="com.mysql.jdbc.Driver";
            String user="bdprueba";
            String pwd="prueba2019";

            Class.forName(driver);
            Connection con=DriverManager.getConnection(url,user,pwd);
            con.setAutoCommit(false);


            System.out.println("Valor total pedido 1:"+valorTotalPedido(con, 1));

            List<String> prodsPedido=nombresProductosPedido(con, 1);


            System.out.println("Productos del pedido 1:");
            System.out.println("-----------------------");
            for (String nomprod:prodsPedido){
                System.out.println(nomprod);
            }
            System.out.println("-----------------------");


            //int suCodigoECI=20134423;
            //registrarNuevoProducto(con, suCodigoECI, "SU NOMBRE", 99999999);
            con.commit();


            con.close();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(JDBCExample.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    /**
     * Agregar un nuevo producto con los parámetros dados
     * @param con la conexión JDBC
     * @param codigo
     * @param nombre
     * @param precio
     * @throws SQLException
     */
    public static void registrarNuevoProducto(Connection con, int codigo, String nombre,int precio) throws SQLException{

        //Crear preparedStatement
        PreparedStatement insertarProducto = null;
        insertarProducto = con.prepareStatement("insert into ORD_PRODUCTOS values (null,?,?,?)");
        //Asignar parámetros
        insertarProducto.setInt(1,codigo);
        insertarProducto.setString(2,nombre);
        insertarProducto.setInt(3,precio);
        //usar 'execute'
        insertarProducto.executeUpdate();

        con.commit();

    }

    /**
     * Consultar los nombres de los productos asociados a un pedido
     * @param con la conexión JDBC
     * @param codigoPedido el código del pedido
     * @return
     */
    public static List<String> nombresProductosPedido(Connection con, int codigoPedido) throws SQLException {
        List<String> np=new LinkedList<>();

        //Crear prepared statement

        String select = "SELECT nombre FROM ORD_PRODUCTOS op\n" +
                "JOIN ORD_DETALLE_PEDIDO odp \n" +
                "ON odp.producto_fk = op.codigo\n" +
                "JOIN ORD_PEDIDOS op2 \n" +
                "ON odp.pedido_fk = op2.codigo \n" +
                "WHERE op2.codigo = 1;";
        try(PreparedStatement preparedStatement = con.prepareStatement(select); ){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String nombreProducto=resultSet.getString("nombre");
                np.add(nombreProducto);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        //System.out.println(consultarProducto);
        //asignar parámetros
        //asignar parámetros
        //consultarProducto.setInt(1, codigoPedido);
        //usar executeQuery

        //Sacar resultados del ResultSet
        //Llenar la lista y retornarla


        return np;
    }


    /**
     * Calcular el costo total de un pedido
     * @param con
     * @param codigoPedido código del pedido cuyo total se calculará
     * @return el costo total del pedido (suma de: cantidades*precios)
     */
    public static int valorTotalPedido(Connection con, int codigoPedido){
        int costoTotal=0;
        //Crear prepared statement

        String select = "SELECT SUM(op2.precio * odp.cantidad) AS total\n" +
                "FROM ORD_PRODUCTOS op2 \n" +
                "JOIN ORD_DETALLE_PEDIDO odp \n" +
                "ON op2.codigo = odp.producto_fk \n" +
                "JOIN ORD_PEDIDOS op \n" +
                "ON odp.pedido_fk = op.codigo\n" +
                "WHERE op.codigo = 1;";
        try(PreparedStatement preparedStatement = con.prepareStatement(select); ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                costoTotal = resultSet.getInt("total");
            }
            //totalPedido=con.prepareStatement(calcularTotal);
            //asignar parámetros
            //totalPedido.setInt(1, codigoPedido);
            //usar executeQuery
            //ResultSet costo = totalPedido.executeQuery();
            //Sacar resultado del ResultSet

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return costoTotal;
    }
}