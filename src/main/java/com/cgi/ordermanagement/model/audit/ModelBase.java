package com.cgi.ordermanagement.model.audit;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class ModelBase implements Serializable {
    private static final long serialVersionUID = 1L;

    public ModelBase() {
    }

    public abstract Long getId();

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(this.getClass().getCanonicalName()).append("[id=").append(this.getId()).append("]");
        return result.toString();
    }

    public boolean equals(Object object) {
        if (!this.getClass().isInstance(object)) {
            return false;
        } else {
            ModelBase other = (ModelBase) object;
            return this.getId() == other.getId() || this.getId() != null && this.getId().equals(other.getId());
        }
    }

    public int hashCode() {
        byte hash = 0;
        int hash1 = hash + (this.getId() != null ? this.getId().hashCode() : 0);
        return hash1;
    }
}