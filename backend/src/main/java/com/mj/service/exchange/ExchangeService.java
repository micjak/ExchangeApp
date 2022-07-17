package com.mj.service.exchange;

import com.mj.api.dto.in.ExchangeDto;
import com.mj.api.dto.out.ExchangeRatesSeries;

public interface ExchangeService {
    void exchange(ExchangeDto dto, ExchangeRatesSeries exchangeRatesSeries);

}
