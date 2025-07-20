package net.vodculen.drogenmissbrauch.util;

import java.util.Random;

public class PerlinNoise {

	private final int[] permutation;

	public PerlinNoise(long seed) {
		Random random = new Random(seed);
		int[] p = new int[256];

		permutation = new int[512];
		
		// Fill p with 0..255
		for (int i = 0; i < 256; i++) {
			p[i] = i;
		}

		// Shuffle p array
		for (int i = 255; i > 0; i--) {
			int index = random.nextInt(i + 1);
			int temp = p[i];

			p[i] = p[index];
			p[index] = temp;
		}

		// Duplicate array for overflow
		for (int i = 0; i < 512; i++) {
			permutation[i] = p[i & 255];
		}
	}

	private static double fade(double t) {
		// 6t^5 - 15t^4 + 10t^3 smoothing curve
		return t * t * t * (t * (t * 6 - 15) + 10);
	}

	private static double lerp(double a, double b, double t) {
		return a + t * (b - a);
	}

	private static double grad(int hash, double x, double y) {
		int h = hash & 7;
		double u = h < 4 ? x : y;
		double v = h < 4 ? y : x;

		return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
	}

	public double noise(double x, double y) {
		int X = (int) Math.floor(x) & 255;
		int Y = (int) Math.floor(y) & 255;

		double xf = x - Math.floor(x);
		double yf = y - Math.floor(y);

		double u = fade(xf);
		double v = fade(yf);

		int aa = permutation[permutation[X] + Y];
		int ab = permutation[permutation[X] + Y + 1];
		int ba = permutation[permutation[X + 1] + Y];
		int bb = permutation[permutation[X + 1] + Y + 1];

		double x1 = lerp(grad(aa, xf, yf), grad(ba, xf - 1, yf), u);
		double x2 = lerp(grad(ab, xf, yf - 1), grad(bb, xf - 1, yf - 1), u);

		return lerp(x1, x2, v);
	}

	public double octaveNoise(double x, double y, int octaves, double persistence) {
		double total = 0;
		double frequency = 1;
		double amplitude = 1;
		double maxValue = 0;

		for (int i = 0; i < octaves; i++) {
			total += noise(x * frequency, y * frequency) * amplitude;
			maxValue += amplitude;

			amplitude *= persistence;
			frequency *= 2;
		}

		return total / maxValue;
	}
}
