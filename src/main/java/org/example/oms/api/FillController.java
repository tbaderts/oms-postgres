package org.example.oms.api;

import java.util.List;
import java.util.Optional;

import org.example.common.model.Fill;
import org.example.oms.service.infra.repository.FillRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fills")
@Transactional(readOnly = true)
public class FillController {

    private final FillRepository fillRepository;

    public FillController(FillRepository fillRepository) {
        this.fillRepository = fillRepository;
    }

    @GetMapping
    public List<Fill> getAllFills() {
        return fillRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fill> getFillById(@PathVariable Long id) {
        Optional<Fill> fill = fillRepository.findById(id);
        return fill.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}