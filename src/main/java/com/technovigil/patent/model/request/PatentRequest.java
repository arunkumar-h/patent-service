package com.technovigil.patent.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * @author kumarhar
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatentRequest {

    private String title;

    private String claims;

    private String patentApplicationNumber;

    @JsonProperty("abstract")
    private String patentAbstract;

    private LocalDate filingDate;

    private LocalDate publicationFromDate;

    private LocalDate publicationToDate;

    private String applicantName;

    private String ipcClassification;
}
