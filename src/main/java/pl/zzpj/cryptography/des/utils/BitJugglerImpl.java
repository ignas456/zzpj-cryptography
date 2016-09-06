package pl.zzpj.cryptography.des.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.zzpj.cryptography.des.utils.interfaces.ArrayUtils;
import pl.zzpj.cryptography.des.utils.interfaces.BitJuggler;

@Service
public class BitJugglerImpl implements BitJuggler {

	private static final int BITS_IN_BYTE = 8;
	
	private ArrayUtils arrayUtils;
	
	@Autowired
	public BitJugglerImpl(ArrayUtils arrayUtils) {
		this.arrayUtils = arrayUtils;
	}
	
	public byte[] xorArrays(byte[] source1, byte[] source2) {
		if (source1 == null || source2 == null) {
			throw new IllegalArgumentException("Source array is null");
		}
		
		if (source1.length > source2.length) {
			source2 = arrayUtils.extendArraySize(source2, source1.length);

		}
		
		if (source1.length < source2.length) {
			source1 = arrayUtils.extendArraySize(source1, source2.length);
		}
		
		byte[] result = new byte[source1.length];
		for (int i = 0; i < source1.length; i++) {
			result[i] = (byte) (source1[i] ^ source2[i]);
		}

		return result;
	}

	public int getBit(byte[] source, int position) {
		int bytePosition = position / BITS_IN_BYTE;
		int bitPositionInByte = position % BITS_IN_BYTE;

		byte trackedByte = source[bytePosition];
		int resultBit = trackedByte >> (BITS_IN_BYTE - (bitPositionInByte + 1)) & 0x0001;

		return resultBit;
	}

	public byte[] getBits(byte[] source, int startPosition, int bitsNumber) {
		int newByteArraySize = ((bitsNumber - 1) / BITS_IN_BYTE) + 1;
		byte[] result = new byte[newByteArraySize];

		for (int i = 0; i < bitsNumber; i++) {
			int selectedBit = getBit(source, startPosition + i);
			setBit(result, i, selectedBit);
		}

		return result;
	}

	public void setBit(byte[] destination, int position, int value) {
		int bytePosition = (position) / BITS_IN_BYTE;
		byte changedByte = destination[bytePosition];

		if (1 == value) {
			changedByte |= 1 << (BITS_IN_BYTE - ((position % BITS_IN_BYTE) + 1));
		} else {
			changedByte &= ~(1 << (BITS_IN_BYTE - ((position % BITS_IN_BYTE) + 1)));
		}

		destination[bytePosition] = changedByte;
	}

	public void setBits(byte[] destination, int destStartPosition, byte[] source, int srcStartPosition, int length) {
		for (int i = destStartPosition, j = srcStartPosition; i < destStartPosition + length; i++, j++) {
			setBit(destination, i, getBit(source, j));
		}
	}

	public byte[] rotateSelectedBitsLeft(byte[] source, int bitsNumber, int step) {
		int bytesNumber = (bitsNumber - 1) / BITS_IN_BYTE + 1;
		byte[] result = new byte[bytesNumber];
		for (int i = 0; i < bitsNumber; i++) {
			int selectedBit = getBit(source, (i + step) % bitsNumber);
			setBit(result, i, selectedBit);
		}
		return result;
	}

	public byte[] separateBits(byte[] source, int lenght) {
		int newByteArraySize = (BITS_IN_BYTE * source.length - 1) / lenght + 1;
		byte[] result = new byte[newByteArraySize];
		for (int i = 0; i < newByteArraySize; i++) {
			for (int j = 0; j < lenght; j++) {
				int selectedBit = getBit(source, lenght * i + j);
				setBit(result, BITS_IN_BYTE * i + j, selectedBit);
			}
		}
		return result;
	}

	public byte[] concatBitSeries(byte[] firstSeries, int firstLenght, byte[] secondSeries, int secondLenght) {
		int newByteArraySize = (firstLenght + secondLenght - 1) / BITS_IN_BYTE + 1;
		byte[] result = new byte[newByteArraySize];
		int j = 0;

		for (int i = 0; i < firstLenght; i++) {
			int selectedBit = getBit(firstSeries, i);
			setBit(result, j, selectedBit);
			j++;
		}
		for (int i = 0; i < secondLenght; i++) {
			int selectedBit = getBit(secondSeries, i);
			setBit(result, j, selectedBit);
			j++;
		}
		
		return result;
	}

}
