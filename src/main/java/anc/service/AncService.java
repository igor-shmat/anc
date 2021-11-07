package anc.service;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class AncService {
    @Autowired
    private ResizeUrlService resizeUrlService;

    public String convert(String old) throws IOException {
        String check = checkUrl(old);
        String http;
        if (check.isEmpty()) {
            http = resizeUrlService.generateUrl();
            addRule(http + "#" + old);
        } else {
            http = check;
        }
        return http;
    }

    public void addRule(String rule) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("rule.txt", true));
        bufferedWriter.newLine();
        bufferedWriter.append(rule);
        bufferedWriter.close();
    }

    public String findUrlByShortUrl(String url) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("rule.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            // получили строку
            line = reader.readLine();
            // проверили не пустая ли она
            if (!Strings.isNullOrEmpty(line)) {
                String split[] = line.split("#");
                if (split.length > 0 && split[0].equals(url)) {
                    return split[1];
                }
            }
        }
        return "";
    }

    public String checkUrl(String url) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("rule.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            // проверили не пустая ли она
            if (!Strings.isNullOrEmpty(line)) {
                String split[] = line.split("#");
                if (split[1].equals(url)) {
                    return split[0];
                }
                 line = reader.readLine();
            }
        }
        return "";
    }
}
