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

    @Key(name = "__createdBy")
    Optional<String> getCreatedBy();

    @Key(name = "__createdById")
    Optional<String> getCreatedById();

    @Key(name = "__createTimestamp")
    Optional<LocalDateTime> getCreated();

    @Key(name = "__updatedBy")
    Optional<String> getUpdatedBy();

    @Key(name = "__updatedById")
    Optional<String> getUpdatedById();

    @Key(name = "__updateTimestamp")
    Optional<LocalDateTime> getUpdated();

    <T> T adaptTo(Class<T> target);

}
