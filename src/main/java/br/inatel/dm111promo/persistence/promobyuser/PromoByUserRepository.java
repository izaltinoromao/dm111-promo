package br.inatel.dm111promo.persistence.promobyuser;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface PromoByUserRepository {

    void save(PromoByUser promoByUser);

    List<PromoByUser> findAll() throws ExecutionException, InterruptedException;

    Optional<PromoByUser> findById(String id) throws ExecutionException, InterruptedException;

    void delete(String id) throws ExecutionException, InterruptedException;

    void update(PromoByUser promoByUser) throws ExecutionException, InterruptedException;

    List<PromoByUser> findAllByUserId(String userId) throws ExecutionException, InterruptedException;
}
