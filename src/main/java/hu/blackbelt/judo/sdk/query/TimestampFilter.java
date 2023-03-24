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

import java.time.*;
import java.time.format.DateTimeFormatter;

public class TimestampFilter implements Filter {

    private final NumericOperation operation;
    private final LocalDateTime value;

    private TimestampFilter(NumericOperation operation, LocalDateTime value) {
        this.operation = operation;
        this.value = value;
    }

    public static TimestampFilter lessThan(LocalDateTime value) {
        return new TimestampFilter(NumericOperation.LESS_THAN, value);
    }

    public static TimestampFilter lessThan(OffsetDateTime value) {
        return new TimestampFilter(NumericOperation.LESS_THAN, value.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
    }

    public static TimestampFilter lessOrEqualThan(LocalDateTime value) {
        return new TimestampFilter(NumericOperation.LESS_OR_EQUAL_THAN, value);
    }

    public static TimestampFilter lessOrEqualThan(OffsetDateTime value) {
        return new TimestampFilter(NumericOperation.LESS_OR_EQUAL_THAN, value.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
    }

    public static TimestampFilter greaterOrEqualThan(LocalDateTime value) {
        return new TimestampFilter(NumericOperation.GREATER_OR_EQUAL_THAN, value);
    }

    public static TimestampFilter greaterOrEqualThan(OffsetDateTime value) {
        return new TimestampFilter(NumericOperation.GREATER_OR_EQUAL_THAN, value.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
    }

    public static TimestampFilter greaterThan(LocalDateTime value) {
        return new TimestampFilter(NumericOperation.GREATER_THAN, value);
    }

    public static TimestampFilter greaterThan(OffsetDateTime value) {
        return new TimestampFilter(NumericOperation.GREATER_THAN, value.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
    }

    public static TimestampFilter equalTo(LocalDateTime value) {
        return new TimestampFilter(NumericOperation.EQUAL_TO, value);
    }

    public static TimestampFilter equalTo(OffsetDateTime value) {
        return new TimestampFilter(NumericOperation.EQUAL_TO, value.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
    }

    public static TimestampFilter notEqualTo(LocalDateTime value) {
        return new TimestampFilter(NumericOperation.NOT_EQUAL_TO, value);
    }

    public static TimestampFilter notEqualTo(OffsetDateTime value) {
        return new TimestampFilter(NumericOperation.NOT_EQUAL_TO, value.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
    }

    @Override
    public NumericOperation getOperation() {
        return operation;
    }

    @Override
    public String getValueAsString() {
        return "`" + value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "`";
    }

}
