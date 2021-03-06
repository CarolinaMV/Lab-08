package edu.eci.cvds.sampleprj.dao.mybatis;

import com.google.inject.Inject;
import edu.eci.cvds.sampleprj.dao.ClienteDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ClienteMapper;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import org.apache.ibatis.exceptions.PersistenceException;

import java.util.Date;
import java.util.List;

public class MyBATISClienteDAO implements ClienteDAO {

    @Inject
    private ClienteMapper clienteMapper;

    @Override
    public void saveCliente(Cliente c) throws PersistenceException {
        try{
            clienteMapper.registrarCliente(c);
        }
        catch(Exception e){
            throw new PersistenceException(ExcepcionServiciosAlquiler.ERROR_REGISTRAR_CLIENTE);
        }
    }

    @Override
    public Cliente loadCliente(int id) throws PersistenceException {
        try{
            return clienteMapper.consultarCliente(id);
        }
        catch(Exception e){
            throw new PersistenceException(ExcepcionServiciosAlquiler.ERROR_CONSULTAR_CLIENTE);
        }
    }

    @Override
    public List<Cliente> loadClientes() throws PersistenceException {
        try{
            return clienteMapper.consultarClientes();
        }
        catch(Exception e){
            throw new PersistenceException(ExcepcionServiciosAlquiler.ERROR_CONSULTAR_CLIENTES);
        }
    }

    @Override
    public void saveItemRentado(int id, int idi, Date fechaInicio, Date fechaFin) throws PersistenceException{
        try {
            clienteMapper.agregarItemRentadoACliente(id, idi, fechaInicio, fechaFin);
        }catch (Exception e){
            throw new PersistenceException(ExcepcionServiciosAlquiler.ERROR_REGISTRAR_ITEMRENTADO);
        }
    }

    @Override
    public void vetarCliente(long doc, boolean estado) throws PersistenceException {
        try {
            clienteMapper.vetarCliente(doc, estado);
        }catch (PersistenceException e){
            throw new PersistenceException(ExcepcionServiciosAlquiler.ERROR_VETAR_CLIENTE);
        }
    }
}
