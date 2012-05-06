package com.kreative.rainbowstudio.gui.editor;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.kreative.rainbowstudio.utility.Pair;

public abstract class LargeToSmallAnimationDataOptimizationStrategy implements AnimationDataOptimizationStrategy {
	@Override
	public final void optimizeAnimationData(
			int count, List<Pair<Integer, byte[]>> data,
			int[] duration, int[] address, int[] length, int[] offset,
			byte[] animationData
	) throws AnimationTooComplexException {
		int[] animationDataFreeSpace = new int[]{ 0, animationData.length };
		
		Collections.sort(data, new Comparator<Pair<Integer, byte[]>>() {
			@Override
			public int compare(Pair<Integer, byte[]> a, Pair<Integer, byte[]> b) {
				return a.getLatter().length - b.getLatter().length;
			}
		});
		
		Pair<Integer, byte[]> first = data.remove(0);
		int firstSlot = first.getFormer();
		byte[] firstData = first.getLatter();
		address[firstSlot] = 0;
		offset[firstSlot] = 0;
		for (int i = 0; i < firstData.length; i++) {
			animationData[i] = firstData[i];
		}
		animationDataFreeSpace[0] = firstData.length;
		
		while (!data.isEmpty()) {
			Pair<Integer, byte[]> next = data.remove(0);
			optimizeAnimationData(
					next.getFormer(), next.getLatter(),
					duration, address, length, offset,
					animationData, animationDataFreeSpace
			);
		}
	}
	
	public abstract void optimizeAnimationData(
			int slot, byte[] data,
			int[] duration, int[] address, int[] length, int[] offset,
			byte[] animationData, int[] animationDataFreeSpace
	) throws AnimationTooComplexException;
}
