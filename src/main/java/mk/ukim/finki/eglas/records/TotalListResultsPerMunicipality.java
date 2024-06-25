package mk.ukim.finki.eglas.records;

import lombok.Data;

@Data
public class TotalListResultsPerMunicipality {
    public String listName;
    public String partyName;
    public String municipalityId;
    public String voteCount;
}