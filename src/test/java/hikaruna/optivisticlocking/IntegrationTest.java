package hikaruna.optivisticlocking;

import static org.junit.Assert.assertEquals;
import hikaruna.optivisticlocking.IntegrationTest.TestData.Append;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class IntegrationTest {

	class TestData extends GuardData {
		List<Integer> data;

		public TestData() {
			data = new ArrayList<Integer>();
		}

		class Append extends Updater<Void> {

			@Override
			protected void update(Void param) {
				int size = data.size();
				data.add(size);
			}
		}
	}

	@Test
	public void test() throws InterruptedException {
		final TestData target = new TestData();
		Append a = target.new Append();
		Append b = target.new Append();

		target.update(a, null);
		target.update(b, null);

		assertEquals(Arrays.asList(new Integer[] { 0 }), target.data);
	}
}
