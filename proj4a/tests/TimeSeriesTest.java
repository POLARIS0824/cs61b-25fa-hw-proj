import main.TimeSeries;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

/** Unit Tests for the TimeSeries class.
 *  @author Josh Hug
 */
public class TimeSeriesTest {
    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 0,
        //           1992: 100
        //           1994: 600
        //           1995: 500

        List<Integer> expectedYears = new ArrayList<>();
        expectedYears.add(1991);
        expectedYears.add(1992);
        expectedYears.add(1994);
        expectedYears.add(1995);

        assertThat(totalPopulation.years()).isEqualTo(expectedYears);

        List<Double> expectedTotal = new ArrayList<>();
        expectedTotal.add(0.0);
        expectedTotal.add(100.0);
        expectedTotal.add(600.0);
        expectedTotal.add(500.0);

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertThat(totalPopulation.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }
    }

    @Test
    public void testEmptyBasic() {
        TimeSeries catPopulation = new TimeSeries();
        TimeSeries dogPopulation = new TimeSeries();

        assertThat(catPopulation.years()).isEmpty();
        assertThat(catPopulation.data()).isEmpty();

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);

        assertThat(totalPopulation.years()).isEmpty();
        assertThat(totalPopulation.data()).isEmpty();
    }

    @Test
    public void testDevideBy() {
        TimeSeries ts1 = new TimeSeries();
        TimeSeries ts2 = new TimeSeries();

        ts1.put(1990, 10.0);
        ts1.put(1991, 20.0);
        ts1.put(1992, 30.0);
        ts2.put(1990, 2.0);  // 10 / 2 = 5
        ts2.put(1991, 4.0);  // 20 / 4 = 5
        ts2.put(1992, 5.0);  // 30 / 5 = 6
        ts2.put(1993, 10.0); // 这一年 ts1 没有，应该被忽略

        TimeSeries res = ts1.dividedBy(ts2);
        assertThat(res.get(1990)).isWithin(1E-10).of(5);
        assertThat(res.get(1991)).isWithin(1E-10).of(5);
        assertThat(res.get(1992)).isWithin(1E-10).of(6);
        assertThat(res.containsKey(1993)).isFalse();

        TimeSeries ts3 = new TimeSeries();
        ts3.put(1990, 2.0);

        try {
            ts1.dividedBy(ts3);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
} 