package utn.project.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import utn.project.domain.City;
import utn.project.domain.Tariff;
import utn.project.exceptions.TariffNotExistsException;
import utn.project.repository.TariffRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TariffServiceTest {

    @Mock
    TariffRepository tariffRepository;

    @InjectMocks
    TariffService tariffService;

    @Before
    public void setUp() {
        initMocks(this);
    }

    private Tariff createTariff() {
        return Tariff.builder()
                .id(1)
                .originCity(new City())
                .destinyCity(new City())
                .build();
    }

    @Test
    public void getTariffsOkTest(){
        List<Tariff> list = new ArrayList<>();
        Tariff tariff = createTariff();
        list.add(tariff);
        when(this.tariffRepository.findAll()).thenReturn(list);
        ResponseEntity<List<Tariff>> response = this.tariffService.getTariffs();
        Assert.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void getTariffsNoContentTest(){
        List<Tariff> list = new ArrayList<>();
        when(this.tariffRepository.findAll()).thenReturn(list);
        ResponseEntity<List<Tariff>> response = this.tariffService.getTariffs();
        Assert.assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
    }

    @Test
    public void getTariffByLocalityFromToOkTest() throws TariffNotExistsException {
        Tariff tariff = createTariff();
        Integer idLocalityFrom = 1;
        Integer idLocalityTo = 1;
        when(this.tariffRepository.getTariffByDestinyOriginFromTo(idLocalityFrom,idLocalityTo)).thenReturn(tariff);
        Assert.assertEquals(tariff,this.tariffService.getTariffByDestinyOriginFromTo(idLocalityFrom,idLocalityTo));
    }

    @Test(expected = TariffNotExistsException.class)
    public void getTariffByLocalityFromToEmptyTariffTest() throws TariffNotExistsException {
        Integer idLocalityFrom = 1;
        Integer idLocalityTo = 1;
        when(this.tariffRepository.getTariffByDestinyOriginFromTo(idLocalityFrom,idLocalityTo)).thenReturn(null);
        this.tariffService.getTariffByDestinyOriginFromTo(idLocalityFrom,idLocalityTo);
    }

    @Test
    public void addTariffTest() {
        Tariff tariff = createTariff();
        this.tariffService.add(tariff);
    }

    @Test
    public void getTariffTest() {
        List<Tariff> tariff =  this.tariffService.getTariff();
    }

    @Test
    public void getTariffForPhonesDesAndOrigTest() {
        Integer idOrigin = 1;
        Integer idDestiny = 2;
        Tariff tariff = this.tariffService.getTariffForPhonesDesAndOrig(idOrigin,idDestiny);
    }

}
