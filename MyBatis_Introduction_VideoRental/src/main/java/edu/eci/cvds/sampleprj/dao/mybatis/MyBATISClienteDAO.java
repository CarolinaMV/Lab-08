package edu.eci.cvds.sampleprj.dao.mybatis;

import com.google.inject.Inject;
import edu.eci.cvds.sampleprj.dao.ClienteDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ClienteMapper;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import org.apache.ibatis.exceptions.PersistenceException;

import java.util.List;

public class MyBATISClienteDAO implements ClienteDAO {

    @Inject
    private ClienteMapper ClienteMapper;

    @Override
    public void saveCliente(Cliente c) throws PersistenceException {
        try{
            ClienteMapper.registrarCliente(c);
        }
        catch(Exception e){
            throw new PersistenceException(ExcepcionServiciosAlquiler.ERROR_REGISTRAR_CLIENTE);
        }
    }

    @Override
    public Cliente loadCliente(int id) throws PersistenceException {
        try{
            return ClienteMapper.consultarCliente(id);
        }
        catch(Exception e){
            throw new PersistenceException(ExcepcionServiciosAlquiler.ERROR_CONSULTAR_CLIENTE);
        }
    }

    @Override
    public List<Cliente> loadClientes() throws PersistenceException {
        try{
            return ClienteMapper.consultarClientes();
        }
        catch(Exception e){
            throw new PersistenceException(ExcepcionServiciosAlquiler.ERROR_CONSULTAR_CLIENTES);
        }
    }
}
