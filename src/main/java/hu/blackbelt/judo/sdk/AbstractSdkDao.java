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

import java.util.UUID;

import hu.blackbelt.judo.dao.api.PayloadValidator;
import org.eclipse.emf.ecore.EClass;

import hu.blackbelt.judo.dao.api.DAO;

import hu.blackbelt.judo.meta.asm.runtime.AsmModel;
import hu.blackbelt.judo.meta.asm.runtime.AsmUtils;

public abstract class AbstractSdkDao {

    protected DAO<UUID> dao;
    protected AsmModel asmModel;
    protected AsmUtils asmUtils;

    protected PayloadValidator payloadValidator;

    public void setDao(DAO<UUID> dao) {
        this.dao = dao;
    }

    public void setAsmModel(AsmModel asmModel) {
        this.asmModel = asmModel;
        this.asmUtils = new AsmUtils(asmModel.getResourceSet());
    }

    public void setPayloadValidator(PayloadValidator payloadValidator) {
        this.payloadValidator = payloadValidator;
    }

    protected EClass getEClass(String fqName) {
        EClass eClass = asmUtils.getClassByFQName(fqName).get();
        return eClass;
    }

}
