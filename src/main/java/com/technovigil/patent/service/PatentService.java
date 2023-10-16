package com.technovigil.patent.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.technovigil.patent.model.Patent;
import com.technovigil.patent.model.request.PatentRequest;

import java.util.List;

/**
 * @author kumarhar
 */

public interface PatentService {

    List<Patent> getFilteredPatents(PatentRequest patentRequest) throws JsonProcessingException;
}
