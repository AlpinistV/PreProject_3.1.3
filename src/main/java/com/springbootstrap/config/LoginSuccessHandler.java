package com.springbootstrap.config;

import com.springbootstrap.model.Role;
import com.springbootstrap.model.User;
import com.springbootstrap.service.RoleService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final RoleService roleService;


    public LoginSuccessHandler(RoleService roleService ) {
        this.roleService = roleService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        Set<Role> set = user.getRoles();
        List<User> listUser = new ArrayList<>();
        listUser.add(user);
        httpServletRequest.getSession().setAttribute("userInf", listUser);
        httpServletRequest.getSession().setAttribute("userInfo", user);
        httpServletRequest.getSession().setAttribute("adminRole", new Role("ADMIN"));
        httpServletRequest.getSession().setAttribute("userRole",new Role("USER"));
        Role role = roleService.getRoleByName("ADMIN");
        if (set.contains(role)){
            httpServletResponse.sendRedirect("/admin/select");
        } else if(user.getRoles().contains(roleService.getRoleByName("USER")) && !user.getRoles().contains(roleService.getRoleByName("ADMIN"))){
            httpServletResponse.sendRedirect("/user");
        }

    }
}