package com.smurf.SmurfShop.data;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.smurf.SmurfShop.model.Smurf;

@Stateless
@LocalBean
public class SmurfDAO {

    @PersistenceContext
    private EntityManager em;

    private final static Logger LOG = Logger.getLogger(SmurfDAO.class.getName());
	
    
	@SuppressWarnings("unchecked")
	public List<Smurf> getAllSmurf() {
    	Query query=em.createQuery("SELECT w FROM Smurf w");
        return query.getResultList();
    }
	
	@SuppressWarnings("unchecked")
	public List<Smurf> getSmurfByName(String name) {
    	Query query=em.createQuery("SELECT w FROM Smurf AS w "+
    								"WHERE w.name LIKE ?1");
    	query.setParameter(1, "%"+name.toUpperCase()+"%");
        return query.getResultList();
    }
	
	public List<Smurf> getSmurfMaxId() {
		Query query=em.createQuery("SELECT w FROM Smurf w ORDER BY ID DESC LIMIT 1");
        return query.getResultList();        
	}
	
	public Smurf getSmurf(int id ) {
        return em.find(Smurf.class, id);
    }
	
	public void save(Smurf smurf){
		LOG.info("-----------line 48: save..");
		
		em.persist(smurf);
	}
	
	public void update(Smurf smurf) {
		LOG.info("-----------line 54: upate..");
		em.merge(smurf);
	}
	
	public void delete(int id) {
		LOG.info("-----------line 59: delete..");
		em.remove(getSmurf(id));
	}
	public void deleteTable(){
		em.createQuery("DELETE FROM Smurf").executeUpdate();
	}
      
}

