/**
 *
 */
package com.github.maumay.jflow.iterators.impl.slice;

import java.util.function.IntUnaryOperator;

import org.junit.jupiter.api.Test;

import com.github.maumay.jflow.iterators.impl2.AbstractIntIterator;
import com.github.maumay.jflow.testutilities.AbstractIterableInts;
import com.github.maumay.jflow.testutilities.IteratorExampleProvider;
import com.github.maumay.jflow.testutilities.IteratorTest;

/**
 * @author ThomasB
 *
 */
class AbstractIntIteratorSliceTest extends IteratorExampleProvider implements IteratorTest
{
	@Test
	void test()
	{
		AbstractIterableInts populated = getIntTestIteratorProvider();
		AbstractIterableInts empty = getEmptyIntTestIteratorProvider();

		IntUnaryOperator allSlicedOperator = i -> i;
		assertIntIteratorAsExpected(new int[] { 0, 1, 2, 3, 4 },
				createSlicedIteratorProviderFrom(populated, allSlicedOperator));
		assertIntIteratorAsExpected(new int[] {},
				createSlicedIteratorProviderFrom(empty, allSlicedOperator));

		IntUnaryOperator someSlicedOperator = i -> 2 * i;
		assertIntIteratorAsExpected(new int[] { 0, 2, 4 },
				createSlicedIteratorProviderFrom(populated, someSlicedOperator));
		assertIntIteratorAsExpected(new int[] {},
				createSlicedIteratorProviderFrom(empty, someSlicedOperator));

		IntUnaryOperator noneSlicedOperator = i -> i + 5;
		assertIntIteratorAsExpected(new int[] {},
				createSlicedIteratorProviderFrom(populated, noneSlicedOperator));
		assertIntIteratorAsExpected(new int[] {},
				createSlicedIteratorProviderFrom(empty, noneSlicedOperator));
	}

	private AbstractIterableInts createSlicedIteratorProviderFrom(AbstractIterableInts src,
			IntUnaryOperator indexMapping)
	{
		return new AbstractIterableInts() {
			@Override
			public AbstractIntIterator iter()
			{
				return src.iter().slice(indexMapping);
			}
		};
	}
}
