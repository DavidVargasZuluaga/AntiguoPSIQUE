/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fachada;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import modelo.Psicologo;

/**
 *
 * @author DavidBrootal
 */
@Stateless
public class PsicologoFacade extends AbstractFacade<Psicologo> {
    @PersistenceContext(unitName = "psiquePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PsicologoFacade() {
        super(Psicologo.class);
    }
    
}
