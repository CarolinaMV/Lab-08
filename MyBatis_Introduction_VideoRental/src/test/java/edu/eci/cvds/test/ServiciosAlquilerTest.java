package edu.eci.cvds.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.entities.TipoItem;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquilerFactory;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class ServiciosAlquilerTest {

	@Inject
	//private SqlSession sqlSession;
	public ServiciosAlquiler serviciosAlquiler;

	public ServiciosAlquilerTest() {
		serviciosAlquiler = ServiciosAlquilerFactory.getInstance().getServiciosAlquilerTesting();
	}


	@Test
	public void emptyDB() {
		for(int i = 0; i < 100; i += 10) {
			boolean r = false;
			try {
				serviciosAlquiler.consultarCliente(i);
			} catch(ExcepcionServiciosAlquiler e) {
				r = true;
			} catch(IndexOutOfBoundsException e) {
				r = true;
			}
			// Validate no Client was found;
			Assert.assertTrue(r);
		};
	}

	@Test
	public void deberiaRegistrarItem(){
		TipoItem tipo = new TipoItem(4, "Libros");
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-mm-dd");
		try {
			java.sql.Date fechaLanzamiento = new java.sql.Date(formatoFecha.parse("2015-01-06").getTime());
			Item nuevoItem = new Item(tipo, 5, "La chica del tren", "Marca de cojines famosa", fechaLanzamiento, 1000, "Fisico", "Novela");
			serviciosAlquiler.registrarItem(nuevoItem);
			Assert.assertEquals(serviciosAlquiler.consultarItem(5).getId(), 2162844);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void deberiaNoRegistrarItem(){
		TipoItem tipo = new TipoItem(4, "Libros");
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-mm-dd");
		try {
			java.sql.Date fechaLanzamiento = new java.sql.Date(formatoFecha.parse("2015-01-06").getTime());
			java.sql.Date segundaFechaLanzamiento = new java.sql.Date(formatoFecha.parse("2017-05-02").getTime());
			Item nuevoItem = new Item(tipo, 6, "La chica del tren", "Libro popular de la autora Paula Hawkins del 2015", fechaLanzamiento, 1000, "Fisico", "Novela");
			Item segundoNuevoItem = new Item(tipo, 6, "Escrito en el agua", "Marca de cojines famosa Paula Hawkins del 2017", segundaFechaLanzamiento, 2000, "Fisico", "Novela");
			serviciosAlquiler.registrarItem(nuevoItem);
			serviciosAlquiler.registrarItem(segundoNuevoItem);
		}catch (Exception e){
			Assert.assertEquals(e.getMessage(), "Se produjo un error al registrar el Item.");
		}
	}

	@Test
	public void deberiaConsultarItem(){
		TipoItem tipo = new TipoItem(4, "Libros");
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-mm-dd");
		try {
			java.sql.Date fechaLanzamiento = new java.sql.Date(formatoFecha.parse("2015-01-06").getTime());
			Item nuevoItem = new Item(tipo, 5, "La chica del tren", "Marca de cojines famosa", fechaLanzamiento, 1000, "Fisico", "Novela");
			serviciosAlquiler.registrarItem(nuevoItem);
			Assert.assertEquals(serviciosAlquiler.consultarItem(5).getNombre(), "La chica del tren");
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void deberiaNoConsultarItem(){
		try {
			serviciosAlquiler.consultarItem(4);
		}catch (Exception e){
			Assert.assertEquals(e.getMessage(), "Se produjo un error al registrar el Item.");
		}
	}

	@Test
	public void deberiaConsultarItems(){
		TipoItem tipo = new TipoItem(4, "Libros");
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-mm-dd");
		try {
			java.sql.Date fechaLanzamiento = new java.sql.Date(formatoFecha.parse("2015-01-06").getTime());
			java.sql.Date segundaFechaLanzamiento = new java.sql.Date(formatoFecha.parse("2017-05-02").getTime());
			Item nuevoItem = new Item(tipo, 1, "La chica del tren", "Libro popular de la autora Paula Hawkins del 2015", fechaLanzamiento, 1000, "Fisico", "Novela");
			Item segundoNuevoItem = new Item(tipo, 2, "Escrito en el agua", "Marca de cojines famosa Paula Hawkins del 2017", segundaFechaLanzamiento, 2000, "Fisico", "Novela");
			serviciosAlquiler.registrarItem(nuevoItem);
			serviciosAlquiler.registrarItem(segundoNuevoItem);
			List<Item> itemsActuales = serviciosAlquiler.consultarItemsDisponibles();
			if (itemsActuales != null){
				Assert.assertEquals(serviciosAlquiler.consultarItem(1).getNombre(), "La chica del tren");
				Assert.assertEquals(serviciosAlquiler.consultarItem(2).getNombre(), "Escrito en el agua");
			}else {
				Assert.assertFalse(false);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void deberiaActualizarTarifaItem(){
		TipoItem tipo = new TipoItem(4, "Libros");
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-mm-dd");
		try {
			java.sql.Date fechaLanzamiento = new java.sql.Date(formatoFecha.parse("2015-01-06").getTime());
			Item nuevoItem = new Item(tipo, 5, "La chica del tren", "Marca de cojines famosa", fechaLanzamiento, 1000, "Fisico", "Novela");
			serviciosAlquiler.registrarItem(nuevoItem);
			serviciosAlquiler.actualizarTarifaItem(5, 2000);
			Assert.assertEquals(serviciosAlquiler.consultarItem(5).getTarifaxDia(), 2000);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void deberiaRegistrarCliente(){
		try {
			Cliente cliente = new Cliente("Juan", 12345678, "3501234567", "Calle 123 #45a-67", "juan@mail.com", false, null);
			serviciosAlquiler.registrarCliente(cliente);
			Assert.assertEquals(serviciosAlquiler.consultarCliente(12345678).getDocumento(), 12345678);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void deberiaConsultarCliente(){
		try {
			Cliente cliente = new Cliente("Juan", 12345678, "3501234567", "Calle 123 #45a-67", "juan@mail.com", false, null);
			serviciosAlquiler.registrarCliente(cliente);
			Assert.assertEquals(serviciosAlquiler.consultarCliente(12345678).getNombre(), "Juan");
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void deberiaVetarCliente(){
		try {
			Cliente cliente = new Cliente("Juan", 12345678, "3501234567", "Calle 123 #45a-67", "juan@mail.com", false, null);
			serviciosAlquiler.registrarCliente(cliente);
			serviciosAlquiler.vetarCliente(12345678, true);
			Assert.assertEquals(serviciosAlquiler.consultarCliente(12345678).isVetado(), true);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void deberiaRegistrarTipoItem(){
		TipoItem tipo = new TipoItem(4, "Libros");
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-mm-dd");
		try {
			java.sql.Date fechaLanzamiento = new java.sql.Date(formatoFecha.parse("2015-01-06").getTime());
			Item nuevoItem = new Item(tipo, 5, "La chica del tren", "Marca de cojines famosa", fechaLanzamiento, 1000, "Fisico", "Novela");
			serviciosAlquiler.registrarItem(nuevoItem);
			Assert.assertEquals(serviciosAlquiler.consultarTipoItem(4).getDescripcion(), "Libros");
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}