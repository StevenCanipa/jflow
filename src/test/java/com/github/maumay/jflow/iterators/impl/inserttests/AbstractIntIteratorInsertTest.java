/**
 *
 */
package com.github.maumay.jflow.iterators.impl.inserttests;

import org.junit.jupiter.api.Test;

import com.github.maumay.jflow.impl.AbstractIntIterator;
import com.github.maumay.jflow.testutilities.AbstractIterableInts;
import com.github.maumay.jflow.testutilities.IteratorExampleProviders;
import com.github.maumay.jflow.testutilities.FiniteIteratorTest;

/**
 * @author ThomasB
 */
class AbstractIntIteratorInsertTest extends IteratorExampleProviders implements FiniteIteratorTest
{
	@Test
	void test()
	{
		AbstractIterableInts populated = getIntTestIteratorProvider();
		AbstractIterableInts empty = getEmptyIntTestIteratorProvider();
		AbstractIterableInts small = getSmallIntTestIteratorProvider();

		assertIntIteratorAsExpected(new int[] { 10, 11, 0, 1, 2, 3, 4 },
				createInsertIteratorProviderFrom(populated, small));
		assertIntIteratorAsExpected(new int[] { 0, 1, 2, 3, 4 },
				createInsertIteratorProviderFrom(populated, empty));

		assertIntIteratorAsExpected(new int[] { 10, 11 },
				createInsertIteratorProviderFrom(empty, small));
		assertIntIteratorAsExpected(new int[0], createInsertIteratorProviderFrom(empty, empty));
	}

	private AbstractIterableInts createInsertIteratorProviderFrom(AbstractIterableInts source,
			AbstractIterableInts toInsert)
	{
		return new AbstractIterableInts() {
			@Override
			public AbstractIntIterator iter()
			{
				return source.iter().insert(toInsert.iter());
			}
		};
	}
}
