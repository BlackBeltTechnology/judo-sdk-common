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

public class BooleanFilter implements Filter {

    private BooleanOperation operation;

    private BooleanFilter(BooleanOperation operation) {
        this.operation = operation;
    }

    public static BooleanFilter isTrue() {
        return new BooleanFilter(BooleanOperation.TRUE);
    }

    public static BooleanFilter isFalse() {
        return new BooleanFilter(BooleanOperation.FALSE);
    }

    @Override
    public BooleanOperation getOperation() {
        return operation;
    }

    @Override
    public String getValueAsString() {
        // not used by pattern
        return operation.toString();
    }
}
