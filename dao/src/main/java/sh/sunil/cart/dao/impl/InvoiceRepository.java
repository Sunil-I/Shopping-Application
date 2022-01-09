package sh.sunil.cart.dao.impl;


import sh.sunil.cart.model.dto.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    @Override
    List<Invoice> findAllById(Iterable<Long> iterable);

    @Override
    Optional<Invoice> findById(Long aLong);

    @Override
    List<Invoice> findAll();
}