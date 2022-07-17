package com.mj.controller.exchange;

import javax.validation.Valid;

import com.mj.api.dto.in.ExchangeDto;
import com.mj.api.dto.out.ExchangeRatesSeries;
import com.mj.domain.Currency;
import com.mj.service.exchange.ExchangeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("exchange")
@RequiredArgsConstructor
@Validated
@Slf4j
public class ExchangeController {

    private static final String NBP_API_URI = "http://api.nbp.pl/api/exchangerates/rates/a/";

    private final ExchangeService exchangeService;

    @PostMapping(value = "/")
    public ResponseEntity<String> exchange(@Valid @RequestBody ExchangeDto dto) {
        ExchangeRatesSeries exchangeSeries = getExchangeSeries();
        try {
            exchangeService.exchange(dto, exchangeSeries);
        } catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ExchangeRatesSeries getExchangeSeries(){
        String uri = NBP_API_URI + Currency.USD.name();

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, ExchangeRatesSeries.class);
    }

}
