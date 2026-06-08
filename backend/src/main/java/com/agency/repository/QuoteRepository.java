package com.agency.repository;

import com.agency.entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {
    List<Quote> findByClientId(Long clientId);
    List<Quote> findByStatus(Quote.QuoteStatus status);
    Quote findByQuoteNumber(String quoteNumber);
}
