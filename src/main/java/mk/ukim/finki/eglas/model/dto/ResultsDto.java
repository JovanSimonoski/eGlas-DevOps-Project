package mk.ukim.finki.eglas.model.dto;

import lombok.Data;

@Data
public class ResultsDto {
    private Long id;
    private String participantName;
    private Long votesCount;
    private Double votesPercentage;
}
