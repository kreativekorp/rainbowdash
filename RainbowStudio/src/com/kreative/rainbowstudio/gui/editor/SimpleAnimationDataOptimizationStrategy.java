package com.kreative.rainbowstudio.gui.editor;

public class SimpleAnimationDataOptimizationStrategy extends LargeToSmallAnimationDataOptimizationStrategy {
	@Override
	public int optimizeAnimationData(
			int slot, byte[] data,
			int[] address, int[] length, int[] offset,
			byte[] animationData, int animationDataLength
	) throws AnimationTooComplexException {
		if (animationDataLength + data.length > animationData.length) throw new AnimationTooComplexException();
		address[slot] = animationDataLength;
		length[slot] = data.length;
		offset[slot] = 0;
		for (int i = 0; i < data.length; i++) {
			animationData[animationDataLength++] = data[i];
		}
		return animationDataLength;
	}
}
