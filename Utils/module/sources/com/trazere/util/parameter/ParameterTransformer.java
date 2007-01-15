package com.trazere.util.parameter;

import com.trazere.util.parameter.ParameterException;
import com.trazere.util.parameter.Parameters;

public interface ParameterTransformer {
	public void apply(final Parameters<Object> parameters, final Parameters.Builder<Object> builder)
	throws ParameterException;
}
