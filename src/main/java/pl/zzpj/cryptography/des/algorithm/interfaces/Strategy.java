package pl.zzpj.cryptography.des.algorithm.interfaces;

public interface Strategy {

	public byte[] performFFunction(byte[] input, int iteration);
	
	public byte[][] removeUnnecessaryBytes(byte[][] source);
	
}
