package com.kreative.rainbowstudio.gui.editor;

import java.util.List;
import com.kreative.rainbowstudio.utility.Pair;

public interface AnimationDataOptimizationStrategy {
	public void optimizeAnimationData(
			int count, List<Pair<Integer,byte[]>> data,
			int[] duration, int[] address, int[] length, int[] offset,
			byte[] animationData
	) throws AnimationTooComplexException;
}
