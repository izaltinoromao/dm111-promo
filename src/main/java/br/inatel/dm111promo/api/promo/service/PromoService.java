package br.inatel.dm111promo.api.promo.service;

import br.inatel.dm111promo.api.core.ApiException;
import br.inatel.dm111promo.api.core.AppErrorCode;
import br.inatel.dm111promo.api.promo.PromoDto;
import br.inatel.dm111promo.api.promo.PromoRequest;
import br.inatel.dm111promo.persistence.promo.Promo;
import br.inatel.dm111promo.persistence.promo.PromoRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class PromoService {

    private final PromoRepository repository;

    public PromoService(PromoRepository repository) {
        this.repository = repository;
    }

    public PromoDto createPromo(PromoRequest request) throws ParseException {
        var promo = buildPromo(request);
        repository.save(promo);
        return buildPromoDto(promo);
    }

    public List<PromoDto> searchPromos() throws ApiException {
        List<Promo> promoValidatedList = validatePromos(retrieveAllPromos());

        return buildPromoDtoList(promoValidatedList);
    }

    public PromoDto searchPromo(String id) throws ApiException {
        return buildPromoDto(validatePromo(retrievePromo(id)));
    }

    public PromoDto updatePromo(String id, PromoRequest request) throws ApiException, ParseException, ExecutionException, InterruptedException {
        var promo = retrievePromo(id);
        var promoRequest = buildPromo(request);
        promo.setName(promoRequest.getName());
        promo.setStarting(promoRequest.getStarting());
        promo.setExpiration(promoRequest.getExpiration());
        promo.setProducts(promoRequest.getProducts());

        repository.update(promo);

        return buildPromoDto(promo);
    }

    public void removePromo(String id) throws ApiException {
        try {
            repository.delete(id);
        } catch (ExecutionException | InterruptedException e) {
            throw new ApiException(AppErrorCode.PROMO_QUERY_ERROR);
        }
    }

    private Promo retrievePromo(String id) throws ApiException {
        try {
            return repository.findById(id)
                    .orElseThrow(() -> new ApiException(AppErrorCode.PROMO_NOT_FOUND));
        } catch (ExecutionException | InterruptedException e) {
            throw new ApiException(AppErrorCode.PROMO_QUERY_ERROR);
        }
    }

    private List<Promo> retrieveAllPromos() throws ApiException {
        try {
            return repository.findAll();
        } catch (ExecutionException | InterruptedException e) {
            throw new ApiException(AppErrorCode.PROMO_QUERY_ERROR);
        }
    }

    private Promo validatePromo(Promo promo) throws ApiException {
        Date now = Date.from(Instant.now());

        if (now.after(promo.getStarting()) && now.before(promo.getExpiration())) {
            return promo;
        } else {
            throw new ApiException(AppErrorCode.PROMO_INVALID);
        }
    }

    private List<Promo> validatePromos(List<Promo> promoList) {
        Date now = Date.from(Instant.now());

        return promoList.stream()
                .filter(promo -> now.after(promo.getStarting()) && now.before(promo.getExpiration()))
                .collect(Collectors.toList());
    }

    private Promo buildPromo(PromoRequest request) throws ParseException {
        var id = UUID.randomUUID().toString();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date starting = formatter.parse(request.starting());
        Date expiration = formatter.parse(request.expiration());
        return new Promo(id,
                request.name(),
                starting,
                expiration,
                request.products());
    }

    private PromoDto buildPromoDto(Promo promo) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String starting = formatter.format(promo.getStarting());
        String expiration = formatter.format(promo.getExpiration());
        return new PromoDto(promo.getId(),
                promo.getName(),
                starting,
                expiration,
                promo.getProducts());
    }

    private List<PromoDto> buildPromoDtoList(List<Promo> promoValidatedList) {
        return promoValidatedList.stream()
                .map(this::buildPromoDto)
                .collect(Collectors.toList());
    }
}
