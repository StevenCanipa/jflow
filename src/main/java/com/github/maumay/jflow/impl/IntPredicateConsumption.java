/**
 *
 */
package com.github.maumay.jflow.impl;

import java.util.function.IntPredicate;

import com.github.maumay.jflow.utils.Exceptions;

/**
 * @author ThomasB
 */
public final class IntPredicateConsumption
{
	public IntPredicateConsumption()
	{
	}

	public static boolean allEqual(AbstractIntIterator source)
	{
		Exceptions.require(source.hasOwnership());
		boolean initialised = false;
		int last = -1;
		while (source.hasNext()) {
			int next = source.nextIntImpl();
			if (!initialised) {
				initialised = true;
				last = next;
			} else if (last == next) {
				last = next;
			} else {
				return false;
			}
		}
		return true;
	}

	public static boolean allMatch(AbstractIntIterator source, IntPredicate predicate)
	{
		Exceptions.require(source.hasOwnership());
		while (source.hasNext()) {
			if (!predicate.test(source.nextIntImpl())) {
				return false;
			}
		}
		return true;
	}

	public static boolean anyMatch(AbstractIntIterator source, IntPredicate predicate)
	{
		Exceptions.require(source.hasOwnership());
		while (source.hasNext()) {
			if (predicate.test(source.nextIntImpl())) {
				return true;
			}
		}
		return false;
	}

	public static boolean noneMatch(AbstractIntIterator source, IntPredicate predicate)
	{
		Exceptions.require(source.hasOwnership());
		while (source.hasNext()) {
			if (predicate.test(source.nextIntImpl())) {
				return false;
			}
		}
		return true;
	}
}
