package edu.eci.cvds.sampleprj.dao;

import edu.eci.cvds.samples.entities.Item;
import org.apache.ibatis.exceptions.PersistenceException;

import java.util.List;

public interface ItemDAO {
    public void saveItem(Item it) throws PersistenceException;

    public Item loadItem(int id) throws PersistenceException;

    public List<Item> loadItems() throws PersistenceException;

}
