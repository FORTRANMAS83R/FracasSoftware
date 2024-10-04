package transmetteurs;
import information.Information;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.junit.Assert.*;

//1-> 101
//0-> 010
public class CodageCanalTest {

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void testCodeCanal() {
        Information<Boolean> information = new Information<>();
        information.add(true);
        information.add(false);
        information.add(true);
        Information<Boolean> informationCodee = Codeur.codeCanal(information);
        assertEquals(" true false true false true false true false true", informationCodee.toString());
    }
}
