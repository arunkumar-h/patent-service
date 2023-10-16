package com.technovigil.patent.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.technovigil.patent.model.FilteredPatentDto;
import com.technovigil.patent.model.Patent;
import com.technovigil.patent.model.request.PatentRequest;
import com.technovigil.patent.model.response.PatentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.technovigil.patent.util.constants.PatentConstants.API_URL;

/**
 * @author kumarhar
 */

@Service
public class PatentServiceImpl implements PatentService {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    RestTemplate restTemplate;

    @Override
    public List<Patent> getFilteredPatents(PatentRequest request) throws JsonProcessingException {
        PatentResponse patentResponse;
        try {
            String apiUrl = getApiUrl(request);
            System.out.println("API URL = " + apiUrl);
            ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
            String responseBody = response.getBody();
            System.out.println(responseBody);

            // To support Java 8 date/time types
            mapper.registerModule(new JavaTimeModule());

            patentResponse = mapper.readValue(responseBody, PatentResponse.class);
            System.out.println("Patent Response = " + patentResponse);

            List<FilteredPatentDto> filteredPatentDtos = patentResponse.getResults();

            return createPatentsFromFilteredDtos(filteredPatentDtos);

        } catch (HttpStatusCodeException ex) {
            // Handle specific HTTP status code exceptions
            throw new RuntimeException("HTTP Status Code Exception: " + ex.getRawStatusCode());
        } catch (RestClientException ex) {
            // Handle general RestClientExceptions
            throw new RuntimeException("RestClientException: " + ex.getMessage());
        }
    }

    private String getApiUrl(PatentRequest request) {
        StringBuilder apiUrlBuilder = new StringBuilder(API_URL);

        //appendQueryParam(apiUrlBuilder, "patentApplicationNumber", request.getPatentApplicationNumber());
        appendQueryParam(apiUrlBuilder, "inventionTitle", request.getTitle());
        appendQueryParam(apiUrlBuilder, "claimText", request.getClaims());
        appendQueryParam(apiUrlBuilder, "abstractText", request.getPatentAbstract());
        appendQueryParam(apiUrlBuilder, "assigneeEntityName", request.getApplicantName());
        appendQueryParam(apiUrlBuilder, "mainCPCSymbolText", request.getIpcClassification());
        appendQueryParam(apiUrlBuilder, "publicationFromDate",
                request.getPublicationFromDate() != null ? request.getPublicationFromDate().toString() : null);
        appendQueryParam(apiUrlBuilder, "publicationToDate",
                request.getPublicationToDate() != null ? request.getPublicationToDate().toString() : null);


        return apiUrlBuilder.toString();
    }

    private void appendQueryParam(StringBuilder builder, String paramName, String paramValue) {
        if (notEmpty(paramValue)) {
            try {
                builder.append(builder.indexOf("?") == -1 ? "?" : "&")
                        .append(paramName)
                        .append("=")
                        .append(URLEncoder.encode(paramValue, StandardCharsets.UTF_8.toString()));
            } catch (UnsupportedEncodingException e) {
                // Handle the exception, e.g., log it or throw a runtime exception
                e.printStackTrace();
            }
        }
    }


    private boolean notEmpty(String value) {
        return value != null && !value.isEmpty();
    }


    private List<Patent> createPatentsFromFilteredDtos(List<FilteredPatentDto> filteredPatentDtos) {
        List<Patent> patents = new ArrayList<>();
        for (FilteredPatentDto filteredPatentDto : filteredPatentDtos) {
            List<String> ipcClassifications = filteredPatentDto.getFurtherCPCSymbolArrayText();
            if(ipcClassifications == null){
                ipcClassifications = new ArrayList<>();
            }
            ipcClassifications.add(filteredPatentDto.getMainCPCSymbolText());

            Patent patent = new Patent();
            patent.setIpcClassifications(ipcClassifications);
            patent.setAbstractText(filteredPatentDto.getAbstractText());
            patent.setClaims(filteredPatentDto.getClaimText());
            patent.setApplicantName(filteredPatentDto.getAssigneeEntityName());
            patent.setTitle(filteredPatentDto.getInventionTitle());
            patent.setFilingDate(filteredPatentDto.getFilingDate());
            patent.setPublicationDate(filteredPatentDto.getPublicationDate());
            patent.setPatentApplicationNumber(filteredPatentDto.getPatentApplicationNumber());
            patent.setPublicationNumber(filteredPatentDto.getPublicationDocumentIdentifier());
            patent.setPublicationURL("http://URLTOBEDEFINED");

            patents.add(patent);
        }
        return patents;
    }


}
