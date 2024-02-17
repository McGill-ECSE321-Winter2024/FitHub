package ca.mcgill.ecse321.sportcenter.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Registration {
    @EmbeddedId
    private Key key;

    protected Registration() {
    }

    public Registration(Key key) {
        this.key = key;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    @Embeddable
    public static class Key implements Serializable {
        @ManyToOne
        private Customer customer;
        @ManyToOne
        private Session session;

        public Key() {
        }

        public Key(Customer customer, Session session) {
            this.customer = customer;
            this.session = session;
        }

        public Customer getCustomer() {
            return customer;
        }

        public Session getSession() {
            return session;
        }

        public void setCustomer(Customer customer) {
            this.customer = customer;
        }

        public void setSession(Session session) {
            this.session = session;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Key)) {
                return false;
            }
            Key other = (Key) obj;
            return this.getCustomer().getId() == other.getCustomer().getId()
                    && this.getSession().getId() == other.getSession().getId();
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.getCustomer().getId(), this.getSession().getId());
        }
    }
}