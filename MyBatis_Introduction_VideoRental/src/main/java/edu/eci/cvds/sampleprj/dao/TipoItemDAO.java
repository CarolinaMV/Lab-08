package edu.eci.cvds.sampleprj.dao;

import edu.eci.cvds.samples.entities.TipoItem;
import org.apache.ibatis.exceptions.PersistenceException;

import java.util.List;

public interface TipoItemDAO {

    public void saveTipoItem(TipoItem c) throws PersistenceException;

    public TipoItem loadTipoItem(int id) throws PersistenceException;

    public List<TipoItem> loadTiposItems() throws PersistenceException;

}
