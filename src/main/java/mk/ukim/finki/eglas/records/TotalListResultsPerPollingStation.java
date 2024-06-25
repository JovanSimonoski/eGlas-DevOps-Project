package mk.ukim.finki.eglas.records;

import mk.ukim.finki.eglas.model.Candidacy;

public class TotalListResultsPerPollingStation {
    public String listName;
    public String partyName;
    public String pollingStationId;
    public String voteCount;

    public TotalListResultsPerPollingStation() {

    }

    public TotalListResultsPerPollingStation(String listName, String partyName, String pollingStationId, String voteCount) {
        this.listName = listName;
        this.partyName = partyName;
        this.pollingStationId = pollingStationId;
        this.voteCount = voteCount;
    }

    // Getters and Setters (or use Lombok @Data annotation)
    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPollingStationId() {
        return pollingStationId;
    }

    public void setPollingStationId(String pollingStationId) {
        this.pollingStationId = pollingStationId;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }
}
