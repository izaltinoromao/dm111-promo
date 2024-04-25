package br.inatel.dm111promo.api.promobyuser;

import br.inatel.dm111promo.persistence.promo.Product;

import java.util.List;

public record PromoByUserResponse(String id,
                                  String name,
                                  String starting,
                                  String expiration,
                                  List<Product> productsForYou,
                                  List<Product> products) {
}
