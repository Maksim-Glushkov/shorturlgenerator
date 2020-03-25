package com.shorturlgenerator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepo extends JpaRepository<UrlModel, Long> {

    UrlModel findFirstByShortUrl(String shortUrl);

    void deleteById(int id);

    boolean existsByShortUrl(String shortUrl);

}
