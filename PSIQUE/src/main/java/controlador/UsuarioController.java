package controlador;

import modelo.*;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import fachada.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("usuarioController")
@SessionScoped
public class UsuarioController implements Serializable {

    @EJB
    private UsuarioFacade ejbFacade;
    private List<Usuario> items = null;
    private Usuario selected;
    private Usuario usuario = null;
    private List<Usuario> listaAprendices = null;

    @PostConstruct
    public void init() {
        selected = new Usuario();
        usuario = new Usuario();
    }

    public String autenticar() {
        Usuario us;
        String res = null;
        try {
            us = ejbFacade.autenticar(usuario);
            this.usuario = us;
            if (us != null) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", us);
                if (us.getIdRol().getIdRol() == 1) {
                    res = "modUsuario/principalAdmon.xhtml";
                } else {
                    res = "modUsuario/principalUsuario.xhtml";
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Credenciales incorrectos", "Intentelo de nuevo"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Alerta", "Error de validacion"));
        }
        return res;
    }

    public void verificarSesion() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Usuario usua;
            usua = (Usuario) context.getExternalContext().getSessionMap().get("usuario");
            if (usua == null) {
                context.getExternalContext().redirect("./../permisos.xhtml");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Usuario> getListaAprendices() {
        if(listaAprendices==null){
            listaAprendices = ejbFacade.listaAprendices();
        }
        return listaAprendices;
    }

    public Usuario getUsuario() {
        if (usuario == null) {
            usuario = new Usuario();
        }
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getSelected() {
        return selected;
    }

    public void setSelected(Usuario selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private UsuarioFacade getFacade() {
        return ejbFacade;
    }

    public Usuario prepareCreate() {
        selected = new Usuario();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("UsuarioCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("UsuarioUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("UsuarioDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Usuario> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Usuario getUsuario(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Usuario> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Usuario> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Usuario.class)
    public static class UsuarioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UsuarioController controller = (UsuarioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usuarioController");
            return controller.getUsuario(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Usuario) {
                Usuario o = (Usuario) object;
                return getStringKey(o.getIdUsuario());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Usuario.class.getName()});
                return null;
            }
        }

    }

}
