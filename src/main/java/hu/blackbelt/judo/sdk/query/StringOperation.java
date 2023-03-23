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

public enum StringOperation implements Operation {
    LESS_THAN("<", false),
    LESS_OR_EQUAL_THAN("<=", false),
    GREATER_THAN(">", false),
    GREATER_OR_EQUAL_THAN(">=", false),
    EQUAL_TO("==", false),
    NOT_EQUAL_TO("!=", false),
    MATCHES("matches", true),
    LIKE("like", true),
    ILIKE("ilike", true);

    private String operator;
    private boolean function;

    StringOperation(String operator, boolean function) {
        this.operator = operator;
        this.function = function;
    }

    @Override
    public String getPattern() {
        if (function) {
            return "({0})!" + operator + "({1})";
        } else {
            return "({0} " + operator + " {1})";
        }
    }
}
