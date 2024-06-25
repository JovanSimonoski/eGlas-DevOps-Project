package mk.ukim.finki.eglas.controllers;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.eglas.model.*;
import mk.ukim.finki.eglas.model.dto.Candidate;
import mk.ukim.finki.eglas.records.TotalCandidacyResults;
import mk.ukim.finki.eglas.repository.CommitteeRepository;
import mk.ukim.finki.eglas.services.*;
import mk.ukim.finki.eglas.services.Impl.ResultsServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class HomeController {
    private final ElectoralUnitService electoralUnitService;
    private final MunicipalityService municipalityService;
    private final AddressService addressService;
    private final CitizenService citizenService;
    private final ElectionService electionService;
    private final DocumentsService documentsService;
    private final PartyService partyService;
    private final CandidateService candidateService;
    private final CommitteeMemberService committeeMemberService;
    private final CandidatesElectionRealizationService candidatesElectionRealizationService;
    private final CandidatesListElectionRealizationService candidatesListElectionRealizationService;
    private final ElectionRealizationService electionRealizationService;
    private final CandidacyService candidacyService;
    private final TurnoutService turnoutService;
    private final CandidacyVoteService candidacyVoteService;
    private final ObjectionService objectionService;
    private final VoteIdentificationCodeService voteIdentificationCodeService;
    private final VoteService voteService;
    private final VotingCodeService votingCodeService;
    private final CandidatesListService candidatesListService;
    private final CommitteeService committeeService;
    private final CoalitionService coalitionService;
    private final ResultsServiceImpl resultsServiceImpl;


    public HomeController(ElectoralUnitService electoralUnitService,
                          AddressService addressService,
                          TurnoutService turnoutService,
                          CandidacyVoteService candidacyVoteService,
                          MunicipalityService municipalityService,
                          CitizenService citizenService,
                          ElectionService electionService,
                          DocumentsService documentsService,
                          PartyService partyService,
                          CandidateService candidateService,
                          CommitteeMemberService committeeMemberService,
                          CandidatesElectionRealizationService candidatesElectionRealizationService,
                          CandidatesListElectionRealizationService candidatesListElectionRealizationService,
                          ElectionRealizationService electionRealizationService, CandidacyService candidacyService,
                          ObjectionService objectionService, VoteIdentificationCodeService voteIdentificationCodeService,
                          VoteService voteService, VotingCodeService votingCodeService, CandidatesListService candidatesListService, CommitteeService committeeService, CoalitionService coalitionService, ResultsServiceImpl resultsServiceImpl) {
        this.electoralUnitService = electoralUnitService;
        this.addressService = addressService;
        this.municipalityService = municipalityService;
        this.citizenService = citizenService;
        this.electionService = electionService;
        this.documentsService = documentsService;
        this.partyService = partyService;
        this.candidateService = candidateService;
        this.committeeMemberService = committeeMemberService;
        this.candidatesElectionRealizationService = candidatesElectionRealizationService;
        this.candidatesListElectionRealizationService = candidatesListElectionRealizationService;
        this.electionRealizationService = electionRealizationService;
        this.candidacyService = candidacyService;
        this.turnoutService = turnoutService;
        this.candidacyVoteService = candidacyVoteService;
        this.objectionService = objectionService;
        this.voteIdentificationCodeService = voteIdentificationCodeService;
        this.voteService = voteService;
        this.votingCodeService = votingCodeService;
        this.candidatesListService = candidatesListService;
        this.committeeService = committeeService;
        this.coalitionService = coalitionService;
        this.resultsServiceImpl = resultsServiceImpl;
    }

    @GetMapping("/")
    public String home(Model m) {
        m.addAttribute("replaceTemplate", "menu");
        return "index";
    }

    @GetMapping("/vote")
    public String showVote(@RequestParam(required = false) Boolean error,
                           Model m) {
        m.addAttribute("replaceTemplate", "vote_form");
        m.addAttribute("error", error);
        return "index";
    }

    @GetMapping("/admin/voteCode/{realizationId}/{citizenId}") // Realizacija ID
    public String generateVoteCode(@PathVariable Long citizenId,
                                   @PathVariable Long realizationId,
                                   Model m) {

        VotingCode votingCode = votingCodeService.generateCode(citizenId, realizationId);
        if (votingCode == null) {
            return "redirect:/error";
        }
        return "redirect:/admin/code/{realizationId}/{citizenId}";
    }

    @GetMapping("/admin/code/{realizationId}/{citizenId}")
    public String showVoteCode(@PathVariable Long citizenId,
                               @PathVariable Long realizationId,
                               Model m) {
        VotingCode votingCode = votingCodeService.findByCitizenIdAndRealizationId(citizenId, realizationId);
        m.addAttribute("code", votingCode);
        m.addAttribute("replaceTemplate", "voting_code");
        return "admin";
    }

    @GetMapping("/codeVote")
    public String showVoteWithCode(@RequestParam(required = false) Boolean error,
                                   Model m) {
        m.addAttribute("replaceTemplate", "vote_code");
        m.addAttribute("error", error);
        return "index";
    }

    //=======================================================

    @GetMapping("admin/committee")
    public String showCommittee(Model m) {
        m.addAttribute("realizations", electionRealizationService.findAll());
        m.addAttribute("committeeMembers", committeeMemberService.findAll());
        m.addAttribute("pollingStations", addressService.findAllPollingStations());
        m.addAttribute("committees", committeeService.findAll());
        m.addAttribute("committeeForm", new Committee());
        m.addAttribute("replaceTemplate", "add_comitee");
        return "admin";
    }

    @PostMapping("/admin/committee")
    public String addCommittee(
            @RequestParam(required = false) Long id,
            @RequestParam Long electionRealization,
            @RequestParam Long pollingStation,
            @RequestParam List<Long> membersId
    ) {
        committeeService.update(id, pollingStation, electionRealization, membersId);
        return "redirect:/admin/committee";
    }

    @GetMapping("/admin/committee/{id}/edit")
    public String editCommittee(Model m, @PathVariable Long id) {
        m.addAttribute("realizations", electionRealizationService.findAll());
        m.addAttribute("committeeMembers", committeeMemberService.findAll());
        m.addAttribute("pollingStations", addressService.findAllPollingStations());
        m.addAttribute("committees", committeeService.findAll());
        m.addAttribute("committeeForm", committeeService.findById(id));
        m.addAttribute("replaceTemplate", "add_comitee");
        return "admin";
    }

    //==================================

    @PostMapping("/vote")
    public String vote(
            Model m,
            @RequestParam String numberLicence,
            @RequestParam String embg,
            HttpServletRequest httpServletRequest) {
        Citizen citizen = citizenService.validateCitizen(embg, numberLicence);
        if (citizen != null) {
            httpServletRequest.getSession(true).setAttribute("citizenId", citizen.getId());
            m.addAttribute("candidacies", candidacyService.findAll());
            m.addAttribute("replaceTemplate", "candidacy_list");
            return "redirect:/vote/available";
        } else {
            m.addAttribute("replaceTemplate", "vote_form");
            return "redirect:/vote?error=true";
        }
    }

    @PostMapping("/codeVote")
    public String codete(
            Model m,
            @RequestParam UUID code,
            HttpServletRequest httpServletRequest) {
        Citizen citizen = votingCodeService.findCitizenByCode(code);
        if (citizen != null) {
            httpServletRequest.getSession(true).setAttribute("citizenId", citizen.getId());
            m.addAttribute("candidacies", candidacyService.findAll());
            m.addAttribute("replaceTemplate", "candidacy_list");
            return "redirect:/vote/available";
        } else {
            m.addAttribute("replaceTemplate", "vote_form");
            return "redirect:/vote?error=true";
        }
    }

    @GetMapping("/vote/available")
    public String getAvailableElections(Model m,
                                        HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession(false) == null) {
            return "redirect:/vote";
        }
        Long citizenId = (Long) httpServletRequest.getSession().getAttribute("citizenId");
        List<CandidatesElectionRealization> availableElections = candidatesElectionRealizationService.findAvailable(citizenId);
        m.addAttribute("realizations", availableElections);
        m.addAttribute("replaceTemplate", "elections_choice");
        return "index";
    }

    @PostMapping("/vote/submit")
    public String prepareBallot(@RequestParam Long realizationId,
                                HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession(false) == null) {
            return "redirect:/vote";
        }
        Long citizenId = (Long) httpServletRequest.getSession().getAttribute("citizenId");
        httpServletRequest.getSession().setAttribute("voteIdCode", voteIdentificationCodeService.findRandomIdentificationCode().getId());
