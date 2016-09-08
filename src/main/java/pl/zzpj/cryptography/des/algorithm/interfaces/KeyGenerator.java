package pl.zzpj.cryptography.des.algorithm.interfaces;

public interface KeyGenerator {
	public byte[][] generateSubKeys(byte[] key);
}
