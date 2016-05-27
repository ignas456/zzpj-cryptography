package pl.zzpj.cryptography.des.algorithm;

import java.util.Arrays;

import pl.zzpj.cryptography.des.utils.ArrayUtils;
import pl.zzpj.cryptography.des.utils.BitJuggler;

public class DES {
  
  private byte[][] subKeys;
  
  public DES(byte[] key) {
    this.subKeys = this.generateSubKeys(key);
  }

  public byte[] enctrypt(byte[] src, boolean isDecrypt) {
    byte[][] srcIn2D = ArrayUtils.transformToTwoDimensionsArray(src);
    
    byte[][] tmp = new byte[srcIn2D.length][8];
    for (int i = 0; i < tmp.length; i++) {
      tmp[i] = this.encrypt8ByteBlock(srcIn2D[i], isDecrypt);
    }
    
    if (isDecrypt) {
      int counter = 0;
      for(int i = tmp.length - 1; i >= 0; i--) {
        for (int j = tmp[i].length - 1; j >= 0; j--) {
          if(tmp[i][j] == 0) {
            counter++;
          } else {
            break;
          }
        }
      }
      if (counter > 7) counter = 0;
      tmp[tmp.length - 1] = Arrays.copyOfRange(tmp[tmp.length - 1], 0, 8 - counter);
    }

    return ArrayUtils.transformToOneDimensionArray(tmp);
  }
  
  public byte[] encrypt8ByteBlock(byte[] block, boolean isDecrypt) {
    byte[] permutedOrginalBlock = this.permute(block, DESPermutationTables.IP);
    
    byte[] left = BitJuggler.getBits(permutedOrginalBlock, 0, 32);
    byte[] right = BitJuggler.getBits(permutedOrginalBlock, 32, 32);
    
    for (int i = 0; i < 16; i++) {
      byte[] tmpR = right;
      
      if (isDecrypt) {
        right = this.performFFunction(right, this.subKeys[15 - i]);
      } else {
        right = this.performFFunction(right, this.subKeys[i]);
      }
      
      right = BitJuggler.xorArrays(left, right);
      left = tmpR;
      
    }
    
    byte[] result = BitJuggler.concatBitSeries(right, 32, left, 32);    
    result = this.permute(result, DESPermutationTables.FP);
    
    return result;
  }
  
  private byte[] permute(byte[] input, byte[] permutationTable) {
    byte[] result = new byte[(permutationTable.length - 1) / 8 + 1];
    for (int i = 0; i < permutationTable.length; i++) {
      int value = BitJuggler.getBit(input, permutationTable[i] - 1);
      BitJuggler.setBit(result, i, value);
    }
    
    return result;
  }
  
  private byte[] useSBoxes(byte[] input) {
    byte[] inputAfterSeparation = BitJuggler.separateBits(input, 6);      // length = 8
    byte[] rowAndColumn = {0, 0}; // 00000000 00000000 // 0-7 row bits, 8-15 column bits
    byte[] result = new byte[4];
    
    for (int i = 0, j = 0; i < inputAfterSeparation.length; i++, j += 4) {
      byte[] currentByte = { inputAfterSeparation[i] };
      
      // Row
      BitJuggler.setBit(rowAndColumn, 6, BitJuggler.getBit(currentByte, 0));
      BitJuggler.setBit(rowAndColumn, 7, BitJuggler.getBit(currentByte, 5));
      
      // Column
      BitJuggler.setBits(rowAndColumn, 12, currentByte, 1, 4);
      
      byte row = rowAndColumn[0];
      byte column = rowAndColumn[1];
      byte[] sBoxValue = { DESPermutationTables.S_BOXES[i][row][column] };
      
      BitJuggler.setBits(result, j, sBoxValue, 4, 4);
      
    }
    
    return result;
  }
  
  private byte[] performFFunction(byte[] input, byte[] key) {
    byte[] result;
    result = this.permute(input, DESPermutationTables.E);
    result = BitJuggler.xorArrays(result, key);
    result = this.useSBoxes(result);
    result = this.permute(result, DESPermutationTables.P);
    
    return result;
  }
  
  public byte[][] generateSubKeys(byte[] key) {
    byte[][] subKeys = new byte[16][];
    byte[] permutedKey = this.permute(key, DESPermutationTables.PC1);
    
    byte[] C0 = BitJuggler.getBits(permutedKey, 0, 28);
    byte[] D0 = BitJuggler.getBits(permutedKey, 28, 28);
    
    for (int i = 0; i < 16; i++) {
      C0 = BitJuggler.rotateSelectedBitsLeft(C0, 28, DESPermutationTables.SUBKEY_ROTATIONS[i]);
      D0 = BitJuggler.rotateSelectedBitsLeft(D0, 28, DESPermutationTables.SUBKEY_ROTATIONS[i]);
      
      byte[] C0AndD0Together = BitJuggler.concatBitSeries(C0, 28, D0, 28);
      C0AndD0Together = this.permute(C0AndD0Together, DESPermutationTables.PC2);
      subKeys[i] = C0AndD0Together; 
    }
    
    return subKeys;
  }
  
}
