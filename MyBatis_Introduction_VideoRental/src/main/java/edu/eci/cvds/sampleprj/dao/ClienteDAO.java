package edu.eci.cvds.sampleprj.dao;

import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import org.apache.ibatis.exceptions.PersistenceException;

import java.util.List;

public interface ClienteDAO {
    public void saveCliente(Cliente c) throws PersistenceException;

    public Cliente loadCliente(int id) throws PersistenceException;

    public List<Cliente> loadClientes() throws PersistenceException;

}
