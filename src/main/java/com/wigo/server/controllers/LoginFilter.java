package com.wigo.server.controllers;

import com.wigo.server.WigoEndpoints;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.EnumSet;
import java.util.UUID;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@Component
public class LoginFilter implements Filter {
    private static final EnumSet<RequestMethod> MUTATING_METHODS = EnumSet.of(PUT, POST, PATCH, DELETE);
    private final JwtLogic jwtLogic;

    @Autowired
    public LoginFilter(JwtLogic jwtLogic) {
        this.jwtLogic = jwtLogic;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest hreq = (HttpServletRequest) req;
        HttpServletResponse hres = (HttpServletResponse) res;
        RequestMethod meth = RequestMethod.valueOf(hreq.getMethod());
        String path = hreq.getServletPath();
        if (MUTATING_METHODS.contains(meth) &&
                !path.equals(WigoEndpoints.API_URL + WigoEndpoints.LOGIN) && !path.equals("/error")) {
            try {
                String authHeader = hreq.getHeader("Authorization");
                if (authHeader == null)
                    throw new JwtException("No authorization header");
                UUID userId = jwtLogic.parseJwtToken(authHeader.split(" ")[1]);
                req.setAttribute("userId", userId);
            } catch (JwtException e) {
                hres.setStatus(SC_UNAUTHORIZED);
                return;
            }
        }
        chain.doFilter(req, res);
    }

    // need these empty methods due to java bug https://bugs.openjdk.java.net/browse/JDK-8071693
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