//        Turnout vote = voteService.update(null, LocalDateTime.now(), citizenId, realizationId);
//        httpServletRequest.getSession().setAttribute("voteId", vote.getId());

        httpServletRequest.getSession().setAttribute("realizationId", realizationId);
        return "redirect:/vote/list";
    }


    @GetMapping("/vote/list")
    public String vote_list(Model m, HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession(false) == null) {
            return "redirect:/vote";
        }
        Long realizationId = (Long) httpServletRequest.getSession().getAttribute("realizationId");
        Long citizenId = (Long) httpServletRequest.getSession().getAttribute("citizenId");
        m.addAttribute("candidacies", candidacyService.findAllByElectionsRealiztion(realizationId, citizenId));
        m.addAttribute("replaceTemplate", "candidacy_list");
        return "index";
    }

    @GetMapping("/vote/candidate/{id}")
    public String vote_candidate(@PathVariable Long id,
                                 HttpServletRequest httpServletRequest) {
        UUID voteId = (UUID) httpServletRequest.getSession().getAttribute("voteIdCode");
        Long citizenId = (Long) httpServletRequest.getSession().getAttribute("citizenId");
        Long realizationId = (Long) httpServletRequest.getSession().getAttribute("realizationId");
        voteService.voteForCandidate(citizenId, voteId, realizationId, id);
        httpServletRequest.getSession().invalidate();
        return "redirect:/";
    }


    //Jovan
    //------------------------------------------------------------------------------------------------------------------
    @GetMapping("/admin/municipalities")
    public String municipalities(Model m) {
        m.addAttribute("municipalities", municipalityService.findAll());
        m.addAttribute("municipality", new Municipality());
        m.addAttribute("replaceTemplate", "municipalities");
        return "admin";
    }

    @GetMapping("/admin/municipalities/{id}/edit")
    public String municipalities(@PathVariable Long id,
                                 Model m) {
        m.addAttribute("municipalities", municipalityService.findAll());
        m.addAttribute("municipality", municipalityService.findById(id));
        m.addAttribute("replaceTemplate", "municipalities");
        return "admin";
    }

    @PostMapping("/admin/municipalities")
    public String createMunicipality(@RequestParam(required = false) Long id,
                                     @RequestParam String name) {
        municipalityService.update(id, name);
        return "redirect:/admin/municipalities";
    }

    //------------------------------------------------------------------------------------------------------------------
    //Jovan
    //------------------------------------------------------------------------------------------------------------------
    @GetMapping("/admin/elections")
    public String elections(Model m) {
        m.addAttribute("elections", electionService.findAll());
        m.addAttribute("election", new Election());
        m.addAttribute("replaceTemplate", "elections");
        return "admin";
    }

    @GetMapping("/admin/elections/{id}/edit")
    public String elections(@PathVariable Long id,
                            Model m) {
        m.addAttribute("elections", electionService.findAll());
        m.addAttribute("election", electionService.findById(id));
        m.addAttribute("replaceTemplate", "elections");
        return "admin";
    }

    @PostMapping("/admin/elections")
    public String createElection(@RequestParam(required = false) Long id,
                                 @RequestParam String name) {
        electionService.update(id, name);
        return "redirect:/admin/elections";
    }
    //------------------------------------------------------------------------------------------------------------------

    @GetMapping("/admin/units")
    public String electoralUnits(Model m) {
        m.addAttribute("units", electoralUnitService.findAll());
        m.addAttribute("unit", new ElectoralUnit());
        m.addAttribute("replaceTemplate", "electoral_units");
        return "admin";
    }

    @GetMapping("/admin/units/{id}/edit")
    public String electoralUnits(@PathVariable Long id,
                                 Model m) {
        m.addAttribute("units", electoralUnitService.findAll());
        m.addAttribute("unit", electoralUnitService.findById(id));
        m.addAttribute("replaceTemplate", "electoral_units");
        return "admin";
    }

    @PostMapping("/admin/units")
    public String createElectoralUnit(@RequestParam(required = false) Long id,
                                      @RequestParam String name,
                                      @RequestParam Integer numDeputies) {
        electoralUnitService.update(id, name, numDeputies);
        return "redirect:/admin/units";
    }
    //=============================================================

    @GetMapping("/admin/address")
    String showAddress(Model m) {
        m.addAttribute("addresses", addressService.findAllAddresses());
        m.addAttribute("address", new Address());
        m.addAttribute("municipalities", municipalityService.findAll());
        m.addAttribute("pollingStations", addressService.findAllPollingStations());
        m.addAttribute("replaceTemplate", "address");
        return "admin";
    }

    @PostMapping("/admin/address")
    String addAddress(@RequestParam(required = false) Long id,
                      @RequestParam String street,
                      @RequestParam Integer houseNumber,
                      @RequestParam Integer entranceNumber,
                      @RequestParam Integer apartmentNumber,
                      @RequestParam Long municipality,
                      @RequestParam Long pollingStation
    ) {
        addressService.updateAddress(id, street, houseNumber, entranceNumber, apartmentNumber, municipality, pollingStation);
        return "redirect:/admin/address";
    }

    @GetMapping("/admin/address/{id}/edit")
    public String editAddress(@PathVariable Long id,
                              Model m) {
        m.addAttribute("addresses", addressService.findAllAddresses());
        m.addAttribute("address", addressService.findAddressById(id));
        m.addAttribute("municipalities", municipalityService.findAll());
        m.addAttribute("pollingStations", addressService.findAllPollingStations());
        m.addAttribute("replaceTemplate", "address");
        return "admin";
    }

    //==========================================================

    @GetMapping("/admin/citizens")
    public String citizens(Model m) {
        m.addAttribute("replaceTemplate", "citizens");
        m.addAttribute("citizens", citizenService.findAll());
        m.addAttribute("citizen", new Citizen());
        m.addAttribute("streets", addressService.findStreetNames());
        m.addAttribute("municipalities", municipalityService.findAll());
        System.out.println(addressService.findStreetNames());
        return "admin";
    }

    @PostMapping("/admin/citizens")
    public String addCitizen(@RequestParam(required = false) Long id,
                             @RequestParam String name,
                             @RequestParam String surname,
                             @RequestParam LocalDate dateOfBirth,
                             @RequestParam String idNum,
                             @RequestParam String street,
                             @RequestParam(required = false) Integer houseNumber,
                             @RequestParam(required = false) Integer entranceNumber,
                             @RequestParam(required = false) Integer apartmentNumber,
                             @RequestParam Long municipality,
                             Model m) {
        citizenService.update(id, name, surname, dateOfBirth, idNum, street, houseNumber, entranceNumber, apartmentNumber, municipality);
        System.out.println(name + surname + street + dateOfBirth.toString() + idNum + houseNumber + entranceNumber + municipality);
        return "redirect:/admin/citizens";
    }

    @GetMapping("/admin/citizens/{id}/edit")
    public String editCitizen(@PathVariable Long id,
                              Model m) {
        m.addAttribute("replaceTemplate", "citizens");
        m.addAttribute("citizens", citizenService.findAll());
        m.addAttribute("citizen", citizenService.findById(id));
        m.addAttribute("streets", addressService.findStreetNames());
        m.addAttribute("municipalities", municipalityService.findAll());
        System.out.println(addressService.findStreetNames());
        return "admin";
    }

    @GetMapping("/admin/citizens/{id}/candidate")
    public String makeCandidate(@PathVariable Long id) {
        candidateService.update(id);
        return "redirect:/admin/citizens";
    }

    @GetMapping("/admin/citizens/{id}/committee")
    public String makeCommitteeMember(@PathVariable Long id) {
        committeeMemberService.update(id);
        return "redirect:/admin/citizens";
    }

    @PostMapping("/admin/documents")
    public String editCitizensDocument(@RequestParam Long citizenId,
                                       @RequestParam(required = false) Long documentId,
                                       @RequestParam String documentNumber,
                                       @RequestParam String issuer,
                                       @RequestParam LocalDate issueDate,
                                       @RequestParam LocalDate expiryDate) {
        documentsService.update(documentId, documentNumber, issueDate, expiryDate, issuer, citizenId);
        return "redirect:/admin/citizens";
    }

    //=======================================
    @GetMapping("/admin/citizens/{id}")
    public String citizensPollingStation(@PathVariable Long id,
                                         Model m) {
        m.addAttribute("replaceTemplate", "citizens_pollingStation");
        m.addAttribute("citizens", citizenService.findAllByAddress_PollingStation_Id(id));
        m.addAttribute("citizen", new Citizen());
        m.addAttribute("streets", addressService.findStreetNames());
        m.addAttribute("municipalities", municipalityService.findAll());
        System.out.println(addressService.findStreetNames());
        return "admin";
    }

    //=======================================
    @GetMapping("/admin/parties")
    public String parties(Model m) {
        m.addAttribute("replaceTemplate", "parties");
        m.addAttribute("parties", partyService.findAll());
        m.addAttribute("party", new Party());
        m.addAttribute("streets", addressService.findStreetNames());
        m.addAttribute("municipalities", municipalityService.findAll());
        return "admin";
    }

    @PostMapping("/admin/parties")
    public String addParty(@RequestParam(required = false) Long id,
                           @RequestParam String name,
                           @RequestParam String street,
                           @RequestParam(required = false) Integer houseNumber,
                           @RequestParam(required = false) Integer entranceNumber,
                           @RequestParam(required = false) Integer apartmentNumber,
                           @RequestParam Long municipality,
                           Model m) {
        partyService.update(id, name, street, houseNumber, entranceNumber, apartmentNumber, municipality);
        return "redirect:/admin/parties";
    }
    //======================================================================

    @GetMapping("/admin/stations")
    String showStations(Model m) {
        m.addAttribute("stations", addressService.findAllPollingStations());
        m.addAttribute("pollingStation", new PollingStation());
        m.addAttribute("streets", addressService.findStreetNames());
        m.addAttribute("electoralUnits", electoralUnitService.findAll());
        m.addAttribute("municipalities", municipalityService.findAll());
        m.addAttribute("replaceTemplate", "polling_station");
        return "admin";
    }

    @PostMapping("/admin/stations")
    String addStation(@RequestParam(required = false) Long id,
                      @RequestParam String name,
                      @RequestParam String street,
                      @RequestParam Integer houseNumber,
                      @RequestParam Integer entranceNumber,
                      @RequestParam Integer apartmentNumber,
                      @RequestParam Long electoralUnit,
                      @RequestParam Long municipality
    ) {
        addressService.updatePollingStation(id, name, street, houseNumber, entranceNumber, apartmentNumber, municipality, electoralUnit);
        return "redirect:/admin/stations";
    }

    @GetMapping("/admin/stations/{id}/edit")
    public String editStation(@PathVariable Long id,
                              Model m) {
        System.out.println(addressService.findPollingStationById(id));
        m.addAttribute("addresses", addressService.findAllAddresses());
        m.addAttribute("pollingStation", addressService.findPollingStationById(id));
        m.addAttribute("electoralUnits", electoralUnitService.findAll());
        m.addAttribute("stations", addressService.findAllPollingStations());
        m.addAttribute("streets", addressService.findStreetNames());
        m.addAttribute("municipalities", municipalityService.findAll());
        m.addAttribute("replaceTemplate", "polling_station");
        return "admin";
    }

    //=======================================================================

    @GetMapping("/admin/realizations")
    public String realizations(Model m) {
        m.addAttribute("electionRelaization", new ElectionRealization());
        m.addAttribute("candidacyRealizations", candidatesElectionRealizationService.findAll());
        m.addAttribute("candidateListRealizations", candidatesListElectionRealizationService.findAll());
        m.addAttribute("elections", electionService.findAll());
        m.addAttribute("replaceTemplate", "election_realizations");
        return "admin";
    }

    @PostMapping("/admin/realizations")
    public String addRealization(@RequestParam(required = false) Long id,
                                 @RequestParam String name,
                                 @RequestParam LocalDate date,
                                 @RequestParam Long election,
                                 @RequestParam(required = false) String candidacyElections,
                                 @RequestParam(required = false) String candidateListElections) {
        electionRealizationService.update(id, date, name, election, candidacyElections, candidateListElections);
        return "redirect:/admin/realizations";
    }

    @GetMapping("/admin/realizations/{id}/edit")
    public String editRealizations(@PathVariable Long id, Model m) {
        m.addAttribute("electionRelaization", electionRealizationService.findById(id));
        m.addAttribute("candidacyRealizations", candidatesElectionRealizationService.findAll());
        m.addAttribute("candidateListRealizations", candidatesListElectionRealizationService.findAll());
        m.addAttribute("elections", electionService.findAll());
        m.addAttribute("replaceTemplate", "election_realizations");
        return "admin";
    }

    //===============================================================================

    @GetMapping("/admin/participations")
    public String candidacies(Model m) {
        m.addAttribute("replaceTemplate", "elections_participation");
        m.addAttribute("electionsRealizations", candidatesElectionRealizationService.findAll());
        m.addAttribute("municipalities", municipalityService.findAll());
        m.addAttribute("parties", partyService.findAll());
        m.addAttribute("candidates", candidateService.findAll());
        m.addAttribute("candidacy", new Candidacy());
        m.addAttribute("candidacies", candidacyService.findAll());
        return "admin";
    }

    @PostMapping("/admin/participations")
    public String addCandidacy(@RequestParam(required = false) Long id,
                               @RequestParam String description,
                               @RequestParam Long candidate,
                               @RequestParam Long candidatesElectionRealization,
                               @RequestParam(required = false) Long municipality,
                               @RequestParam Long party,
                               Model m) {
        candidacyService.update(id, description, candidate, party, candidatesElectionRealization, municipality);
        return "redirect:/admin/participations";
    }

    //==================================================================================================================
    @GetMapping("/turnout")
    public String turnOutResults(Model m,
                                 @RequestParam(required = false, defaultValue = "2") Long realizationId,
                                 @RequestParam(required = false) String opshtinaId,
                                 @RequestParam(required = false) Long izbirachkoMestoId) {
        Double turnOut;
        if(opshtinaId != null && izbirachkoMestoId != null){
            turnOut = turnoutService.getTurnOutByRealizationAndMunicipalityAndPollingStation(realizationId, opshtinaId, izbirachkoMestoId);
        }else if(opshtinaId != null){
            turnOut = turnoutService.getTurnOutByRealizationAndMunicipality(realizationId, opshtinaId);
        }else {
            turnOut = turnoutService.turnOutByElectionRealization(realizationId);
        }
        m.addAttribute("replaceTemplate", "turnout_circle");
        m.addAttribute("turnoutPercentage", turnOut);
        m.addAttribute("realizationId", realizationId);
        m.addAttribute("municipalities", municipalityService.findAll());
        m.addAttribute("realizations", candidatesElectionRealizationService.findAll());
        m.addAttribute("pollingStations", addressService.findAllPollingStations());
        m.addAttribute("realization", candidatesElectionRealizationService.findById(realizationId));
        return "index";
    }

    @GetMapping("/results")
    public String totalResults(@RequestParam(required = false, defaultValue = "2") Long realizationId,
                               @RequestParam(required = false, defaultValue = "1") Long municipalityId,
                               @RequestParam(required = false) Long pollingStationId,
                               Model m) {
        List<TotalCandidacyResults> total = turnoutService.resultsByCandidateElectionsRealization(realizationId);
        m.addAttribute("candidacies", resultsServiceImpl.getResultsForRealizationBy(realizationId, municipalityId, pollingStationId));
//        m.addAttribute("turnoutPercentage", turnOut);
        m.addAttribute("realizationId", realizationId);
        m.addAttribute("municipalityId", municipalityId);
        m.addAttribute("pollingStationId", pollingStationId);
        m.addAttribute("municipalities", municipalityService.findAll());
        m.addAttribute("realizations", candidatesElectionRealizationService.findAll());
        m.addAttribute("pollingStations", addressService.findAllPollingStationsByMunicipalityId(municipalityId));
        m.addAttribute("realization", candidatesElectionRealizationService.findById(realizationId));
        m.addAttribute("replaceTemplate", "results");
            return "index";
    }

    //=============================================================


    @GetMapping("/admin/objections")
    public String objections(Model m) {
        List<Objection> objections = objectionService.findAll();
        m.addAttribute("objections", objections);
        m.addAttribute("realizations", electionRealizationService.findAll());
        m.addAttribute("pollingStations", addressService.findAllPollingStations());
        m.addAttribute("objection", new Objection());
        m.addAttribute("replaceTemplate", "objection");
        return "admin";
    }

    @PostMapping("/admin/objections")
    public String addObjection(@RequestParam(required = false) Long id,
                               @RequestParam String description,
                               @RequestParam Long realization,
                               @RequestParam Long pollingStation,
                               Model m) {
        objectionService.update(id, realization, pollingStation, description);
        return "redirect:/admin/objections";
    }

    @GetMapping("/admin/objections/{id}/accept")
    public String responseToObjectionAccept(@PathVariable Long id,
                                            Model m) {
        objectionService.accept(id);
        return "redirect:/admin/objections";
    }

    @GetMapping("/admin/objections/{id}/reject")
    public String responseToObjectionReject(@PathVariable Long id,
                                            Model m) {
        objectionService.reject(id);
        return "redirect:/admin/objections";
    }


    //=============================================
    @GetMapping("/admin/candidatesList")
    public String showCandidatesList(Model model) {
        model.addAttribute("replaceTemplate", "candidates_list");
        model.addAttribute("candidatesList", new CandidatesList());
        model.addAttribute("candidates", IntStream.range(0, 20).mapToObj(x -> new Candidate()).toArray());
        model.addAttribute("candidatesLists", candidatesListService.findAll());
        model.addAttribute("candidatesNames", candidateService.findAll());
        model.addAttribute("candidatesListElectionRealizations", candidatesListElectionRealizationService.findAll());
        model.addAttribute("parties", partyService.findAll());
        model.addAttribute("municipalities", municipalityService.findAll());
        model.addAttribute("electoralUnits", electoralUnitService.findAll());
        return "admin";
    }

    @PostMapping("/admin/candidatesList")
    public String createCandidatesList(@RequestParam(required = false) Long id,
                                       @RequestParam String description,
                                       @RequestParam Long party,
                                       @RequestParam Long candidatesListElectionRealization,
                                       @RequestParam Long municipality,
                                       @RequestParam Long electoralUnit,
                                       @RequestParam List<Long> candidatesInList) {
        candidatesListService.update(id, description, party, candidatesListElectionRealization, municipality, electoralUnit, candidatesInList);
        return "redirect:/admin/candidatesList";
    }

    @GetMapping("/admin/candidatesList/{id}")
    public String addCandidateToCandidatesList(Model model, @PathVariable Long id) {
        model.addAttribute("replaceTemplate", "candidates_list");
        model.addAttribute("candidatesList", candidatesListService.findById(id));
        model.addAttribute("candidatesLists", candidatesListService.findAll());
        model.addAttribute("candidatesNames", candidateService.findAll());
        model.addAttribute("candidates", candidatesListService.findById(id).getCandidates());
        model.addAttribute("candidatesListElectionRealizations", candidatesListElectionRealizationService.findAll());
        model.addAttribute("parties", partyService.findAll());
        model.addAttribute("municipalities", municipalityService.findAll());
        model.addAttribute("electoralUnits", electoralUnitService.findAll());
        return "admin";
    }

    //=============================================
    @GetMapping("/admin/coalition")
    public String showCoalition(Model m) {
        m.addAttribute("realizations", electionRealizationService.findAll());
        m.addAttribute("parties", partyService.findAll());
        m.addAttribute("coalitionForm", new Coalition());
        m.addAttribute("coalitions", coalitionService.findAll());
        m.addAttribute("replaceTemplate", "add_coalition");
        return "admin";
    }

    @PostMapping("/admin/coalition/add")
    public String addCoalition(
            @RequestParam(required = false) Long id,
            @RequestParam String name,
            @RequestParam String motto,
            @RequestParam Long electionRealization,
            @RequestParam List<Long> parties
    ) {
        coalitionService.update(id, name, motto, electionRealization, parties);
        return "redirect:/admin/coalition";
    }

    @GetMapping("/admin/coalition/{id}")
    public String editCoalition(Model m, @PathVariable Long id) {
        m.addAttribute("realizations", electionRealizationService.findAll());
        m.addAttribute("parties", partyService.findAll());
        m.addAttribute("coalitionForm", coalitionService.findById(id));
        m.addAttribute("coalitions", coalitionService.findAll());
        m.addAttribute("replaceTemplate", "add_coalition");
        return "index";
    }

    //=============================================
    @GetMapping("/admin/electionRealizationInterface")
    public String showElectionRealization(Model m, Principal p, Authentication authentication) {
        UserProfile userProfile = (UserProfile) authentication.getPrincipal();
//        m.addAttribute("realization", committeeService.findElectionRealizationByCitizen(userProfile.getCitizen().getId()));
        m.addAttribute("citizens", committeeService.getCitizens(userProfile.getCitizen().getId()));
        m.addAttribute("replaceTemplate","election_realization_interface");
        return "admin";
    }


    @GetMapping("/admin/electionRealizationInterface/{id}")
    public String showElectionRealization(Model m, @PathVariable Long id, Principal p, Authentication authentication) {
        UserProfile userProfile = (UserProfile) authentication.getPrincipal();
        Long realizationId = committeeService.findElectionRealizationByCitizen(userProfile.getCitizen().getId()).getId();
        CommitteeMember committeeMember = committeeMemberService.findById(userProfile.getCitizen().getId());
        if(!committeeService.getSamePollingStation(committeeMember.getId(), id)){
            return "redirect:/admin/electionRealizationInterface";
        }
        return "redirect:/admin/voteCode/" + realizationId + "/" + id;
    }
}
