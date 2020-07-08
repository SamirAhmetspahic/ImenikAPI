package org.imenik.rest.services;

import org.imenik.rest.models.Contact;

import java.util.List;

public interface ContactService {
    Contact persistContact(Contact contact);

    Contact getContact(long id);

    List<Contact> getContactList();

    Contact updateContact(Contact contact);

    void deleteContact(long id);
}
