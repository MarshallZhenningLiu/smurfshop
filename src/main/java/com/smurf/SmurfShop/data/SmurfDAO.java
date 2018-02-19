package com.smurf.SmurfShop.data;

import java.util.List;

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
	
	public Smurf getSmurf(int id ) {
        return em.find(Smurf.class, id);
    }
	
	public void save(Smurf smurf){
		em.persist(smurf);
	}
	
	public void update(Smurf smurf) {
		em.merge(smurf);
	}
	
	public void delete(int id) {
		em.remove(getSmurf(id));
	}
	public void deleteTable(){
		em.createQuery("DELETE FROM Smurf").executeUpdate();
	}
      
}

