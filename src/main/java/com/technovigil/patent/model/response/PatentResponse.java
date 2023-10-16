package com.technovigil.patent.model.response;

import com.technovigil.patent.model.FilteredPatentDto;
import com.technovigil.patent.model.Patent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author kumarhar
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatentResponse {

    private List<FilteredPatentDto> results;

    private String recordTotalQuantity;
}
