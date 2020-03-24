import com.google.common.io.Files;
import com.google.common.io.Resources;
import java.io.File;
import java.io.IOException;
import org.junit.Test;

/**
 * @author ：komikun
 * @date ：Created in 2020-03-23 09:38
 * @description：
 * @modified By：
 * @version:
 */
public class DijikstraTest {
  @Test
  public void djTest() throws IOException {
    Dijikstra dj = new Dijikstra("USA-road-d.NY.gr");
//    Dijikstra dj = new Dijikstra("test");
    int[] dis = dj.calMinDistanceByArray(1);
//    File writeTo = new File(Resources.getResource("test_result").getFile());
    File writeTo = new File(Resources.getResource("USA_result_array").getFile());
    StringBuilder sb = new StringBuilder();
    for (int di : dis) {
      sb.append(di + "\n");
    }
    Files.write(sb.toString().getBytes(), writeTo);
  }
  @Test
  public void testWithHeap() throws IOException {
    Dijikstra dj = new Dijikstra("USA-road-d.NY.gr");
    File writeTo = new File(Resources.getResource("USA_result_heap").getFile());
//    Dijikstra dj = new Dijikstra("test");
//    File writeTo = new File(Resources.getResource("test_result").getFile());
    int[] dis = dj.calMinDistanceByHeap(1,"F");
    StringBuilder sb = new StringBuilder();
    for (int di : dis) {
      sb.append(di + "\n");
    }
    Files.write(sb.toString().getBytes(), writeTo);
  }

}
