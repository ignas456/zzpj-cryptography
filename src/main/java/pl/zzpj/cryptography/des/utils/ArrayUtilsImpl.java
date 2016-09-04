package pl.zzpj.cryptography.des.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import pl.zzpj.cryptography.des.utils.interfaces.ArrayUtils;

@Service
public class ArrayUtilsImpl implements ArrayUtils {

	private final static int BLOCK_LENGTH = 8;
	
	public byte[] extendArraySize(byte[] array, int newLength) {
		if (newLength < 0)
			throw new IllegalArgumentException("newLength is negative number");

		byte[] result = new byte[newLength];
		System.arraycopy(array, 0, result, 0, array.length);

		return result;
	}

	public byte[] transformToOneDimensionArray(byte[][] source) {
		if (source == null) 
			throw new IllegalArgumentException("source is null");
		
		List<Byte> byteList = change2DByteArrayToByteList(source);

		return prefromByteListUnboxingToByteArray(byteList);
	}
	
	public byte[][] transformToTwoDimensionsArray(byte[] source) {
		byte[][] result = new byte[(source.length - 1) / BLOCK_LENGTH + 1][BLOCK_LENGTH];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = Arrays.copyOfRange(source, i * BLOCK_LENGTH, (i + 1) * BLOCK_LENGTH);
		}
		
		return result;
	}

	private List<Byte> change2DByteArrayToByteList(byte[][] source) {
		List<Byte> result = new ArrayList<Byte>();

		for (byte[] byteArray : source) {
			for (byte b : byteArray) {
				result.add(b);
			}
		}
		
		return result;
	}
	
	private byte[] prefromByteListUnboxingToByteArray(List<Byte> source) {
		byte[] result = new byte[source.size()];

		for (int i = 0; i < result.length; i++) {
			result[i] = source.get(i);
		}
		
		return result;
	}
}
