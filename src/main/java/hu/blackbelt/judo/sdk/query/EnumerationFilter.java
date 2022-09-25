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

import hu.blackbelt.judo.sdk.Enumeration;

public class EnumerationFilter implements Filter {

    private EnumerationOperation operation;
    private Enumeration value;

    private EnumerationFilter(EnumerationOperation operation, Enumeration value) {
        this.operation = operation;
        this.value = value;
    }

    public static EnumerationFilter equalTo(Enumeration value) {
        return new EnumerationFilter(EnumerationOperation.EQUAL_TO, value);
    }

    public static EnumerationFilter notEqualTo(Enumeration value) {
        return new EnumerationFilter(EnumerationOperation.NOT_EQUAL_TO, value);
    }

    @Override
    public EnumerationOperation getOperation() {
        return operation;
    }

    @Override
    public String getValueAsString() {
        return value.getFqName();
    }
}
