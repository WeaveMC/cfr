package org.benf.cfr.reader.entityfactories;

import org.benf.cfr.reader.util.KnowsRawName;
import org.benf.cfr.reader.util.KnowsRawSize;
import org.benf.cfr.reader.util.functors.UnaryFunction;
import org.benf.cfr.reader.util.bytestream.ByteData;
import org.benf.cfr.reader.util.bytestream.OffsettingByteData;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: lee
 * Date: 16/04/2011
 * Time: 15:00
 * To change this template use File | Settings | File Templates.
 */
public class ContiguousEntityFactory {

    public static<X extends KnowsRawSize> long build(final ByteData raw, short count, List<X> tgt, UnaryFunction<ByteData, X> func)
    {
        OffsettingByteData data = raw.getOffsettingOffsetData(0);
        for (short x=0;x<count;++x)
        {
            X tmp = func.invoke(data);
            tgt.add(tmp);
            data.advance(tmp.getRawByteLength());
        }
        return data.getOffset();
    }

    public static<X> long buildSized(final ByteData raw, short count, int itemLength, List<X> tgt, UnaryFunction<ByteData, X> func)
    {
        OffsettingByteData data = raw.getOffsettingOffsetData(0);
        for (short x=0;x<count;++x)
        {
            X tmp = func.invoke(data);
            tgt.add(tmp);
            data.advance(itemLength);
        }
        return data.getOffset(); // will be count * itemLength, of course....
    }

    public static<X extends KnowsRawName> Map<String, X> addToMap(Map<String, X> tgt, List<X> source)
    {
        for (X item : source)
        {
            tgt.put(item.getRawName(), item);
        }
        return tgt;
    }
}
