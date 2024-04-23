package br.inatel.dm111promo.api.promo;

import br.inatel.dm111promo.persistence.promo.Product;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public record PromoRequest(String name,
                           String starting,
                           String expiration,
                           List<Product> products) {
}
