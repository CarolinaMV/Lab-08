package edu.eci.cvds.samples.services.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.eci.cvds.sampleprj.dao.ClienteDAO;
import edu.eci.cvds.sampleprj.dao.ItemDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.TipoItemDAO;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.entities.TipoItem;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Singleton
public class ServiciosAlquilerImpl implements ServiciosAlquiler {

    @Inject
    private ItemDAO itemDAO;

    @Inject
    private ClienteDAO clienteDAO;

    @Inject
    private TipoItemDAO tipoItemDAO;

    @Override
    public int valorMultaRetrasoxDia(int itemId) throws ExcepcionServiciosAlquiler {
        try{
            return (int) itemDAO.loadItem(itemId).getTarifaxDia();
        }catch (PersistenceException e){
            throw new ExcepcionServiciosAlquiler("Este item no se encuentra registrado.");
        }
    }

    @Override
    public Cliente consultarCliente(long docu) throws ExcepcionServiciosAlquiler {
        try {
            Cliente cliente = clienteDAO.loadCliente((int) docu);
            if (cliente == null) throw new ExcepcionServiciosAlquiler(ExcepcionServiciosAlquiler.CLIENTE_NO_REGISTRADO);
            else return cliente;
        }catch (PersistenceException e){
            throw new ExcepcionServiciosAlquiler(ExcepcionServiciosAlquiler.ERROR_CONSULTAR_CLIENTE);
        }
    }

    @Override
    public List<ItemRentado> consultarItemsCliente(long idcliente) throws ExcepcionServiciosAlquiler {
        try {
            Cliente cliente = clienteDAO.loadCliente((int) idcliente);
            if (cliente == null) throw new ExcepcionServiciosAlquiler(ExcepcionServiciosAlquiler.CLIENTE_NO_REGISTRADO);
            else return cliente.getRentados();
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosAlquiler(ExcepcionServiciosAlquiler.ERROR_CONSULTAR_CLIENTE);
        }
    }

    @Override
    public List<Cliente> consultarClientes() throws ExcepcionServiciosAlquiler {
        try {
            return clienteDAO.loadClientes();
        }catch (PersistenceException e){
            throw new ExcepcionServiciosAlquiler(ExcepcionServiciosAlquiler.ERROR_CONSULTAR_CLIENTES);
        }
    }

    @Override
    public Item consultarItem(int id) throws ExcepcionServiciosAlquiler {
        try {
            Item item = itemDAO.loadItem(id);
            if (item == null) throw new ExcepcionServiciosAlquiler(ExcepcionServiciosAlquiler.ITEM_NO_REGISTRADO);
            else return item;
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosAlquiler(ExcepcionServiciosAlquiler.ERROR_CONSULTAR_ITEM);
        }
    }

    @Override
    public List<Item> consultarItemsDisponibles() throws ExcepcionServiciosAlquiler{
        try {
            return itemDAO.loadItems();
        }catch (PersistenceException e){
            throw new ExcepcionServiciosAlquiler(ExcepcionServiciosAlquiler.ERROR_CONSULTAR_ITEMS);
        }
    }

    @Override
    public long consultarMultaAlquiler(int iditem, Date fechaDevolucion) throws ExcepcionServiciosAlquiler {
        List<Cliente> clientes = consultarClientes();
        for (Cliente c : clientes){
            List<ItemRentado> rentados = c.getRentados();
            for (ItemRentado i : rentados){
                LocalDate fechaFin = i.getFechafinrenta().toLocalDate();
                LocalDate fechaEntrega = fechaDevolucion.toLocalDate();
                long retraso = ChronoUnit.DAYS.between(fechaFin, fechaEntrega);
                if (retraso <= 0){
                    return 0;
                }
                return retraso * valorMultaRetrasoxDia(i.getItem().getId());
            }
        }
        throw new ExcepcionServiciosAlquiler("El item se puede alquilar");
    }

    @Override
    public TipoItem consultarTipoItem(int id) throws ExcepcionServiciosAlquiler {
        try {
            TipoItem tipoItem = tipoItemDAO.loadTipoItem(id);
            if (tipoItem == null) throw new ExcepcionServiciosAlquiler(ExcepcionServiciosAlquiler.TIPOITEM_NO_REGISTRADO);
            else return tipoItem;
        }catch (PersistenceException e){
            throw new ExcepcionServiciosAlquiler(ExcepcionServiciosAlquiler.ERROR_CONSULTAR_TIPOITEM);
        }
    }

    @Override
    public List<TipoItem> consultarTiposItem() throws ExcepcionServiciosAlquiler {
        try {
            return tipoItemDAO.loadTiposItems();
        }catch (PersistenceException e){
            throw new ExcepcionServiciosAlquiler(ExcepcionServiciosAlquiler.ERROR_CONSULTAR_TIPOSITEMS);
        }
    }

    @Override
    public void registrarAlquilerCliente(Date date, long docu, Item item, int numdias) throws ExcepcionServiciosAlquiler {
        try {
            if (numdias < 1) throw new ExcepcionServiciosAlquiler(ExcepcionServiciosAlquiler.ERROR_DIAS_RENTA);
            if (consultarCliente(docu) != null && consultarItem(item.getId()) != null){
                LocalDate inicio = date.toLocalDate();
                LocalDate fin = inicio.plusDays(numdias);
                clienteDAO.saveItemRentado((int) docu, item.getId(), date, java.sql.Date.valueOf(fin));
            }
        }catch (Exception e){
            throw new ExcepcionServiciosAlquiler(ExcepcionServiciosAlquiler.ERROR_REGISTRAR_ITEMRENTADO);
        }
    }

    @Override
    public void registrarCliente(Cliente c) throws ExcepcionServiciosAlquiler {
        try {
            clienteDAO.saveCliente(c);
        }catch (PersistenceException e){
            throw new ExcepcionServiciosAlquiler(ExcepcionServiciosAlquiler.ERROR_REGISTRAR_CLIENTE);
        }
    }

    @Override
    public long consultarCostoAlquiler(int iditem, int numdias) throws ExcepcionServiciosAlquiler {
        return consultarItem(iditem).getTarifaxDia() * numdias;
    }

    @Override
    public void actualizarTarifaItem(int id, long tarifa) throws ExcepcionServiciosAlquiler {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void registrarItem(Item i) throws ExcepcionServiciosAlquiler {
        try {
            Item item = itemDAO.loadItem(i.getId());
            if (item == null) itemDAO.saveItem(i);
            else throw new Exception();
        }catch (Exception e){
            throw new ExcepcionServiciosAlquiler(ExcepcionServiciosAlquiler.ERROR_REGISTRAR_ITEM);
        }
    }

    @Override
    public void vetarCliente(long docu, boolean estado) throws ExcepcionServiciosAlquiler {
        try {
            clienteDAO.vetarCliente(docu, estado);
        }catch (PersistenceException e){
            throw new ExcepcionServiciosAlquiler(ExcepcionServiciosAlquiler.ERROR_VETAR_CLIENTE);
        }
    }
}