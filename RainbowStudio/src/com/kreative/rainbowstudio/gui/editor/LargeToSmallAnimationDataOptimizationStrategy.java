package com.kreative.rainbowstudio.gui.editor;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.kreative.rainbowstudio.utility.Pair;

public abstract class LargeToSmallAnimationDataOptimizationStrategy implements AnimationDataOptimizationStrategy {
	@Override
	public final void optimizeAnimationData(
			int count, List<Pair<Integer, byte[]>> data,
			int[] address, int[] length, int[] offset,
			byte[] animationData
	) throws AnimationTooComplexException {
		Collections.sort(data, new Comparator<Pair<Integer, byte[]>>() {
			@Override
			public int compare(Pair<Integer, byte[]> a, Pair<Integer, byte[]> b) {
				return a.getLatter().length - b.getLatter().length;
			}
		});
		int animationDataLength = 0;
		
		Pair<Integer, byte[]> first = data.remove(0);
		int firstSlot = first.getFormer();
		byte[] firstData = first.getLatter();
		if (firstData.length > animationData.length) throw new AnimationTooComplexException();
		address[firstSlot] = 0;
		length[firstSlot] = firstData.length;
		offset[firstSlot] = 0;
		for (int i = 0; i < firstData.length; i++) {
			animationData[i] = firstData[i];
		}
		animationDataLength = firstData.length;
		
		while (!data.isEmpty()) {
			Pair<Integer, byte[]> next = data.remove(0);
			int nextSlot = next.getFormer();
			byte[] nextData = next.getLatter();
			if (nextData.length > animationData.length) throw new AnimationTooComplexException();
			animationDataLength = optimizeAnimationData(
					nextSlot, nextData,
					address, length, offset,
					animationData, animationDataLength
			);
		}
	}
	
	public abstract int optimizeAnimationData(
			int slot, byte[] data,
			int[] address, int[] length, int[] offset,
			byte[] animationData, int animationDataLength
	) throws AnimationTooComplexException;
}
