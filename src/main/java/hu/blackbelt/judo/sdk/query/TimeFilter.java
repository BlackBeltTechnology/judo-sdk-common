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

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class TimeFilter implements Filter {

    private NumericOperation operation;
    private LocalTime value;

    private TimeFilter(NumericOperation operation, LocalTime value) {
        this.operation = operation;
        this.value = value;
    }

    public static TimeFilter lessThan(LocalTime value) {
        return new TimeFilter(NumericOperation.LESS_THAN, value);
    }

    public static TimeFilter lessOrEqualThan(LocalTime value) {
        return new TimeFilter(NumericOperation.LESS_OR_EQUAL_THAN, value);
    }

    public static TimeFilter greaterOrEqualThan(LocalTime value) {
        return new TimeFilter(NumericOperation.GREATER_OR_EQUAL_THAN, value);
    }

    public static TimeFilter greaterThan(LocalTime value) {
        return new TimeFilter(NumericOperation.GREATER_THAN, value);
    }

    public static TimeFilter equalTo(LocalTime value) {
        return new TimeFilter(NumericOperation.EQUAL_TO, value);
    }

    public static TimeFilter notEqualTo(LocalTime value) {
        return new TimeFilter(NumericOperation.NOT_EQUAL_TO, value);
    }

    @Override
    public NumericOperation getOperation() {
        return operation;
    }

    @Override
    public String getValueAsString() {
        return "`" + value.format(DateTimeFormatter.ISO_LOCAL_TIME) + "`";
    }
}
