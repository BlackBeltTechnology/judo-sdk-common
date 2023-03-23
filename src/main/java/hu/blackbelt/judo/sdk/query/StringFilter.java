package hu.blackbelt.judo.sdk.query;

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

public class StringFilter implements Filter {

    private StringOperation operation;
    private String value;

    private StringFilter(StringOperation operation, String value) {
        this.operation = operation;
        this.value = value;
    }

    public static StringFilter lessThan(String value) {
        return new StringFilter(StringOperation.LESS_THAN, value);
    }

    public static StringFilter lessOrEqualThan(String value) {
        return new StringFilter(StringOperation.LESS_OR_EQUAL_THAN, value);
    }

    public static StringFilter greaterOrEqualThan(String value) {
        return new StringFilter(StringOperation.GREATER_OR_EQUAL_THAN, value);
    }

    public static StringFilter greaterThan(String value) {
        return new StringFilter(StringOperation.GREATER_THAN, value);
    }

    public static StringFilter equalTo(String value) {
        return new StringFilter(StringOperation.EQUAL_TO, value);
    }

    public static StringFilter notEqualTo(String value) {
        return new StringFilter(StringOperation.NOT_EQUAL_TO, value);
    }

    public static StringFilter matches(String value) {
        return new StringFilter(StringOperation.MATCHES, value);
    }

    public static StringFilter like(String value) {
        return new StringFilter(StringOperation.LIKE, value);
    }

    public static StringFilter ilike(String value) {
        return new StringFilter(StringOperation.ILIKE, value);
    }

    @Override
    public StringOperation getOperation() {
        return operation;
    }

    @Override
    public String getValueAsString() {
        return '"' + value.replace("\"", "\\\"") + '"';
    }
}
