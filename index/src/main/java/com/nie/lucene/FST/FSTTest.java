package com.nie.lucene.FST;

import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.IntsRef;
import org.apache.lucene.util.IntsRefBuilder;
import org.apache.lucene.util.fst.Builder;
import org.apache.lucene.util.fst.FST;
import org.apache.lucene.util.fst.PositiveIntOutputs;
import org.apache.lucene.util.fst.Util;

/**
 * @author zhaochengye
 * @date 2019-05-08 18:03
 */
public class FSTTest {

    public static void main(String[] args) {
        try {
            String inputValues[] = {"cat", "deep", "do", "dog", "dogs"};
            long outputValues[] = {5, 7, 17, 18, 21};
            PositiveIntOutputs outputs = PositiveIntOutputs.getSingleton();
            Builder<Long> builder = new Builder<Long>(FST.INPUT_TYPE.BYTE1, outputs);
            BytesRef scratchBytes = new BytesRef();
            IntsRefBuilder intsRefBuilder = new IntsRefBuilder();
            IntsRef scratchInts = new IntsRef();
            for (int i = 0; i < inputValues.length; i++) {
                builder.add(Util.toIntsRef(new BytesRef(inputValues[i].getBytes()), intsRefBuilder), outputValues[i]);
            }
            FST<Long> fst = builder.finish();
            Long value = Util.get(fst, new BytesRef("dog"));
            System.out.println(value); // 18
        } catch (Exception e) {
            ;
        }
    }


}
