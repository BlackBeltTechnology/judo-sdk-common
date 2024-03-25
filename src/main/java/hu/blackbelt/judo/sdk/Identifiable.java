package hu.blackbelt.judo.sdk;

import java.io.Serializable;

public interface Identifiable extends Serializable {

    Serializable getIdentifier();

    String getEntityType();

    Integer getVersion();

    <T> T adaptTo(Class<T> target);

    static boolean equals(Identifiable identifiable, Object o) {
        if (o == null) {
            return false;
        }
        if (Identifiable.class.isAssignableFrom(o.getClass())) {
            return identifiable.getIdentifier().equals(((Identifiable) o).getIdentifier());
        }
        return false;
    }
}
