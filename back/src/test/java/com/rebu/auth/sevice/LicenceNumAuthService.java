package com.rebu.auth.sevice;

import com.rebu.auth.dto.LicenseNumAuthResult;
import com.rebu.auth.exception.LicenceNumInvalidException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class LicenceNumAuthService {

    public LicenseNumAuthResult verifyLicenceNum(String licenceNum) {
        String URL = "https://bizno.net/article/" + licenceNum;
        Document doc = null;

        try {
            doc = Jsoup.connect(URL).get();
            Elements element = doc.select("body > section.post-area.section-gap > div > div > div.col-lg-8.post-list > div.single-post.d-flex.flex-row > div");

            String shopName = element.get(0).select(".titles h1").text();

            Element phoneElement = element.select("tr:has(th:contains(전화번호)) td").first();
            String phone = phoneElement.text();

            Element addressElement = element.select("tr:has(th:contains(회사주소)) td").first();
            String addressHtml = addressElement.html();
            String address = extractFirstAddress(addressHtml);

            if (shopName.isEmpty()) {
                throw new LicenceNumInvalidException();
            }

            return LicenseNumAuthResult.builder()
                    .shopName(shopName)
                    .phone(phone)
                    .address(address)
                    .build();

        } catch (IOException e) {
            throw new LicenceNumInvalidException();
        }
    }

    private String extractFirstAddress(String addressHtml) {
        Document tempDoc = Jsoup.parseBodyFragment(addressHtml);
        Element firstAddressElement = tempDoc.select("body").first();

        String[] lines = firstAddressElement.html().split("<br\\s*/?>");
        return Jsoup.parse(lines[0]).text();
    }

}
