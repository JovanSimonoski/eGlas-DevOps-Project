package mk.ukim.finki.eglas.records;

import mk.ukim.finki.eglas.model.Candidacy;

public record TotalCandidacyResults (Candidacy candidacy, Long voteCount, Double percent) {}
