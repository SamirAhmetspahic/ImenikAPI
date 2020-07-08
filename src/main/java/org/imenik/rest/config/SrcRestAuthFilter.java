package org.imenik.rest.config;

import org.imenik.rest.models.Role;
import org.imenik.rest.models.User;
import org.imenik.rest.services.UserService;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.*;

@Provider
public class SrcRestAuthFilter implements ContainerRequestFilter {

    @Inject
    private UserService userService;
    @Context
    private ResourceInfo resourceInfo;

    private static final String USERNAME = "srcuser";
    private static final String PWD = "srcpwd";

    @Override
    public void filter(ContainerRequestContext requestContext) throws UnsupportedEncodingException {
        Method method = resourceInfo.getResourceMethod();
        //Access allowed for all
        if (!method.isAnnotationPresent(PermitAll.class)) {
            //Access denied for all
            if (method.isAnnotationPresent(DenyAll.class)) {
                requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                        .entity("Access blocked for all users !!").build());
                return;
            }

            //Get request headers
            final MultivaluedMap<String, String> headers = requestContext.getHeaders();

            //Fetch authorization header
            final List<String> authorization = headers.get(HttpHeaders.AUTHORIZATION);

            //If no authorization information present; block access
            if (authorization == null || authorization.isEmpty()) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                        .entity("You cannot access this resource").build());
                return;
            }

            //Get encoded username and password
            final String encodedUserPassword = authorization.get(0).replaceFirst("Basic" + " ", "");

            //Decode username and password
            byte[] decodedBytes = org.apache.commons.codec.binary.Base64.decodeBase64(encodedUserPassword);
            String usernameAndPassword = new String(decodedBytes);

            final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
            final String username = tokenizer.nextToken();
            final String password = tokenizer.nextToken();

            if (method.isAnnotationPresent(RolesAllowed.class)) {
                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));

                if (!isUserAllowed(userService.getUser(username), rolesSet)) {
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                            .entity("You cannot access this resource").build());
                }
            }
        }
    }

    /**
     * Check
     *
     * @param user
     * @param rolesSet
     * @return {@link Boolean}
     */
    private boolean isUserAllowed(User user, final Set<String> rolesSet) {
        boolean isAllowed = false;

        if (user.getUsername().equals(user.getUsername()) && user.getPassword().equals(user.getPassword())) {
            Role userRole = user.getRole();
            if (rolesSet.contains(userRole)) {
                isAllowed = true;
            }
        }
        return isAllowed;
    }
}

