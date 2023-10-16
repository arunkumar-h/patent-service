package com.technovigil.patent.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Patent {

    private String patentApplicationNumber;

    private String publicationNumber;

    private String title;

    private List<String> abstractText;

    private List<String> claims;

    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDate filingDate;

    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDate publicationDate;

    private String applicantName;

    private List<String> ipcClassifications;

    private String publicationURL;
}
