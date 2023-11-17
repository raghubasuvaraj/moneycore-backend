package com.moneycore.repository;
import com.moneycore.entity.ReferenceCode;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReferenceCodeRepository extends JpaRepository<ReferenceCode, String> {

    ReferenceCode findByReferencecodeId(String refrenceCode);
}

