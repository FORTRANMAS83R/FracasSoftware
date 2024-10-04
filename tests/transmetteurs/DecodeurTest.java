package transmetteurs;

import information.Information;
import information.InformationNonConformeException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import static org.hamcrest.core.Is.is;

public class DecodeurTest {
    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void testDecode() {
        Boolean[] symb0 = {false, false, false}; //Expected output: false
        Boolean[] symb1 = {false, false, true}; // Expected output: true
        Boolean[] symb2 = {false, true, false}; // Expected output: false
        Boolean[] symb3 = {false, true, true};  // Expected output: false
        Boolean[] symb4 = {true, false, false};  //Expected output: true
        Boolean[] symb5 = {true, false, true};  //Expected output: true
        Boolean[] symb6 = {true, true, false};  //Expected output: false
        Boolean[] symb7 = {true, true, true}; //Expected output: true
        collector.checkThat(Decodeur.decode(symb0), is(false));
        collector.checkThat(Decodeur.decode(symb1), is(true));
        collector.checkThat(Decodeur.decode(symb2), is(false));
        collector.checkThat(Decodeur.decode(symb3), is(false));
        collector.checkThat(Decodeur.decode(symb4), is(true));
        collector.checkThat(Decodeur.decode(symb5), is(true));
        collector.checkThat(Decodeur.decode(symb6), is(false));
        collector.checkThat(Decodeur.decode(symb7), is(true));
    }
    @Test(expected = InformationNonConformeException.class)
    public void testInvalidSequenceLength() throws InformationNonConformeException {
        Decodeur decodeur = new Decodeur();
        Information<Boolean> input = new Information<>();
        input.add(false); input.add(true); // Non-multiple de 3

        decodeur.recevoir(input);
    }
    @Test
    public void testDecodeInformation() throws InformationNonConformeException {
        Decodeur decodeur = new Decodeur();
        Information<Boolean> input = new Information<>();
        input.add(false); input.add(false); input.add(false); // 000
        input.add(false); input.add(false); input.add(true); // 001
        input.add(false); input.add(true); input.add(false); // 010
        input.add(false); input.add(true); input.add(true); // 011
        input.add(true); input.add(false); input.add(false); // 100
        input.add(true); input.add(false); input.add(true); // 101
        input.add(true); input.add(true); input.add(false); // 110
        input.add(true); input.add(true); input.add(true); // 111

        Information<Boolean> expected = new Information<>();
        expected.add(false); expected.add(true); expected.add(false); expected.add(false); expected.add(true); expected.add(true); expected.add(false); expected.add(true);

        decodeur.recevoir(input);
        collector.checkThat(decodeur.getInformationEmise(), is(expected));
    }

}
