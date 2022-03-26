package edu.eci.cvds.sampleprj.dao.mybatis;

import edu.eci.cvds.sampleprj.dao.ItemDAO;
import edu.eci.cvds.samples.entities.Item;
import org.apache.ibatis.exceptions.PersistenceException;

import java.util.List;

public class XXItemDAO implements ItemDAO {

    @Override
    public void saveItem(Item it) throws PersistenceException {

    }

    @Override
    public Item loadItem(int id) throws PersistenceException {
        return null;
    }

    @Override
    public List<Item> loadItems() throws PersistenceException {
        return null;
    }
}
