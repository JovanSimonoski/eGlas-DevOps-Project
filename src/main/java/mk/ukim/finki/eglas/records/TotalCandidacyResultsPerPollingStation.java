package mk.ukim.finki.eglas.records;

import lombok.Data;

@Data
public class TotalCandidacyResultsPerPollingStation {
    public String candidacyId;
    public String citizenName;
    public String pollingStationId;
    public String voteCount;
}
