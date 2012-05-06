package com.kreative.rainbowstudio.gui.editor;

public class DefaultAnimationDataOptimizationStrategy extends LargeToSmallAnimationDataOptimizationStrategy {
	@Override
	public void optimizeAnimationData(
			int slot, byte[] data,
			int[] duration, int[] address, int[] length, int[] offset,
			byte[] animationData, int[] animationDataFreeSpace
	) throws AnimationTooComplexException {
		aloop: for (int a = 0; a <= animationDataFreeSpace[0]; a++) {
			int possibleAddress = a;
			int possibleOffset = 0;
			iloop: for (int i = 0; i < data.length; i++) {
				int ai = (possibleAddress + i) % animationData.length;
				if (ai >= animationDataFreeSpace[0] && ai < animationDataFreeSpace[1]) {
					animationData[ai] = data[i];
					continue iloop;
				} else if (animationData[ai] == data[i]) {
					continue iloop;
				} else {
					if (possibleAddress == a && possibleOffset == 0) {
						int oi = ((ai - data.length) + animationData.length) % animationData.length;
						if (animationData[oi] == data[i]) {
							possibleAddress = oi;
							possibleOffset = a - oi;
							continue iloop;
						}
					}
					continue aloop;
				}
			}
			address[slot] = possibleAddress;
			offset[slot] = possibleOffset;
			for (int i = 0; i < data.length; i++) {
				int ai = (possibleAddress + i) % animationData.length;
				if (ai == animationDataFreeSpace[0] && ai < animationDataFreeSpace[1]) {
					animationDataFreeSpace[0]++;
				}
			}
			for (int i = data.length-1; i >= 0; i--) {
				int ai = (possibleAddress + i) % animationData.length;
				if (ai >= animationDataFreeSpace[0] && ai == animationDataFreeSpace[1]-1) {
					animationDataFreeSpace[1]--;
				}
			}
			return;
		}
		throw new AnimationTooComplexException();
	}
}
