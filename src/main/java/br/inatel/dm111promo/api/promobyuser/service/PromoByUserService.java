package br.inatel.dm111promo.api.promobyuser.service;

import br.inatel.dm111promo.api.core.ApiException;
import br.inatel.dm111promo.api.core.AppErrorCode;
import br.inatel.dm111promo.api.promobyuser.PromoByUserResponse;
import br.inatel.dm111promo.persistence.promo.Product;
import br.inatel.dm111promo.persistence.promo.Promo;
import br.inatel.dm111promo.persistence.promo.PromoRepository;
import br.inatel.dm111promo.persistence.promobyuser.PromoByUser;
import br.inatel.dm111promo.persistence.promobyuser.PromoByUserRepository;
import br.inatel.dm111promo.persistence.supermarketlist.SuperMarketList;
import br.inatel.dm111promo.persistence.supermarketlist.SuperMarketListRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class PromoByUserService {

    private final PromoByUserRepository promoByUserRepository;
    private final PromoRepository promoRepository;
    private final SuperMarketListRepository superMarketListRepository;

    public PromoByUserService(PromoByUserRepository promoByUserRepository, PromoRepository promoRepository, SuperMarketListRepository superMarketListRepository) {
        this.promoByUserRepository = promoByUserRepository;
        this.promoRepository = promoRepository;
        this.superMarketListRepository = superMarketListRepository;
    }


    public List<PromoByUserResponse> getAllPromoByUserId(String userId) throws ExecutionException, InterruptedException {
        List<PromoByUser> promoByUserList = promoByUserRepository.findAllByUserId(userId);
        return buildPromoByUserResponseList(validatePromosByUser(promoByUserList));
    }

    public void createPromoByUser(PromoByUser promoByUser) throws ParseException {
        promoByUserRepository.save(promoByUser);
    }

    public void createAllPromosByUser(List<PromoByUser> promoByUserList) {
        promoByUserList.forEach(promoByUser -> {
            try {
                createPromoByUser(promoByUser);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updatePromoByUserEntity(String userId) throws ExecutionException, InterruptedException, ApiException {
        //Get 5 last supermarket list for this user
        List<SuperMarketList> superMarketList = superMarketListRepository.findLastFiveByUserId(userId);

        //Get all valid promos
        List<Promo> validPromos = validatePromos(retrieveAllPromos());

        // Extract just the products ids from SuperMarketList
        List<String> productIds = superMarketList.stream()
                .flatMap(list -> list.getProducts().stream())
                .toList();

        List<PromoByUser> promoByUserList = new ArrayList<>();

        //Validate the products that is relevant for the user, and mapping to a new array
        for(Promo promo : validPromos) {
            List<Product> productsFromPromo = promo.getProducts();

            List<Product> productsForYou = productsFromPromo.stream()
                    .filter(product -> productIds.contains(product.getProductId()))
                    .toList();

            PromoByUser promoByUserTemp = new PromoByUser(promo.getId(),
                    userId,
                    promo.getName(),
                    promo.getStarting(),
                    promo.getExpiration(),
                    productsForYou,
                    promo.getProducts());

            promoByUserList.add(promoByUserTemp);
        }

        createAllPromosByUser(promoByUserList);
    }

    private List<Promo> validatePromos(List<Promo> promoList) {
        Date now = Date.from(Instant.now());

        return promoList.stream()
                .filter(promo -> now.after(promo.getStarting()) && now.before(promo.getExpiration()))
                .collect(Collectors.toList());
    }

    private List<Promo> retrieveAllPromos() throws ApiException {
        try {
            return promoRepository.findAll();
        } catch (ExecutionException | InterruptedException e) {
            throw new ApiException(AppErrorCode.PROMO_QUERY_ERROR);
        }
    }

    private PromoByUserResponse buildPromoByUserResponse(PromoByUser promoByUser) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String starting = formatter.format(promoByUser.getStarting());
        String expiration = formatter.format(promoByUser.getExpiration());
        return new PromoByUserResponse(promoByUser.getId(),
                promoByUser.getName(),
                starting,
                expiration,
                promoByUser.getProductsForYou(),
                promoByUser.getProducts()
                );
    }

    private List<PromoByUserResponse> buildPromoByUserResponseList(List<PromoByUser> promoByUserList) {
        return promoByUserList.stream()
                .map(this::buildPromoByUserResponse)
                .toList();
    }

    private List<PromoByUser> validatePromosByUser(List<PromoByUser> promoByUserList) {
        Date now = Date.from(Instant.now());

        return promoByUserList.stream()
                .filter(promoByUser -> now.after(promoByUser.getStarting()) && now.before(promoByUser.getExpiration()))
                .collect(Collectors.toList());
    }

}
