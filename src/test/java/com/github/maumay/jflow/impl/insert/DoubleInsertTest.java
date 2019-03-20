/**
 * 
 */
package com.github.maumay.jflow.impl.insert;

import java.util.Iterator;
import java.util.List;
import java.util.PrimitiveIterator;

import com.github.maumay.jflow.impl.AbstractDoubleIterator;
import com.github.maumay.jflow.testutilities.AbstractDoubleAdapterTest;

/**
 * @author thomasb
 *
 */
public final class DoubleInsertTest extends AbstractDoubleAdapterTest<AbstractDoubleIterator>
{
	@Override
	protected List<DoubleCase<AbstractDoubleIterator>> getTestCases()
	{
		List<Double> empty = list(), populated = list(0.0);
		return list(new DoubleCase<>(empty, i -> i.insert(unbox(empty.iterator())), empty),
				new DoubleCase<>(empty, i -> i.insert(unbox(populated.iterator())), populated),
				new DoubleCase<>(populated, i -> i.insert(unbox(empty.iterator())), populated),
				new DoubleCase<>(populated, i -> i.insert(unbox(list(1.0).iterator())),
						list(1.0, 0.0)));
	}

	private PrimitiveIterator.OfDouble unbox(Iterator<Double> source)
	{
		return new PrimitiveIterator.OfDouble() {
			@Override
			public boolean hasNext()
			{
				return source.hasNext();
			}

			@Override
			public double nextDouble()
			{
				return source.next();
			}
		};
	}
}
