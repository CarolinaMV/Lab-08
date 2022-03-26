package edu.eci.cvds.sampleprj.dao.mybatis;

import edu.eci.cvds.sampleprj.dao.ClienteDAO;
import edu.eci.cvds.samples.entities.Cliente;
import org.apache.ibatis.exceptions.PersistenceException;

import java.util.Date;
import java.util.List;

public class XXClienteDAO implements ClienteDAO {

    @Override
    public void saveCliente(Cliente c) throws PersistenceException {

    }

    @Override
    public Cliente loadCliente(int id) throws PersistenceException {
        return null;
    }

    @Override
    public List<Cliente> loadClientes() throws PersistenceException {
        return null;
    }

    @Override
    public void saveItemRentado(int id, int idi, Date fechaInicio, Date fechaFin) {

    }

    @Override
    public void vetarCliente(long doc, boolean estado) throws edu.eci.cvds.sampleprj.dao.PersistenceException {

    }
}
