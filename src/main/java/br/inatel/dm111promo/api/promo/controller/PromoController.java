package br.inatel.dm111promo.api.promo.controller;

import br.inatel.dm111promo.api.core.ApiException;
import br.inatel.dm111promo.api.promo.PromoDto;
import br.inatel.dm111promo.api.promo.PromoRequest;
import br.inatel.dm111promo.api.promo.service.PromoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/dm111")
public class PromoController {

    public final PromoService service;

    public PromoController(PromoService service) {
        this.service = service;
    }

    @PostMapping("/promo")
    public ResponseEntity<PromoDto> postPromo(@RequestBody PromoRequest request) throws ParseException {
        var promoDto = service.createPromo(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(promoDto);
    }

    @PutMapping("/promo/{id}")
    public ResponseEntity<PromoDto> putPromo(@PathVariable("id") String id,
                                             @RequestBody PromoRequest request) throws ApiException, ParseException, ExecutionException, InterruptedException {
        var promoDto = service.updatePromo(id, request);
        return ResponseEntity.ok(promoDto);
    }

    @DeleteMapping("/promo/{id}")
    public ResponseEntity<?> deletePromo(@PathVariable("id") String id) throws ApiException {
        service.removePromo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/promo")
    public ResponseEntity<List<PromoDto>> getPromos() throws ApiException {
        var promosDto = service.searchPromos();
        return ResponseEntity.ok(promosDto);
    }

    @GetMapping("/promo/{id}")
    public ResponseEntity<PromoDto> getPromo(@PathVariable("id") String id) throws ApiException {
        var promoDto = service.searchPromo(id);
        return ResponseEntity.ok(promoDto);
    }
}
