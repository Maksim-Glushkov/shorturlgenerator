package com.shorturlgenerator;

import com.google.common.hash.Hashing;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.RedirectView;

import java.nio.charset.StandardCharsets;

@Service
public class UrlService {
    private final UrlRepo urlRepo;

    public UrlService(UrlRepo urlRepo) {
        this.urlRepo = urlRepo;
    }

    String getShortUrl(String shortUrl) {
        UrlModel urlModel = urlRepo.findFirstByShortUrl(shortUrl);
        return urlModel.getLongUrl();
    }

    RedirectView localRedirect(String shortUrl) {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(urlRepo.findFirstByShortUrl(shortUrl).getLongUrl());
        return redirectView;
    }

    String createUrl(String longUrl) {
        UrlValidator urlValidator = new UrlValidator(
                new String[]{"http", "https"});
        if (urlValidator.isValid(longUrl)) {
            String shortUrl = Hashing.murmur3_32().hashString(longUrl, StandardCharsets.UTF_8).toString();
            UrlModel urlModel = new UrlModel(shortUrl, longUrl);
            urlRepo.save(urlModel);
            return "http://localhost:8080/api/"+shortUrl;
        }
        throw new RuntimeException("Invalid URL");
    }

    @Transactional
    String checkTimer(String shortUrl) {
        long current = System.currentTimeMillis();
        int MILLIS_IN_MIN = 600000;
        if (urlRepo.existsByShortUrl(shortUrl)) {
            if (urlRepo.findFirstByShortUrl(shortUrl).getTimer() > current - MILLIS_IN_MIN) {
                return urlRepo.findFirstByShortUrl(shortUrl).getLongUrl();

            } else
                urlRepo.deleteById(urlRepo.findFirstByShortUrl(shortUrl).getId());
            return "your short URL is dead";
        } else return "This short URL has not been created";
    }
}
