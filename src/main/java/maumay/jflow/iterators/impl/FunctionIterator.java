/**
 *
 */
package maumay.jflow.iterators.impl;

import java.util.NoSuchElementException;
import java.util.OptionalInt;
import java.util.function.IntFunction;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;

import maumay.jflow.iterators.AbstractEnhancedDoubleIterator;
import maumay.jflow.iterators.AbstractEnhancedIntIterator;
import maumay.jflow.iterators.AbstractEnhancedIterator;
import maumay.jflow.iterators.AbstractEnhancedLongIterator;

/**
 * @author t
 *
 */
public final class FunctionIterator
{
	private FunctionIterator()
	{
	}

	public static class OfObject<T> extends AbstractEnhancedIterator<T>
	{
		private final IntFunction<T> src;
		private int count = 0;

		public OfObject(final IntFunction<T> src, final int elementCap)
		{
			super(elementCap < 0 ? OptionalInt.empty() : OptionalInt.of(elementCap));
			this.src = src;
		}

		public OfObject(final IntFunction<T> src)
		{
			this(src, -1);
		}

		@Override
		public boolean hasNext()
		{
			return !size.isPresent() || count < size.getAsInt();
		}

		@Override
		public T next()
		{
			if (hasNext()) {
				return src.apply(count++);
			} else {
				throw new NoSuchElementException();
			}
		}

		@Override
		public void skip()
		{
			if (hasNext()) {
				count++;
			} else {
				throw new NoSuchElementException();
			}
		}
	}

	public static class OfLong extends AbstractEnhancedLongIterator
	{
		private final IntToLongFunction src;
		private int count = 0;

		public OfLong(final IntToLongFunction src, final int elementCap)
		{
			super(elementCap < 0 ? OptionalInt.empty() : OptionalInt.of(elementCap));
			this.src = src;
		}

		@Override
		public boolean hasNext()
		{
			return !size.isPresent() || count < size.getAsInt();
		}

		@Override
		public long nextLong()
		{
			if (hasNext()) {
				return src.applyAsLong(count++);
			} else {
				throw new NoSuchElementException();
			}
		}

		@Override
		public void skip()
		{
			if (hasNext()) {
				count++;
			} else {
				throw new NoSuchElementException();
			}
		}
	}

	public static class OfInt extends AbstractEnhancedIntIterator
	{
		private final IntUnaryOperator src;
		private int count = 0;

		public OfInt(final IntUnaryOperator src, final int elementCap)
		{
			super(elementCap < 0 ? OptionalInt.empty() : OptionalInt.of(elementCap));
			this.src = src;
		}

		@Override
		public boolean hasNext()
		{
			return !size.isPresent() || count < size.getAsInt();
		}

		@Override
		public int nextInt()
		{
			if (hasNext()) {
				return src.applyAsInt(count++);
			} else {
				throw new NoSuchElementException();
			}
		}

		@Override
		public void skip()
		{
			if (hasNext()) {
				count++;
			} else {
				throw new NoSuchElementException();
			}
		}
	}

	public static class OfDouble extends AbstractEnhancedDoubleIterator
	{
		private final IntToDoubleFunction src;
		private int count = 0;

		public OfDouble(final IntToDoubleFunction src, final int elementCap)
		{
			super(elementCap < 0 ? OptionalInt.empty() : OptionalInt.of(elementCap));
			this.src = src;
		}

		@Override
		public boolean hasNext()
		{
			return !size.isPresent() || count < size.getAsInt();
		}

		@Override
		public double nextDouble()
		{
			if (hasNext()) {
				return src.applyAsDouble(count++);
			} else {
				throw new NoSuchElementException();
			}
		}

		@Override
		public void skip()
		{
			if (hasNext()) {
				count++;
			} else {
				throw new NoSuchElementException();
			}
		}
	}
}