package org.benf.cfr.reader.bytecode.analysis.parse.expression;

import com.sun.org.apache.xpath.internal.operations.Variable;
import org.benf.cfr.reader.bytecode.analysis.parse.Expression;
import org.benf.cfr.reader.bytecode.analysis.parse.utils.LValueCollector;
import org.benf.cfr.reader.bytecode.analysis.parse.utils.VariableNamer;

/**
 * Created by IntelliJ IDEA.
 * User: lee
 * Date: 15/03/2012
 * Time: 18:19
 * To change this template use File | Settings | File Templates.
 */
public class LocalValueConstant implements LocalValue {
    private final String name;

    public LocalValueConstant(long index, VariableNamer variableNamer, int originalRawOffset) {
        this.name = variableNamer.getName(originalRawOffset, index);
    }
    
    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean isSimple() {
        return true;
    }

    @Override
    public Expression replaceSingleUsageLValues(LValueCollector lValueCollector) {
        return this;
    }
}
