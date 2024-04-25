package br.inatel.dm111promo.api.promobyuser.controller;

import br.inatel.dm111promo.api.promobyuser.PromoByUserResponse;
import br.inatel.dm111promo.api.promobyuser.service.PromoByUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/dm111")
public class PromoByUserController {

    private final PromoByUserService promoByUserService;

    public PromoByUserController(PromoByUserService promoByUserService) {
        this.promoByUserService = promoByUserService;
    }

    @GetMapping("/promo/users/{userId}")
    public ResponseEntity<List<PromoByUserResponse>> getAllPromosByUser(@PathVariable String userId) throws ExecutionException, InterruptedException {
        List<PromoByUserResponse> promoByUserResponseList = promoByUserService.getAllPromoByUserId(userId);
        return ResponseEntity.ok(promoByUserResponseList);
    }
}
