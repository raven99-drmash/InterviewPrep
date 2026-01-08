package stringTests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.testng.annotations.Test;

public class ST1 {

	/**
	 * Get only numbers from string
	 */
	@Test
	void testcase001() {
		String str = "Ragul123";
		for (Character c : str.toCharArray()) {
			System.out.println(Character.getNumericValue(c));
		}
	}

	/**
	 * Find Longest common prefix of string arrays
	 */
	@Test
	void testcase002() {
		String[] arr = { "flower", "flow", "fling", "flan", "flu" };

		// Way1
		// find shortest string
		String str = Arrays.stream(arr).reduce((a, b) -> a.length() < b.length() ? a : b).get();

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			boolean allMatch = true;

			for (String s : arr) {
				if (s.charAt(i) != ch) {
					allMatch = false;
					break;
				}
			}
			if (allMatch) {
				sb.append(ch);
			} else {
				break;
			}
		}
		System.out.println(sb.toString());

	}

	@Test
	void testcase003() {
		String str = "A1B2C3D4"; // D12C734B3A1234
		List<Object> charList = new ArrayList<>();
		List<Object> numList = new ArrayList<>();
		StringBuffer sb = new StringBuffer();

		Matcher m = Pattern.compile("[A-Za-z]+|\\d+").matcher(str);
		while (m.find()) {
			String ele = m.group();
			if (Character.isLetter(ele.charAt(0))) {
				charList.add(ele);
			} else {
				numList.add(Integer.valueOf(ele)); // Char to Integer
				// Integer to char??
			}
		}
		Collections.reverse(charList);

		for (int i = 0; i < numList.size(); i++) {
			sb.append(charList.get(i));
			sb.append(numList.get(i));
		}
		System.out.println(sb.toString());
	}

	@Test
	void testcase004() {
		String[] strs = { "eat", "tea", "tan", "ate", "nat", "bat" };
		Map<String, List<String>> map = new HashMap<>();

		for (String s : strs) {
			char[] ch = s.toCharArray();
			Arrays.sort(ch);
			String key = new String(ch);

			System.out.println("key: " + key + " --> " + map);
			map.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
		}
		List<List<String>> result = new ArrayList<>(map.values());
		for (List<String> ans : result) {
			System.out.println(ans);
		}
	}

	@Test
	void testcase005() {
		String[] strs = { "eat", "tea", "tan", "ate", "nat", "bat" };
		Map<String, String> result = new HashMap<>();
		for (String st : strs) {
			char[] ca = st.toCharArray();
			Arrays.sort(ca);
			String value = new String(ca);
			result.put(st, value);
		}
	
		System.out.println(result);
		System.out.println(result.entrySet());
		Map<String, List<String>> obj = result.entrySet().stream().collect(
				Collectors.groupingBy(e -> e.getValue(), Collectors.mapping(e -> e.getKey(), Collectors.toList())));
		System.out.println(obj);

	}

}
