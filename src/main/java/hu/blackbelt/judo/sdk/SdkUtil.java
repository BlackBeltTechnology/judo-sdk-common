package hu.blackbelt.judo.sdk;

/*-
 * #%L
 * Judo SDK common
 * %%
 * Copyright (C) 2018 - 2022 BlackBelt Technology
 * %%
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License, v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is
 * available at https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 * #L%
 */

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
