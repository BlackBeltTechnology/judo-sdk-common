package hu.blackbelt.judo.sdk;

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
