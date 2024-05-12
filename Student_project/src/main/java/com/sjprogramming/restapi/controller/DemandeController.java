package com.sjprogramming.restapi.controller;

import com.sjprogramming.restapi.model.Demande;
import com.sjprogramming.restapi.repository.DemandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/demandes")
public class DemandeController {

    @Autowired
    private DemandeRepository demandeRepository;

    @GetMapping
    public List<Demande> getAllDemandes() {
        return demandeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Demande> getDemandeById(@PathVariable Long id) {
        Optional<Demande> demande = demandeRepository.findById(id);
        if (demande.isPresent()) {
            return ResponseEntity.ok(demande.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Demande createDemande(@RequestBody Demande demande) {
        return demandeRepository.save(demande);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Demande> updateDemande(@PathVariable Long id, @RequestBody Demande demandeDetails) {
        Demande demande = demandeRepository.findById(id).orElseThrow(() -> new RuntimeException("Demande not found"));

        demande.setSujet(demandeDetails.getSujet());
        demande.setDescription(demandeDetails.getDescription());
        demande.setDateCreation(demandeDetails.getDateCreation());

        Demande updatedDemande = demandeRepository.save(demande);
        return ResponseEntity.ok(updatedDemande);
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<Demande> updateStateToAccepted(@PathVariable Long id) {
        Demande demande = demandeRepository.findById(id).orElseThrow(() -> new RuntimeException("Demande not found"));

        demande.setState("acceptée");

        Demande updatedDemande = demandeRepository.save(demande);
        return ResponseEntity.ok(updatedDemande);
    }
    @PutMapping("/{id}/refus")
    public ResponseEntity<Demande> updateStateToRefused(@PathVariable Long id) {
        Demande demande = demandeRepository.findById(id).orElseThrow(() -> new RuntimeException("Demande not found"));

        demande.setState("Refusée");

        Demande updatedDemande = demandeRepository.save(demande);
        return ResponseEntity.ok(updatedDemande);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDemande(@PathVariable Long id) {
        Demande demande = demandeRepository.findById(id).orElseThrow(() -> new RuntimeException("Demande not found"));
        demandeRepository.delete(demande);
        return ResponseEntity.noContent().build();
    }
}
