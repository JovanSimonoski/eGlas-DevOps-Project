package mk.ukim.finki.eglas.records;

import lombok.Data;

@Data
public class TotalCandidacyResultsPerMunicipality {
    public String candidacyId;
    public String citizenName;
    public String municipalityId;
    public String voteCount;
}
