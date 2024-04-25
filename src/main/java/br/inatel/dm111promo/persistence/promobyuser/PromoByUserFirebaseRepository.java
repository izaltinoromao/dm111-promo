package br.inatel.dm111promo.persistence.promobyuser;

import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Component
public class PromoByUserFirebaseRepository implements PromoByUserRepository{

    private static final String COLLECTION_NAME = "promo_by_user";

    private final Firestore firestore;

    public PromoByUserFirebaseRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public void save(PromoByUser promoByUser) {
        firestore.collection(COLLECTION_NAME)
                .document(promoByUser.getId())
                .set(promoByUser);
    }

    @Override
    public List<PromoByUser> findAll() throws ExecutionException, InterruptedException {
        return firestore.collection(COLLECTION_NAME)
                .get()
                .get()
                .getDocuments()
                .parallelStream()
                .map(promoByUser -> promoByUser.toObject(PromoByUser.class))
                .toList();
    }

    @Override
    public Optional<PromoByUser> findById(String id) throws ExecutionException, InterruptedException {
        var promoByUser = firestore.collection(COLLECTION_NAME)
                .document(id)
                .get()
                .get()
                .toObject(PromoByUser.class);
        return Optional.ofNullable(promoByUser);
    }

    @Override
    public void delete(String id) throws ExecutionException, InterruptedException {
        firestore.collection(COLLECTION_NAME).document(id).delete().get();
    }

    @Override
    public void update(PromoByUser promoByUser) throws ExecutionException, InterruptedException {
        save(promoByUser);
    }

    @Override
    public List<PromoByUser> findAllByUserId(String userId) throws ExecutionException, InterruptedException {
        return firestore.collection(COLLECTION_NAME)
                .get()
                .get()
                .getDocuments()
                .parallelStream()
                .map(promoByUser -> promoByUser.toObject(PromoByUser.class))
                .filter(spl -> spl.getUserId().equals(userId))
                .toList();
    }
}
