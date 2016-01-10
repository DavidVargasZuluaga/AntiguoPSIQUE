/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fachada;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modelo.*;

/**
 *
 * @author DavidBrootal
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {
    @PersistenceContext(unitName = "psiquePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    
    public Usuario autenticar(Usuario us){
        Usuario usuario = null;
        String consulta;
        try {
            consulta="FROM Usuario u WHERE u.idUsuario = ?1 and u.clave = ?2";
            Query q = em.createQuery(consulta);
            q.setParameter(1,us.getIdUsuario());
            q.setParameter(2,us.getClave());
            List<Usuario> lista = q.getResultList();
            if(!lista.isEmpty()){
                usuario = lista.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }
    
}
