package com.lydiaplullc.CarLeasing.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarBrandResponse {
    private Long id;
    private String name;
    private String country;
    private String foundedYear;
    // change BLOB to string, because http base on string
    private String logo;

    public CarBrandResponse(Long id, String name, String country, Integer foundedYear) {
        this.id = id;
        this.name = name;
        this.country = country != null ? country : "";
        this.foundedYear = foundedYear != null ? foundedYear.toString() : "";
    }

    public CarBrandResponse(Long id, String name, String country, Integer foundedYear, byte[] logo) {
        this(id, name, country, foundedYear);
        this.logo = logo != null ? Base64.encodeBase64String(logo) : null;
    }
}
