package com.kreative.rainbowstudio.gui.editor;

public class SimpleAnimationDataOptimizationStrategy extends LargeToSmallAnimationDataOptimizationStrategy {
	@Override
	public void optimizeAnimationData(
			int slot, byte[] data,
			int[] duration, int[] address, int[] length, int[] offset,
			byte[] animationData, int[] animationDataFreeSpace
	) throws AnimationTooComplexException {
		if (animationDataFreeSpace[0] + data.length > animationDataFreeSpace[1]) throw new AnimationTooComplexException();
		address[slot] = animationDataFreeSpace[0];
		offset[slot] = 0;
		for (int i = 0; i < data.length; i++) {
			animationData[animationDataFreeSpace[0]++] = data[i];
		}
	}
}
