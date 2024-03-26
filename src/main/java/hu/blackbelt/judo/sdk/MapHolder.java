package hu.blackbelt.judo.sdk;

public interface MapHolder {

    <T> T adaptTo(Class<T> target);

    java.util.Map<String, Object> toMap();

}
