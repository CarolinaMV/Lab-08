package edu.eci.cvds.sampleprj.dao.mybatis;

import com.google.inject.Inject;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.TipoItemMapper;
import edu.eci.cvds.sampleprj.dao.TipoItemDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.samples.entities.TipoItem;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;

import java.util.List;

public class MyBATISTipoItemDAO implements TipoItemDAO {

    @Inject
    private TipoItemMapper tipoItemMapper;

    @Override
    public void saveTipoItem(String des) throws PersistenceException {
        try{
            tipoItemMapper.addTipoItem(des);
        }
        catch(Exception e){
            throw new PersistenceException(ExcepcionServiciosAlquiler.ERROR_REGISTRAR_TIPOITEM);
        }
    }

    @Override
    public TipoItem loadTipoItem(int id) throws PersistenceException {
        try{
            return tipoItemMapper.getTipoItem(id);
        }
        catch(Exception e){
            throw new PersistenceException(ExcepcionServiciosAlquiler.ERROR_CONSULTAR_TIPOITEM);
        }
    }

    @Override
    public List<TipoItem> loadTiposItems() throws PersistenceException {
        try{
            return tipoItemMapper.getTiposItems();
        }
        catch(Exception e){
            throw new PersistenceException(ExcepcionServiciosAlquiler.ERROR_CONSULTAR_TIPOSITEMS);
        }
    }
}
