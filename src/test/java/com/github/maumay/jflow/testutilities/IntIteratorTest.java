package com.github.maumay.jflow.testutilities;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import com.github.maumay.jflow.iterators.impl2.AbstractIntIterator;
import com.github.maumay.jflow.iterators.impl2.AbstractIteratorSize;

/**
 * @author ThomasB
 * @since 27 Apr 2018
 */
public interface IntIteratorTest
{
	default void assertIntIteratorAsExpected(int[] expectedElements,
			AbstractIterableInts iteratorProvider)
	{
		assertSizeAsExpected(expectedElements, iteratorProvider.iter());
		assertSizeDecreasesAsExpected(iteratorProvider.iter());
		assertSkippingAsExpected(expectedElements, iteratorProvider.iter());
		assertNextElementChecksAsExpected(expectedElements, iteratorProvider.iter());
		assertStandardIterationAsExpected(expectedElements, iteratorProvider.iter());
		assertUncheckedIterationAsExpected(expectedElements, iteratorProvider.iter());
		assertAlternatingNextAndSkipCallsAsExpected(expectedElements, iteratorProvider.iter());
	}

	static void assertSizeAsExpected(int[] expectedElements, AbstractIntIterator iterator)
	{
		iterator.getSize().getExactSize().ifPresent(n -> {
			assertEquals(expectedElements.length, n);
		});
		iterator.getSize().getUpperBound().ifPresent(n -> {
			assertTrue(expectedElements.length <= n);
		});
		iterator.getSize().getLowerBound().ifPresent(n -> {
			assertTrue(n <= expectedElements.length);
		});
	}

	static void assertSizeDecreasesAsExpected(AbstractIntIterator iterator)
	{
		AbstractIteratorSize size = iterator.getSize();
		OptionalInt lower = size.getLowerBound(), exact = size.getExactSize(),
				upper = size.getUpperBound();

		int count = 0;
		while (iterator.hasNext()) {
			count++;
			iterator.nextInt();
			assertEquals(size.getLowerBound(), Utils.subtractSize(lower, count));
			assertEquals(size.getUpperBound(), Utils.subtractSize(upper, count));
			assertEquals(size.getExactSize(), Utils.subtractSize(exact, count));
		}
	}

	static void assertSkippingAsExpected(int[] expectedElements, AbstractIntIterator iterator)
	{
		IntStream.range(0, expectedElements.length).forEach(i -> iterator.skip());
		assertThrows(NoSuchElementException.class, iterator::skip);
	}

	static void assertNextElementChecksAsExpected(int[] expectedElements,
			AbstractIntIterator iterator)
	{
		IntStream.range(0, expectedElements.length).forEach(i -> {
			assertTrue(iterator.hasNext());
			iterator.skip();
		});
		assertFalse(iterator.hasNext());
	}

	static void assertStandardIterationAsExpected(int[] expectedElements,
			AbstractIntIterator iterator)
	{
		List<Integer> recoveredElements = new ArrayList<>();
		while (iterator.hasNext()) {
			recoveredElements.add(iterator.nextInt());
		}
		assertThrows(NoSuchElementException.class, iterator::nextInt);
		assertThrows(NoSuchElementException.class, iterator::skip);
		assertArrayEquals(expectedElements, convertFromBoxed(recoveredElements));
	}

	static void assertUncheckedIterationAsExpected(int[] expectedElements,
			AbstractIntIterator iterator)
	{
		List<Integer> recoveredElements = new ArrayList<>();
		IntStream.range(0, expectedElements.length)
				.forEach(i -> recoveredElements.add(iterator.nextInt()));

		assertThrows(NoSuchElementException.class, iterator::nextInt);
		assertThrows(NoSuchElementException.class, iterator::skip);
		assertArrayEquals(expectedElements, convertFromBoxed(recoveredElements));
	}

	static void assertAlternatingNextAndSkipCallsAsExpected(int[] expectedElements,
			AbstractIntIterator iterator)
	{
		List<Integer> expectedOutcome = new ArrayList<>(), recoveredElements = new ArrayList<>();

		IntStream.range(0, expectedElements.length).forEach(i -> {
			if (i % 2 == 0) {
				recoveredElements.add(iterator.nextInt());
				expectedOutcome.add(expectedElements[i]);
			} else {
				iterator.skip();
			}
		});

		assertFalse(iterator.hasNext());
		assertThrows(NoSuchElementException.class, iterator::nextInt);
		assertThrows(NoSuchElementException.class, iterator::skip);
		assertEquals(expectedOutcome, recoveredElements);
	}

	static int[] convertFromBoxed(List<Integer> boxedInts)
	{
		return boxedInts.stream().mapToInt(i -> i).toArray();
	}
}
