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

    public Registration() {
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
        private Account account;
        @ManyToOne
        private Session session;

        public Key() {
        }

        public Key(Account account, Session session) {
            this.account = account;
            this.session = session;
        }

        public Account getAccount() {
            return account;
        }

        public Session getSession() {
            return session;
        }

        public void setAccount(Account account) {
            this.account = account;
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
            return this.getAccount().getId() == other.getAccount().getId()
                    && this.getSession().getId() == other.getSession().getId();
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.getAccount().getId(), this.getSession().getId());
        }
    }
}