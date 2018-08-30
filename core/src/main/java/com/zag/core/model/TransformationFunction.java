package com.zag.core.model;

import com.google.common.base.Function;

public class TransformationFunction implements  Function<Identifiable<Long>,Long>{
	@Override
	public Long apply(Identifiable<Long> input) {
		return input.getId();
	}
}
