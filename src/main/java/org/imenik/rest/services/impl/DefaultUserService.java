package org.imenik.rest.services.impl;

import org.imenik.rest.models.Role;
import org.imenik.rest.models.User;
import org.imenik.rest.services.UserService;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class DefaultUserService implements UserService {

    /**
     * Entity manager.
     */
    @PersistenceContext(unitName = "contact")
    private EntityManager entityManager;

    /**
     * Find user by id.
     *
     * @param username
     * @return {@link User}
     */
    @Override
    public User getUser(String username) {
        User user = entityManager.createNamedQuery("User.findByUsername", User.class).setParameter("username", username).getSingleResult();
        return user;
    }

    /**
     * Create simple user.
     *
     */
    @PostConstruct
    public void insertTestUser() {
        User srcUser = new User();
        srcUser.setUsername("srcuser");
        srcUser.setPassword("srcpwd");
        srcUser.setRole(Role.ADMIN);
        entityManager.persist(srcUser);
    }
}
