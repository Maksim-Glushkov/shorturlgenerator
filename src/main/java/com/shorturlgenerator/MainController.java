package com.shorturlgenerator;

import com.google.common.hash.Hashing;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


@RestController
@RequestMapping("api")
public class MainController {
    private final UrlService urlService;

    public MainController(UrlService urlService) {
        this.urlService = urlService;
    }

    /**
     * @param shortUrl короткая ссылка по которой идет поиск в базе
     * @return "длинную" строку, из котороый была
     * создана короткая, при этом проверяет "жива" ли ссылка.
     */
    @Transactional
    @GetMapping("getlong/{shortUrl}")
    public String getUrl(@PathVariable String shortUrl) {
        return urlService.checkTimer(shortUrl);

    }

    /**
     * @param shortUrl короткая ссылка по которой идет поиск в базе
     * @return объект класса RedirectView, который переводит
     * на "длинную" ссылку.
     */
    @RequestMapping("{shortUrl}")
    public RedirectView localRedirect(@PathVariable String shortUrl) {
        return urlService.localRedirect(shortUrl);
    }

    /**
     * @param longUrl строка из которой создается короткая ссылка
     *                должна начинаться с http/https
     * @return "укороченную" ссылку
     * @see Hashing
     */
    @PostMapping()
    public String create(@RequestBody String longUrl) {
        return urlService.createUrl(longUrl);

    }
}

