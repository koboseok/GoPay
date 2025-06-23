package com.gopay.money.query.application.port.in;

import com.gopay.money.query.domain.MoneySumByRegion;

public interface QueryMoneySumByRegionUseCase {

    MoneySumByRegion queryMoneySumByRegion (QueryMoneySumByRegionQuery query);
}
