package org.imenik.rest.services;

import org.imenik.rest.models.Contact;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Objects;

@Stateless
public class DefaultContactService implements ContactService {

    /**
     * Entity manager.
     */
    @PersistenceContext(unitName = "contact")
    private EntityManager entityManager;

    /**
     * Save new contact
     *
     * @param contact
     * @return {@link Contact}
     */
    @Override
    public Contact persistContact(Contact contact) {
        entityManager.persist(contact);
        Contact newContact = entityManager.createNamedQuery("ContactInfo.findByEmail", Contact.class)
                .setParameter("email", contact.getEmail())
                .getSingleResult();
        if (Objects.isNull(newContact)) {
            throw new PersistenceException();
        }
        return newContact;
    }

    /**
     * Retrieve contact by id.
     *
     * @param id
     * @return {@link Contact}
     */
    @Override
    public Contact getContact(long id) {
        //Contact contact = entityManager.find(Contact.class, id);
        Contact contact = entityManager.find(Contact.class, id);
        if (Objects.isNull(contact)) {
            throw new EntityNotFoundException(String.format("User with id = %s not found", id));
        }
        return contact;
    }

    /**
     * Get all contacts.
     *
     * @return {@link List<Contact>}
     */
    @Override
    public List<Contact> getContactList() {
        return entityManager.createNamedQuery("ContactInfo.findAll", Contact.class).getResultList();
    }

    /**
     * Update Contact  information.
     *
     * @param contact
     * @return {@link Contact}
     */
    @Override
    public Contact updateContact(Contact contact) {
        Contact c = entityManager.find(Contact.class, contact.getId());
        c.setName(contact.getName());
        c.setLastName(contact.getLastName());
        c.setEmail(contact.getEmail());
        c.setMobile(contact.getMobile());
        c.setPhone(contact.getPhone());
        c.setAddress(contact.getAddress());
        c.setDescription(contact.getDescription());
        Contact mergedContact = entityManager.merge(c);
        return mergedContact;
    }

    /**
     * Delete contact.
     *
     * @param id
     * @return contact
     */
    @Override
    public void deleteContact(long id) {
        Contact c = entityManager.createNamedQuery("ContactInfo.findById", Contact.class)
                .setParameter("id", id).getSingleResult();
        if (Objects.isNull(c)) {
            throw new EntityNotFoundException("Specified contact does not exist");
        }
        entityManager.remove(c);
    }
}
