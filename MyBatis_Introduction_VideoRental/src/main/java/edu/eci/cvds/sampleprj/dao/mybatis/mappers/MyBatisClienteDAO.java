package edu.eci.cvds.sampleprj.dao.mybatis.mappers;

import edu.eci.cvds.samples.entities.Item;
import org.apache.ibatis.exceptions.PersistenceException;

public class MyBatisClienteDAO implements ClienteDAO{
    public void save(Item it) throws PersistenceException {

    }

    public Item load(int id) throws PersistenceException {
        return null;
    }
}
