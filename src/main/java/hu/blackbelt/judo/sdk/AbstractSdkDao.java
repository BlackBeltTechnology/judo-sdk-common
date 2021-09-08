package hu.blackbelt.judo.sdk;

import java.util.UUID;
import org.eclipse.emf.ecore.EClass;

import hu.blackbelt.judo.dao.api.DAO;

import hu.blackbelt.judo.meta.asm.runtime.AsmModel;
import hu.blackbelt.judo.meta.asm.runtime.AsmUtils;

public abstract class AbstractSdkDao {

    protected DAO<UUID> dao;
    protected AsmModel asmModel;
    protected AsmUtils asmUtils;

    public void setDao(DAO<UUID> dao) {
        this.dao = dao;
    }

    public void setAsmModel(AsmModel asmModel) {
        this.asmModel = asmModel;
        this.asmUtils = new AsmUtils(asmModel.getResourceSet());
    }

    protected EClass getEClass(String fqName) {
        EClass eClass = asmUtils.getClassByFQName(fqName).get();
        return eClass;
    }

}
