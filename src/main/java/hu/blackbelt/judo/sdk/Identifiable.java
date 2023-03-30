package hu.blackbelt.judo.sdk;

import hu.blackbelt.structured.map.proxy.annotation.Key;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

public interface Identifiable extends Serializable {

    @Key(name = "__identifier")
    Serializable getIdentifier();

    @Key(name = "__entityType")
    String getEntityType();

    @Key(name = "__version")
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
