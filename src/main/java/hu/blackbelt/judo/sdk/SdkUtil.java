package hu.blackbelt.judo.sdk;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;

import hu.blackbelt.structured.map.proxy.MapHolder;
import hu.blackbelt.structured.map.proxy.MapProxy;

public class SdkUtil {

    private SdkUtil() {
    }

    public static <T> T fromPayload(Class<T> resultClass, Map<String, Object> payload) {
        return MapProxy.builder(resultClass).withMap(payload).withIdentifierField("__identifier").withEnumMappingMethod("getOrdinal").newInstance();
    }

    public static <T> Collection<Map<String, Object>> asMap(Collection<T> t) {
        return ((Collection<?>)t).stream().map(SdkUtil::asMap).collect(Collectors.toList());
    }

    public static <T> Map<String, Object> asMap(T t) {
        return ((MapHolder) t).toMap();
    }

    public static EReference getReference(EClass eClass, String referenceName) {
        EReference ref = eClass.getEAllReferences().stream().filter(r -> Objects.equals(r.getName(), referenceName))
                .findAny().get();
        return ref;
    }

    public static Map<String, Object> asMap(String k, Object v) {
        Map<String, Object> m = new HashMap<>();
        m.put(k,  v);
        return m;
    }

    public static Map<String, Object> asMap(String k1, Object v1, String k2, Object v2) {
        Map<String, Object> m = new HashMap<>();
        m.put(k1,  v1);
        m.put(k2,  v2);
        return m;
    }

    public static Map<String, Object> asMap(String k1, Object v1, String k2, Object v2, String k3, Object v3) {
        Map<String, Object> m = new HashMap<>();
        m.put(k1,  v1);
        m.put(k2,  v2);
        m.put(k3,  v3);
        return m;
    }

    public static Map<String, Object> asMap(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4) {
        Map<String, Object> m = new HashMap<>();
        m.put(k1,  v1);
        m.put(k2,  v2);
        m.put(k3,  v3);
        m.put(k4,  v4);
        return m;
    }

}
