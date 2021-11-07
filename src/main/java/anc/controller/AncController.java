package anc.controller;

import anc.service.AncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class AncController {
    @Autowired
    private AncService ancService;

    @PostMapping(value = "/getUrl")
    public String resizeUrl(@RequestParam String oldUrl) throws IOException {
        return ancService.convert(oldUrl);
    }

    @GetMapping(path = "/redirect/{hash}")
    public ResponseEntity redirectShorter(@PathVariable String hash) throws IOException {
        String old = ancService.findUrlByShortUrl(hash);
        if (old != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", old);
            return new ResponseEntity<String>(headers, HttpStatus.FOUND);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
