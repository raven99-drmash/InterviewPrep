package numberTests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import org.testng.annotations.Test;

public class NT1 {

	@Test
	void testcase001() {
		int i = 567;
		int result = String.valueOf(i).chars()
				.map(c -> c - '0')
				.sum();
		System.out.println(result);
	}

	@Test
	void testcase002() {
		int[] arr = {4, 5, 6,7};
		Integer result = Arrays.stream(arr).reduce((a, b) -> a + b).getAsInt();
		System.out.println(result);
	}
	
	
}
