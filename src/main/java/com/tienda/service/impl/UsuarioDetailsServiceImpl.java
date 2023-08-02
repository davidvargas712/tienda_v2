
package com.tienda.service.impl;

import com.tienda.dao.UsuarioDao;
import com.tienda.domain.Rol;
import com.tienda.domain.Usuario;
import com.tienda.service.UsuarioDetailsService;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class UsuarioDetailsServiceImpl 
        implements UsuarioDetailsService, UserDetailsService{

    @Autowired
    private UsuarioDao usuarioDao;
    
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
       //se recupera el usuario que tiene el mismo username
       
       Usuario usuario =usuarioDao.findByUsername(username);
    
    //se verifica qe si se carga un usuario que exista
    
    if(usuario==null){
        throw new UsernameNotFoundException(username);
    }
    
    //si estamos aca, entonces si hay un usuairo con ese username
    
    //se recupera los roles del usuario... y se crean como roles
    
    var roles=new ArrayList<GrantedAuthority>();
    //se recorre la lista de roles de usuario
    
    for(Rol rol:usuario.getRoles()){
        roles.add(new SimpleGrantedAuthority(rol.getNombre()));
    }
    //se retorna el usuario con los roles...
    return new User(usuario.getUsername(),
                usuario.getPassword(),
                roles);   
}
    
}

