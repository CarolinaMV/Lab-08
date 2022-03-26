package edu.eci.cvds.sampleprj.dao;

import edu.eci.cvds.samples.entities.Cliente;

import java.util.Date;
import java.util.List;

public interface ClienteDAO {
    public void saveCliente(Cliente c) throws PersistenceException;

    public Cliente loadCliente(int id) throws PersistenceException;

    public List<Cliente> loadClientes() throws PersistenceException;

    public void saveItemRentado(int id, int idi, Date fechaInicio, Date fechaFin);

    public void vetarCliente(long doc, boolean estado) throws PersistenceException;
}
