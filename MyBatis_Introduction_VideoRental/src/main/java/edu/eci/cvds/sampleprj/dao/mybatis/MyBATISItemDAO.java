package edu.eci.cvds.sampleprj.dao.mybatis;

import com.google.inject.Inject;
import edu.eci.cvds.sampleprj.dao.ItemDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ItemMapper;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;

import java.util.List;

public class MyBATISItemDAO implements ItemDAO{

    @Inject
    private ItemMapper itemMapper;

    @Override
    public void saveItem(Item it) throws PersistenceException{
        try{
            itemMapper.insertarItem(it);
        }
        catch(Exception e){
            throw new PersistenceException(ExcepcionServiciosAlquiler.ERROR_REGISTRAR_ITEM);
        }

    }

    @Override
    public Item loadItem(int id) throws PersistenceException {
        try{
            return itemMapper.consultarItem(id);
        }
        catch(Exception e){
            throw new PersistenceException(ExcepcionServiciosAlquiler.ERROR_CONSULTAR_ITEM);
        }

    }

    @Override
    public List<Item> loadItems() throws PersistenceException {
        try{
            return itemMapper.consultarItems();
        }
        catch(Exception e){
            throw new PersistenceException(ExcepcionServiciosAlquiler.ERROR_CONSULTAR_ITEMS);
        }
    }

}