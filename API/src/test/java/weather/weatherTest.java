package weather;

// import io.restassured.itest.java.support.WithJetty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static io.restassured.RestAssured.*;
import static io.restassured.http.Method.GET;
import static org.hamcrest.Matchers.is;

@RunWith(Parameterized.class)

public class weatherTest {
    private int idCity;
    private String expCity;
    private String expCountry;
    public weatherTest(int idCity, String expCity, String expCountry) {
        this.idCity = idCity;
        this.expCity = expCity;
        this.expCountry = expCountry;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{{498817, "Saint Petersburg", "RU"}, {5549222, "Washington", "US"}, {1819729, "Hong Kong", "HK"}};
        return Arrays.asList(data);
    }



    @Test
    public void checkCity() {
                when()
                .request(GET, "http://api.openweathermap.org/data/2.5/weather?id={ID}&appid=c5ce4d8d7647d5a3a8046e6a33605f90", idCity) //PROD
                //.request(GET, "http://samples.openweathermap.org/data/2.5/weather?id={cityID}&appid=b6907d289e10d714a6e88b30761fae22", number ) //TEST ONLY
                .then()
                .statusCode(200)
                .body("name", is(expCity),"sys.country", is(expCountry));

    }
}