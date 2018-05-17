/**
 *
 */
package xawd.jflow.iterators.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

import xawd.jflow.iterators.AbstractDoubleFlow;
import xawd.jflow.iterators.AbstractFlow;
import xawd.jflow.iterators.AbstractIntFlow;
import xawd.jflow.iterators.AbstractLongFlow;

/**
 * @author t
 *
 */
public final class FlowFromValues
{
	private FlowFromValues() {}

	public static class OfObject<E> extends AbstractFlow<E>
	{
		private final Iterable<? extends E> src;
		private Iterator<? extends E> iterator;

		public OfObject(final Iterable<? extends E> src)
		{
			this.src = src;
		}

		@Override
		public boolean hasNext()
		{
			if (iterator == null) {
				iterator = src.iterator();
			}
			return iterator.hasNext();
		}
		@Override
		public E next()
		{
			if (iterator == null) {
				iterator = src.iterator();
			}
			return iterator.next();
		}
		@Override
		public void skip()
		{
			next();
		}
	}

	public static class OfLong extends AbstractLongFlow
	{
		private final long[] cache;
		private int count;

		public OfLong(final long...src)
		{
			this.cache = src;
		}

		@Override
		public boolean hasNext()
		{
			return count < cache.length;
		}
		@Override
		public long nextLong()
		{
			try {
				return cache[count++];
			}
			catch (final IndexOutOfBoundsException ex) {
				throw new NoSuchElementException();
			}
		}
		@Override
		public void skip()
		{
			if (count++ >= cache.length) {
				throw new NoSuchElementException();
			}
		}
	}

	public static class OfDouble extends AbstractDoubleFlow
	{
		private final double[] cache;
		private int count;

		public OfDouble(final double...src)
		{
			this.cache = src;
		}

		@Override
		public boolean hasNext()
		{
			return count < cache.length;
		}
		@Override
		public double nextDouble()
		{
			try {
				return cache[count++];
			}
			catch (final IndexOutOfBoundsException ex) {
				throw new NoSuchElementException();
			}
		}
		@Override
		public void skip()
		{
			if (count++ >= cache.length) {
				throw new NoSuchElementException();
			}
		}
	}

	public static class OfInt extends AbstractIntFlow
	{
		private final int[] cache;
		private int count;

		public OfInt(final int...src)
		{
			this.cache = src;
		}

		@Override
		public boolean hasNext()
		{
			return count < cache.length;
		}
		@Override
		public int nextInt()
		{
			try {
				return cache[count++];
			}
			catch (final IndexOutOfBoundsException ex) {
				throw new NoSuchElementException();
			}
		}
		@Override
		public void skip()
		{
			if (count++ >= cache.length) {
				throw new NoSuchElementException();
			}
		}
	}
}