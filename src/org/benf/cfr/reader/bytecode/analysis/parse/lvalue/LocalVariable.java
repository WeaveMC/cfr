package org.benf.cfr.reader.bytecode.analysis.parse.lvalue;

import org.benf.cfr.reader.bytecode.analysis.parse.Expression;
import org.benf.cfr.reader.bytecode.analysis.parse.LValue;
import org.benf.cfr.reader.bytecode.analysis.parse.StatementContainer;
import org.benf.cfr.reader.bytecode.analysis.parse.expression.LocalValue;
import org.benf.cfr.reader.bytecode.analysis.parse.utils.LValueCollector;
import org.benf.cfr.reader.util.ConfusedCFRException;

/**
 * Created by IntelliJ IDEA.
 * User: lee
 * Date: 22/03/2012
 * Time: 18:28
 * To change this template use File | Settings | File Templates.
 */
public class LocalVariable implements LValue {
    private final LocalValue localValue;
    
    public LocalVariable(LocalValue localValue) {
        this.localValue = localValue;
    }

    @Override
    public int getNumberOfCreators() {
        throw new ConfusedCFRException("NYI");
    }
    
    @Override
    public String toString() {
        return localValue.toString();
    }

    @Override
    public void determineLValueEquivalence(Expression assignedTo, StatementContainer statementContainer, LValueCollector lValueCollector) {
    }

    @Override
    public LValue replaceSingleUsageLValues(LValueCollector lValueCollector) {
        return this;
    }
}
