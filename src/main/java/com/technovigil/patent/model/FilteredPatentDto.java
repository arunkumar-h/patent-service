package com.technovigil.patent.model;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FilteredPatentDto {

    private String inventionSubjectMatterCategory;

    private String patentApplicationNumber;

    private String inventionTitle;

    private List<String> claimText;

    private List<String> abstractText;

    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDate filingDate;

    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDate publicationDate;

    private String assigneeEntityName;

    private List<String> furtherCPCSymbolArrayText;

    private String mainCPCSymbolText;

    private String assigneePostalAddressText;

    private String filelocationURI;

    private String archiveURI;

    private List<String> descriptionText;

    private String publicationDocumentIdentifier;

}
