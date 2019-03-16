package org.zaproxy.zap.extension.spiderDSStore.parser;

import org.junit.Assert;
import org.junit.Test;

public class DsStoreOffsetCalculatorUnitTests {

    int correctIntRootBlockStartOffset = 8192;
    Byte[] correctByteArrayRootBlockStartOffset = new Byte[]{0x00, 0x00, 0x20, 0x00};
    int excpectedIntRootBlockStartOffset = correctIntRootBlockStartOffset - 4;
    int negativeIntOffset = -55;


    int correctRootBlockssize = 2048;
    int expctedRootBlockEndOffset = excpectedIntRootBlockStartOffset+correctRootBlockssize;
    // Todo: Dependency Mocking with Exceptions


    // Calculate Offset with Int Input
    @Test
    public void DsStoreOffsetCalculation_WithCorrectInt_Unittest(){

        // given
        int offsetToCalculate = this.correctIntRootBlockStartOffset;
        int excpectedOffsetSite = this.excpectedIntRootBlockStartOffset;
        // When
        int offsetFromFunction = DsStoreOffsetCalculator.calculateOffset(offsetToCalculate);
        // Then
        Assert.assertEquals(offsetFromFunction,excpectedOffsetSite);
    }

    @Test(expected = IllegalArgumentException.class)
    public void DsStoreOffsetCalculation_WithNegativeInt_Unittest(){
        // given
        int offsetToCalculate = this.negativeIntOffset;
        // when
        int offsetFromFunction = DsStoreOffsetCalculator.calculateOffset(offsetToCalculate);

    }

    @Test(expected = IllegalArgumentException.class)
    public void DsStoreOffsetCalculation_WithZeroInt_Unittest(){
        // given
        int offsetToCalculate = 0;
        // when
        int offsetFromFunction = DsStoreOffsetCalculator.calculateOffset(offsetToCalculate);
    }


    // Calculate Offset with ByteArray as Input
    @Test
    public void DsStoreOffsetCalculation_WithCorrectByteArray_Unittest(){
        //given
        Byte[] offsetToCalculate = this.correctByteArrayRootBlockStartOffset;
        int expectedOffset = this.excpectedIntRootBlockStartOffset;
        // when
        int offsetFromFunction = DsStoreOffsetCalculator.calculateOffset(offsetToCalculate);
        // then
        Assert.assertEquals(expectedOffset,offsetFromFunction);
    }

    @Test(expected = IllegalArgumentException.class)
    public void DsStoreOffsetCalculation_withNullValueContainingByteArray_Unittest(){
        // given
        Byte[] offsetToCalculate =  this.correctByteArrayRootBlockStartOffset;
        offsetToCalculate[0]=null;
        // when
        int offsetFromFunction = DsStoreOffsetCalculator.calculateOffset(offsetToCalculate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void DsStoreOffsetCalcuclation_WithNullByteArray_Unittest(){
        // given
        Byte[] offsetToCalculate = null;
        // when
        int offsetFromFunction = DsStoreOffsetCalculator.calculateOffset(offsetToCalculate);
    }

    @Test
    public void calculateRootBlockBoundries_withCorrectInput_Unittests(){
        // given
        int offsetInput = this.correctIntRootBlockStartOffset;
        int rootBlocksize = this.correctRootBlockssize;
        // when
        int[] rootBlockBoundries = DsStoreOffsetCalculator.calculateRootBlock(offsetInput, rootBlocksize);
        // then
        Assert.assertEquals(rootBlockBoundries.length,2);
        Assert.assertEquals(rootBlockBoundries[0],this.excpectedIntRootBlockStartOffset);
        Assert.assertEquals(rootBlockBoundries[1],this.expctedRootBlockEndOffset);
    }

    @Test
    public void calculateRootBlockBoundries_withInvalidSize_Unittests(){
        // given
        int offsetInput = this.correctIntRootBlockStartOffset;

        // when
        // then
    }

}
